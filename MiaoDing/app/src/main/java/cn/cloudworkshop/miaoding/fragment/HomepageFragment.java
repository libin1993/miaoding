package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
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
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.HomepageNewsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.ui.DressingResultActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.JoinUsActivity;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.NewsListActivity;
import cn.cloudworkshop.miaoding.ui.StoreInfoActivity;
import cn.cloudworkshop.miaoding.ui.StoreListActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/6 13:13
 * Email：1993911441@qq.com
 * Describe：首页（当前版）
 */
public class HomepageFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_news)
    LRecyclerView rvNews;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private Unbinder unbinder;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private List<HomepageNewsBean.DataBean> dataList = new ArrayList<>();
    private HomepageNewsBean homepageBean;
    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<HomepageNewsBean.DataBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.HOMEPAGE_LIST)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (imgLoadError != null) {
                            imgLoadError.setVisibility(View.GONE);
                        }
                        homepageBean = GsonUtils.jsonToBean(response, HomepageNewsBean.class);
                        if (homepageBean.getData() != null && homepageBean.getData().size() > 0) {
                            //刷新，初始化数据
                            if (isRefresh) {
                                dataList.clear();
                            }
                            for (int i = 0; i < homepageBean.getData().size(); i++) {
                                dataList.addAll(homepageBean.getData().get(i));
                            }
                            //刷新，加载完成
                            if (isLoadMore || isRefresh) {
                                rvNews.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isLoadMore = false;
                            isRefresh = false;

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(), rvNews,
                                    0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<HomepageNewsBean.DataBean>(getActivity(),
                R.layout.listitem_homepage_news, dataList) {
            @Override
            protected void convert(ViewHolder holder, final HomepageNewsBean.DataBean dataBean, final int position) {

                SimpleDraweeView imgNews = holder.getView(R.id.img_homepage_news);
                if (!TextUtils.isEmpty(dataBean.getImg_info())) {
                    imgNews.setAspectRatio(Float.parseFloat(dataBean.getImg_info()));
                }
                imgNews.setImageURI(Constant.IMG_HOST + dataBean.getImg_new());

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
                        switch (dataBean.getIs_type()) {
                            case 1:
                                toHomepageInfo(dataBean);
                                break;
                            case 3:
                                Intent intent = new Intent(getActivity(), StoreInfoActivity.class);
                                intent.putExtra("shop_id", dataBean.getId());
                                startActivity(intent);
                                break;
                        }
                    }
                });
                //分享

                if (dataBean.getIs_type() == 1) {
                    imgShare.setVisibility(View.VISIBLE);
                    imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dataBean.getIs_type() == 1) {
                                ShareUtils.showShare(getActivity(), Constant.IMG_HOST +
                                        dataBean.getImg_new(), dataBean.getTitle(), dataBean
                                        .getSub_title(), Constant.HOMEPAGE_SHARE +
                                        "?content=1&id=" + dataBean.getId());
                            }
                        }
                    });
                } else {
                    imgShare.setVisibility(View.GONE);
                }


                //收藏
                imgCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addCollection(dataBean, position - 2);
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "收藏");
                            startActivity(login);
                        }

                    }
                });
                //喜爱
                llLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addLove(dataBean, position - 2, dataBean.getLovenum());
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "喜爱");
                            startActivity(login);
                        }

                    }
                });
                //店铺隐藏评论
                if (dataBean.getIs_type() == 1) {
                    llComment.setVisibility(View.VISIBLE);
                    llComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toHomepageInfo(dataBean);
                        }
                    });
                } else {
                    llComment.setVisibility(View.GONE);
                }

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvNews.setAdapter(mLRecyclerViewAdapter);
        //添加头部
        mLRecyclerViewAdapter.addHeaderView(initHeader());
        //刷新
        rvNews.setOnRefreshListener(new OnRefreshListener() {
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
        rvNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvNews, 0,
                        LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });
    }

    /**
     * @param dataBean 跳转资讯详情
     */
    private void toHomepageInfo(HomepageNewsBean.DataBean dataBean) {
        homepageLog(dataBean.getTag_name());

        Intent intent = new Intent(getActivity(), HomepageInfoActivity.class);
        intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id=" + dataBean.getId());
        intent.putExtra("title", dataBean.getTitle());
        intent.putExtra("content", dataBean.getSub_title());
        intent.putExtra("img_url", Constant.IMG_HOST + dataBean.getImg_new());
        intent.putExtra("share_url", Constant.HOMEPAGE_SHARE + "?content=1&id="
                + dataBean.getId());
        startActivity(intent);
    }

    /**
     * @param dataBean 添加收藏
     */
    private void addCollection(HomepageNewsBean.DataBean dataBean, final int position) {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
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
    private void addLove(HomepageNewsBean.DataBean dataBean, final int position, final int loveNum) {
        OkHttpUtils.get()
                .url(Constant.ADD_LOVE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
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


    /**
     * 加载头部
     */
    private View initHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_homepage_header, null);
        if (homepageBean.getLunbo() != null && homepageBean.getLunbo().size() > 0) {
            final ConvenientBanner banner = (ConvenientBanner) view.findViewById(R.id.banner_news);
            //设置banner宽高固定比
            ViewGroup.LayoutParams layoutParams1 = banner.getLayoutParams();
            int widthPixels = DisplayUtils.getMetrics(getActivity()).widthPixels;
            layoutParams1.width = widthPixels;
            String imgRatio = homepageBean.getLunbo().get(0).getImg_info();
            if (!TextUtils.isEmpty(imgRatio)) {
                layoutParams1.height = (int) (widthPixels / Float.parseFloat(imgRatio));
                banner.setLayoutParams(layoutParams1);
            }

            banner.startTurning(4000);
            final List<String> bannerImg = new ArrayList<>();
            for (int i = 0; i < homepageBean.getLunbo().size(); i++) {
                bannerImg.add(homepageBean.getLunbo().get(i).getImg_new());
            }

            banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, bannerImg)
                    //设置两个点图片作为翻页指示器
                    .setPageIndicator(new int[]{R.drawable.indicator_normal, R.drawable.indicator_focus})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);


            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (homepageBean.getLunbo().size() < 2) {
                        banner.stopTurning();
                    }
                }
            });


            banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    homepageLog("banner");
                    //banner点击事件统计
                    MobclickAgent.onEvent(getActivity(), "banner");
                    switch (homepageBean.getLunbo().get(position).getBanner_type()) {
                        //咨询页webview
                        case 1:
                            Intent intent = new Intent(getActivity(), HomepageInfoActivity.class);
                            intent.putExtra("url", Constant.HOST + homepageBean.getLunbo()
                                    .get(position).getLink());
                            intent.putExtra("title", homepageBean.getLunbo().get(position).getTitle());
                            intent.putExtra("content", "");
                            intent.putExtra("img_url", Constant.IMG_HOST + homepageBean.getLunbo()
                                    .get(position).getImg_new());
                            intent.putExtra("share_url", Constant.HOST + homepageBean.getLunbo()
                                    .get(position).getShare_link());
                            startActivity(intent);
                            break;
                        //设计师申请入驻
                        case 2:
                            startActivity(new Intent(getActivity(), JoinUsActivity.class));
                            break;
                        //定制商品
                        case 3:
                            Intent intent1 = new Intent(getActivity(), CustomizedGoodsActivity.class);
                            intent1.putExtra("id", String.valueOf(homepageBean.getLunbo()
                                    .get(position).getRelation_id()));
                            startActivity(intent1);
                            break;
                        //成品
                        case 4:
                            Intent intent2 = new Intent(getActivity(), WorksDetailActivity.class);
                            intent2.putExtra("id", String.valueOf(homepageBean.getLunbo()
                                    .get(position).getRelation_id()));
                            startActivity(intent2);
                            break;
                        //设计师详情
                        case 5:
                            Intent intent3 = new Intent(getActivity(), DesignerDetailActivity.class);
                            intent3.putExtra("id", String.valueOf(homepageBean.getLunbo()
                                    .get(position).getRelation_id()));
                            startActivity(intent3);
                            break;
                        //邀请好友
                        case 6:
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                                String uid = SharedPreferencesUtils.getStr(getActivity(), "uid");
                                Intent invite = new Intent(getActivity(), DressingResultActivity.class);
                                invite.putExtra("title", getString(R.string.invite_gift));
                                invite.putExtra("share_title", getString(R.string.invite_gift));
                                invite.putExtra("share_content", getString(R.string.friend) + SharedPreferencesUtils
                                        .getStr(getActivity(), "username") + getString(R.string.invite_join));
                                invite.putExtra("url", Constant.HOST + homepageBean.getLunbo()
                                        .get(position).getLink() + uid);
                                invite.putExtra("share_url", Constant.INVITE_SHARE + "?id=" + uid);
                                startActivity(invite);
                            } else {
                                Intent login = new Intent(getActivity(), LoginActivity.class);
                                login.putExtra("page_name", "banner");
                                startActivity(login);
                            }
                            break;
                    }
                }
            });
        }


        RecyclerView rvGoods = (RecyclerView) view.findViewById(R.id.rv_news_recommend);
        rvGoods.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //布局宽高
        int widthPixels = DisplayUtils.getMetrics(getActivity()).widthPixels;
        final int width = (int) ((widthPixels - DisplayUtils.dp2px(getActivity(), 36)) / 2);
        final int height = (int) (width * 105 / 169.5);
        CommonAdapter<HomepageNewsBean.Indextype> adapter = new CommonAdapter<HomepageNewsBean
                .Indextype>(getActivity(), R.layout.listitem_recommend_shop, homepageBean.getIndextype()) {
            @Override
            protected void convert(ViewHolder holder, HomepageNewsBean.Indextype indextype, int position) {
                SimpleDraweeView imgShop = holder.getView(R.id.img_recommend_shop);

                ViewGroup.LayoutParams layoutParams = imgShop.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = height;
                imgShop.setLayoutParams(layoutParams);

                GenericDraweeHierarchy hierarchy = imgShop.getHierarchy();
                imgShop.setHierarchy(hierarchy);
                imgShop.setImageURI(Constant.IMG_HOST + indextype.getImg());
            }

        };

        rvGoods.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = null;
                HomepageNewsBean.Indextype indextype = homepageBean.getIndextype().get(position);
                switch (indextype.getIs_type()) {
                    //资讯
                    case 1:
                        intent = new Intent(getActivity(), NewsListActivity.class);
                        break;
                    //商品
                    case 2:
                        if (indextype.getGoods_type() == 1) {
                            intent = new Intent(getActivity(), CustomizedGoodsActivity.class);
                            intent.putExtra("id", String.valueOf(indextype.getGoods_id()));
                        } else if (indextype.getGoods_type() == 2) {
                            intent = new Intent(getActivity(), WorksDetailActivity.class);
                            intent.putExtra("id", String.valueOf(indextype.getGoods_id()));
                        }
                        break;
                    //店铺
                    case 3:
                        intent = new Intent(getActivity(), StoreListActivity.class);
                        break;
                }
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return view;
    }


    /**
     * 首页跟踪
     */
    private void homepageLog(String module_name) {
        long time = DateUtils.getCurrentTime() - MyApplication.homeEnterTime;
        OkHttpUtils.post()
                .url(Constant.HOMEPAGE_LOG)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("time", String.valueOf(time))
                .addParams("p_module_name", "首页")
                .addParams("module_name", module_name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });

    }


    public static HomepageFragment newInstance() {

        Bundle args = new Bundle();

        HomepageFragment fragment = new HomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_load_error)
    public void onViewClicked() {
        initData();
    }

}
