package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.GuideBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;


/**
 * Author：Libin on 2016/11/24 12:18
 * Email：1993911441@qq.com
 * Describe：预约结果、支付结果、入驻结果
 */
public class AppointmentActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_appoint_result)
    TextView tvAppointResult;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.tv_go_back)
    TextView tvGoBack;
    @BindView(R.id.tv_check_order)
    TextView tvCheckOrder;
    @BindView(R.id.img_pay_result)
    ImageView imgPayResult;
    //支付结果
    private String type;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        switch (type) {
            case "appoint_measure":
                tvHeaderTitle.setText("预约详情");
                tvAppointResult.setTextSize(15);

                OkHttpUtils.get()
                        .url(Constant.APPOINTMENT_STATUS)
                        .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    if (jsonObject1 != null) {
                                        int status = jsonObject1.getInt("status");
                                        int time = jsonObject1.getInt("sm_time");
                                        switch (status) {
                                            case 1:
                                            case 2:
                                                tvAppointResult.setText("当前状态：预约成功，等待客服受理");
                                                break;
                                            case 3:
                                            case 4:
                                                tvAppointResult.setText("当前状态：派单成功，等待上门量体\n"
                                                        + "上门时间：" + DateUtils.getDate("yyyy.MM.dd HH:mm", time));
                                                break;
                                            case -1:
                                                tvAppointResult.setText("当前状态：已取消");
                                                break;
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                break;
            case "apply_join":
                tvHeaderTitle.setText("申请详情");
                tvAppointResult.setText("申请成功");
                imgPayResult.setImageResource(R.mipmap.icon_appoint_success);
                break;
            case "pay_success":
                tvHeaderTitle.setText("支付成功");
                tvAppointResult.setText("支付成功");
                imgPayResult.setImageResource(R.mipmap.icon_appoint_success);
                tvCheckOrder.setVisibility(View.VISIBLE);
                tvGoBack.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
                tvGoBack.setBackgroundResource(R.drawable.text_white_2dp);
                shareCoupon();
                break;
            case "pay_fail":
                tvHeaderTitle.setText("支付失败");
                tvAppointResult.setText("支付失败");
                tvCheckOrder.setVisibility(View.VISIBLE);
                imgPayResult.setImageResource(R.mipmap.icon_appoint_fail);
                tvGoBack.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
                tvGoBack.setBackgroundResource(R.drawable.text_white_2dp);
                break;
        }
    }

    /**
     * 支付成功分享红包
     */
    private void shareCoupon() {

        OkHttpUtils.get()
                .url(Constant.GUIDE_IMG)
                .addParams("id", "6")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final GuideBean guideBean = GsonUtils.jsonToBean(response, GuideBean.class);
                        if (guideBean.getData() != null && guideBean.getData().getImg_urls() != null
                                && guideBean.getData().getImg_urls().size() > 0) {

                            View popupView = getLayoutInflater().inflate(R.layout.ppw_coupon, null);
                            mPopupWindow = new PopupWindow(popupView,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            mPopupWindow.setTouchable(true);
                            mPopupWindow.setFocusable(true);
                            mPopupWindow.setOutsideTouchable(true);
                            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                            if (!isFinishing()) {
                                mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                            }


                            View viewShare = popupView.findViewById(R.id.view_share);
                            View viewCancel = popupView.findViewById(R.id.view_close);
                            ImageView imgCoupon = (ImageView) popupView.findViewById(R.id.img_coupon);

                            Glide.with(AppointmentActivity.this)
                                    .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(0))
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(imgCoupon);
                            viewShare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShareUtils.showShare(AppointmentActivity.this,
                                            Constant.IMG_HOST + guideBean.getData().getImg_urls().get(0),
                                            "【妙定APP】送你1000元定制红包，手快有，手慢无！",
                                            "做你不敢想的黑科技服饰定制，雅痞还是绅士，成熟还是睿智，先从一件衬衣开始。",
                                            Constant.SHARE_COUPON + "?pay_ids=" + MyApplication.payId
                                                    + "&uid=" + SharedPreferencesUtils.getStr(AppointmentActivity.this, "uid"));
                                }
                            });
                            viewCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPopupWindow.dismiss();
                                }
                            });
                        }
                    }
                });

    }

    private void getData() {
        type = getIntent().getStringExtra("type");
    }

    @OnClick({R.id.img_header_back, R.id.tv_header_next, R.id.tv_go_back, R.id.tv_check_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_header_next:
//                Intent intent = new Intent(this, UserAgreementActivity.class);
//                intent.putExtra("content", "measure");
//                startActivity(intent);
                break;
            case R.id.tv_go_back:
                if (type.equals("pay_success") || type.equals("pay_fail")) {
                    Intent intent2 = new Intent(this, MainActivity.class);
                    intent2.putExtra("page", 0);
                    startActivity(intent2);
                }
                finish();

                break;
            case R.id.tv_check_order:
                Intent intent1 = new Intent(this, OrderDetailActivity.class);
                intent1.putExtra("id", getIntent().getStringExtra("order_id"));
                startActivity(intent1);
                finish();
                break;
        }
    }

}

