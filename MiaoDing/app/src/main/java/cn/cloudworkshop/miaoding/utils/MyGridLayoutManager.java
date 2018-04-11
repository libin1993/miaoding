package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;


/**
 * Author：Libin on 2018/4/10 14:19
 * Email：1993911441@qq.com
 * Describe：ScrollerView嵌套RecyclerView滑动冲突
 */
public class MyGridLayoutManager extends GridLayoutManager{
    private boolean isScrollEnabled = true;

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
