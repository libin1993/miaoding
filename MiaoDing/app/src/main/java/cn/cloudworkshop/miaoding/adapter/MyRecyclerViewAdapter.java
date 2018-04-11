package cn.cloudworkshop.miaoding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.bean.HomeClassifyBean;
import cn.cloudworkshop.miaoding.constant.Constant;

/**
 * Author：Libin on 2016/10/14 15:19
 * Email：1993911441@qq.com
 * Describe：
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<HomeClassifyBean.DataBeanX.DataBean> list;
    private Context context;
    private LayoutInflater inflater;


    public MyRecyclerViewAdapter(Context context, List<HomeClassifyBean.DataBeanX.DataBean> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_homepage_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context)
                .load(Constant.IMG_HOST + list.get(position).getImg())
                .into(holder.img);
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvContent.setText(list.get(position).getContent());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tvTitle;
        public TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_home_item);
//            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommend_title);
//            tvContent = (TextView) itemView.findViewById(R.id.tv_recommend_content);
        }
    }

}
