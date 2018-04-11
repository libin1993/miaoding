package cn.cloudworkshop.miaoding.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author：Libin on 2017/12/13 18:20
 * Email：1993911441@qq.com
 * Describe：recyclerview item等间距
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    //是否添加头部
    private boolean hasHeader;

    public SpaceItemDecoration(int space,boolean hasHeader) {
        this.mSpace = space;
        this.hasHeader = hasHeader;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        //recyclerview添加头部
        if (hasHeader){
            if (position > 0){
                //第二列
                if ( position % 2 == 0) {
                    outRect.set(mSpace, 0, 0, mSpace * 2);
                } else {
                    outRect.set(0, 0, mSpace, mSpace * 2);
                }
            }
        }else {
            //第一列
            if ( position % 2 == 0) {
                outRect.set(0, 0, mSpace, mSpace * 2);
            } else {
                outRect.set(mSpace, 0, 0, mSpace * 2);
            }
        }

    }

}
