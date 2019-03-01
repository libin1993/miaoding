package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.zhy.adapter.recyclerview.CommonAdapter;
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
import cn.cloudworkshop.miaoding.bean.GoodsListBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SpaceItemDecoration;
import okhttp3.Call;

/**
 * Author：Libin on 2017/9/25 19:
 * Email：1993911441@qq.com
 * Describe：定制商品（当前版）
 */
public class SubGoodsFragment extends BaseFragment {
    @BindView(R.id.rv_sub_goods)
    LRecyclerView rvGoods;
    //订单状态
    private int classifyId;
    //页面
    private int page = 1;
    //刷新
    private boolean isRefresh;

    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<GoodsListBean.DataBean.itemDataBean> dataList = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subgoods, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initData();
        return view;
    }

    private void getData() {

        Bundle bundle = getArguments();
        classifyId = bundle.getInt("classify_id");
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.GOODS_LIST)
                .addParams("type", "1")
                .addParams("classify_id", String.valueOf(classifyId))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        GoodsListBean listBean = GsonUtils.jsonToBean(response, GoodsListBean.class);
                        if (listBean.getData().getData() != null && listBean.getData().getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(listBean.getData().getData());
                            if (isRefresh || isLoadMore) {
                                rvGoods.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getParentFragment().getActivity(),
                                    rvGoods, 0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }

    private void initView() {
        rvGoods.setLayoutManager(new GridLayoutManager(getParentFragment().getActivity(), 2));
        rvGoods.addItemDecoration(new SpaceItemDecoration((int) DisplayUtils.dp2px(
                getParentFragment().getActivity(), 4.5f), true));
        CommonAdapter<GoodsListBean.DataBean.itemDataBean> adapter = new CommonAdapter
                <GoodsListBean.DataBean.itemDataBean>(getParentFragment().getActivity(),
                R.layout.listitem_sub_goods, dataList) {
            @Override
            protected void convert(ViewHolder holder, GoodsListBean.DataBean.itemDataBean
                    itemDataBean, int position) {
                SimpleDraweeView imgGoods = holder.getView(R.id.img_sub_goods);
                if (!TextUtils.isEmpty(itemDataBean.getImg_info())) {
                    imgGoods.setAspectRatio(Float.parseFloat(itemDataBean.getImg_info()));
                }
                imgGoods.setImageURI(Constant.IMG_HOST + itemDataBean.getImg_new());

                holder.setText(R.id.tv_sub_title, itemDataBean.getName());
                holder.setText(R.id.tv_sub_price, itemDataBean.getPrice());
                holder.setText(R.id.tv_sub_content, itemDataBean.getSub_name());
            }
        };


        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvGoods.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvGoods.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        initData();
                    }
                }, 1000);
            }
        });
        //加载更多
        rvGoods.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getParentFragment().getActivity(), rvGoods
                        , 0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getParentFragment().getActivity(), CustomizedGoodsActivity.class);
                intent.putExtra("id", String.valueOf(dataList.get(position).getId()));
                startActivity(intent);
            }
        });
    }


    public static SubGoodsFragment newInstance(int classify_id) {

        Bundle args = new Bundle();
        args.putInt("classify_id", classify_id);
        SubGoodsFragment fragment = new SubGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
