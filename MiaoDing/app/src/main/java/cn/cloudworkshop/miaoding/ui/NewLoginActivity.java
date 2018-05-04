package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.PhoneNumberUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/5 15:05
 * Email：1993911441@qq.com
 * Describe：手机登录
 */
public class NewLoginActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;
    @BindView(R.id.et_user_phone)
    EditText etPhone;
    @BindView(R.id.et_identify_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_confirm_login)
    TextView tvLogin;

    //验证码token
    private String msgToken;
    //是否输入手机号
    private boolean isPhone;
    //是否输入验证码
    private boolean isCode;

    private String logId;
    private String userId;
    private String icon;
    private String nickname;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        logId = intent.getStringExtra("log_id");
        userId = intent.getStringExtra("userid");
        icon = intent.getStringExtra("icon");
        nickname = intent.getStringExtra("nickname");
        type = intent.getIntExtra("is_type", 0);

    }


    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<NewLoginActivity> mActivity;

        private MyHandler(NewLoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            NewLoginActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 1) {
                    activity.tvGetCode.setText("重发(" + msg.arg1 + ")");
                    activity.tvGetCode.setBackgroundResource(R.drawable.bound_ed_3dp);
                    activity.tvGetCode.setTextColor(ContextCompat.getColor(activity, R.color.light_gray_43));
                } else if (msg.what == 2) {
                    activity.tvGetCode.setText("获取验证码");
                    activity.tvGetCode.setClickable(true);
                    activity.tvGetCode.setBackgroundResource(R.drawable.bound_15_3dp);
                    activity.tvGetCode.setTextColor(Color.WHITE);
                }
            }
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        viewHeaderLine.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(userId)) {
            tvHeaderTitle.setText("绑定手机");
        } else {
            tvHeaderTitle.setText("手机登录");
        }

        msgToken = SharedPreferencesUtils.getStr(this, "msg_token");

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (PhoneNumberUtils.judgePhoneNumber(etPhone.getText().toString().trim())) {
                    tvGetCode.setBackgroundResource(R.drawable.bound_15_3dp);
                    tvGetCode.setTextColor(Color.WHITE);
                    isPhone = true;
                    if (isCode) {
                        tvLogin.setEnabled(true);
                    } else {
                        tvLogin.setEnabled(false);
                    }
                } else {
                    tvGetCode.setBackgroundResource(R.drawable.bound_ed_3dp);
                    tvGetCode.setTextColor(ContextCompat.getColor(NewLoginActivity.this, R.color.light_gray_43));
                    isPhone = false;
                    tvLogin.setEnabled(false);
                }
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                    isCode = false;
                    tvLogin.setEnabled(false);
                } else {
                    isCode = true;
                    if (isPhone) {
                        tvLogin.setEnabled(true);
                    } else {
                        tvLogin.setEnabled(false);
                    }
                }
            }
        });
    }

    @OnClick({R.id.img_header_back, R.id.tv_get_code, R.id.tv_confirm_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_get_code:
                getIdentifyCode();
                break;
            case R.id.tv_confirm_login:
                confirmLogin();
                break;
        }
    }

    /**
     * 登录
     */
    private void confirmLogin() {

        if (!TextUtils.isEmpty(msgToken)) {
            Map<String, String> map = new HashMap<>();
            map.put("phone", etPhone.getText().toString().trim());
            map.put("code", etCode.getText().toString().trim());
            map.put("token", msgToken);
            map.put("device_id", SharedPreferencesUtils.getStr(NewLoginActivity.this, "client_id"));
            if (logId != null) {
                map.put("id", logId);
            }
            map.put("device_type", Build.MODEL);
            map.put("phone_type", "1");

            if (!TextUtils.isEmpty(userId)) {
                map.put("userid", userId);
            }
            if (!TextUtils.isEmpty(icon)) {
                map.put("icon", icon);
            }
            if (!TextUtils.isEmpty(nickname)) {
                map.put("nickname", nickname);
            }
            if (!TextUtils.isEmpty(userId)) {
                map.put("is_type", String.valueOf(type));
            }


            OkHttpUtils.post()
                    .url(Constant.LOGIN)
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
                                if (code == 1) {
                                    //登录成功，销毁登录界面
                                    EventBus.getDefault().post("login_success");
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    String loginToken = data.getString("token");
                                    int uid = data.getInt("uid");
                                    String name = data.getString("name");
                                    String avatar = data.getString("avatar");
                                    String phone = data.getString("phone");

                                    SharedPreferencesUtils.saveStr(NewLoginActivity.this,
                                            "token", loginToken);
                                    SharedPreferencesUtils.saveStr(NewLoginActivity.this,
                                            "uid", String.valueOf(uid));
                                    SharedPreferencesUtils.saveStr(NewLoginActivity.this,
                                            "avatar", avatar);
                                    SharedPreferencesUtils.saveStr(NewLoginActivity.this,
                                            "username", name);
                                    SharedPreferencesUtils.saveStr(NewLoginActivity.this,
                                            "phone", phone);
                                    MobclickAgent.onEvent(NewLoginActivity.this, "log_in");
                                    finish();
                                }
                                ToastUtils.showToast(NewLoginActivity.this, jsonObject.getString("msg"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }

    }


    /**
     * 获取验证码
     */
    private void getIdentifyCode() {

        if (PhoneNumberUtils.judgePhoneNumber(etPhone.getText().toString().trim())) {
            etCode.setClickable(false);
            sendPhoneNumber();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 30; i > 0; i--) {
                        if (i <= 0) {
                            break;
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        msg.arg1 = i;
                        mHandler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendEmptyMessage(2);
                }
            }).start();
        } else {
            ToastUtils.showToast(this, "手机号输入有误，请重新输入");
        }
    }


    /**
     * 发送手机号
     */
    private void sendPhoneNumber() {

        OkHttpUtils.post()
                .url(Constant.IDENTIFY_CODE)
                .addParams("phone", etPhone.getText().toString().trim())
                .addParams("type", "1")
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
                                ToastUtils.showToast(NewLoginActivity.this, jsonObject.getString("msg"));
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                msgToken = jsonObject1.getString("token");
                                SharedPreferencesUtils.saveStr(NewLoginActivity.this, "msg_token", msgToken);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
