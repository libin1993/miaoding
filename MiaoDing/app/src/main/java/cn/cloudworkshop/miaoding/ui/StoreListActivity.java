package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.StoreListBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2018/3/2 09:02
 * Email：1993911441@qq.com
 * Describe：定制店列表
 */
public class StoreListActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_store_name)
    EditText etStoreName;

    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    @BindView(R.id.rv_store_list)
    LRecyclerView rvStoreList;
    @BindView(R.id.img_cancel_search)
    ImageView imgCancelSearch;
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    //是否搜索
    private boolean isSearch;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<StoreListBean.DataBean> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("定制店");
        initData();

    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.STORE_LIST)
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

                        loadData(response);
                    }
                });

    }

    /**
     * @param response
     */
    private void loadData(String response) {
        StoreListBean listBean = GsonUtils.jsonToBean(response, StoreListBean.class);
        if (listBean.getData() != null && listBean.getData().size() > 0) {
            if (isRefresh || isSearch) {
                storeList.clear();
            }
            storeList.addAll(listBean.getData());
            //刷新、加载更多
            if (isLoadMore || isRefresh || isSearch) {
                rvStoreList.refreshComplete(0);
                mLRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                initView();
            }
            isRefresh = false;
            isLoadMore = false;
        } else {
            if (isSearch) {
                if (page == 1) {
                    storeList.clear();
                    rvStoreList.refreshComplete(0);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                }
            } else {
                RecyclerViewStateUtils.setFooterViewState(StoreListActivity.this,
                        rvStoreList, 0, LoadingFooter.State.NoMore, null);
            }

        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvStoreList.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<StoreListBean.DataBean> adapter = new CommonAdapter<StoreListBean.DataBean>
                (this, R.layout.listitem_store, storeList) {
            @Override
            protected void convert(ViewHolder holder, StoreListBean.DataBean dataBean, int position) {
                Glide.with(StoreListActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getImg())
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_store));
                holder.setText(R.id.tv_store_name, dataBean.getName());
                holder.setText(R.id.tv_store_love_num, "粉丝： " + dataBean.getLovenum());
                holder.setText(R.id.tv_address_store, "地址： " + dataBean.getAddress());
            }

        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvStoreList.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvStoreList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        if (isSearch) {
                            searchStore(etStoreName.getText().toString().trim());
                        } else {
                            initData();
                        }

                    }
                }, 1000);
            }
        });

        //加载更多
        rvStoreList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(StoreListActivity.this, rvStoreList,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                if (isSearch) {
                    searchStore(etStoreName.getText().toString().trim());
                } else {
                    initData();
                }
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(StoreListActivity.this, StoreInfoActivity.class);
                intent.putExtra("shop_id", storeList.get(position).getId());
                startActivity(intent);
            }
        });

        etStoreName.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //禁止输入空格
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        }});


        //监听
        etStoreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyWord = s.toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    isRefresh = true;
                    isSearch = false;
                    page = 1;
                    initData();
                } else {
                    page = 1;
                    isSearch = true;
                    isRefresh = false;
                    searchStore(keyWord);
                }
            }
        });
    }

    /**
     * @param keyWord 关键字
     *                搜索店铺
     */
    private void searchStore(String keyWord) {
        OkHttpUtils.get()
                .url(Constant.STORE_SEARCH)
                .addParams("like", keyWord)
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadData(response);
                    }
                });

    }

    @OnClick({R.id.img_header_back, R.id.img_load_error, R.id.img_cancel_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.img_cancel_search:
                if (!TextUtils.isEmpty(etStoreName.getText().toString().trim())) {
                    etStoreName.setText(null);
                }
                break;
        }
    }

}
