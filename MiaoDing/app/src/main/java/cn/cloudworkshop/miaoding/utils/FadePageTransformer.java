package cn.cloudworkshop.miaoding.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author：Libin on 2017/3/27 16:17
 * Email：1993911441@qq.com
 * Describe：viewpager单页显示3个item,滑动fadeout效果
 */
public class FadePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        float v = Math.abs(position);
        float v1 = (float) (0.1 * (v * v));
        page.setScaleY(1 - v1);
    }
}
