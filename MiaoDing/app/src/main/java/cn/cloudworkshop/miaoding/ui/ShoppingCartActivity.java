package cn.cloudworkshop.miaoding.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CartDetailsBean;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.bean.RecommendGoodsBean;
import cn.cloudworkshop.miaoding.bean.ShoppingCartBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.AppManagerUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.SpaceItemDecoration;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 11:02
 * Email：1993911441@qq.com
 * Describe：购物袋
 */
public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_my_bag)
    TextView tvMyBag;
    @BindView(R.id.rl_null_bag)
    RelativeLayout rlNullBag;
    @BindView(R.id.ll_cart_goods)
    LinearLayout llCartGoods;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.checkbox_all_select)
    CheckBox checkboxAllSelect;
    @BindView(R.id.tv_total_counts)
    TextView tvTotalCounts;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_goods_buy)
    TextView tvGoodsBuy;
    @BindView(R.id.rv_cart_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.rv_goods_cart)
    LRecyclerView rvGoodsCart;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    private List<ShoppingCartBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    //页数
    private int page = 1;
    //刷新
    private boolean isRefresh;
    //加载更多
    private boolean isLoadMore;
    private CommonAdapter<ShoppingCartBean.DataBeanX.DataBean> adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    //是否编辑状态
    private boolean isEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        tvHeaderTitle.setText("购物袋");
        tvHeaderNext.setText("编辑");

        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRefresh = true;
        page = 1;
        checkboxAllSelect.setChecked(true);
        initData();
    }

    /**
     * 获取数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.SHOPPING_CART)
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
                        ShoppingCartBean cartBean = GsonUtils.jsonToBean(response, ShoppingCartBean.class);
                        if (cartBean.getData().getData() != null && cartBean.getData().getData().size() > 0) {
                            if (isRefresh) {
                                dataList.clear();
                            }
                            dataList.addAll(cartBean.getData().getData());
                            if (isLoadMore || isRefresh) {
                                rvGoodsCart.refreshComplete(0);
                                mLRecyclerViewAdapter.notifyDataSetChanged();
                                getTotalCount();
                                getTotalPrice();
                            } else {
                                initView();
                            }
                            isRefresh = false;
                            isLoadMore = false;

                            rlNullBag.setVisibility(View.GONE);
                            llCartGoods.setVisibility(View.VISIBLE);
                            tvHeaderNext.setVisibility(View.VISIBLE);
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(ShoppingCartActivity.this,
                                    rvGoodsCart, 0, LoadingFooter.State.NoMore, null);
                            if (page == 1) {
                                nullCart();
                            }
                        }
                    }
                });
    }

    /**
     * 空购物车
     */
    private void nullCart() {
        rlNullBag.setVisibility(View.VISIBLE);
        llCartGoods.setVisibility(View.GONE);
        tvHeaderNext.setVisibility(View.GONE);
        OkHttpUtils.get()
                .url(Constant.GOODS_RECOMMEND)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        RecommendGoodsBean recommendBean = GsonUtils.jsonToBean(response,
                                RecommendGoodsBean.class);
                        if (recommendBean.getData().getData() != null) {
                            recommendGoods(recommendBean.getData().getData());
                        }
                    }
                });
    }

    /**
     * 推荐商品
     */
    private void recommendGoods(final List<RecommendGoodsBean.DataBeanX.DataBean> recommendBean) {
        rvRecommend.setLayoutManager(new GridLayoutManager(ShoppingCartActivity.this, 2));
        rvRecommend.addItemDecoration(new SpaceItemDecoration((int) DisplayUtils.dp2px(this,
                4.5f), false));
        CommonAdapter<RecommendGoodsBean.DataBeanX.DataBean> adapter = new CommonAdapter<RecommendGoodsBean
                .DataBeanX.DataBean>(
                this, R.layout.listitem_sub_goods, recommendBean) {
            @Override
            protected void convert(ViewHolder holder, RecommendGoodsBean.DataBeanX.DataBean dataBean,
                                   int position) {

                SimpleDraweeView imgGoods = holder.getView(R.id.img_sub_goods);
                imgGoods.setImageURI(Constant.IMG_HOST + dataBean.getThumb());
                holder.setText(R.id.tv_sub_title, dataBean.getName());
                holder.setText(R.id.tv_sub_price, dataBean.getPrice());
                holder.setText(R.id.tv_sub_content, dataBean.getSub_name());
            }
        };
        rvRecommend.setAdapter(adapter);


        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent;
                if (recommendBean.get(position).getType() == 1) {
                    intent = new Intent(ShoppingCartActivity.this, CustomizedGoodsActivity.class);
                } else {
                    intent = new Intent(ShoppingCartActivity.this, WorksDetailActivity.class);
                }

                intent.putExtra("id", String.valueOf(recommendBean.get(position).getGoods_id()));
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    /**
     * 加载视图
     */
    private void initView() {
        getTotalPrice();
        getTotalCount();

        checkboxAllSelect.setChecked(true);
        rvGoodsCart.setLayoutManager(new LinearLayoutManager(ShoppingCartActivity.this));
        adapter = new CommonAdapter<ShoppingCartBean.DataBeanX.DataBean>(ShoppingCartActivity.this,
                R.layout.listitem_shopping_cart, dataList) {
            @Override
            protected void convert(final ViewHolder holder, final ShoppingCartBean.DataBeanX.DataBean
                    dataBean, final int position) {
                Glide.with(ShoppingCartActivity.this)
                        .load(Constant.IMG_HOST + dataBean.getGoods_thumb())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                TextView tvGoodsName = holder.getView(R.id.tv_goods_name);
                tvGoodsName.setTypeface(DisplayUtils.setTextType(mContext));
                tvGoodsName.setText(dataBean.getGoods_name());
                switch (dataBean.getGoods_type()) {
                    case 2:
                        holder.setText(R.id.tv_goods_content, dataBean.getSize_content());
                        break;
                    default:
                        holder.setText(R.id.tv_goods_content, "定制款");
                        break;
                }

                holder.setText(R.id.tv_goods_price, "¥" + DisplayUtils.decimalFormat(
                        Float.parseFloat(dataBean.getPrice())));
                holder.setText(R.id.tv_goods_count, "x" + dataBean.getNum());
                holder.setVisible(R.id.ll_cart_edit, isEdited);
                holder.setVisible(R.id.tv_goods_content, !isEdited);
                holder.setVisible(R.id.tv_goods_price, !isEdited);
                holder.setVisible(R.id.tv_goods_count, !isEdited);
                holder.setText(R.id.tv_cart_count, dataBean.getNum() + "");
                final CheckBox checkBox = holder.getView(R.id.checkbox_goods_select);
                checkBox.setOnCheckedChangeListener(null);
                holder.setChecked(R.id.checkbox_goods_select, dataBean.getIs_select());

                final TextView tvReduce = holder.getView(R.id.tv_cart_reduce);
                tvReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dataBean.getNum() > 1) {
                            changeCartCount(position - 1, dataBean.getNum() - 1);
                        }
                    }
                });

                final TextView tvAdd = holder.getView(R.id.tv_cart_add);
                tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeCartCount(position - 1, dataBean.getNum() + 1);
                    }
                });

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        dataBean.setIs_select(b);
                        checkBox.setChecked(b);
                        int count = 0;
                        for (int i = 0; i < dataList.size(); i++) {
                            if (dataList.get(i).getIs_select()) {
                                count++;
                            }
                        }
                        if (count == dataList.size()) {
                            checkboxAllSelect.setChecked(true);
                        } else {
                            checkboxAllSelect.setChecked(false);
                        }
                        getTotalCount();
                        getTotalPrice();
                    }
                });

            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rvGoodsCart.setAdapter(mLRecyclerViewAdapter);

        //刷新
        rvGoodsCart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        isRefresh = true;
                        page = 1;
                        checkboxAllSelect.setChecked(true);
                        initData();
                    }
                }, 1000);
            }
        });
        //加载更多
        rvGoodsCart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                RecyclerViewStateUtils.setFooterViewState(ShoppingCartActivity.this, rvGoodsCart,
                        0, LoadingFooter.State.Loading, null);
                isLoadMore = true;
                page++;
                initData();
            }
        });

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!isEdited) {
                    switch (dataList.get(position).getGoods_type()) {
                        case 1:
                            cartToCustomResult(position);
                            break;
                        case 2:
                            Intent intent = new Intent(ShoppingCartActivity.this, WorksDetailActivity.class);
                            intent.putExtra("id", String.valueOf(dataList.get(position).getGoods_id()));
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                List<Integer> itemList = new ArrayList<>();
                itemList.add(dataList.get(position).getId());
                deleteGoods(itemList);
            }
        });

        checkboxAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxAllSelect.isChecked()) {
                    checkboxAllSelect.setChecked(true);
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setIs_select(true);
                    }
                } else {
                    checkboxAllSelect.setChecked(false);
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setIs_select(false);
                    }
                }
                adapter.notifyDataSetChanged();
                getTotalCount();
                getTotalPrice();

            }
        });

    }

    /**
     * 购物车跳转定制详情
     */
    private void cartToCustomResult(int position) {
        OkHttpUtils.get()
                .url(Constant.CART_TO_CUSTOM)
                .addParams("token", SharedPreferencesUtils.getStr(ShoppingCartActivity.this, "token"))
                .addParams("car_id", dataList.get(position).getId() + "")
                .addParams("phone_type", "3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CartDetailsBean cartDetails = GsonUtils.jsonToBean(response, CartDetailsBean.class);

                        if (cartDetails.getData() != null) {
                            Intent intent = new Intent(ShoppingCartActivity.this, CustomizeResultActivity.class);
                            Bundle bundle = new Bundle();
                            CustomItemBean customItemBean = new CustomItemBean();
                            customItemBean.setId(cartDetails.getData().getGoods_id() + "");
                            customItemBean.setGoods_name(cartDetails.getData().getGoods_name());
                            customItemBean.setPrice(DisplayUtils.decimalFormat((float) cartDetails
                                    .getData().getPrice()));
                            customItemBean.setPrice_type(cartDetails.getData().getPrice_id() + "");
                            customItemBean.setImg_url(cartDetails.getData().getGoods_thumb());
                            customItemBean.setSpec_ids(cartDetails.getData().getSpec_ids());
                            customItemBean.setSpec_content(cartDetails.getData().getSpec_content());
                            customItemBean.setIs_scan(cartDetails.getData().getIs_scan());
                            //0：个性定制 1：定制同款
                            switch (cartDetails.getData().getIs_scan()) {
                                case 0:
                                    List<CustomItemBean.ItemBean> itemList = new ArrayList<>();
                                    for (int i = 0; i < cartDetails.getData().getImg_list().size(); i++) {
                                        CustomItemBean.ItemBean itemBean = new CustomItemBean.ItemBean();
                                        itemBean.setImg(cartDetails.getData().getImg_list().get(i).getImg_c());
                                        itemBean.setPosition_id(cartDetails.getData().getImg_list().get(i)
                                                .getPosition_id());
                                        itemList.add(itemBean);
                                    }
                                    //图片
                                    customItemBean.setItemBean(itemList);
                                    customItemBean.setDefault_img(cartDetails.getData().getGoods_thumb());
                                    break;
                                case 1:
                                    customItemBean.setDefault_img(cartDetails.getData().getDefault_img());
                                    break;
                            }

                            //面料
                            if (!TextUtils.isEmpty(cartDetails.getData().getMianliao_id())) {
                                customItemBean.setFabric_id(cartDetails.getData().getMianliao_id());
                            }

                            //版型
                            if (!TextUtils.isEmpty(cartDetails.getData().getBanxing_id())) {
                                customItemBean.setBanxing_id(cartDetails.getData().getBanxing_id());
                            }

                            //部件id
                            customItemBean.setSpec_ids(cartDetails.getData().getSpec_ids());
                            //部件名称
                            customItemBean.setSpec_content(cartDetails.getData().getSpec_content());
                            //个性定制
                            if (!TextUtils.isEmpty(cartDetails.getData().getDiy_content())) {
                                customItemBean.setDiy_contet(cartDetails.getData().getDiy_content());
                            }
                            bundle.putSerializable("tailor", customItemBean);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
    }

    /**
     * @param position 改变购物车数量
     */
    private void changeCartCount(final int position, final int count) {
        OkHttpUtils.post()
                .url(Constant.CART_COUNT)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("car_id", String.valueOf(dataList.get(position).getId()))
                .addParams("num", String.valueOf(count))
                .addParams("type", "1")
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
                                dataList.get(position).setNum(count);
                                adapter.notifyDataSetChanged();
                                getTotalPrice();
                                getTotalCount();
                            } else {
                                ToastUtils.showToast(ShoppingCartActivity.this, "库存不足");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 删除购物车
     */
    private void deleteGoods(final List<Integer> itemList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("删除宝贝");
        dialog.setMessage("您确定要删除该宝贝吗？");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < itemList.size(); i++) {
                    if (i == itemList.size() - 1) {
                        sb.append(itemList.get(i));
                    } else {
                        sb.append(itemList.get(i)).append(",");
                    }

                }

                OkHttpUtils.post()
                        .url(Constant.DELETE_CART)
                        .addParams("token", SharedPreferencesUtils.getStr(ShoppingCartActivity.this, "token"))
                        .addParams("car_id", sb.toString())
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
                                        for (int i = 0; i < dataList.size(); i++) {
                                            for (int j = 0; j < itemList.size(); j++) {
                                                if (dataList.get(i).getId() == (itemList.get(j))) {
                                                    dataList.remove(i);
                                                    adapter.notifyItemRemoved(i);
                                                    if (i != dataList.size()) {
                                                        adapter.notifyItemRangeChanged(i, dataList.size() - i);
                                                    }
                                                }
                                            }
                                        }
                                        if (dataList.size() == 0) {
                                            nullCart();
                                        } else {
                                            getTotalCount();
                                            getTotalPrice();
                                        }
                                        ToastUtils.showToast(ShoppingCartActivity.this, "删除成功");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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


    @OnClick({R.id.img_header_back, R.id.tv_my_bag, R.id.tv_header_next, R.id.tv_goods_buy, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_goods_buy:
                if (isEdited) {
                    if (getTotalCount() > 0) {
                        deleteGoods(getSelected());
                    } else {
                        ToastUtils.showToast(this, "请选择商品");
                    }
                } else {
                    if (getTotalCount() > 0) {
                        buyGoods();
                    } else {
                        ToastUtils.showToast(this, "请选择商品");
                    }
                }

                break;
            case R.id.tv_my_bag:
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_header_next:
                if (!isEdited) {
                    isEdited = true;
                    adapter.notifyDataSetChanged();
                    tvHeaderNext.setText("确定");
                    tvGoodsBuy.setText("删除");
                } else {
                    isEdited = false;
                    adapter.notifyDataSetChanged();
                    tvHeaderNext.setText("编辑");
                    getTotalCount();
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 下单
     */
    private void buyGoods() {
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cart_id", getCartIds());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * @return 已勾选商品
     */
    private List<Integer> getSelected() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getIs_select()) {
                list.add(dataList.get(i).getId());
            }
        }

        return list;
    }


    /**
     * 已选择商品ids
     */
    private String getCartIds() {

        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getIs_select()) {
                idList.add(dataList.get(i).getId());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < idList.size(); i++) {
            if (i == idList.size() - 1) {
                sb.append(idList.get(i));
            } else {
                sb.append(idList.get(i)).append(",");
            }
        }
        return sb.toString();
    }


    /**
     * 获取总价格
     */
    public void getTotalPrice() {
        float sum = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getIs_select()) {
                sum += Float.parseFloat(dataList.get(i).getPrice()) * dataList.get(i).getNum();
            }
        }
        tvTotalPrice.setText("¥" + DisplayUtils.decimalFormat(sum));
    }


    /**
     * 总数量
     */
    public int getTotalCount() {
        int selectCount = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getIs_select()) {
                selectCount += dataList.get(i).getNum();
            }
        }
        if (isEdited) {
            tvGoodsBuy.setText("删除");
        } else {
            tvGoodsBuy.setText("下单(" + selectCount + ")");
        }
        return selectCount;
    }


    /**
     * @param msg 下单成功，结束当前页面
     */
    @Subscribe
    public void orderSuccess(String msg) {
        if ("order_success".equals(msg)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
