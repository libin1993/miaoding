package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.HomepageNewsBean;
import cn.cloudworkshop.miaoding.bean.NewsListBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2018/3/14 15:50
 * Email：1993911441@qq.com
 * Describe：资讯
 */
public class NewsListActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_news_list)
    LRecyclerView rvNewsList;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;


    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private List<NewsListBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<NewsListBean.DataBeanX.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("资讯");
        initData();
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.NEWS_LIST)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        NewsListBean newsListBean = GsonUtils.jsonToBean(response, NewsListBean.class);
                        if (newsListBean.getData().getData() != null && newsListBean.getData().getData().size() > 0) {
                            //刷新，初始化数据
                            if (isRefresh) {
                                dataList.clear();
                            }

                            dataList.addAll(newsListBean.getData().getData());
                            //刷新，加载完成
                            if (isLoadMore || isRefresh) {
                                rvNewsList.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isLoadMore = false;
                            isRefresh = false;

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(NewsListActivity.this,
                                    rvNewsList, 0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }

    private void initView() {
        rvNewsList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<NewsListBean.DataBeanX.DataBean>(this,
                R.layout.listitem_homepage_news, dataList) {
            @Override
            protected void convert(ViewHolder holder, final NewsListBean.DataBeanX.DataBean dataBean,
                                   final int position) {
                SimpleDraweeView imgNews = holder.getView(R.id.img_homepage_news);
                if (!TextUtils.isEmpty(dataBean.getImg_info())) {
                    imgNews.setAspectRatio(Float.parseFloat(dataBean.getImg_info()));
                }

                imgNews.setImageURI(Constant.IMG_HOST + dataBean.getImg());

                ImageView imgShare = (ImageView) holder.itemView.findViewById(R.id.img_news_share);
                ImageView imgCollect = (ImageView) holder.itemView.findViewById(R.id.img_add_collect);

                LinearLayout llLove = (LinearLayout) holder.itemView.findViewById(R.id.ll_news_love);
                ImageView imgLove = (ImageView) holder.itemView.findViewById(R.id.img_love_num);
                TextView tvLove = (TextView) holder.itemView.findViewById(R.id.tv_love_num);

                LinearLayout llComment = (LinearLayout) holder.itemView.findViewById(R.id.ll_news_comment);
                TextView tvComment = (TextView) holder.itemView.findViewById(R.id.tv_comment_num);

                holder.setText(R.id.tv_news_title, dataBean.getTitle());
                holder.setText(R.id.tv_news_content, dataBean.getSub_title());
                tvLove.setText(String.valueOf(dataBean.getLovenum()));
                tvComment.setText(String.valueOf(dataBean.getCommentnum()));

                if (dataBean.getIs_collect() == 1) {
                    imgCollect.setImageResource(R.mipmap.icon_collect_check);
                } else {
                    imgCollect.setImageResource(R.mipmap.icon_collect_normal);
                }
                if (dataBean.getIs_love() == 1) {
                    imgLove.setImageResource(R.mipmap.icon_love_check);
                } else {
                    imgLove.setImageResource(R.mipmap.icon_love_normal);
                }

                //图片点击
                imgNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dataBean.getIs_type() == 1) {
                            gotoHomepageInfo(dataBean);
                        }
                    }
                });
                //分享
                imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataBean.getIs_type() == 1) {
                            ShareUtils.showShare(NewsListActivity.this,
                                    Constant.IMG_HOST + dataBean.getImg(), dataBean.getTitle(),
                                    dataBean.getSub_title(), Constant.HOMEPAGE_SHARE
                                            + "?content=1&id=" + dataBean.getId());
                        }
                    }
                });

                //收藏
                imgCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(NewsListActivity.this, "token"))) {
                            addCollection(dataBean, position - 1);
                        } else {
                            Intent login = new Intent(NewsListActivity.this, LoginActivity.class);
                            login.putExtra("page_name", "收藏");
                            startActivity(login);
                        }

                    }
                });
                //喜爱
                llLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(NewsListActivity.this, "token"))) {
                            addLove(dataBean, position - 1, dataBean.getLovenum());
                        } else {
                            Intent login = new Intent(NewsListActivity.this, LoginActivity.class);
                            login.putExtra("page_name", "喜爱");
                            startActivity(login);
                        }

                    }
                });
                llComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoHomepageInfo(dataBean);
                    }
                });

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvNewsList.setAdapter(mLRecyclerViewAdapter);
        //刷新
        rvNewsList.setOnRefreshListener(new OnRefreshListener() {
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
        //加载下一页
        rvNewsList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(NewsListActivity.this,
                        rvNewsList, 0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });
    }

    /**
     * @param dataBean 跳转资讯详情
     */
    private void gotoHomepageInfo(NewsListBean.DataBeanX.DataBean dataBean) {

        Intent intent = new Intent(NewsListActivity.this, HomepageInfoActivity.class);
        intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id=" + dataBean.getId());
        intent.putExtra("title", dataBean.getTitle());
        intent.putExtra("content", dataBean.getSub_title());
        intent.putExtra("img_url", Constant.IMG_HOST + dataBean.getImg());
        intent.putExtra("share_url", Constant.HOMEPAGE_SHARE
                + "?content=1&id=" + dataBean.getId());
        startActivity(intent);
    }

    /**
     * @param dataBean 添加收藏
     */
    private void addCollection(NewsListBean.DataBeanX.DataBean dataBean, final int position) {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(NewsListActivity.this, "token"))
                .addParams("type", String.valueOf(dataBean.getIs_type()))
                .addParams("cid", String.valueOf(dataBean.getId()))
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
                            if (code == 1) {
                                dataList.get(position).setIs_collect(1);
                            } else {
                                dataList.get(position).setIs_collect(0);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 添加喜爱
     */
    private void addLove(NewsListBean.DataBeanX.DataBean dataBean, final int position, final int loveNum) {
        OkHttpUtils.get()
                .url(Constant.ADD_LOVE)
                .addParams("token", SharedPreferencesUtils.getStr(NewsListActivity.this, "token"))
                .addParams("news_id", String.valueOf(dataBean.getId()))
                .addParams("is_type", String.valueOf(dataBean.getIs_type()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int data = jsonObject.getInt("data");
                            if (data == 1) {
                                dataList.get(position).setIs_love(1);
                                dataList.get(position).setLovenum(loveNum + 1);
                            } else {
                                dataList.get(position).setIs_love(0);
                                dataList.get(position).setLovenum(loveNum - 1);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.img_header_back, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


}