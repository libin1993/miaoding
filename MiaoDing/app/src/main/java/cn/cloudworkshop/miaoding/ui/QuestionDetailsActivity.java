package cn.cloudworkshop.miaoding.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.QuestionDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/11/9 18:25
 * Email：1993911441@qq.com
 * Describe：问题详情
 */
public class QuestionDetailsActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_question_title)
    TextView tvQuestionTitle;
    @BindView(R.id.tv_question_content)
    TextView tvQuestionContent;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private String title;
    private String id;
    private QuestionDetailBean question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
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
                .url(Constant.QUESTION_DETAIL)
                .addParams("id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        question = GsonUtils.jsonToBean(response, QuestionDetailBean.class);
                        initView();
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvQuestionTitle.setText(question.getData().getName());
        tvQuestionContent.setText(question.getData().getContent());
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
