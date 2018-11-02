package cn.cloudworkshop.miaoding.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.MeasureDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;


/**
 * Author：Libin on 2017/1/6 16:20
 * Email：1993911441@qq.com
 * Describe：量体数据详情
 */
public class MeasureDetailActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_measure_data)
    RecyclerView rvMeasureData;
    private MeasureDetailBean measureBean;

    //量体id
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_detail);
        ButterKnife.bind(this);
        getData();
        initData();

    }


    private void getData() {
        id = getIntent().getIntExtra("id", 0);
        tvHeaderTitle.setText(R.string.measure_detail);
    }


    /**
     * 加载量体数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.MEASURE_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("lt_id", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        measureBean = GsonUtils.jsonToBean(response, MeasureDetailBean.class);
                        if (measureBean.getData() != null && measureBean.getData().size() > 0) {
                            initView();
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {
        rvMeasureData.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<MeasureDetailBean.DataBean> adapter = new CommonAdapter<MeasureDetailBean.DataBean>
                (this, R.layout.listitem_measure_detail, measureBean.getData()) {
            @Override
            protected void convert(ViewHolder holder, MeasureDetailBean.DataBean measureDataBean, int position) {
                holder.setText(R.id.tv_measure_name, measureDataBean.getName());
                String value = measureDataBean.getValue();
                if (!TextUtils.isEmpty(value)) {
                    holder.setText(R.id.tv_measure_value, value);
                } else {
                    holder.setText(R.id.tv_measure_value, "");
                }
            }
        };
        rvMeasureData.setAdapter(adapter);
    }

    @OnClick(R.id.img_header_back)
    public void onClick() {
        finish();
    }
}
