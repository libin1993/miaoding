package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Author：Libin on 2016/9/19 16:01
 * Email：1993911441@qq.com
 * Describe：解决ViewPager嵌套ScrollView冲突
 */
public class ViewPagerContainScrollView extends ScrollView {

    private boolean mCanScroll = true;
    private float mDownY;

    public ViewPagerContainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollY = getScrollY();
                if ((scrollY == 0 && mDownY  <= ev.getY())
                        || (getChildAt(0).getMeasuredHeight() == (scrollY + getHeight()) && mDownY >= ev.getY())) {
                    mCanScroll = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mCanScroll = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCanScroll = true;
                break;
        }

        if (mCanScroll) {
            //通知ViewPager不要干扰自身的操作
            getParent().requestDisallowInterceptTouchEvent(true);
            return super.onTouchEvent(ev);
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }
    }
}
