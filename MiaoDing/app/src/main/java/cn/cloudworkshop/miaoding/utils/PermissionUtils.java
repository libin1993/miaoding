package cn.cloudworkshop.miaoding.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import cn.cloudworkshop.miaoding.R;

/**
 * Author：Libin on 2016/11/7 10:31
 * Email：1993911441@qq.com
 * Describe：运行时权限
 */
public class PermissionUtils {
    /**
     * 提示对话框
     */
    public static void showPermissionDialog(final Context context, String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(context.getString(R.string.help));
        dialog.setMessage(context.getString(R.string.app_need) + msg + context.getString(R.string.permission));
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(context.getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 启动应用的设置
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

}

