package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ConfirmOrderBean;
import cn.cloudworkshop.miaoding.bean.MeasureDataBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2018/4/24 15:57
 * Email：1993911441@qq.com
 * Describe：量体数据
 */
public class MeasureDataActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_measure_user)
    LRecyclerView rvMeasure;
    @BindView(R.id.rl_add_measure)
    RelativeLayout rlAddMeasure;
    @BindView(R.id.img_null_measure)
    ImageView imgNullMeasure;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //1：量体数据列表 2:选择量体数据
    private int type;
    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private List<MeasureDataBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CommonAdapter<MeasureDataBean.DataBeanX.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_data);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initData();
    }

    private void getData() {
        type = getIntent().getIntExtra("type", 0);
    }

    private void initData() {
        switch (type) {
            case 1:
                tvHeaderTitle.setText("量体数据");
                break;
            case 2:
                tvHeaderTitle.setText("选择量体数据");
                break;
        }
        OkHttpUtils.get()
                .url(Constant.MEASURE_DATA)
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
                        MeasureDataBean measureBean = GsonUtils.jsonToBean(response, MeasureDataBean.class);
                        if (measureBean.getData().getData() != null && measureBean.getData().getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(measureBean.getData().getData());
                            if (isRefresh || isLoadMore) {
                                rvMeasure.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                            imgNullMeasure.setVisibility(View.GONE);
                            rvMeasure.setVisibility(View.VISIBLE);
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(MeasureDataActivity.this,
                                    rvMeasure, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                rvMeasure.setVisibility(View.GONE);
                                imgNullMeasure.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    private void initView() {
        rvMeasure.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<MeasureDataBean
                .DataBeanX.DataBean>(this, R.layout.listitem_measure_data, dataList) {
            @Override
            protected void convert(ViewHolder holder, MeasureDataBean.DataBeanX.DataBean dataBean, final int position) {
                holder.setText(R.id.tv_name_measure, dataBean.getName());
                holder.setText(R.id.tv_height_measure, dataBean.getHeight() + "cm");
                holder.setText(R.id.tv_weight_measure, dataBean.getWeight() + "kg");

                TextView tvDefault = holder.getView(R.id.tv_default_measure);

                if (dataBean.getIs_index() == 1) {
                    Drawable leftDrawable = ContextCompat.getDrawable(MeasureDataActivity.this,
                            R.mipmap.icon_default_address);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
                            leftDrawable.getMinimumHeight());
                    tvDefault.setCompoundDrawables(leftDrawable, null, null, null);
                } else if (dataBean.getIs_index() == 0) {
                    Drawable leftDrawable = ContextCompat.getDrawable(MeasureDataActivity.this,
                            R.mipmap.icon_not_default);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
                            leftDrawable.getMinimumHeight());
                    tvDefault.setCompoundDrawables(leftDrawable, null, null, null);
                }

                tvDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataList.get(position - 1).getIs_index() == 0) {
                            setDefaultMeasure(dataList.get(position - 1).getId(), position - 1);
                        }
                    }
                });

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvMeasure.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvMeasure.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                initData();
            }
        });

        //加载更多
        rvMeasure.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(MeasureDataActivity.this,
                        rvMeasure, 0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (type == 1) {
                    Intent intent = new Intent(MeasureDataActivity.this, MeasureDetailActivity.class);
                    intent.putExtra("id", dataList.get(position).getId());
                    startActivity(intent);
                } else if (type == 2) {
                    selectMeasureData(position);
                    finish();
                }
            }
        });
    }

    /**
     * 设置默认地址
     */
    private void setDefaultMeasure(int id, final int position) {
        OkHttpUtils.post()
                .url(Constant.DEFAULT_MEASURE_DATA)
                .addParams("token", SharedPreferencesUtils.getStr(
                        MeasureDataActivity.this, "token"))
                .addParams("lt_id", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        for (int i = 0; i < dataList.size(); i++) {
                            if (i == position) {
                                dataList.get(i).setIs_index(1);
                            } else {
                                dataList.get(i).setIs_index(0);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }


    /**
     * @param position 选择量体数据
     */
    private void selectMeasureData(int position) {
        ConfirmOrderBean.LtArrBean measureBean = new ConfirmOrderBean.LtArrBean();
        measureBean.setId(dataList.get(position).getId());
        measureBean.setName(dataList.get(position).getName());
        measureBean.setHeight(dataList.get(position).getHeight());
        measureBean.setWeight(dataList.get(position).getWeight());
        EventBus.getDefault().post(measureBean);
    }


    @OnClick({R.id.img_header_back, R.id.rl_add_measure, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.rl_add_measure:
                Intent intent = new Intent(this, AddMeasureActivity.class);
                //是否有量体数据 1：无数据 2:有数据
                int measureStatus;
                if (dataList.size() > 0) {
                    measureStatus = 2;
                } else {
                    measureStatus = 1;
                }
                intent.putExtra("measure_status", measureStatus);
                startActivity(intent);
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    @Subscribe
    public void addSuccess(String msg) {
        if ("add_success".equals(msg)) {
            page = 1;
            dataList.clear();
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
