package cn.cloudworkshop.miaoding.utils;

import android.app.Activity;

import android.content.Context;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RadioGroup;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Author：Libin on 2017-04-07 13:24
 * Email：1993911441@qq.com
 * Describe：获取屏幕宽高度，dp,px转换
 */
public class DisplayUtils {

    /**
     * @param context
     * @param dpValue
     * @return dp -》 px
     */
    public static float dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /**
     * @param context
     * @param pxValue
     * @return px-> dp
     */
    public static float px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    /**
     * @param activity
     * @return
     */
    public static DisplayMetrics getMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? activity.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * @param context
     * @return 设置字体
     */
    public static Typeface setTextType(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/futura.otf");
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param
     */
    public static void setBackgroundAlpha(Context mContext, boolean isShow) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        if (isShow) {
            lp.alpha = 0.5f;
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            lp.alpha = 1.0f;
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        ((Activity) mContext).getWindow().setAttributes(lp);

    }

    /**
     * @return 虚拟导航栏高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? activity.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * @return 保留两位小数
     */
    public static String decimalFormat(Float value) {
        return new DecimalFormat("0.00").format(value);
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumberDecimal(String value) {
        if (!TextUtils.isEmpty(value)) {
            return isInteger(value) || isDouble(value);
        }
        return false;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
