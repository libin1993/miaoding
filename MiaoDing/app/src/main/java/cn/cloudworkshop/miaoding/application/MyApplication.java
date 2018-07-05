package cn.cloudworkshop.miaoding.application;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.zhy.http.okhttp.OkHttpUtils;
import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.FrescoImageLoader;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2016/7/7 17:41
 * Email：1993911441@qq.com
 * Describe：application全局对象
 */
public class MyApplication extends Application {
    //更新链接
    public static String updateUrl;
    //更新内容
    public static String updateContent;
    //客服电话
    public static String serverPhone;
    //登录背景图
    public static String loginBg;
    //用户协议
    public static String userAgreement;
    //量体协议
    //public static String measureAgreement;
    //订单id
    public static String orderId;
    //首页时间
    public static long homeEnterTime;
    //支付id
    public static String payId;


    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "CloudWorkshop/Cache");
            builder.cache(new Cache(file, 1024 * 1024 * 100));
        }
        OkHttpClient okHttpClient = builder.connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        Unicorn.init(this, "e98a79aca99f25ebf9bacbc8c334b76b", options(), new FrescoImageLoader(this));

    }


    /**
     * @return 七鱼配置
     */
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        UICustomization uiCustomization = new UICustomization();
        uiCustomization.rightAvatar = SharedPreferencesUtils.getStr(this, "avatar");
        options.uiCustomization = uiCustomization;
        return options;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}


