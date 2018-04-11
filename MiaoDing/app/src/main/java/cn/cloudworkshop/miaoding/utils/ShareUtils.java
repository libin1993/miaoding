package cn.cloudworkshop.miaoding.utils;

import android.app.Activity;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import cn.cloudworkshop.miaoding.R;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Author：Libin on 2016/11/3 09:13
 * Email：1993911441@qq.com
 * Describe：sharesdk分享
 */
public class ShareUtils {

    public static void showShare(final Activity activity, final String imgUrl, final String title, final String content, final String url) {

        ShareSDK.initSDK(activity, "188b0b9b49186");
        final OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        //隐藏新浪分享
        oks.addHiddenPlatform(SinaWeibo.NAME);

        //分享图片
        oks.setImageUrl(imgUrl);

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                switch (platform.getName()) {
                    case "Wechat":
                    case "WechatMoments":
                        paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                        paramsToShare.setTitle(title);
                        paramsToShare.setText(content);
                        paramsToShare.setUrl(url);
                        break;
                    case "QQ":
                        paramsToShare.setTitle(title);
                        paramsToShare.setText(content);
                        paramsToShare.setTitleUrl(url);
                        break;
                    case "SinaWeibo":
                        paramsToShare.setText(content + url);
                        break;
                }
            }
        });
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                MobclickAgent.onEvent(activity, "share");
            }


            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        // 启动分享GUI
        oks.show(activity);
    }
}

