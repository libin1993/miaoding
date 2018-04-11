package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import cn.cloudworkshop.miaoding.bean.MsgDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/12/23 11:05
 * Email：1993911441@qq.com
 * Describe：消息详情
 */
public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_message_detail)
    RecyclerView rvMessage;
    @BindView(R.id.tv_no_message)
    TextView tvNoneMsg;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private int type;
    //消息详情布局id
    private int layoutId;
    private List<MsgDetailBean.DataBean> msgList = new ArrayList<>();
    //物流字段分割
    private String[] logisticsStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        getData();
        initData();
    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.MESSAGE_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", type + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        MsgDetailBean msgBean = GsonUtils.jsonToBean(response, MsgDetailBean.class);
                        if (msgBean.getData() != null && msgBean.getData().size() > 0) {
                            msgList.addAll(msgBean.getData());
                            tvNoneMsg.setVisibility(View.GONE);
                            initView(layoutId);
                        } else {
                            tvNoneMsg.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    /**
     * @param layoutId 加载视图
     */
    private void initView(int layoutId) {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<MsgDetailBean.DataBean> adapter = new CommonAdapter<MsgDetailBean.DataBean>(this, layoutId, msgList) {
            @Override
            protected void convert(ViewHolder holder, MsgDetailBean.DataBean dataBean, int position) {
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_notice_time, DateUtils.getDate("yyyy-MM-dd HH:mm:ss",
                                dataBean.getC_time()));
                        holder.setText(R.id.tv_notice_content, dataBean.getContent());
                        holder.setText(R.id.tv_notice_date, DateUtils.getDate("yyyy年MM月dd日",
                                dataBean.getC_time()));
                        break;
                    case 2:
                        holder.setText(R.id.tv_select_date, DateUtils.getDate("yyyy-MM-dd HH:mm:ss",
                                dataBean.getC_time()));
                        holder.setText(R.id.tv_select_title, dataBean.getTitle());
                        Glide.with(MessageDetailActivity.this)
                                .load(Constant.IMG_HOST + dataBean.getImg())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_select_goods));
                        holder.setText(R.id.tv_select_content, dataBean.getContent());
                        break;
                    case 3:
                        holder.setText(R.id.tv_logistics_date, DateUtils.getDate("yyyy-MM-dd HH:mm:ss",
                                msgList.get(position).getC_time()));
                        holder.setText(R.id.tv_logistics_title, "您的订单：" + dataBean.getTitle());
                        Glide.with(MessageDetailActivity.this)
                                .load(Constant.IMG_HOST + dataBean.getImg())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_logistics_goods));
                        logisticsStr = dataBean.getContent().split(",");
                        holder.setText(R.id.tv_logistics_name, logisticsStr[0]);
                        if (logisticsStr[1].equals("1")) {
                            holder.setText(R.id.tv_logistics_size, "定制款");
                        } else {
                            holder.setText(R.id.tv_logistics_size, logisticsStr[1]);
                        }

                        holder.setText(R.id.tv_logistics_company, "配送单位：" + logisticsStr[2]);
                        break;
                    default:
                        break;
                }
            }
        };
        rvMessage.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                switch (type) {
                    case 2:
                        Intent intent2 = new Intent(MessageDetailActivity.this, CustomizedGoodsActivity.class);
                        intent2.putExtra("id", String.valueOf("38"));
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MessageDetailActivity.this, LogisticsActivity.class);
                        intent3.putExtra("number", logisticsStr[4].trim());
                        intent3.putExtra("company", logisticsStr[3]);
                        intent3.putExtra("company_name", logisticsStr[2]);
                        intent3.putExtra("img_url", msgList.get(position).getImg());
                        startActivity(intent3);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void getData() {
        type = getIntent().getIntExtra("content", 1);
        switch (type) {
            case 1:
                layoutId = R.layout.listitem_notice_message;
                tvHeaderTitle.setText("通知消息");
                break;
            case 2:
                layoutId = R.layout.listitem_select_message;
                tvHeaderTitle.setText("活动精选");
                break;
            case 3:
                layoutId = R.layout.listitem_logistics_message;
                tvHeaderTitle.setText("物流通知");
                break;
            default:
                break;

        }
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
