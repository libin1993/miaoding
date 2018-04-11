package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;

import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.TitleBarUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/5 12:42
 * Email：1993911441@qq.com
 * Describe：三方登录
 */
public class QuickLoginActivity extends BaseActivity {
    @BindView(R.id.img_close_login)
    ImageView imgCancel;
    @BindView(R.id.img_wechat_login)
    ImageView imgWechat;
    @BindView(R.id.img_qq_login)
    ImageView imgQq;
    @BindView(R.id.img_sina_login)
    ImageView imgSina;
    @BindView(R.id.img_phone_login)
    ImageView imgPhone;
    @BindView(R.id.img_bg_login)
    ImageView imgBgLogin;

    private String logId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtils.setNoTitleBar(this);
        setContentView(R.layout.activity_quick_login);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        EventBus.getDefault().register(this);
//        initView();
        getLoginId();
    }

    private void initView() {
        if (MyApplication.loginBg != null) {
            Glide.with(this)
                    .load(Constant.IMG_HOST + MyApplication.loginBg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imgBgLogin);
        }
    }

    /**
     * 登录跟踪
     */
    private void getLoginId() {
        String pageName = getIntent().getStringExtra("page_name");
        if (pageName != null) {
            OkHttpUtils.post()
                    .url(Constant.LOGIN_LOG)
                    .addParams("p_module_name", pageName)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                logId = jsonObject.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }


    @OnClick({R.id.img_close_login, R.id.img_wechat_login, R.id.img_qq_login, R.id.img_sina_login,
            R.id.img_phone_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close_login:
                isFromCenter();
                break;
            case R.id.img_wechat_login:
                login(Wechat.NAME, 1);
                break;
            case R.id.img_qq_login:
                login(QQ.NAME, 2);
                break;
            case R.id.img_sina_login:
                login(SinaWeibo.NAME, 3);
                break;
            case R.id.img_phone_login:
                Intent intent = new Intent(this, NewLoginActivity.class);
                intent.putExtra("log_id", logId);
                startActivity(intent);
                break;
        }
    }


    /**
     * @param name
     */
    private void login(final String name, final int type) {
        Platform platform = ShareSDK.getPlatform(name);
        // 使用SSO授权。有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权。
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }
        platform.SSOSetting(false);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                try {
                    JSONObject jsonObject = new JSONObject(platform.getDb().exportData());
                    String uid = jsonObject.getString("userID");
                    String icon = jsonObject.getString("icon");
                    String nickname = jsonObject.getString("nickname");
                    quickLogin(uid, icon, nickname, type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtils.showToast(QuickLoginActivity.this, "登录失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtils.showToast(QuickLoginActivity.this, "登录取消");
            }
        });
        // 参数null表示获取当前授权用户资料
        platform.showUser(null);
    }

    /**
     * @param userid
     * @param icon
     * @param nickname
     * @param type
     */
    private void quickLogin(final String userid, final String icon, final String nickname, final int type) {
        OkHttpUtils.post()
                .url(Constant.QUICK_LOGIN)
                .addParams("userid", userid)
                .addParams("icon", icon)
                .addParams("nickname", nickname)
                .addParams("is_type", String.valueOf(type))
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
                            if (code == 1){
                                JSONObject data = jsonObject.getJSONObject("data");
                                String token = data.getString("token");
                                int uid = data.getInt("uid");
                                String name = data.getString("name");
                                String avatar = data.getString("avatar");
                                String phone = data.getString("phone");
                                SharedPreferencesUtils.saveStr(QuickLoginActivity.this,
                                        "token", token);
                                SharedPreferencesUtils.saveStr(QuickLoginActivity.this,
                                        "uid", String.valueOf(uid));
                                SharedPreferencesUtils.saveStr(QuickLoginActivity.this,
                                        "avatar", avatar);
                                SharedPreferencesUtils.saveStr(QuickLoginActivity.this,
                                        "username", name);
                                SharedPreferencesUtils.saveStr(QuickLoginActivity.this,
                                        "phone", phone);
                                MobclickAgent.onEvent(QuickLoginActivity.this, "log_in");
                                finish();
                            }else {
                                Intent intent = new Intent(QuickLoginActivity.this, NewLoginActivity.class);
                                intent.putExtra("userid", userid);
                                intent.putExtra("icon", icon);
                                intent.putExtra("nickname", nickname);
                                intent.putExtra("is_type", type);
                                intent.putExtra("log_id", logId);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    /**
     * @param msg 登录成功，结束当前页面
     */
    @Subscribe
    public void loginSuccess(String msg) {
        if ("login_success".equals(msg)) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isFromCenter();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 是否从个人中心跳转,取消登录后则跳转首页
     */
    private void isFromCenter() {
        String loginTag = getIntent().getStringExtra("log_in");
        if (!TextUtils.isEmpty(loginTag) && loginTag.equals("center")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("page", 0);
            startActivity(intent);
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
