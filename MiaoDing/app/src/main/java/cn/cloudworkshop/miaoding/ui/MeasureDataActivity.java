package cn.cloudworkshop.miaoding.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.MeasureDataBean;


/**
 * Author：Libin on 2017/1/6 16:20
 * Email：1993911441@qq.com
 * Describe：量体数据
 */
public class MeasureDataActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_measure_data)
    RecyclerView rvMeasureData;
    private List<MeasureDataBean.DataBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_data);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        dataList = (List<MeasureDataBean.DataBean>) getIntent().getSerializableExtra("measure_data");
        initView();
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText("量体详情");
        rvMeasureData.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<MeasureDataBean.DataBean> adapter = new CommonAdapter<MeasureDataBean.DataBean>
                (this, R.layout.listitem_measure_data, dataList) {
            @Override
            protected void convert(ViewHolder holder, MeasureDataBean.DataBean measureDataBean, int position) {
                holder.setText(R.id.tv_measure_name, measureDataBean.getName());
                holder.setText(R.id.tv_measure_value, String.valueOf(measureDataBean.getValue()));
            }
        };
        rvMeasureData.setAdapter(adapter);
    }

    @OnClick(R.id.img_header_back)
    public void onClick() {
        finish();
    }
}
