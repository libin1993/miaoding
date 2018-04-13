package cn.cloudworkshop.miaoding.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.cloudworkshop.miaoding.bean.DeliveryAddressBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/7 17:03
 * Email：1993911441@qq.com
 * Describe：收货地址
 */
public class DeliveryAddressActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.rv_delivery_address)
    LRecyclerView recyclerView;
    @BindView(R.id.ll_no_address)
    LinearLayout llNoAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    //1：编辑地址 2:选择地址
    private int type;
    //地址id
    private int addressId = -1;
    //页面
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private List<DeliveryAddressBean.DataBean> dataList = new ArrayList<>();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initData();
    }

    private void getData() {
        type = getIntent().getIntExtra("type", 0);
    }


    /**
     * 加载数据
     */
    private void initData() {
        switch (type) {
            case 1:
                tvHeaderTitle.setText("收货地址");
                break;
            case 2:
                tvHeaderTitle.setText("选择地址");
                addressId = getIntent().getIntExtra("address_id", -1);
                break;
        }

        OkHttpUtils.get()
                .url(Constant.MY_ADDRESS)
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
                        DeliveryAddressBean addressBean = GsonUtils.jsonToBean(response, DeliveryAddressBean.class);
                        if (addressBean.getData() != null && addressBean.getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(addressBean.getData());
                            if (isRefresh || isLoadMore) {
                                recyclerView.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;
                            llNoAddress.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(DeliveryAddressActivity.this,
                                    recyclerView, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                recyclerView.setVisibility(View.GONE);
                                llNoAddress.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<DeliveryAddressBean.DataBean> adapter = new CommonAdapter<DeliveryAddressBean
                .DataBean>(this, R.layout.listitem_address, dataList) {
            @Override
            protected void convert(ViewHolder holder, final DeliveryAddressBean.DataBean dataBean,
                                   final int position) {
                holder.setText(R.id.tv_user_name, dataBean.getName());
                holder.setText(R.id.tv_user_phone, dataBean.getPhone());
                holder.setText(R.id.tv_user_address, dataBean.getProvince() + dataBean.getCity()
                        + dataBean.getArea() + dataBean.getAddress());
                holder.setVisible(R.id.view_address_line, true);
                holder.setVisible(R.id.ll_edit_address, true);


                TextView tvDefault = holder.getView(R.id.tv_set_default);

                if (dataBean.getIs_default() == 1) {
                    Drawable leftDrawable = ContextCompat.getDrawable(DeliveryAddressActivity.this,
                            R.mipmap.icon_default_address);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
                            leftDrawable.getMinimumHeight());
                    tvDefault.setCompoundDrawables(leftDrawable, null, null, null);
                } else if (dataBean.getIs_default() == 0) {
                    Drawable leftDrawable = ContextCompat.getDrawable(DeliveryAddressActivity.this,
                            R.mipmap.icon_not_default);
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
                            leftDrawable.getMinimumHeight());
                    tvDefault.setCompoundDrawables(leftDrawable, null, null, null);
                }

                tvDefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataList.get(position - 1).getIs_default() == 0) {
                            setDefaultAddress(dataList.get(position - 1).getId());
                        }

                    }
                });

                TextView tvDelete = holder.getView(R.id.tv_delete_address);
                TextView tvEdit = holder.getView(R.id.tv_edit_address);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress(dataList.get(position - 1).getId());
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeliveryAddressActivity.this,
                                AddAddressActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 2);
                        bundle.putSerializable("edit", dataBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(mLRecyclerViewAdapter);

        //刷新
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                initData();
            }
        });

        //加载更多
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(DeliveryAddressActivity.this, recyclerView,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (type == 2) {
                    selectedAddress(position, 1);
                    finish();
                }
            }
        });

    }


    /**
     * 已选择地址
     */
    private void selectedAddress(int position, int resultCode) {
        Intent intent = new Intent();
        ConfirmOrderBean.DataBean.AddressListBean addressListBean = new ConfirmOrderBean.DataBean.AddressListBean();
        addressListBean.setId(dataList.get(position).getId());
        addressListBean.setProvince(dataList.get(position).getProvince());
        addressListBean.setCity(dataList.get(position).getCity());
        addressListBean.setArea(dataList.get(position).getArea());
        addressListBean.setAddress(dataList.get(position).getAddress());
        addressListBean.setName(dataList.get(position).getName());
        addressListBean.setPhone(dataList.get(position).getPhone());
        addressListBean.setIs_default(dataList.get(position).getIs_default());
        intent.putExtra("address", addressListBean);
        setResult(resultCode, intent);
    }

    /**
     * 设置默认地址
     */
    private void setDefaultAddress(int id) {
        OkHttpUtils.post()
                .url(Constant.DEFAULT_ADDRESS)
                .addParams("token", SharedPreferencesUtils.getStr(DeliveryAddressActivity.this, "token"))
                .addParams("id", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        isRefresh = true;
                        page = 1;
                        initData();
                    }
                });

    }

    /**
     * 删除地址
     */
    private void deleteAddress(final int id) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this,
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("删除地址");
        dialog.setMessage("您确定要删除该地址吗？");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.post()
                        .url(Constant.DELETE_ADDRESS)
                        .addParams("token", SharedPreferencesUtils.getStr(DeliveryAddressActivity.this, "token"))
                        .addParams("id", String.valueOf(id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                isRefresh = true;
                                page = 1;
                                initData();
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }

    @OnClick({R.id.img_header_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                isSelectAddress();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(DeliveryAddressActivity.this, AddAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isSelectAddress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void isSelectAddress() {
        //地址被清空返回2;已选地址被删除返回3
        if (type == 2) {
            if (dataList.size() > 0) {
                if (addressId == -1) {
                    setResult(3);
                } else {
                    List<Integer> idList = new ArrayList<>();
                    for (int i = 0; i < dataList.size(); i++) {
                        idList.add(dataList.get(i).getId());
                        if (addressId == dataList.get(i).getId()) {
                            selectedAddress(i, 4);
                            break;
                        }
                    }
                    if (!idList.contains(addressId)) {
                        setResult(3);
                    }
                }
            } else {
                setResult(2);
            }
        }
        finish();
    }


    @Subscribe
    public void editAddress(String msg) {
        if ("edit_success".equals(msg)) {
            isRefresh = true;
            page = 1;
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
