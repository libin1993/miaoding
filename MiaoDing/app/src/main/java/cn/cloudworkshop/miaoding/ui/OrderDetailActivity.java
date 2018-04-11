package cn.cloudworkshop.miaoding.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.OrderDetailsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.PayOrderUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/27 17:14
 * Email：1993911441@qq.com
 * Describe：订单详情
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_status_order)
    TextView tvOrderStatus;
    @BindView(R.id.rv_order_details)
    RecyclerView rvOrderDetails;
    @BindView(R.id.tv_order_pay_money)
    TextView tvOrderPayMoney;
    @BindView(R.id.tv_order_cancel)
    TextView tvOrderCancel;
    @BindView(R.id.tv_order_user_name)
    TextView tvOrderUserName;
    @BindView(R.id.tv_order_user_phone)
    TextView tvOrderUserPhone;
    @BindView(R.id.tv_order_user_address)
    TextView tvOrderUserAddress;
    @BindView(R.id.tv_order_pay_style)
    TextView tvOrderPayStyle;
    @BindView(R.id.tv_order_total_money)
    TextView tvOrderTotalMoney;
    @BindView(R.id.tv_order_need_pay)
    TextView tvOrderNeedPay;
    @BindView(R.id.tv_order_discount)
    TextView tvOrderDiscount;
    @BindView(R.id.tv_order_after)
    TextView tvOrderAfter;
    @BindView(R.id.ll_order_address)
    LinearLayout llOrderAddress;
    //订单id
    private String orderId;
    private OrderDetailsBean orderBean;
    //倒计时时间
    private int recLen;
    Timer timer = new Timer();

    private MyTimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        getData();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 获取网络数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.ORDER_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("id", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        orderBean = GsonUtils.jsonToBean(response, OrderDetailsBean.class);
                        if (orderBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvOrderTime.setText(orderBean.getData().getC_time());
        recLen = orderBean.getData().getLast_time() + 1;
        switch (orderBean.getData().getStatus()) {
            case 1:
                if (task == null) {
                    task = new MyTimerTask();
                    timer.schedule(task, 1000, 1000);
                }

                tvPayTime.setTextColor(ContextCompat.getColor(this, R.color.dark_red));
                tvOrderStatus.setText("待付款");
                tvOrderCancel.setText("取消订单");
                tvOrderPayMoney.setText("付款");
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderPayMoney.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvPayTime.setText(orderBean.getData().getP_time());
                tvOrderStatus.setText("待发货");
                tvOrderCancel.setText("提醒发货");
                tvOrderCancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvPayTime.setText(orderBean.getData().getP_time());
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderAfter.setText("售后服务");
                tvOrderStatus.setText("已发货");
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderPayMoney.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("查看物流");
                tvOrderPayMoney.setText("确认收货");
                break;
            case 4:
                tvPayTime.setText(orderBean.getData().getP_time());
                tvOrderAfter.setVisibility(View.GONE);
                tvOrderAfter.setText("售后服务");
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderStatus.setText("已完成");
                tvOrderCancel.setText("再次购买");
                if (orderBean.getData().getOrder_comment().getId() == 0) {
                    tvOrderPayMoney.setVisibility(View.VISIBLE);
                    tvOrderPayMoney.setText("评价");
                } else {
                    tvOrderPayMoney.setVisibility(View.GONE);
                }
                break;
            case -2:
                tvPayTime.setText("已取消");
                tvOrderStatus.setText("已取消");
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("删除订单");
                break;
        }
        tvOrderNum.setText(orderBean.getData().getOrder_no());

        if (orderBean.getData().getQuick_type() == 2) {
            llOrderAddress.setVisibility(View.GONE);
        } else {
            llOrderAddress.setVisibility(View.VISIBLE);
            tvOrderUserName.setText(orderBean.getData().getName());
            tvOrderUserPhone.setText(orderBean.getData().getPhone());
            tvOrderUserAddress.setText(orderBean.getData().getProvince()
                    + orderBean.getData().getCity()
                    + orderBean.getData().getArea()
                    + orderBean.getData().getAddress());
        }


        float totalMoney = getTotalMoney();
        float payMoney = Float.parseFloat(orderBean.getData().getMoney());

        tvOrderTotalMoney.setText("¥" + DisplayUtils.decimalFormat(totalMoney));

        tvOrderDiscount.setText("¥" + DisplayUtils.decimalFormat(totalMoney - payMoney));

        tvOrderNeedPay.setTypeface(DisplayUtils.setTextType(this));
        tvOrderNeedPay.setText("¥" + DisplayUtils.decimalFormat(payMoney));

        switch (orderBean.getData().getPay_type()) {
            case 0:
                tvOrderPayStyle.setText("待付款");
                break;
            case 1:
                tvOrderPayStyle.setText("支付宝");
                break;
            case 2:
                tvOrderPayStyle.setText("微信");
                break;
        }

        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvOrderDetails.setLayoutManager(linearLayoutManager);
        CommonAdapter<OrderDetailsBean.DataBean.CarListBean> adapter = new CommonAdapter<OrderDetailsBean.DataBean.CarListBean>(this,
                R.layout.listitem_shopping_cart, orderBean.getData().getCar_list()) {
            @Override
            protected void convert(ViewHolder holder, OrderDetailsBean.DataBean.CarListBean carListBean
                    , int position) {
                holder.setVisible(R.id.checkbox_goods_select, false);
                holder.setVisible(R.id.view_cart_divide, true);
                Glide.with(OrderDetailActivity.this)
                        .load(Constant.IMG_HOST + carListBean.getGoods_thumb())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                TextView tvGoodsName = holder.getView(R.id.tv_goods_name);
                tvGoodsName.setText(carListBean.getGoods_name());
                tvGoodsName.setTypeface(DisplayUtils.setTextType(mContext));
                switch (carListBean.getGoods_type()) {
                    case 2:
                        holder.setText(R.id.tv_goods_content, carListBean.getSize_content());
                        break;
                    default:
                        holder.setText(R.id.tv_goods_content, "定制款");
                        break;
                }
                holder.setText(R.id.tv_goods_price, "¥" + carListBean.getPrice());
                holder.setText(R.id.tv_goods_count, "x" + carListBean.getNum());
                holder.setVisible(R.id.view_cart, true);
            }
        };
        rvOrderDetails.setAdapter(adapter);
    }

    /**
     * @return 订单总金额
     */
    private float getTotalMoney() {
        float totalMoney = 0;

        for (int i = 0; i < orderBean.getData().getCar_list().size(); i++) {
            totalMoney += Float.parseFloat(orderBean.getData().getCar_list().get(i).getPrice()) *
                    orderBean.getData().getCar_list().get(i).getNum();
        }

        return totalMoney;
    }

    private void getData() {
        tvHeaderTitle.setText("订单详情");

        Intent intent = getIntent();
        orderId = intent.getStringExtra("id");

    }

    @OnClick({R.id.img_header_back, R.id.tv_order_pay_money, R.id.tv_order_cancel, R.id.tv_order_after})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                }
                finish();
                break;
            case R.id.tv_order_pay_money:
                payOrder();
                break;
            case R.id.tv_order_cancel:
                controlOrder();
                break;
            case R.id.tv_order_after:
                afterSale();
                break;
        }
    }

    //定时器
    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    if (recLen > 0) {
                        tvPayTime.setTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.dark_red));
                        tvPayTime.setText("请在 " + recLen / 60 + "分" + recLen % 60 + "秒 内完成支付，超时订单将自动取消");
                    } else {
                        if (timer != null && task != null) {
                            timer.cancel();
                            task.cancel();
                        }
                        finish();
                        ToastUtils.showToast(OrderDetailActivity.this, "订单已过期");
                    }
                }
            });
        }
    }

    /**
     * 售后服务
     */
    private void afterSale() {
        Intent intent1 = new Intent(this, AfterSalesActivity.class);
        intent1.putExtra("order_id", orderId);
        startActivity(intent1);
    }

    private void payOrder() {
        switch (orderBean.getData().getStatus()) {
            case 1:
                PayOrderUtils payOrderUtil = new PayOrderUtils(this, orderBean.getData().getMoney(), orderId);
                payOrderUtil.payMoney();
                break;
            case 3:
                confirmReceive(orderId);
                break;
            case 4:
                //订单评价
                Intent intent = new Intent(this, EvaluateActivity.class);
                intent.putExtra("order_id", orderId);
                intent.putExtra("cart_id", orderBean.getData().getCar_list().get(0).getId() + "");
                intent.putExtra("goods_id", orderBean.getData().getCar_list().get(0).getGoods_id() + "");
                intent.putExtra("goods_img", orderBean.getData().getCar_list().get(0).getGoods_thumb());
                intent.putExtra("goods_name", orderBean.getData().getCar_list().get(0).getGoods_name());

                switch (orderBean.getData().getCar_list().get(0).getGoods_type()) {
                    case 2:
                        intent.putExtra("goods_type", orderBean.getData().getCar_list().get(0).getSize_content());
                        break;
                    default:
                        intent.putExtra("goods_type", "定制款");
                        break;
                }

                startActivity(intent);
                break;
        }
    }

    private void controlOrder() {
        switch (orderBean.getData().getStatus()) {
            case 1:
                cancelOrder();
                break;
            case 2:
                ToastUtils.showToast(this, "已提醒商家发货，请耐心等待");
                break;
            case 3:
                Intent intent = new Intent(this, LogisticsActivity.class);
                intent.putExtra("number", orderBean.getData().getEms_no());
                intent.putExtra("company", orderBean.getData().getEms_com());
                intent.putExtra("company_name", orderBean.getData().getEms_com_name());
                intent.putExtra("img_url", orderBean.getData().getCar_list().get(0).getGoods_thumb());
                startActivity(intent);
                break;
            case 4:
                buyAgain(orderId);
                break;
            case -2:
                deleteOrder();
                break;
        }
    }

    /**
     * @param id 再次购买
     */
    private void buyAgain(String id) {
        OkHttpUtils.post()
                .url(Constant.BUY_AGAIN)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("orderid", id)
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
                            String msg = jsonObject.getString("msg");
                            if (code == 1) {
                                String cartId = jsonObject.getString("car_id");
                                Intent intent = new Intent(OrderDetailActivity.this, ConfirmOrderActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cart_id", cartId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(OrderDetailActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 确认收货
     */
    private void confirmReceive(final String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("确认收货");
        dialog.setMessage("您要确认收货吗？");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CONFIRM_RECEIVE)
                        .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                        .addParams("order_id", String.valueOf(id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {


                            }

                            @Override
                            public void onResponse(String response, int id) {
                                finish();
                                ToastUtils.showToast(OrderDetailActivity.this, "交易完成，祝您购物愉快！");
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }


    /**
     * 删除订单
     */
    private void deleteOrder() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("删除订单");
        dialog.setMessage("您确定要删除订单吗？");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.DELETE_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                        .addParams("order_id", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                finish();
                                ToastUtils.showToast(OrderDetailActivity.this, "删除成功");
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }


    /**
     * 取消订单
     */
    private void cancelOrder() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("取消订单");
        dialog.setMessage("您确定要取消订单吗？");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CANCEL_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(OrderDetailActivity.this, "token"))
                        .addParams("order_id", orderId)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                MobclickAgent.onEvent(OrderDetailActivity.this, "cancel_order");
                                finish();
                                ToastUtils.showToast(OrderDetailActivity.this, "取消成功");
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (timer != null && task != null) {
                timer.cancel();
                task.cancel();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
