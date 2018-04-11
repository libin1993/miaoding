package cn.cloudworkshop.miaoding.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.bean.DesignerWorksBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DateUtils;

/**
 * Author：Libin on 2017-04-21 10:43
 * Email：1993911441@qq.com
 * Describe：
 */
public class FlipAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private List<DesignerWorksBean.DataBeanX.DataBean> dataList;

    public FlipAdapter(Context context, List<DesignerWorksBean.DataBeanX.DataBean> dataList) {
        this.dataList = dataList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.designer_flipview_item, parent, false);
            holder.imgWorks = (ImageView) convertView.findViewById(R.id.img_designer_goods);
            holder.tvWorks = (TextView) convertView.findViewById(R.id.tv_works_name);
            holder.tvDesigner = (TextView) convertView.findViewById(R.id.tv_works_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DesignerWorksBean.DataBeanX.DataBean itemBean = dataList.get(position);
        Glide.with(context)
                .load(Constant.IMG_HOST + itemBean.getImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imgWorks);
        if (!TextUtils.isEmpty(itemBean.getId())) {
            holder.tvWorks.setText(itemBean.getName());
            holder.tvDesigner.setText(DateUtils.getDate("yyyy-MM-dd", itemBean.getC_time()) +
                    "         " + itemBean.getUsername());
        }


        holder.imgWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(itemBean.getId())) {
                    Intent intent = new Intent(context, WorksDetailActivity.class);
                    intent.putExtra("id", String.valueOf(itemBean.getId() + ""));
                    context.startActivity(intent);
                }
            }
        });

        holder.tvDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(itemBean.getId())) {
                    Intent intent = new Intent(context, DesignerDetailActivity.class);
                    intent.putExtra("id", itemBean.getDes_uid() + "");
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView imgWorks;
        TextView tvWorks;
        TextView tvDesigner;
    }
}
