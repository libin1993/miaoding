package cn.cloudworkshop.miaoding.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Author：Libin on 2017-07-03 14:49
 * Email：1993911441@qq.com
 * Describe：activity的管理类
 */
public class AppManagerUtils {
    private static AppManagerUtils instance;
    private static Stack<Activity> activityStack;

    private AppManagerUtils() {
    }

    public static AppManagerUtils getInstance() {
        if (instance == null) {
            instance = new AppManagerUtils();
        }
        return instance;
    }


    /**
     * 新建一个activity
     * @param activity
     */

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */

    private void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityClass(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                    break;
                }
            }
        }
    }


    /**
     * 应用退出，结束所有的activity
     */
    public void exitApp() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
