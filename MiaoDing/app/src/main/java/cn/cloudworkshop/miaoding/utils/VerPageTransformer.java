package cn.cloudworkshop.miaoding.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author：Libin on 2016/8/30 17:43
 * Email：1993911441@qq.com
 * Describe：VerticalViewPager滑动动画
 */
public class VerPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(page.getWidth() * -position);
        float yPosition = position * page.getHeight();
        page.setTranslationY(yPosition);
    }
}
