package cn.cloudworkshop.miaoding.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.cloudworkshop.miaoding.constant.Constant;

/**
 * Author：Libin on 2016/11/21 16:23
 * Email：1993911441@qq.com
 * Describe：微信支付
 */
public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp(Constant.APP_ID);
    }
}