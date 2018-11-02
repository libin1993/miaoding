package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.QuestionClassifyBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2016/11/9 17:09
 * Email：1993911441@qq.com
 * Describe：帮助与反馈
 */
public class UserHelpActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.rv_question_classify)
    RecyclerView rvQuestion;
    @BindView(R.id.ll_service_phone)
    LinearLayout llServicePhone;
    @BindView(R.id.ll_service_consult)
    LinearLayout llServiceConsult;
    @BindView(R.id.tv_server_phone)
    TextView tvServerPhone;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private List<QuestionClassifyBean.DataBean> dataList = new ArrayList<>();


    static final String[] permissionStr = {Manifest.permission.CALL_PHONE,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 加载数据
     */
    private void initData() {
        tvHeaderTitle.setText(R.string.feed_back);
        tvServerPhone.setText(MyApplication.serverPhone);
        OkHttpUtils.get()
                .url(Constant.QUESTION_CLASSIFY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setBackgroundColor(Color.WHITE);
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        QuestionClassifyBean question = GsonUtils.jsonToBean(response,
                                QuestionClassifyBean.class);
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
        CommonAdapter<QuestionClassifyBean.DataBean> adapter = new CommonAdapter
                <QuestionClassifyBean.DataBean>(this, R.layout.listitem_common_question, dataList) {
            @Override
            protected void convert(ViewHolder holder, QuestionClassifyBean.DataBean dataBean, int position) {
                holder.setVisible(R.id.view_question, true);
                holder.setText(R.id.tv_common_question, dataBean.getName());
            }
        };
        rvQuestion.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(UserHelpActivity.this, CommonQuestionActivity.class);
                intent.putExtra("id", dataList.get(position).getId() + "");
                intent.putExtra("title", dataList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.img_header_back, R.id.tv_feedback, R.id.ll_service_phone, R.id.ll_service_consult,
            R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.ll_service_phone:
                callPhone();
                break;
            case R.id.ll_service_consult:
                ContactService.contactService(this);
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 电话联系客服
     */
    private void callPhone() {
        if (!EasyPermissions.hasPermissions(this, permissionStr)) {
            EasyPermissions.requestPermissions(this, "", 123, permissionStr);
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + MyApplication.serverPhone));
            startActivity(intent);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, getString(R.string.feed_back));
    }
}
