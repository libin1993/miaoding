package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author：Libin on 2016/12/14 15:02
 * Email：1993911441@qq.com
 * Describe：SharedPreferences工具类
 */
public class SharedPreferencesUtils {

    /**
     * @param context
     * @param key
     * @param value   保存字符串
     */
    public static void saveStr(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(key, value);
        editor.commit();//提交修改
    }

    /**
     * @return 获取字符串
     */
    public static String getStr(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        LogUtils.log(sharedPreferences.getString(key, ""));
        return sharedPreferences.getString(key, "");
    }

    /**
     * @param context
     * @param key
     * @param value   保存boolean
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * @return 获取boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 删除数据
     */
    public static void deleteStr(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.clear();
        editor.commit();//提交修改

    }
}
