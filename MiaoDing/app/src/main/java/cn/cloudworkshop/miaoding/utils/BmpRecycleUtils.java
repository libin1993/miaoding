package cn.cloudworkshop.miaoding.utils;

import android.graphics.Bitmap;

/**
 * Author：Libin on 2017-06-15 17:30
 * Email：1993911441@qq.com
 * Describe：bitmap回收
 */
public class BmpRecycleUtils {
    public static void bmpRecycle(Bitmap bmp) {
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
        System.gc();
    }
}
