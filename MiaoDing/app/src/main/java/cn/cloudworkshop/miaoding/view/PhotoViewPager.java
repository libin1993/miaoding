package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author：Libin on 2017/2/7 16:33
 * Email：1993911441@qq.com
 * Describe：解决连续缩放photoview报错问题
 * 主要原因是viewpager和photoview冲突
 */
public class PhotoViewPager extends ViewPager{
    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
