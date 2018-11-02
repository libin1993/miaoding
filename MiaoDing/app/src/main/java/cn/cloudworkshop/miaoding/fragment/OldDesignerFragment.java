package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.PopDesignerBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-06-08 11:30
 * Email：1993911441@qq.com
 * Describe：腔调设计师（老版）
 */
public class OldDesignerFragment extends BaseFragment {
    @BindView(R.id.rv_works)
    RecyclerView rvDesigner;
    private Unbinder unbinder;
    private List<PopDesignerBean.DataBean> designerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.DESIGNER_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        PopDesignerBean designerBean = GsonUtils.jsonToBean(response, PopDesignerBean.class);
                        if (designerBean.getData() != null) {
                            designerList.addAll(designerBean.getData());
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvDesigner.setLayoutManager(new GridLayoutManager(getParentFragment().getActivity(), 2));
        CommonAdapter<PopDesignerBean.DataBean> adapter = new CommonAdapter<PopDesignerBean.DataBean>
                (getParentFragment().getActivity(), R.layout.listitem_designer, designerList) {
            @Override
            protected void convert(ViewHolder holder, PopDesignerBean.DataBean dataBean, int position) {
                Glide.with(getParentFragment().getActivity())
                        .load(Constant.IMG_HOST + dataBean.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_avatar_designer));
                holder.setText(R.id.tv_nickname_designer, dataBean.getName());
                holder.setText(R.id.tv_designer_tag, dataBean.getTag());
            }
        };
        rvDesigner.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (designerList.get(position).getId() != 0 && position < designerList.size() - 1) {
                    Intent intent = new Intent(getParentFragment().getActivity(), DesignerDetailActivity.class);
                    intent.putExtra("id", String.valueOf(designerList.get(position).getId()));
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(getParentFragment().getActivity(), getString(R.string.coming_soon));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public static OldDesignerFragment newInstance() {

        Bundle args = new Bundle();

        OldDesignerFragment fragment = new OldDesignerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
