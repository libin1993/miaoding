package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Author：Libin on 2016/10/31 18:05
 * Email：1993911441@qq.com
 * Describe：Viewpager嵌套WebView冲突问题
 */
public class MyWebView extends WebView {
    private boolean mCanScroll = true;
    private float mDownY;

    public MyWebView(Context context, AttributeSet attrs) {
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
                if ((scrollY == 0 && mDownY  <= ev.getY())) {
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
