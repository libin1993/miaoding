package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.Ruler;
import cn.cloudworkshop.miaoding.view.WheelMenu;
import cn.qqtheme.framework.picker.NumberPicker;
import okhttp3.Call;

/**
 * Author: Libin on 2016/9/3 13:02
 * Email：1993911441@qq.com
 * Describe：穿衣测试
 */
public class DressingTestActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.tv_user_height)
    TextView tvUserHeight;
    @BindView(R.id.tv_user_weight)
    TextView tvUserWeight;
    @BindView(R.id.ruler_height)
    Ruler rulerHeight;
    @BindView(R.id.tv_user_age)
    TextView tvUserAge;
    @BindView(R.id.ll_current_age)
    LinearLayout llCurrentAge;
    @BindView(R.id.wheel_weight)
    WheelMenu wheelWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressing_test);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText("穿衣测试");
        tvHeaderNext.setVisibility(View.VISIBLE);
        tvHeaderNext.setText("下一步");
        rulerHeight.setValue(180);
        rulerHeight.setOnValueChangeListener(new Ruler.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                tvUserHeight.setText(String.valueOf(value));
            }
        });

        //平分120份
        wheelWeight.setDivCount(120);
        wheelWeight.setWheelImage(R.mipmap.icon_wheel_ruler);
        wheelWeight.setWheelChangeListener(new WheelMenu.WheelChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                int value = selectedPosition + 60;
                if (value > 120) {
                    value -= 120;
                }
                tvUserWeight.setText(String.valueOf(value));
            }
        });
    }


    @OnClick({R.id.img_header_back, R.id.tv_header_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_header_next:
                if (TextUtils.isEmpty(tvUserHeight.getText().toString().trim())
                        || TextUtils.isEmpty(tvUserWeight.getText().toString().trim())) {
                    ToastUtils.showToast(DressingTestActivity.this, "请完善身高体重");
                } else {
                    selectAge();
                }
                break;
        }
    }

    /**
     * 选择年龄
     */
    private void selectAge() {
        llCurrentAge.setVisibility(View.VISIBLE);
        NumberPicker picker = new NumberPicker(this);
        picker.setOffset(2);//偏移量
        picker.setRange(10, 100);//数字范围
        picker.setTextSize(14);
        picker.setLabel("岁");
        picker.setSelectedItem(25);
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                tvUserAge.setText(item.toString());
                submitData();
            }
        });

        picker.show();
    }


    /**
     * 提交测试数据
     */
    private void submitData() {
        OkHttpUtils.get()
                .url(Constant.DRESSING_TEST)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("age", tvUserAge.getText().toString().trim())
                .addParams("height", tvUserHeight.getText().toString().trim())
                .addParams("weight", tvUserWeight.getText().toString().trim())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            //穿衣测试事件监听
                            MobclickAgent.onEvent(DressingTestActivity.this, "dressing_test");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String uid = jsonObject1.getString("id");

                            Intent intent = new Intent(DressingTestActivity.this, DressingResultActivity.class);
                            intent.putExtra("title", "测试结果");
                            intent.putExtra("share_title","穿衣测试");
                            intent.putExtra("share_content", "");
                            intent.putExtra("url", Constant.DRESSING_TEST_RESULT + "?id=" + uid);
                            intent.putExtra("share_url", Constant.DRESSING_TEST_SHARE + "?id=" + uid);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
