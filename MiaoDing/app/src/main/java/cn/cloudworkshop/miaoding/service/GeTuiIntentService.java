package cn.cloudworkshop.miaoding.service;

import android.content.Context;
import android.content.Intent;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import cn.cloudworkshop.miaoding.bean.GeTuiBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.MessageDetailActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;

/**
 * Author：Libin on 2016/12/20 13:47
 * Email：1993911441@qq.com
 * Describe：个推
 */
public class GeTuiIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int i) {
    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        if (s != null) {
            SharedPreferencesUtils.saveStr(this, "client_id", s);
        }
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {

        String msg = new String(gtTransmitMessage.getPayload());
        GeTuiBean bean = GsonUtils.jsonToBean(msg, GeTuiBean.class);

        switch (bean.getType()) {
            case "1":
                Intent intent1 = new Intent(context, MessageDetailActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("content", 1);
                startActivity(intent1);
                break;
            case "2":
                Intent intent2 = new Intent(context, MessageDetailActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("content", 2);
                startActivity(intent2);
                break;
            case "3":
                Intent intent3 = new Intent(context, MessageDetailActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.putExtra("content", 3);
                startActivity(intent3);
                break;
            case "4":
                Intent intent4 = new Intent(context, HomepageInfoActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id=" + bean.getId());
                intent4.putExtra("title", bean.getTitle());
                intent4.putExtra("content", bean.getContent());
                intent4.putExtra("img_url", Constant.IMG_HOST + bean.getImg_url());
                intent4.putExtra("share_url", Constant.HOMEPAGE_SHARE + "?content=1&id=" + bean.getId());
                startActivity(intent4);
                break;
            case "5":
                Intent intent5 = new Intent(context, WorksDetailActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent5.putExtra("id", bean.getId());
                startActivity(intent5);
                break;
            case "6":
                Intent intent6 = new Intent(context, DesignerDetailActivity.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent6.putExtra("id", bean.getId());
                startActivity(intent6);
                break;
            case "7":
                Intent intent7 = new Intent(context, CustomizedGoodsActivity.class);
                intent7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent7.putExtra("id", bean.getId());
                startActivity(intent7);
                break;
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }
}
