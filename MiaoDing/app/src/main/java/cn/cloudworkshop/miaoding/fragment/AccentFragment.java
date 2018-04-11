package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
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

import org.greenrobot.eventbus.EventBus;
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
import cn.cloudworkshop.miaoding.bean.HomepageNewsBean;
import cn.cloudworkshop.miaoding.bean.NewDesignWorksBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.ui.HomepageInfoActivity;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.QuickLoginActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/12/6 10:32
 * Email：1993911441@qq.com
 * Describe：腔调
 */
public class AccentFragment extends BaseFragment {
    @BindView(R.id.rv_accent)
    LRecyclerView rvAccent;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private Unbinder unbinder;

    private List<NewDesignWorksBean.DataBeanX.DataBean> worksList = new ArrayList<>();

    //页面
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;

    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CommonAdapter<NewDesignWorksBean.DataBeanX.DataBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accent, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    /**
     * 加载数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.DESIGNER_WORKS)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        NewDesignWorksBean worksBean = GsonUtils.jsonToBean(response, NewDesignWorksBean.class);
                        if (worksBean.getData().getData() != null && worksBean.getData().getData().size() > 0) {
                            if (isRefresh) {
                                worksList.clear();
                            }
                            worksList.addAll(worksBean.getData().getData());
                            if (isRefresh || isLoadMore) {
                                rvAccent.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(),
                                    rvAccent, 0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {
        rvAccent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<NewDesignWorksBean.DataBeanX.DataBean>(getActivity(),
                R.layout.listitem_accent, worksList) {
            @Override
            protected void convert(ViewHolder holder, final NewDesignWorksBean.DataBeanX.DataBean
                    itemBean, final int position) {

                SimpleDraweeView imgWorks = holder.getView(R.id.img_accent_works);
                if (!TextUtils.isEmpty(itemBean.getImg_info())) {
                    imgWorks.setAspectRatio(Float.parseFloat(itemBean.getImg_info()));
                }
                imgWorks.setImageURI(Constant.IMG_HOST + itemBean.getImg());

                SimpleDraweeView imgDesigner = holder.getView(R.id.img_designer_icon);
                imgDesigner.setImageURI(Constant.IMG_HOST + itemBean.getAvatar());
                holder.setText(R.id.tv_designer_intro, itemBean.getIntroduce());
                holder.setText(R.id.tv_designer_nickname, itemBean.getUname());
                holder.setText(R.id.tv_designer_workshop, "/" + itemBean.getTag());

                ImageView imgShare = (ImageView) holder.itemView.findViewById(R.id.img_accent_share);
                ImageView imgCollect = (ImageView) holder.itemView.findViewById(R.id.img_accent_collect);
                LinearLayout llLove = (LinearLayout) holder.itemView.findViewById(R.id.ll_accent_love);
                ImageView imgLove = (ImageView) holder.itemView.findViewById(R.id.img_accent_love);
                TextView tvLove = (TextView) holder.itemView.findViewById(R.id.tv_accent_love);

                tvLove.setText(String.valueOf(itemBean.getLove_num()));

                if (itemBean.getIs_collect() == 1) {
                    imgCollect.setImageResource(R.mipmap.icon_collect_check);
                } else {
                    imgCollect.setImageResource(R.mipmap.icon_collect_normal);
                }
                if (itemBean.getIs_love() == 1) {
                    imgLove.setImageResource(R.mipmap.icon_love_check);
                } else {
                    imgLove.setImageResource(R.mipmap.icon_love_normal);
                }

                LinearLayout llWorks = (LinearLayout) holder.itemView.findViewById(R.id.ll_accent_works);
                LinearLayout llDesigner = (LinearLayout) holder.itemView.findViewById(R.id.ll_accent_designer);

                llWorks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), WorksDetailActivity.class);
                        intent.putExtra("id", String.valueOf(itemBean.getGoods_id()));
                        startActivity(intent);
                    }
                });

                llDesigner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DesignerDetailActivity.class);
                        intent.putExtra("id", String.valueOf(itemBean.getDes_uid()));
                        startActivity(intent);
                    }
                });

                imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String share_url = Constant.WORKS_SHARE + "?goods_id=" + itemBean.getGoods_id() + "&type=2";
                        String uid = SharedPreferencesUtils.getStr(getActivity(), "uid");
                        if (!TextUtils.isEmpty(uid)) {
                            share_url += "&shareout_id=" + uid;
                        }
                        ShareUtils.showShare(getActivity(), Constant.IMG_HOST + itemBean.getImg(),
                                itemBean.getTitle(), itemBean.getUname(), share_url);
                    }
                });

                imgCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addCollection(itemBean, position - 1);
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "收藏");
                            startActivity(login);
                        }

                    }
                });

                llLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                            addLove(itemBean, position - 1, itemBean.getLove_num());
                        } else {
                            Intent login = new Intent(getActivity(), LoginActivity.class);
                            login.putExtra("page_name", "喜爱");
                            startActivity(login);
                        }

                    }
                });

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvAccent.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvAccent.setOnRefreshListener(new OnRefreshListener() {
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
        rvAccent.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvAccent,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });

    }

    /**
     * @param dataBean 添加收藏
     */
    private void addCollection(NewDesignWorksBean.DataBeanX.DataBean dataBean, final int position) {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("type", "2")
                .addParams("cid", String.valueOf(dataBean.getGoods_id()))
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
                                worksList.get(position).setIs_collect(1);
                            } else {
                                worksList.get(position).setIs_collect(0);
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
    private void addLove(NewDesignWorksBean.DataBeanX.DataBean dataBean, final int position, final int loveNum) {
        OkHttpUtils.get()
                .url(Constant.ADD_LOVE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("news_id", String.valueOf(dataBean.getGoods_id()))
                .addParams("is_type", "2")
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
                                worksList.get(position).setIs_love(1);
                                worksList.get(position).setLove_num(loveNum + 1);
                            } else {
                                worksList.get(position).setIs_love(0);
                                worksList.get(position).setLove_num(loveNum - 1);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static AccentFragment newInstance() {
        Bundle args = new Bundle();
        AccentFragment fragment = new AccentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick(R.id.img_load_error)
    public void onViewClicked() {
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
