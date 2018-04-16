package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.GiftCardBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-09-20 15:21
 * Email：1993911441@qq.com
 * Describe：礼品卡
 */
public class GiftCardActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_card_remain)
    TextView tvCardRemain;
    @BindView(R.id.img_null_card)
    ImageView imgNullCard;
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;
    @BindView(R.id.img_card_rule)
    ImageView imgCardRule;
    @BindView(R.id.sv_card_rule)
    ScrollView svCardRule;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    private GiftCardBean cardBean;
    //选择礼品卡
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("礼品卡");
        imgHeaderShare.setVisibility(View.VISIBLE);
        imgHeaderShare.setImageResource(R.mipmap.icon_member_rule);
        getData();
        initData();
    }

    private void getData() {
        type = getIntent().getStringExtra("type");
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.GIFT_CARD)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
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
                        cardBean = GsonUtils.jsonToBean(response, GiftCardBean.class);
                        if (cardBean.getInfo() != null) {
                            initView();
                        }
                    }
                });
    }

    private void initView() {
        float cardMoney = Float.parseFloat(cardBean.getInfo().getGift_card());
        tvCardRemain.setText(DisplayUtils.decimalFormat(cardMoney));
        if (cardMoney > 0) {
            imgNullCard.setVisibility(View.GONE);
//            svCardRule.setVisibility(View.VISIBLE);
//            Glide.with(this)
//                    .load(Constant.IMG_HOST + cardBean.getInfo().getCard_rule())
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imgCardRule);
        } else {
            imgNullCard.setVisibility(View.VISIBLE);
            svCardRule.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_header_back, R.id.tv_add_card, R.id.img_load_error, R.id.img_header_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                if (cardBean != null && type != null && type.equals("select")) {
                    Intent intent = new Intent();
                    intent.putExtra("card_money", Float.parseFloat(cardBean.getInfo().getGift_card()));
                    setResult(1, intent);
                }
                finish();
                break;
            case R.id.tv_add_card:
                addGiftCard();
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.img_header_share:
                if (cardBean != null && !TextUtils.isEmpty(cardBean.getInfo().getCard_rule())) {
                    Intent intent = new Intent(this, UserRuleActivity.class);
                    intent.putExtra("title", "礼品卡使用规则");
                    intent.putExtra("img_url", cardBean.getInfo().getCard_rule());
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 添加礼品卡
     */
    private void addGiftCard() {
        View popupView = getLayoutInflater().inflate(R.layout.ppw_add_card, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, -300);
        DisplayUtils.setBackgroundAlpha(this, true);

        final EditText etNumber = (EditText) popupView.findViewById(R.id.et_card_number);
        TextView tvConfirm = (TextView) popupView.findViewById(R.id.tv_confirm_card);
        TextView tvCancel = (TextView) popupView.findViewById(R.id.tv_cancel_card);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNumber.getText().toString().trim())) {
                    ToastUtils.showToast(GiftCardActivity.this, "请输入密码");
                } else {
                    exchangeCard(etNumber.getText().toString());
                    mPopupWindow.dismiss();
                }
            }

        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(GiftCardActivity.this, false);
            }
        });

    }

    /**
     * @param number 兑换礼品卡金额
     */
    private void exchangeCard(String number) {
        OkHttpUtils.post()
                .url(Constant.EXCHANGE_CARD)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("exchange_code", number)
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
                            ToastUtils.showToast(GiftCardActivity.this, msg);
                            if (code == 1) {
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type != null && type.equals("select")) {
                Intent intent = new Intent();
                intent.putExtra("card_money", Float.parseFloat(cardBean.getInfo().getGift_card()));
                setResult(1, intent);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
