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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.QuestionClassifyBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/11/9 17:43
 * Email：1993911441@qq.com
 * Describe：常见问题
 */
public class CommonQuestionActivity extends BaseActivity {

    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_common_question)
    RecyclerView rvQuestion;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    private String id;
    private String title;

    private List<QuestionClassifyBean.DataBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_question);
        ButterKnife.bind(this);
        getData();
        initData();

    }

    /**
     * 加载数据
     */
    private void initData() {
        tvHeaderTitle.setText(title);
        OkHttpUtils.get()
                .url(Constant.QUESTION_LIST)
                .addParams("classify_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       imgLoadingError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        QuestionClassifyBean question = GsonUtils.jsonToBean(response, QuestionClassifyBean.class);
                        if (question.getData() != null) {
                            dataList.addAll(question.getData());
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvQuestion.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<QuestionClassifyBean.DataBean> adapter = new CommonAdapter<QuestionClassifyBean.DataBean>
                (this, R.layout.listitem_common_question, dataList) {
            @Override
            protected void convert(ViewHolder holder, QuestionClassifyBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_common_question, dataBean.getName());
            }
        };
        rvQuestion.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(CommonQuestionActivity.this, QuestionDetailsActivity.class);
                intent.putExtra("id", String.valueOf(dataList.get(position).getId()));
                intent.putExtra("title", dataList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void getData() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
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
