package cn.cloudworkshop.miaoding.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.bean.PayResultBean;
import cn.cloudworkshop.miaoding.bean.PayTypeBean;
import cn.cloudworkshop.miaoding.bean.WeChatPayBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.AppointmentActivity;
import cn.cloudworkshop.miaoding.ui.CustomizeResultActivity;
import cn.cloudworkshop.miaoding.ui.CustomizeActivity;
import cn.cloudworkshop.miaoding.ui.EmbroideryActivity;
import cn.cloudworkshop.miaoding.ui.OrderActivity;
import cn.cloudworkshop.miaoding.ui.ShoppingCartActivity;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/31 14:27
 * Email：1993911441@qq.com
 * Describe：支付
 */
public class PayOrderUtils {
    Context context;
    private String money;
    private String orderId;
    private PopupWindow mPopupWindow;
    //0：支付宝 1：微信
    private int index = 0;
    private IWXAPI api;
    //是否确认购买
    private boolean isConfirmBuy;

    private static final int SDK_PAY_FLAG = 1;


    public PayOrderUtils(Context context, String money, String orderId) {
        this.context = context;
        this.money = money;
        this.orderId = orderId;
        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID);
        MyApplication.orderId = orderId;

    }

    /**
     * 支付宝支付回调
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResultBean payResultBean = new PayResultBean((Map<String, String>) msg.obj);
                    String resultInfo = payResultBean.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResultBean.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.showToast(context, context.getString(R.string.pay_success));
                        MobclickAgent.onEvent(context, "pay");


                        Intent intent = new Intent(context, AppointmentActivity.class);
                        intent.putExtra("type", "pay_success");
                        intent.putExtra("order_id", orderId.split(",")[0]);
                        context.startActivity(intent);

                        ((Activity) context).finish();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showToast(context, context.getString(R.string.pay_fail));

                        Intent intent = new Intent(context, AppointmentActivity.class);
                        intent.putExtra("type", "pay_fail");
                        intent.putExtra("order_id", orderId.split(",")[0]);
                        context.startActivity(intent);
                        ((Activity) context).finish();


                    }
                    break;
                }
            }
            return false;
        }
    });

    /**
     * 支付
     */
    public void payMoney() {
        final View popupView = ((Activity) context).getLayoutInflater().inflate(R.layout.ppw_pay_order, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        if (!((Activity) context).isFinishing()) {
            mPopupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
        DisplayUtils.setBackgroundAlpha(context, true);

        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.rv_pay_type);
        TextView tvTotalPrice = (TextView) popupView.findViewById(R.id.tv_pay_price);
        TextView tvConfirmBuy = (TextView) popupView.findViewById(R.id.tv_pay_confirm);

        tvTotalPrice.setText(context.getString(R.string.total_price)+"   ¥" + money);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(context, false);

                EventBus.getDefault().post("pay_result");

                if (!isConfirmBuy) {
                    ToastUtils.showToast(context, context.getString(R.string.cancel_pay));
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("page", 1);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        });


        final List<PayTypeBean> payList = new ArrayList<>();
        payList.add(new PayTypeBean(R.mipmap.icon_ali_pay, context.getString(R.string.alipay), context.getString(R.string.recommend_alipay)));
        payList.add(new PayTypeBean(R.mipmap.icon_wechat_pay, context.getString(R.string.wechat), context.getString(R.string.recommend_wechat)));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final CommonAdapter<PayTypeBean> adapter = new CommonAdapter<PayTypeBean>(context,
                R.layout.listitem_pay_type, payList) {
            @Override
            protected void convert(ViewHolder holder, PayTypeBean payTypeEntity, int position) {
                holder.setImageResource(R.id.img_pay_type, payList.get(position).img);
                holder.setText(R.id.tv_pay_type, payList.get(position).payType);
                holder.setText(R.id.tv_pay_content, payList.get(position).payContent);
                if (index == position) {
                    ((CheckBox) holder.getView(R.id.checkbox_pay_type)).setChecked(true);
                } else {
                    ((CheckBox) holder.getView(R.id.checkbox_pay_type)).setChecked(false);
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                index = holder.getLayoutPosition();
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        tvConfirmBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isConfirmBuy = true;
                mPopupWindow.dismiss();
                switch (index) {
                    case 0:
                        aliPay();
                        break;
                    case 1:
                        weChatPay();
                        break;
                }
            }
        });
    }

    /**
     * 微信支付
     */
    private void weChatPay() {
        OkHttpUtils.post()
                .url(Constant.WE_CHAT_PAY)
                .addParams("order_id", orderId)
                .addParams("token", SharedPreferencesUtils.getStr(context, "token"))
                .addParams("pay_type", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        WeChatPayBean weChatPay = GsonUtils.jsonToBean(response, WeChatPayBean.class);
                        if (weChatPay.getCode() == 1) {
                            if (!api.isWXAppInstalled()) {
                                ToastUtils.showToast(context, context.getString(R.string.not_install_wechat));
                            }
                            if (!api.isWXAppSupportAPI()) {
                                ToastUtils.showToast(context, context.getString(R.string.not_support_wechat));
                            }
                            MyApplication.payId = weChatPay.getPay_code();
                            PayReq req = new PayReq();
                            req.appId = Constant.APP_ID;
                            req.partnerId = weChatPay.getData().getPartnerid();
                            req.prepayId = weChatPay.getData().getPrepayid();
                            req.nonceStr = weChatPay.getData().getNoncestr();
                            req.timeStamp = weChatPay.getData().getTimestamp() + "";
                            req.packageValue = "Sign=WXPay";
                            req.sign = weChatPay.getData().getSign();
                            api.sendReq(req);
                            ((Activity) context).finish();
                        } else {
                            ToastUtils.showToast(context, weChatPay.getMsg());
                            Intent intent = new Intent(context, OrderActivity.class);
                            intent.putExtra("page", 0);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }
                });
    }


    /**
     * 支付宝支付
     */
    private void aliPay() {
        OkHttpUtils.post()
                .url(Constant.ALI_PAY)
                .addParams("order_id", orderId)
                .addParams("token", SharedPreferencesUtils.getStr(context, "token"))
                .addParams("pay_type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if (code == 1) {
                                final String orderInfo = jsonObject.getString("data");
                                MyApplication.payId = jsonObject.getString("pay_code");
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        PayTask aliPay = new PayTask((Activity) context);
                                        Map<String, String> result = aliPay.payV2(orderInfo, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
