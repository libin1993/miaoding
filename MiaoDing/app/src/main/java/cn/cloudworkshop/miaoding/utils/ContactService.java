package cn.cloudworkshop.miaoding.utils;

import android.content.Context;

import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.umeng.analytics.MobclickAgent;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.constant.Constant;

/**
 * Author：Libin on 2016/10/9 18:10
 * Email：1993911441@qq.com
 * Describe：联系客服
 */

public class ContactService {
    public static void contactService(Context context) {
        String title = context.getString(R.string.private_consultant);
        // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
        // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
        ConsultSource source = new ConsultSource(Constant.IMG_HOST, SharedPreferencesUtils.getStr(context, "phone"),
                SharedPreferencesUtils.getStr(context, "username"));
        // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
        Unicorn.openServiceActivity(context, // 上下文
                title, // 聊天窗口的标题
                source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
        );
        //联系客服事件监听
        MobclickAgent.onEvent(context, "customer_service");

    }
}
