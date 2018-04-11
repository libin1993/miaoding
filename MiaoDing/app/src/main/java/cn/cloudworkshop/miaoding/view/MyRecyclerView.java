package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author：Libin on 2017-04-07 11:02
 * Email：1993911441@qq.com
 * Describe：ViewPager嵌套横向RecyclerView
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x1 = 0;
        float y1 = 0;
        float x2 = 0;
        float y2 = 0;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = e.getX();
                y1 = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = e.getX();
                y2 = e.getY();
                break;
        }

        float x = Math.abs(x1 - x2);
        float y = Math.abs(y1 - y2);

        if (x >= y) {
            getParent().getParent().requestDisallowInterceptTouchEvent(true);
            return super.onTouchEvent(e);
        } else {
            getParent().getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }
    }

}
