package cn.cloudworkshop.miaoding.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.OrderInfoBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.AfterSalesActivity;
import cn.cloudworkshop.miaoding.ui.ConfirmOrderActivity;
import cn.cloudworkshop.miaoding.ui.EvaluateActivity;
import cn.cloudworkshop.miaoding.ui.LogisticsActivity;
import cn.cloudworkshop.miaoding.ui.MainActivity;
import cn.cloudworkshop.miaoding.ui.OrderDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PayOrderUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/9/17 15:57
 * Email：1993911441@qq.com
 * Describe：我的订单子界面
 */
public class MyOrderFragment extends BaseFragment {
    Unbinder unbinder;

    @BindView(R.id.tv_my_order)
    TextView tvMyOrder;
    @BindView(R.id.rv_goods)
    LRecyclerView rvGoods;
    @BindView(R.id.ll_null_order)
    LinearLayout llNullOrder;
    @BindView(R.id.img_no_order)
    ImageView imgNoOrder;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private CommonAdapter<OrderInfoBean.DataBeanX.DataBean> adapter;
    private List<OrderInfoBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    //订单状态
    private int orderStatus;
    //页面
    private int page = 1;
    //刷新
    private boolean isRefresh;

    //加载更多
    private boolean isLoadMore;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private OnStateChangeListener onStateChangeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initData();
        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        orderStatus = bundle.getInt("order");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onStateChangeListener = (OnStateChangeListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement OnButton2ClickListener");
        }
    }


    /**
     * 获取订单数据
     */
    private void initData() {
        //是否售后
        int isAfterSale;
        if (orderStatus == 4) {
            isAfterSale = 1;
        } else {
            isAfterSale = 0;
        }
        OkHttpUtils.get()
                .url(Constant.GOODS_ORDER)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("status", String.valueOf(orderStatus))
                .addParams("page", String.valueOf(page))
                .addParams("sh_status", String.valueOf(isAfterSale))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }


                    @Override
                    public void onResponse(String response, int id) {
                        if (getActivity() != null) {
                            imgLoadError.setVisibility(View.GONE);
                            OrderInfoBean orderInfoBean = GsonUtils.jsonToBean(response, OrderInfoBean.class);
                            if (orderInfoBean.getData().getData() != null && orderInfoBean.getData().getData().size() > 0) {
                                if (isRefresh) {
                                    dataList.clear();
                                }
                                dataList.addAll(orderInfoBean.getData().getData());
                                if (isRefresh || isLoadMore) {
                                    rvGoods.refreshComplete(0);
                                    mLRecyclerViewAdapter.notifyDataSetChanged();
                                } else {
                                    initView();
                                }
                                isRefresh = false;
                                isLoadMore = false;
                                llNullOrder.setVisibility(View.GONE);
                                rvGoods.setVisibility(View.VISIBLE);
                            } else {
                                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvGoods, 0,
                                        LoadingFooter.State.NoMore, null);
                                if (page == 1) {
                                    if (rvGoods != null) {
                                        rvGoods.setVisibility(View.GONE);
                                    }
                                    if (imgNoOrder != null) {
                                        imgNoOrder.setImageResource(R.mipmap.icon_null_order);
                                    }

                                    llNullOrder.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                });
    }

    /**
     * 加载视图
     */
    protected void initView() {

        rvGoods.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter<OrderInfoBean.DataBeanX.DataBean>(getActivity(),
                R.layout.listitem_order, dataList) {
            @Override
            protected void convert(ViewHolder holder, final OrderInfoBean.DataBeanX.DataBean dataBean,
                                   final int position) {
                holder.setText(R.id.tv_order_number, dataBean.getOrder_no());

                if (dataBean.getList() != null && dataBean.getList().size() > 0) {
                    Glide.with(getActivity())
                            .load(Constant.IMG_HOST + dataBean.getList().get(0).getGoods_thumb())
                            .placeholder(R.mipmap.place_holder_news)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into((ImageView) holder.getView(R.id.img_order_info));
                    TextView tvGoodsName = holder.getView(R.id.tv_order_name);
                    tvGoodsName.setText(dataBean.getList().get(0).getGoods_name());
                    tvGoodsName.setTypeface(DisplayUtils.setTextType(mContext));
                    switch (dataBean.getList().get(0).getGoods_type()) {
                        case 2:
                            holder.setText(R.id.tv_order_content, dataBean.getList().get(0).getSize_content());
                            break;
                        default:
                            holder.setText(R.id.tv_order_content, getString(R.string.customize_type));
                            break;
                    }
                    holder.setText(R.id.tv_order_count, getString(R.string.together) + dataBean.getList().get(0).getNum() + getString(R.string.piece_goods));
                }

                holder.setText(R.id.tv_order_price, "¥" + dataBean.getMoney());

                switch (dataBean.getStatus()) {
                    case 1:
                        holder.setVisible(R.id.tv_after_sale, false);
                        holder.setVisible(R.id.tv_order_control, true);
                        holder.setVisible(R.id.tv_order_pay, true);
                        holder.setText(R.id.tv_order_status, getString(R.string.not_pay_order));
                        holder.setText(R.id.tv_order_control, getString(R.string.cancel_order));
                        holder.setText(R.id.tv_order_pay, getString(R.string.pay));
                        break;
                    case 2:
                        holder.setVisible(R.id.tv_after_sale, false);
                        holder.setVisible(R.id.tv_order_control, true);
                        holder.setVisible(R.id.tv_order_pay, false);
                        holder.setText(R.id.tv_order_status, getString(R.string.has_pay_order));
                        holder.setText(R.id.tv_order_control, getString(R.string.notice_send_goods));
                        break;
                    case 3:
                        holder.setVisible(R.id.tv_after_sale, false);
                        holder.setVisible(R.id.tv_order_control, true);
                        holder.setVisible(R.id.tv_order_pay, true);
                        holder.setText(R.id.tv_order_status, getString(R.string.has_send_order));
                        holder.setText(R.id.tv_after_sale, getString(R.string.after_sale));
                        holder.setText(R.id.tv_order_control, getString(R.string.view_logistics));
                        holder.setText(R.id.tv_order_pay, getString(R.string.confirm_receive));
                        break;
                    case 4:
                        holder.setVisible(R.id.tv_after_sale, false);
                        holder.setVisible(R.id.tv_order_control, true);

                        holder.setText(R.id.tv_order_status, getString(R.string.completed));
                        holder.setText(R.id.tv_after_sale, getString(R.string.after_sale));
                        holder.setText(R.id.tv_order_control, getString(R.string.buy_again));
                        //订单未评价
                        if (dataBean.getComment_id() == 0) {
                            holder.setVisible(R.id.tv_order_pay, true);
                            holder.setText(R.id.tv_order_pay, getString(R.string.evaluate));
                        } else {
                            holder.setVisible(R.id.tv_order_pay, false);
                        }
                        break;
                    case -2:
                        holder.setVisible(R.id.tv_after_sale, false);
                        holder.setVisible(R.id.tv_order_control, true);
                        holder.setVisible(R.id.tv_order_pay, false);
                        holder.setText(R.id.tv_order_status, getString(R.string.cancelled));
                        holder.setText(R.id.tv_order_control, getString(R.string.delete_order));
                        break;
                }

                holder.getView(R.id.tv_order_control).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (dataBean.getStatus()) {
                            case 1:
                                cancelOrder(dataBean.getId());
                                break;
                            case 2:
                                ToastUtils.showToast(getActivity(), getString(R.string.has_notice));
                                break;
                            case 3:
                                Intent intent = new Intent(getActivity(), LogisticsActivity.class);
                                intent.putExtra("number", dataBean.getEms_no());
                                intent.putExtra("company", dataBean.getEms_com());
                                intent.putExtra("company_name", dataBean.getEms_com_name());
                                intent.putExtra("img_url", dataBean.getList().get(0).getGoods_thumb());
                                startActivity(intent);
                                break;
                            case 4:
                                buyAgain(dataBean.getId());
                                break;
                            case -2:
                                deleteOrder(dataBean.getId(), position - 1);
                                break;
                        }
                    }
                });
                holder.getView(R.id.tv_order_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (dataBean.getStatus()) {
                            case 1:
                                PayOrderUtils payOrderUtil = new PayOrderUtils(getActivity(),
                                        dataBean.getMoney(), dataBean.getId() + "");
                                payOrderUtil.payMoney();
                                break;
                            case 3:
                                confirmReceive(dataBean.getId());
                                break;
                            case 4:
                                //订单评价
                                Intent intent = new Intent(getActivity(), EvaluateActivity.class);
                                intent.putExtra("order_id", String.valueOf(dataBean.getId()));
                                intent.putExtra("goods_id", String.valueOf(dataBean.getList().get(0).getGoods_id()));
                                intent.putExtra("cart_id", String.valueOf(dataBean.getList().get(0).getId()));
                                intent.putExtra("goods_img", dataBean.getList().get(0).getGoods_thumb());
                                intent.putExtra("goods_name", dataBean.getList().get(0).getGoods_name());

                                switch (dataBean.getList().get(0).getGoods_type()) {
                                    case 2:
                                        intent.putExtra("goods_type", dataBean.getList().get(0).getSize_content());
                                        break;
                                    default:
                                        intent.putExtra("goods_type", getString(R.string.customize_type));
                                        break;
                                }

                                startActivity(intent);
                                break;
                        }
                    }
                });
                holder.getView(R.id.tv_after_sale).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AfterSalesActivity.class);
                        intent.putExtra("order_id", dataBean.getId());
                        startActivity(intent);
                    }
                });

            }
        };

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvGoods.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvGoods.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        initData();
                    }
                }, 1000);
            }
        });

        //加载更多
        rvGoods.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), rvGoods,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("id", dataList.get(position).getId() + "");
                startActivity(intent);
            }

        });

    }

    /**
     * @param id 再次购买
     */
    private void buyAgain(int id) {
        OkHttpUtils.post()
                .url(Constant.BUY_AGAIN)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("orderid", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 1) {
                                String cartId = jsonObject.getString("car_id");
                                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("cart_id", cartId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(getActivity(), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 确认收货
     */
    private void confirmReceive(final int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(R.string.confirm_receive);
        dialog.setMessage(R.string.is_confirm_receive);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CONFIRM_RECEIVE)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("order_id", String.valueOf(id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {


                            }

                            @Override
                            public void onResponse(String response, int id) {

                                MobclickAgent.onEvent(getActivity(), "trade_success");
                                ToastUtils.showToast(getActivity(), getString(R.string.transaction_success));
                                onStateChangeListener.onStateChange(0);
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }


    /**
     * 删除订单
     */
    private void deleteOrder(final int id, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(R.string.delete_order);
        dialog.setMessage(R.string.is_delete_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.DELETE_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("order_id", String.valueOf(id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.getInt("code");
                                    if (code == 1) {
                                        dataList.remove(position);
                                        adapter.notifyItemRemoved(position);
                                        if (position != dataList.size()) {
                                            adapter.notifyItemRangeChanged(position, dataList.size() - position);
                                        }
                                        if (dataList.size() == 0 && orderStatus == 0) {
                                            imgNoOrder.setImageResource(R.mipmap.icon_null_order);
                                            llNullOrder.setVisibility(View.VISIBLE);
                                        }
                                        ToastUtils.showToast(getActivity(), getString(R.string.delete_success));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }


    /**
     * 取消订单
     */
    private void cancelOrder(final int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.cancel_order));
        dialog.setMessage(R.string.is_cancel_order);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.CANCEL_ORDER)
                        .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                        .addParams("order_id", String.valueOf(id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                MobclickAgent.onEvent(getActivity(), "cancel_order");
                                ToastUtils.showToast(getActivity(), getString(R.string.cancel_scuccess));
                                onStateChangeListener.onStateChange(0);
                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }

    public static MyOrderFragment newInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt("order", orderStatus);
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_my_order, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_order:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 订单状态监听
     */
    public interface OnStateChangeListener {
        void onStateChange(int page);
    }
}
