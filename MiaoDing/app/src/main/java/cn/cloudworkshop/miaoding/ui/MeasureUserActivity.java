package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.MeasureDataBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/1/6 16:20
 * Email：1993911441@qq.com
 * Describe：用户量体列表
 */
public class MeasureUserActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_measure_data)
    RecyclerView rvMeasureData;
    @BindView(R.id.img_null_data)
    ImageView imgNullData;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private List<List<MeasureDataBean.DataBean>> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_data);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tvHeaderTitle.setText("量体数据");
        OkHttpUtils.get()
                .url(Constant.MEASURE_DATA)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
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
                        if (measureBean.getData() != null && measureBean.getData().size() > 0) {
                            dataList.addAll(measureBean.getData());
                            initView();
                        } else {
                            imgNullData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvMeasureData.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<List<MeasureDataBean.DataBean>> adapter = new CommonAdapter<List<MeasureDataBean.DataBean>>
                (this, R.layout.listitem_measure_user, dataList) {
            @Override
            protected void convert(ViewHolder holder, List<MeasureDataBean.DataBean> dataBean, int position) {
                for (int i = 0; i < dataList.get(position).size(); i++) {
                    if (dataList.get(position).get(i).getName().equals("姓名")) {
                        holder.setText(R.id.tv_measure_user, dataBean.get(i).getValue());
                    }
                }
            }
        };
        rvMeasureData.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(MeasureUserActivity.this, MeasureDataActivity.class);
                intent.putExtra("measure_data", (Serializable) dataList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
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