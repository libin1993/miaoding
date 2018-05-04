package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author：Libin on 2017-07-04 15:01
 * Email：1993911441@qq.com
 * Describe：Toast重复弹出问题
 */
public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}


