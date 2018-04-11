package cn.cloudworkshop.miaoding.focusresize;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.bean.GoodsListBean;
import cn.cloudworkshop.miaoding.constant.Constant;


/**
 * Author：Libin on 2016/9/8 11:00
 * Email：1993911441@qq.com
 * Describe：FocusResize RecyclerView适配器
 */
public class MyFocusResizeAdapter extends FocusResizeAdapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int OFFSET_TEXT_SIZE = 4;
    private static final float OFFSET_TEXT_ALPHA = 100f;
    private static final float ALPHA_SUBTITLE = 0.81f;
    private static final float ALPHA_SUBTITLE_HIDE = 0f;

    private List<GoodsListBean.DataBean.itemDataBean> items;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MyFocusResizeAdapter(Context context, int height) {
        super(context, height);
        this.context = context;
        items = new ArrayList<>();
    }

    public void addItems(List<GoodsListBean.DataBean.itemDataBean> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getFooterItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_viewpager_goods, parent, false);
        v.setOnClickListener(this);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsListBean.DataBean.itemDataBean customObject = items.get(position);
        fill((CustomViewHolder) holder, customObject,position);
    }

    private void fill(CustomViewHolder holder, final GoodsListBean.DataBean.itemDataBean customObject, int position) {
        holder.titleTextView.setText(customObject.getName());
        holder.subtitleTextView.setText(customObject.getSub_name());
        holder.image.setImageURI(Uri.parse(Constant.IMG_HOST + customObject.getThumb()));
        holder.itemView.setTag(position);


    }

    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE) >= context.getResources().getDimension(R.dimen.font_xxxlarge)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    ((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() + alpha >= ALPHA_SUBTITLE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView
                    .setAlpha(((CustomViewHolder) viewHolder)
                    .subtitleTextView.getAlpha() + alpha);
        }
    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);
    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE) <= context.getResources().getDimension(R.dimen.font_medium)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    ((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() - alpha < ALPHA_SUBTITLE_HIDE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(((CustomViewHolder) viewHolder)
                    .subtitleTextView.getAlpha() - alpha);
        }
    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimensionPixelSize(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick((Integer) view.getTag());
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView image;
        TextView titleTextView;
        TextView subtitleTextView;

        CustomViewHolder(View v) {
            super(v);
            image = (SimpleDraweeView) v.findViewById(R.id.image_custom_item);
            titleTextView = (TextView) v.findViewById(R.id.title_custom_item);
            subtitleTextView = (TextView) v.findViewById(R.id.subtitle_custom_item);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
}