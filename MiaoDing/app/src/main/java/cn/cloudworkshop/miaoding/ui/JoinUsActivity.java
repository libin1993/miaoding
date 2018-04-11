package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.JoinUsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 10:50
 * Email：1993911441@qq.com
 * Describe：加入我们
 */
public class JoinUsActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_apply_join)
    TextView tvApplyJoin;
    @BindView(R.id.img_join_us)
    ImageView imgJoinUs;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private JoinUsBean joinUsBean;

    //是否已申请入驻
    private int isApply = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_us);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("设计师入驻");
        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.JOIN_US)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", "3")
                .addParams("phone", SharedPreferencesUtils.getStr(this, "phone"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        joinUsBean = GsonUtils.jsonToBean(response, JoinUsBean.class);
                        if (joinUsBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    private void initView() {
        isApply = joinUsBean.getData().getIs_apply();
        if (joinUsBean.getData().getImg_list() != null && joinUsBean.getData().getImg_list().size() > 0) {
            Glide.with(getApplicationContext())
                    .load(Constant.IMG_HOST + joinUsBean.getData().getImg_list().get(0).getImg())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imgJoinUs);
        }

    }

    @OnClick({R.id.img_header_back, R.id.tv_apply_join, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_apply_join:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    if (joinUsBean.getData() != null) {
                        if (isApply == 0) {
                            startActivity(new Intent(this, ApplyJoinActivity.class));
                        } else {
                            ToastUtils.showToast(this, "您已经提交申请，请耐心等待");
                        }
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.img_load_error:
                initData();
                break;
        }

    }

}


