package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import cn.cloudworkshop.miaoding.bean.GoodsCommentBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2017-06-12 12:24
 * Email：1993911441@qq.com
 * Describe：全部评价
 */
public class AllEvaluationActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_all_evaluate)
    LRecyclerView rvComment;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;

    private String goodsId;
    private List<GoodsCommentBean.ListBean.DataBean> dataList = new ArrayList<>();

    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_evaluate);
        ButterKnife.bind(this);
        getData();
        initData();

    }


    private void getData() {
        goodsId = getIntent().getStringExtra("goods_id");
    }

    private void initData() {
        tvHeaderTitle.setText(R.string.all_evaluation);
        OkHttpUtils.get()
                .url(Constant.EVALUATE_LIST)
                .addParams("goods_id", goodsId)
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
                        GoodsCommentBean commentBean = GsonUtils.jsonToBean(response, GoodsCommentBean.class);
                        if (commentBean.getList().getData() != null && commentBean.getList().getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(commentBean.getList().getData());
                            if (isLoadMore || isRefresh) {
                                rvComment.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(AllEvaluationActivity.this,
                                    rvComment, 0, LoadingFooter.State.NoMore, null);
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<GoodsCommentBean.ListBean.DataBean> adapter = new CommonAdapter
                <GoodsCommentBean.ListBean.DataBean>(this, R.layout.listitem_goods_comment, dataList) {
            @Override
            protected void convert(ViewHolder holder, final GoodsCommentBean.ListBean.DataBean dataBean, int position) {
                Glide.with(AllEvaluationActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into((CircleImageView) holder.getView(R.id.img_user_avatar));
                holder.setText(R.id.tv_name_user, dataBean.getUser_name());
                Glide.with(AllEvaluationActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getUser_grade().getImg2())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_user_grade));
                holder.setText(R.id.tv_comment_time, DateUtils.getDate("yyyy-MM-dd", dataBean.getC_time()));
                holder.setText(R.id.tv_evaluate_content, dataBean.getContent());
                holder.setText(R.id.tv_goods_comment, dataBean.getGoods_intro());
                holder.setVisible(R.id.view_evaluate, true);
                if (dataBean.getImg_list() != null && dataBean.getImg_list().size() > 0) {
                    RecyclerView recyclerView = holder.getView(R.id.rv_evaluate_picture);

                    recyclerView.setLayoutManager(new GridLayoutManager(AllEvaluationActivity.this, 3));
                    CommonAdapter<String> adapter = new CommonAdapter<String>(AllEvaluationActivity.this,
                            R.layout.listitem_user_comment, dataBean.getImg_list()) {
                        @Override
                        protected void convert(ViewHolder holder, String s, final int position) {
                            ImageView imageView = holder.getView(R.id.img_user_comment);
                            Glide.with(AllEvaluationActivity.this)
                                    .load(Constant.IMG_HOST + s)
                                    .centerCrop()
                                    .into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(AllEvaluationActivity.this, ImagePreviewActivity.class);
                                    intent.putExtra("current_pos", position);
                                    intent.putStringArrayListExtra("img_list", dataBean.getImg_list());
                                    startActivity(intent);
                                }
                            });

                        }
                    };
                    recyclerView.setAdapter(adapter);
                }
            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvComment.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvComment.setOnRefreshListener(new OnRefreshListener() {
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
        rvComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(AllEvaluationActivity.this, rvComment, 0,
                        LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
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





