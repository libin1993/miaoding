package cn.cloudworkshop.miaoding.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.CollectionBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.MainActivity;
import cn.cloudworkshop.miaoding.ui.StoreInfoActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.SpaceItemDecoration;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-07-18 17:52
 * Email：1993911441@qq.com
 * Describe：收藏
 */
public class CollectionFragment extends BaseFragment {
    @BindView(R.id.rv_goods)
    LRecyclerView rvCollection;
    @BindView(R.id.img_no_order)
    ImageView imgNoCollect;
    @BindView(R.id.tv_my_order)
    TextView tvCollection;
    @BindView(R.id.ll_null_order)
    LinearLayout llNullCollect;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    private Unbinder unbinder;

    private List<CollectionBean.DataBean> itemList = new ArrayList<>();
    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    //收藏类型 1:资讯 2：商品 3：店铺

    private int type;
    private CommonAdapter<CollectionBean.DataBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initData();
        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("type", String.valueOf(type))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        CollectionBean collectionBean = GsonUtils.jsonToBean(response, CollectionBean.class);
                        if (collectionBean.getData() != null && collectionBean.getData().size() > 0) {
                            if (isRefresh) {
                                itemList.clear();
                            }
                            itemList.addAll(collectionBean.getData());
                            //刷新、加载更多
                            if (isLoadMore || isRefresh) {
                                rvCollection.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                            llNullCollect.setVisibility(View.GONE);
                            rvCollection.setVisibility(View.VISIBLE);

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(),
                                    rvCollection, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                rvCollection.setVisibility(View.GONE);
                                imgNoCollect.setImageResource(R.mipmap.icon_null_collection);
                                llNullCollect.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {
        switch (type) {
            case 1:
                rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CommonAdapter<CollectionBean.DataBean>(getActivity(),
                        R.layout.listitem_homepage_news, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean itemBean, int position) {
                        SimpleDraweeView imgNews = holder.getView(R.id.img_homepage_news);
                        if (!TextUtils.isEmpty(itemBean.getImg_info())) {
                            imgNews.setAspectRatio(Float.parseFloat(itemBean.getImg_info()));
                        }
                        imgNews.setImageURI(Constant.IMG_HOST + itemBean.getImg());
                        holder.setText(R.id.tv_news_title, itemBean.getTitle());
                        holder.setText(R.id.tv_news_content, itemBean.getSub_title());

                        holder.setVisible(R.id.ll_home_news, false);
                        holder.setVisible(R.id.view_news_line, false);
                    }
                };
                break;
            case 2:
                rvCollection.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                rvCollection.addItemDecoration(new SpaceItemDecoration((int) DisplayUtils.dp2px(
                        getActivity(), 4.5f), true));

                adapter = new CommonAdapter<CollectionBean.DataBean>(getActivity(),
                        R.layout.listitem_sub_goods, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean itemBean, int position) {
                        SimpleDraweeView imgGoods = holder.getView(R.id.img_sub_goods);
                        imgGoods.setImageURI(Constant.IMG_HOST + itemBean.getThumb());

                        holder.setText(R.id.tv_sub_title, itemBean.getName());
                        holder.setText(R.id.tv_sub_price, itemBean.getPrice2());
                        holder.setText(R.id.tv_sub_content, itemBean.getSub_name());
                    }
                };
                break;
            case 3:
                rvCollection.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CommonAdapter<CollectionBean.DataBean>(getActivity(),
                        R.layout.listitem_store, itemList) {
                    @Override
                    protected void convert(ViewHolder holder, CollectionBean.DataBean itemBean, int position) {
                        Glide.with(getActivity())
                                .load(Constant.IMG_HOST + itemBean.getFactory_img())
                                .placeholder(R.mipmap.place_holder_goods)
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_store));
                        holder.setText(R.id.tv_store_name, itemBean.getFactory_name());
                        holder.setText(R.id.tv_store_love_num, getString(R.string.fans)+"： " + itemBean.getLovenum());
                        holder.setText(R.id.tv_address_store, getString(R.string.store_address)+"： " + itemBean.getAddress());
                    }
                };
                break;
        }

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvCollection.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvCollection.setOnRefreshListener(new OnRefreshListener() {
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
        rvCollection.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvCollection, 0,
                        LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(getActivity(), HomepageInfoActivity.class);
                        intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id="
                                + itemList.get(position).getCid());
                        intent.putExtra("title", itemList.get(position).getTitle());
                        intent.putExtra("content", itemList.get(position).getTags_name()
                                + " · " + itemList.get(position).getSub_title());
                        intent.putExtra("img_url", itemList.get(position).getImg());
                        intent.putExtra("share_url", Constant.HOMEPAGE_SHARE +
                                "?content=1&id=" + itemList.get(position).getCid());
                        break;
                    case 2:
                        if (itemList.get(position).getGoods_type() == 1) {
                            intent = new Intent(getActivity(), CustomizedGoodsActivity.class);
                        } else {
                            intent = new Intent(getActivity(), WorksDetailActivity.class);
                        }

                        intent.putExtra("id", String.valueOf(itemList.get(position).getCid()));
                        break;
                    case 3:
                        intent = new Intent(getActivity(), StoreInfoActivity.class);
                        intent.putExtra("shop_id", itemList.get(position).getCid());
                        break;
                }
                startActivity(intent);
            }
        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                cancelCollection(itemList.get(position).getCid(), position);
            }
        });

    }


    /**
     * 取消收藏
     */
    private void cancelCollection(final int cid, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.cancel_collect));
        dialog.setMessage(R.string.is_cancel_collect);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.ADD_COLLECTION)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("type", String.valueOf(type))
                        .addParams("cid", String.valueOf(cid))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.getInt("code");
                                    String msg = jsonObject.getString("msg");
                                    if (code == 2) {
                                        ToastUtils.showToast(getActivity(), msg);

                                        itemList.remove(position);
                                        adapter.notifyItemRemoved(position);
                                        if (position != itemList.size()) {
                                            adapter.notifyItemRangeChanged(position, itemList.size() - position);
                                        }
                                        if (itemList.size() == 0) {
                                            rvCollection.setVisibility(View.GONE);
                                            imgNoCollect.setImageResource(R.mipmap.icon_null_collection);
                                            llNullCollect.setVisibility(View.VISIBLE);
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }

    public static CollectionFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_my_order, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_order:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("page", type - 1);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }
}
