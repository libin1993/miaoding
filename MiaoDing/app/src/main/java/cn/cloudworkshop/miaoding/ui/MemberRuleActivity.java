package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import cn.cloudworkshop.miaoding.bean.MemberRuleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/2/15 10:21
 * Email：1993911441@qq.com
 * Describe：会员制度
 */
public class MemberRuleActivity extends BaseActivity {

    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_member_rule)
    RecyclerView rvMemberRule;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private List<MemberRuleBean.DataBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_rule);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tvHeaderTitle.setText("会员制度");
        OkHttpUtils.get()
                .url(Constant.MEMBER_RULE)
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
                        MemberRuleBean bean = GsonUtils.jsonToBean(response, MemberRuleBean.class);
                        if (bean.getData() != null && bean.getData().size() > 0) {
                            dataList.addAll(bean.getData());
                            initView();
                        }

                    }
                });

    }

    private void initView() {

        rvMemberRule.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<MemberRuleBean.DataBean> adapter = new CommonAdapter<MemberRuleBean.DataBean>
                (this, R.layout.listitem_member_rule, dataList) {
            @Override
            protected void convert(ViewHolder holder, MemberRuleBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_member_rule, dataBean.getName());
            }

        };
        rvMemberRule.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!TextUtils.isEmpty(dataList.get(position).getImg_list().get(0))) {
                    Intent intent = new Intent(MemberRuleActivity.this, UserRuleActivity.class);
                    intent.putExtra("title", dataList.get(position).getName());
                    intent.putExtra("img_url", dataList.get(position).getImg_list().get(0));
                    startActivity(intent);
                }

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
