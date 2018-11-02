package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.AppManagerUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.InputMoneyFilter;
import cn.cloudworkshop.miaoding.utils.PayOrderUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2018/3/2 13:18
 * Email：1993911441@qq.com
 * Describe：商铺下单
 */
public class StoreOrderActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_store_price)
    EditText etStorePrice;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_store_buy)
    TextView tvStoreBuy;
    //店铺id
    private int shopId;
    //店铺名
    private String shopName;
    //店铺图
    private String shopIcon;
    //实付金额
    private float actualMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        ButterKnife.bind(this);
        tvHeaderTitle.setText(R.string.menswear_customize);
        getData();
        initView();
    }

    private void initView() {
        etStorePrice.setFilters(new InputFilter[]{new InputMoneyFilter()});
        etStorePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    String money = DisplayUtils.decimalFormat(Float.parseFloat(s.toString()));
                    tvTotalMoney.setText("¥" + money);
                }else {
                    tvTotalMoney.setText("");
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", 0);
        shopName = intent.getStringExtra("shop_name");
        shopIcon = intent.getStringExtra("shop_icon");
    }

    @OnClick({R.id.img_header_back, R.id.tv_store_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_store_buy:
                addToCart();
                break;
        }
    }

    private void addToCart() {
        String inputMoney = etStorePrice.getText().toString().trim();
        if (!TextUtils.isEmpty(inputMoney)) {
            Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
            Matcher matcher = pattern.matcher(inputMoney);
            if (matcher.matches()) {
                actualMoney = Float.parseFloat(inputMoney);
                if (actualMoney > 0) {
                    Map<String, String> map = new HashMap<>();
                    map.put("token", SharedPreferencesUtils.getStr(this, "token"));
                    map.put("shop_id", String.valueOf(shopId));
                    map.put("price", DisplayUtils.decimalFormat(actualMoney));
                    map.put("goods_name",shopName);
                    map.put("goods_thumb",shopIcon);
                    OkHttpUtils.post()
                            .url(Constant.STORE_ADD_CART)
                            .params(map)
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
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                        String msg = jsonObject.getString("msg");
                                        //加入购物车成功
                                        if (code == 1) {
                                            String cartId = jsonObject1.getString("car_id");
                                            confirmOrder(cartId);
                                        } else {
                                            ToastUtils.showToast(StoreOrderActivity.this, msg);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    ToastUtils.showToast(this, getString(R.string.please_input_money));
                }
            } else {
                ToastUtils.showToast(this, getString(R.string.incorrect_amount_of_money));
            }

        } else {
            ToastUtils.showToast(this, getString(R.string.please_input_money));
        }


    }

    /**
     * 确认下单
     */
    private void confirmOrder(String cart_id) {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("car_ids", cart_id);
        map.put("quick_type","2");

        OkHttpUtils.post()
                .url(Constant.STORE_ORDER)
                .params(map)
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
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String orderId = jsonObject1.getString("order_id");
                                if (orderId != null) {

                                    ToastUtils.showToast(StoreOrderActivity.this, msg);

                                    PayOrderUtils payOrderUtil = new PayOrderUtils(StoreOrderActivity.this,
                                            String.valueOf(actualMoney), orderId);
                                    payOrderUtil.payMoney();

                                }
                            } else {
                                ToastUtils.showToast(StoreOrderActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
