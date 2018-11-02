package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.cloudworkshop.miaoding.bean.MemberGrowthBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/2/15 10:33
 * Email：1993911441@qq.com
 * Describe：会员成长记录
 */
public class MemberGrowthActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.rv_member_grow)
    LRecyclerView rvMemberGrow;
    @BindView(R.id.tv_null_growth)
    TextView tvNullGrowth;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    //当前页
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<MemberGrowthBean.DataBean> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_growth);
        ButterKnife.bind(this);
        getData();
        initData();

    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.MEMBER_GROWTH)
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
                        MemberGrowthBean bean = GsonUtils.jsonToBean(response, MemberGrowthBean.class);
                        if (bean.getData() != null && bean.getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(bean.getData());
                            if (isLoadMore || isRefresh) {
                                rvMemberGrow.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isLoadMore = false;
                            isRefresh = false;
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(MemberGrowthActivity.this,
                                    rvMemberGrow, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                tvNullGrowth.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

    }

    private void getData() {
        //会员成长值
        String memberScore = getIntent().getStringExtra("value");
        tvHeaderTitle.setText(R.string.my_growth);
        imgHeaderShare.setVisibility(View.VISIBLE);
        imgHeaderShare.setImageResource(R.mipmap.icon_member_rule);
        tvUserScore.setText(memberScore);
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvMemberGrow.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<MemberGrowthBean.DataBean> adapter = new CommonAdapter<MemberGrowthBean.DataBean>
                (this, R.layout.listitem_member_score, dataList) {
            @Override
            protected void convert(ViewHolder holder, MemberGrowthBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_member_title, dataBean.getName());
                holder.setText(R.id.tv_member_time, DateUtils.getDate("yyyy-MM-dd", dataBean.getC_time()));
                holder.setText(R.id.tv_member_value, "+" + dataBean.getCredit());
            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvMemberGrow.setAdapter(mLRecyclerViewAdapter);

        rvMemberGrow.setOnRefreshListener(new OnRefreshListener() {
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

        rvMemberGrow.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(MemberGrowthActivity.this, rvMemberGrow,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });

    }

    @OnClick({R.id.img_header_back, R.id.img_header_share,R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_header_share:
                startActivity(new Intent(this, MemberRuleActivity.class));
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

}
