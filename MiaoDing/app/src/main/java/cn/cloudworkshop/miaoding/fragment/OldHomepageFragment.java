package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.SectionedRVAdapter;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.HomepageNewsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.DressingResultActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.JoinUsActivity;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-04-06 09:59
 * Email：1993911441@qq.com
 * Describe：首页资讯（老版）
 */
public class OldHomepageFragment extends BaseFragment implements SectionedRVAdapter.SectionTitle {
    @BindView(R.id.rv_recommend)
    LRecyclerView mRecyclerView;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
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
    private SectionedRVAdapter sectionedAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
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
                        homepageBean = GsonUtils.jsonToBean(response, HomepageNewsBean.class);
                        if (homepageBean.getData() != null && homepageBean.getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            for (int i = 0; i < homepageBean.getData().size(); i++) {
                                for (int j = 0; j < homepageBean.getData().get(i).size(); j++) {
                                    HomepageNewsBean.DataBean dataBean = homepageBean.getData().get(i).get(j);
                                    dataBean.setImg(Constant.IMG_HOST + dataBean.getImg());
                                    dataBean.setLink(Constant.HOMEPAGE_INFO + "?content=1&id=" + dataBean.getId());
                                    dataList.add(dataBean);
                                }
                            }

                            if (isLoadMore || isRefresh) {
                                mRecyclerView.refreshComplete(0);
                                sectionedAdapter.setSections(dataList);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isLoadMore = false;
                            isRefresh = false;

                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
                                    0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    protected void initView() {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            CommonAdapter<HomepageNewsBean.DataBean> adapter = new CommonAdapter<HomepageNewsBean
                    .DataBean>(getActivity(), R.layout.listitem_homepage_new, dataList) {
                @Override
                protected void convert(ViewHolder holder, final HomepageNewsBean.DataBean dataBean, int position) {
                    Glide.with(getActivity())
                            .load(dataBean.getImg())
                            .placeholder(R.mipmap.place_holder_banner)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into((ImageView) holder.getView(R.id.img_home_item));

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            homepageLog(dataBean.getTag_name());

                            Intent intent = new Intent(getActivity(), HomepageInfoActivity.class);
                            intent.putExtra("url", Constant.HOMEPAGE_INFO + "?content=1&id=" + dataBean.getId());
                            intent.putExtra("title", dataBean.getTitle());
                            intent.putExtra("content", dataBean.getSub_title());
                            intent.putExtra("img_url", dataBean.getImg());
                            intent.putExtra("share_url", Constant.HOMEPAGE_SHARE
                                    + "?content=1&id=" + dataBean.getId());
                            startActivity(intent);
                        }
                    });
                }
            };
            sectionedAdapter = new SectionedRVAdapter(getActivity(), R.layout.listitem_homepage_title_new,
                    R.id.tv_list_title, adapter, this);
            sectionedAdapter.setSections(dataList);

            mLRecyclerViewAdapter = new LRecyclerViewAdapter(sectionedAdapter);
            mRecyclerView.setAdapter(mLRecyclerViewAdapter);
            mLRecyclerViewAdapter.addHeaderView(initHeader());

            mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
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

            mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 0,
                            LoadingFooter.State.Loading, null);
                    isLoadMore = true;
                    page++;
                    initData();
                }
            });

        }
    }


    /**
     * 加载头部
     */
    private View initHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.listitem_homepage_header, null);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        if (homepageBean.getLunbo() != null && homepageBean.getLunbo().size() > 0) {
            final ConvenientBanner banner = (ConvenientBanner) view.findViewById(R.id.banner_homepage);
            banner.startTurning(4000);
            final List<String> bannerImg = new ArrayList<>();
            for (int i = 0; i < homepageBean.getLunbo().size(); i++) {
                bannerImg.add(homepageBean.getLunbo().get(i).getImg());
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
                                    .get(position).getImg());
                            intent.putExtra("share_url", Constant.HOST + homepageBean.getLunbo()
                                    .get(position).getShare_link());
                            startActivity(intent);
                            break;
                        //设计师申请入驻
                        case 2:
                            startActivity(new Intent(getActivity(), JoinUsActivity.class));
                            break;
                        //邀请好友
                        case 6:
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                                String uid = SharedPreferencesUtils.getStr(getActivity(), "uid");
                                Intent invite = new Intent(getActivity(), DressingResultActivity.class);
                                invite.putExtra("title", getString(R.string.invite_gift));
                                invite.putExtra("share_title",  getString(R.string.invite_gift));
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


        RecyclerView rvGoods = (RecyclerView) view.findViewById(R.id.rv_recommend_goods);
        rvGoods.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        CommonAdapter<HomepageNewsBean.RecommendListBean> adapter = new CommonAdapter
                <HomepageNewsBean.RecommendListBean>(getActivity(), R.layout.listitem_pop_goods,
                homepageBean.getRecommend_list()) {
            @Override
            protected void convert(ViewHolder holder, HomepageNewsBean.RecommendListBean recommendListBean, int position) {

                Glide.with(getActivity())
                        .load(Constant.IMG_HOST + recommendListBean.getRecommend_img())
                        .placeholder(R.mipmap.place_holder_banner)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_pop_goods));
            }
        };

        rvGoods.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = null;
                switch (homepageBean.getRecommend_list().get(position).getGoods_type()) {
                    case 1:
                        intent = new Intent(getActivity(), CustomizedGoodsActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), WorksDetailActivity.class);
                        break;
                }
                intent.putExtra("id", String.valueOf(homepageBean.getRecommend_list().get(position).getGoods_id()));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        return view;
    }


    @Override
    public String getSectionTitle(Object object) {
        return ((HomepageNewsBean.DataBean) object).getP_time();
    }


    public static OldHomepageFragment newInstance() {
        Bundle args = new Bundle();
        OldHomepageFragment fragment = new OldHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 首页跟踪
     */
    private void homepageLog(String module_name) {
        long time = DateUtils.getCurrentTime() - MyApplication.homeEnterTime;
        OkHttpUtils.post()
                .url(Constant.HOMEPAGE_LOG)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("time", time + "")
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


    @OnClick(R.id.img_load_error)
    public void onViewClicked() {
        initData();
    }
}
