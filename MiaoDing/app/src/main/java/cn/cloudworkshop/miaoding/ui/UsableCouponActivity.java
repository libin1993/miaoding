package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CouponBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/20 17:39
 * Email：1993911441@qq.com
 * Describe：可用优惠券
 */
public class UsableCouponActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_coupon_pwd)
    EditText etInputCode;
    @BindView(R.id.tv_coupon_exchange)
    TextView tvExchange;
    @BindView(R.id.ll_no_coupon)
    LinearLayout llNoCoupon;
    @BindView(R.id.rv_coupon_usable)
    RecyclerView rvCoupon;
    @BindView(R.id.tv_more_coupon)
    TextView tvMore;
    @BindView(R.id.sv_null_coupon)
    ScrollView svNullCoupon;
    @BindView(R.id.img_null_coupon)
    ImageView imgNullCoupon;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;

    private List<CouponBean.DataBean> couponList;
    private String couponRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        tvHeaderTitle.setText(R.string.coupon);
        imgHeaderShare.setVisibility(View.VISIBLE);
        imgHeaderShare.setImageResource(R.mipmap.icon_member_rule);
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.COUPON_RULE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setBackgroundColor(Color.WHITE);
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            couponRule = jsonObject.getString("introduce_img");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        OkHttpUtils.get()
                .url(Constant.MY_COUPON)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("status", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        couponList = new ArrayList<>();
                        CouponBean couponBean = GsonUtils.jsonToBean(response, CouponBean.class);
                        if (couponBean.getData() != null && couponBean.getData().size() > 0) {
                            imgNullCoupon.setVisibility(View.GONE);
                            couponList.addAll(couponBean.getData());
                            initView();
                        } else {
                            imgNullCoupon.setVisibility(View.VISIBLE);
                        }
                    }
                });

        etInputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etInputCode.getText().toString().trim().length() > 0) {
                    tvExchange.setEnabled(true);
                    tvExchange.setBackgroundResource(R.drawable.btn_black_bg);
                } else {
                    tvExchange.setEnabled(false);
                    tvExchange.setBackgroundResource(R.drawable.btn_gray_bg);
                }
            }
        });
    }


    /**
     * 加载视图
     */
    private void initView() {
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvCoupon.setLayoutManager(linearLayoutManager);
        CommonAdapter<CouponBean.DataBean> adapter = new CommonAdapter<CouponBean.DataBean>
                (this, R.layout.listitem_coupon, couponList) {
            @Override
            protected void convert(ViewHolder holder, CouponBean.DataBean dataBean, int position) {

                holder.getView(R.id.ll_coupon_bg).setBackgroundResource(R.mipmap.icon_coupon_available);
                TextView tvMoney = holder.getView(R.id.tv_coupon_money);
                tvMoney.setTypeface(DisplayUtils.setTextType(UsableCouponActivity.this));
                tvMoney.setText("¥" + (int) Float.parseFloat(dataBean.getMoney()));
                holder.setText(R.id.tv_coupon_range, dataBean.getTitle());
                holder.setText(R.id.tv_coupon_discount, dataBean.getSub_title());
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.validity_term)+"：")
                        .append(DateUtils.getDate("yyyy-MM-dd", dataBean.getS_time()))
                        .append(getString(R.string.to))
                        .append(DateUtils.getDate("yyyy-MM-dd", dataBean.getE_time()));
                holder.setText(R.id.tv_coupon_term, sb.toString());

            }
        };

        rvCoupon.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(UsableCouponActivity.this, MainActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    @OnClick({R.id.img_header_back, R.id.tv_coupon_exchange, R.id.tv_more_coupon, R.id.img_load_error
    ,R.id.img_header_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_coupon_exchange:
                exchangeCoupon();
                break;
            case R.id.tv_more_coupon:
                startActivity(new Intent(this, CouponActivity.class));
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.img_header_share:
                if (couponRule != null) {
                    Intent intent = new Intent(this, UserRuleActivity.class);
                    intent.putExtra("title", R.string.use_rule);
                    intent.putExtra("img_url", couponRule);
                    startActivity(intent);
                }
                break;
        }
    }


    /**
     * 兑换优惠券
     */
    private void exchangeCoupon() {

        OkHttpUtils.post()
                .url(Constant.EXCHANGE_COUPON)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("kouling", etInputCode.getText().toString().trim())
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
                                initData();
                            }
                            ToastUtils.showToast(UsableCouponActivity.this, msg);
                            etInputCode.setText("");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}


