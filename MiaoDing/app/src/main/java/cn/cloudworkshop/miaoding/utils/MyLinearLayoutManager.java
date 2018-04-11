package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Author：Libin on 2016/11/24 17:20
 * Email：1993911441@qq.com
 * Describe：ScrollerView嵌套RecyclerView滑动冲突
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
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