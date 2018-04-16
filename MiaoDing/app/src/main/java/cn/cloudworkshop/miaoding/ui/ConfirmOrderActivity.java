package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ConfirmOrderBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.AppManagerUtils;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.PayOrderUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/7 14:54
 * Email：1993911441@qq.com
 * Describe：确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_address)
    TextView tvProvinceAddress;
    @BindView(R.id.tv_need_pay)
    TextView tvNeedPay;
    @BindView(R.id.tv_confirm_order)
    TextView tvConfirmOrder;
    @BindView(R.id.rv_confirm_order)
    RecyclerView rvConfirmOrder;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.tv_goods_total)
    TextView tvGoodsTotal;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_discount_money)
    TextView tvCouponDiscount;
    @BindView(R.id.ll_first_order)
    LinearLayout llFirstOrder;
    @BindView(R.id.view_first_order)
    View viewFirstOrder;
    @BindView(R.id.tv_coupon_count)
    TextView tvCouponCount;
    @BindView(R.id.ll_select_coupon)
    LinearLayout llSelectCoupon;
    @BindView(R.id.tv_coupon_content)
    TextView tvCouponContent;
    @BindView(R.id.ll_user_address)
    LinearLayout llUserAddress;
    @BindView(R.id.tv_default_address)
    TextView tvDefaultAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.rl_add_address)
    RelativeLayout rlAddAddress;
    @BindView(R.id.checkbox_card)
    CheckBox checkboxCard;
    @BindView(R.id.tv_card_money)
    TextView tvCardMoney;
    @BindView(R.id.ll_select_card)
    LinearLayout llSelectCard;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;

    //购物车id
    private String cartIds;
    private ConfirmOrderBean confirmOrderBean;
    //收货地址
    private ConfirmOrderBean.DataBean.AddressListBean addressListBean;
    //优惠券id
    private String couponId;
    //优惠券金额
    private String couponMoney;
    //优惠券详情
    private String couponContent;
    //优惠券最低使用金额
    private String couponMinMoney;
    //优惠券适用的商品
    private String goodsIds;
    //优惠券数量
    private int couponNum;
    //显示优惠券优惠金额
    private float displayCoupon = 0.00f;
    //实际优惠券优惠金额
    private float actualCoupon = 0.00f;
    //礼品卡金额
    private float cardMoney = 0.00f;
    //显示礼品卡优惠金额
    private float displayCard = 0.00f;
    //实际礼品卡优惠金额
    private float actualCard = 0.00f;
    //商品adapter
    private CommonAdapter<ConfirmOrderBean.DataBean.CarListBean> adapter;

    private PayOrderUtils payOrderUtil;

    //用户未创建收货地址，新建地址
    private boolean isNoAddress;
    //商品id
    private String goodsId;
    //商品名称
    private String goodsName;
    private String logId;
    private long goodsTime;
    private long dingzhiTime;
    //购物车商品数量更改，可能导致优惠券不能使用
    private boolean canCouponSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        getData();
        initData();
    }


    /**
     * 获取网络数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.ORDER_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("car_ids", cartIds)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setBackgroundColor(Color.WHITE);
                        imgLoadingError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        //修改购物车商品数量，可用优惠券数量改变
                        if (canCouponSelect) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.getJSONObject("data");
                                couponNum = data.getInt("ticket_num");
                                initCoupon();
                                canCouponSelect = false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            confirmOrderBean = GsonUtils.jsonToBean(response, ConfirmOrderBean.class);
                            if (confirmOrderBean.getData() != null) {
                                initView();
                            }
                        }
                    }
                });

    }


    /**
     * 购物车信息
     */
    private void getData() {
        tvHeaderTitle.setText("确认订单");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cartIds = bundle.getString("cart_id");
            logId = bundle.getString("log_id");
            goodsTime = bundle.getLong("goods_time");
            dingzhiTime = bundle.getLong("dingzhi_time");
            goodsId = bundle.getString("goods_id");
            goodsName = bundle.getString("goods_name");
        }
    }

    /**
     * 加载视图
     */
    private void initView() {
        //无地址，提示用户新建地址
        if (confirmOrderBean.getData().getAddress_list() == null) {
            isNoAddress = true;
            addressListBean = null;
        } else {
            isNoAddress = false;
            addressListBean = confirmOrderBean.getData().getAddress_list();
        }

        couponNum = confirmOrderBean.getData().getTicket_num();
        cardMoney = Float.parseFloat(confirmOrderBean.getData().getGift_card());

        //礼品卡是否可用
        if (confirmOrderBean.getData().getCard_userable() == 1) {
            tvCardMoney.setTextColor(ContextCompat.getColor(ConfirmOrderActivity.this,
                    R.color.dark_gray_22));
        } else {
            tvCardMoney.setTextColor(ContextCompat.getColor(ConfirmOrderActivity.this,
                    R.color.light_gray_93));
        }

        initAddress();
        initCoupon();

        //礼品卡checkbox点击监听
        checkboxCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //礼品卡可以使用
                if (confirmOrderBean.getData().getCard_userable() == 1) {
                    //已选择优惠券，不能使用礼品卡
                    if (couponId != null) {
                        ToastUtils.showToast(ConfirmOrderActivity.this, "优惠券和礼品卡不能同时使用");
                        checkboxCard.setChecked(false);
                    }
                    discountCard();
                    initCoupon();
                } else {
                    checkboxCard.setChecked(false);
                    ToastUtils.showToast(ConfirmOrderActivity.this, "该订单不支持使用礼品卡");
                }
            }
        });


        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setScrollEnabled(false);
        rvConfirmOrder.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<ConfirmOrderBean.DataBean.CarListBean>(this,
                R.layout.listitem_shopping_cart, confirmOrderBean.getData().getCar_list()) {
            @Override
            protected void convert(final ViewHolder holder, ConfirmOrderBean.DataBean.CarListBean
                    carListBean, final int position) {
                holder.setVisible(R.id.checkbox_goods_select, false);
                holder.setVisible(R.id.view_cart_divide, true);
                Glide.with(ConfirmOrderActivity.this)
                        .load(Constant.IMG_HOST + carListBean.getGoods_thumb())
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                holder.setVisible(R.id.tv_goods_count, false);
                holder.setVisible(R.id.ll_cart_edit, false);
                holder.setVisible(R.id.ll_cart_edit1, true);
                holder.setText(R.id.tv_goods_name, carListBean.getGoods_name());
                switch (carListBean.getGoods_type()) {
                    case 2:
                        holder.setText(R.id.tv_goods_content, carListBean.getSize_content());
                        break;
                    default:
                        holder.setText(R.id.tv_goods_content, "定制款");
                        break;
                }
                holder.setText(R.id.tv_goods_price, "¥" + carListBean.getPrice());
                holder.setText(R.id.tv_cart_count1, carListBean.getNum() + "");
                holder.setVisible(R.id.view_cart, true);

                //增加购物车数量
                holder.getView(R.id.tv_cart_add1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = confirmOrderBean.getData().getCar_list().get(position).getNum();
                        changeCartCount(position, count + 1);

                    }
                });
                //减少购物车数量
                holder.getView(R.id.tv_cart_reduce1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = confirmOrderBean.getData().getCar_list().get(position).getNum();
                        if (count > 1) {
                            changeCartCount(position, count - 1);
                        }
                    }
                });
            }
        };
        rvConfirmOrder.setAdapter(adapter);
    }

    /**
     * 收货地址
     */
    private void initAddress() {
        if (addressListBean == null) {
            tvNoAddress.setVisibility(View.VISIBLE);
            if (isNoAddress) {
                tvNoAddress.setText("请创建收货地址");
            } else {
                tvNoAddress.setText("请选择收货地址");
            }
            rlAddAddress.setVisibility(View.VISIBLE);
            llUserAddress.setVisibility(View.GONE);
            tvDefaultAddress.setVisibility(View.GONE);
        } else {
            rlAddAddress.setVisibility(View.GONE);
            llUserAddress.setVisibility(View.VISIBLE);
            if (addressListBean.getIs_default() == 1) {
                tvDefaultAddress.setVisibility(View.VISIBLE);
            } else {
                tvDefaultAddress.setVisibility(View.GONE);
            }

            tvUserName.setText(addressListBean.getName());
            tvUserPhone.setText(addressListBean.getPhone());
            tvProvinceAddress.setText(addressListBean.getProvince() + addressListBean.getCity() +
                    addressListBean.getArea() + addressListBean.getAddress());
        }
    }


    /**
     * 是否选择优惠券或礼品卡
     */
    private void initCoupon() {
        if (couponId == null) {
            tvCouponCount.setText(couponNum + "张");
            tvCouponCount.setVisibility(View.VISIBLE);
            tvCouponContent.setText("请选择优惠券");
            tvCouponContent.setTextColor(ContextCompat.getColor(ConfirmOrderActivity.this,
                    R.color.dark_gray_22));
        } else {
            tvCouponCount.setVisibility(View.GONE);
            tvCouponContent.setText(couponContent);
            tvCouponContent.setTextColor(0xffea3a37);
        }
        tvCardMoney.setText("礼品卡余额(¥" + DisplayUtils.decimalFormat(cardMoney) + ")");

        getTotalPrice();
    }

    /**
     * 礼品卡优惠金额
     */
    private void discountCard() {
        float maxPrice = 0.00f;
        int count = 0;
        //该商品是否可以使用礼品卡
        for (int i = 0; i < confirmOrderBean.getData().getCar_list().size(); i++) {
            if (confirmOrderBean.getData().getCar_list().get(i).getCan_use_card() == 1) {
                float price = Float.parseFloat(confirmOrderBean.getData().getCar_list().get(i).getPrice());
                int num = confirmOrderBean.getData().getCar_list().get(i).getNum();
                maxPrice += price * num;
                count++;
            }
        }

        //单笔订单实付金额为不得低于1分钱
        if (cardMoney <= (maxPrice - 0.01 * count)) {
            displayCard = cardMoney;
            actualCard = cardMoney;
        } else {
            displayCard = maxPrice;
            actualCard = (float) (maxPrice - 0.01 * count);
        }
    }


    /**
     * @param position 改变购物车数量
     */
    private void changeCartCount(final int position, final int currentCount) {
        OkHttpUtils.post()
                .url(Constant.CART_COUNT)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("car_id", confirmOrderBean.getData().getCar_list().get(position).getId() + "")
                .addParams("num", currentCount + "")
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
                            //修改购物车数量成功
                            if (code == 1) {
                                confirmOrderBean.getData().getCar_list().get(position).setNum(currentCount);
                                adapter.notifyDataSetChanged();

                                if (couponId != null) {
                                    //已选择优惠券，判断当前优惠券是否选择
                                    if (!isCouponAvailable()) {
                                        couponId = null;
                                    }
                                    initCoupon();
                                } else {
                                    //未选择优惠券，礼品卡选择
                                    if (checkboxCard.isChecked()) {
                                        discountCard();
                                    }
                                    canCouponSelect = true;
                                    initData();
                                }

                            } else {
                                ToastUtils.showToast(ConfirmOrderActivity.this, "库存不足");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 是否达到优惠券使用要求
     * 商品数量减少达不到关联优惠券要求，会取消优惠券
     */
    private boolean isCouponAvailable() {
        float maxPrice = 0.00f;
        int count = 0;
        //该商品是否包含在优惠券中
        String[] split = goodsIds.split(",");
        for (int i = 0; i < confirmOrderBean.getData().getCar_list().size(); i++) {
            if (Arrays.asList(split).contains(confirmOrderBean.getData().getCar_list().get(i)
                    .getGoods_id() + "")) {
                float price = Float.parseFloat(confirmOrderBean.getData().getCar_list().get(i).getPrice());
                int num = confirmOrderBean.getData().getCar_list().get(i).getNum();
                maxPrice += price * num;
                count++;
            }
        }
        //单种商品最高总价格
        if (maxPrice >= Float.parseFloat(couponMinMoney)) {
            if (Float.parseFloat(couponMoney) <= (maxPrice - 0.01 * count)) {
                displayCoupon = Float.parseFloat(couponMoney);
                actualCoupon = Float.parseFloat(couponMoney);
            } else {
                displayCoupon = maxPrice;
                actualCoupon = (float) (maxPrice - 0.01 * count);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取总价格
     */
    private String getTotalPrice() {
        float totalPrice = 0;
        for (int i = 0; i < confirmOrderBean.getData().getCar_list().size(); i++) {
            float price = Float.parseFloat(confirmOrderBean.getData().getCar_list().get(i).getPrice());
            int num = confirmOrderBean.getData().getCar_list().get(i).getNum();
            totalPrice += price * num;
        }
        tvGoodsTotal.setText("¥" + DisplayUtils.decimalFormat(totalPrice));
        //优惠金额
        if (couponId != null) {
            totalPrice -= actualCoupon;
            tvCouponDiscount.setText("- ¥" + DisplayUtils.decimalFormat(displayCoupon));
        } else if (checkboxCard.isChecked()) {
            totalPrice -= actualCard;
            tvCouponDiscount.setText("- ¥" + DisplayUtils.decimalFormat(displayCard));
        } else if (!checkboxCard.isChecked() && couponId == null) {
            tvCouponDiscount.setText("- ¥0.00");
        }

        tvNeedPay.setText("¥" + DisplayUtils.decimalFormat(totalPrice));
        return DisplayUtils.decimalFormat(totalPrice);
    }

    @OnClick({R.id.img_header_back, R.id.rl_select_address, R.id.tv_confirm_order,
            R.id.ll_select_coupon, R.id.ll_select_card, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                customGoodsLog();
                finish();
                break;
            case R.id.rl_select_address:
                if (addressListBean == null) {
                    if (isNoAddress) {
                        //用户为创建收货地址，点击跳转新建地址页面
                        Intent intent = new Intent(ConfirmOrderActivity.this, AddAddressActivity.class);
                        intent.putExtra("type", 1);
                        startActivityForResult(intent, 2);
                    } else {
                        //用户已选择地址被删除，点击重新选择地址
                        Intent intent1 = new Intent(ConfirmOrderActivity.this, DeliveryAddressActivity.class);
                        intent1.putExtra("type", 2);
                        startActivityForResult(intent1, 3);
                    }
                } else {
                    //已选择收货地址，点击跳转选择地址页面
                    Intent intent = new Intent(ConfirmOrderActivity.this, DeliveryAddressActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("address_id", addressListBean.getId());
                    startActivityForResult(intent, 3);
                }
                break;
            case R.id.tv_confirm_order:
                if (addressListBean == null) {
                    ToastUtils.showToast(this, "请选择地址");
                } else {
                    confirmOrder();
                }
                break;
            case R.id.ll_select_coupon:
                if (checkboxCard.isChecked()) {
                    ToastUtils.showToast(this, "优惠券和礼品卡不能同时使用");
                } else {
                    Intent intent = new Intent(this, SelectCouponActivity.class);
                    intent.putExtra("cart_ids", cartIds);
                    startActivityForResult(intent, 1);
                }

                break;
            case R.id.ll_select_card:
                Intent intent = new Intent(this, GiftCardActivity.class);
                intent.putExtra("type", "select");
                startActivityForResult(intent, 4);
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 确认下单
     */
    private void confirmOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("car_ids", cartIds);
        if (couponId != null) {
            map.put("ticket_id", couponId);
        }
        if (checkboxCard.isChecked()) {
            map.put("reduce_card", "1");
        } else {
            map.put("reduce_card", "0");
        }

        map.put("name", addressListBean.getName());
        map.put("phone", addressListBean.getPhone());
        map.put("province", addressListBean.getProvince());
        map.put("city", addressListBean.getCity());
        map.put("area", addressListBean.getArea());
        map.put("address", addressListBean.getAddress());
        map.put("address_id", String.valueOf(addressListBean.getId()));
        if (logId != null) {
            map.put("log_id", logId);
        }
        //1：app支付
        map.put("method", "1");

        OkHttpUtils.post()
                .url(Constant.CONFIRM_ORDER)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.log(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.log(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 1) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String orderId = jsonObject1.getString("order_id");
                                if (orderId != null) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", cartIds);
                                    //下单事件监听
                                    MobclickAgent.onEvent(ConfirmOrderActivity.this, "place_order", map);
                                    ToastUtils.showToast(ConfirmOrderActivity.this, msg);

                                    //下单成功，结束前面的页面
                                    EventBus.getDefault().post("order_success");

                                    payOrderUtil = new PayOrderUtils(ConfirmOrderActivity.this,
                                            getTotalPrice(), orderId);
                                    payOrderUtil.payMoney();

                                }
                            } else {
                                ToastUtils.showToast(ConfirmOrderActivity.this, msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //优惠券
            case 1:
                switch (resultCode) {
                    //点击返回按钮
                    case 1:
                        couponNum = data.getIntExtra("coupon_num", couponNum);
                        initCoupon();
                        break;
                    //不使用优惠券
                    case 2:
                        couponId = null;
                        couponNum = data.getIntExtra("coupon_num", couponNum);
                        initCoupon();
                        break;
                    //使用优惠券
                    case 3:
                        couponId = data.getStringExtra("coupon_id");
                        couponMoney = data.getStringExtra("coupon_money");
                        couponContent = data.getStringExtra("coupon_content");
                        couponMinMoney = data.getStringExtra("coupon_min_money");
                        goodsIds = data.getStringExtra("goods_ids");
                        isCouponAvailable();
                        initCoupon();
                        break;
                }
                break;
            //新增地址
            case 2:
                if (resultCode == 1) {
                    getAddress(data);
                }
                break;
            //选择地址
            case 3:
                switch (resultCode) {
                    //已选择地址
                    case 1:
                        getAddress(data);
                        break;
                    //收货地址全部被删除，重新创建地址
                    case 2:
                        addressListBean = null;
                        isNoAddress = true;
                        initAddress();
                        break;
                    //已选择地址被删除，重新选择收货地址
                    case 3:
                        addressListBean = null;
                        isNoAddress = false;
                        initAddress();
                        break;
                    //收货地址被修改，刷新数据
                    case 4:
                        getAddress(data);
                        break;
                }
                break;
            //礼品卡
            case 4:
                switch (resultCode) {
                    case 1:
                        cardMoney = data.getFloatExtra("card_money", 0);
                        discountCard();
                        initCoupon();
                        break;
                }
                break;
        }
    }

    /**
     * @param intent 地址返回值
     */
    private void getAddress(Intent intent) {
        addressListBean = (ConfirmOrderBean.DataBean.AddressListBean) intent.getSerializableExtra("address");
        initAddress();
    }

    /**
     * 商品订制跟踪
     */
    private void customGoodsLog() {
        if (logId != null) {
            OkHttpUtils.post()
                    .url(Constant.GOODS_LOG)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("id", logId)
                    .addParams("goods_time", String.valueOf(DateUtils.getCurrentTime() - goodsTime))
                    .addParams("dingzhi_time", String.valueOf(dingzhiTime))
                    .addParams("goods_id", goodsId)
                    .addParams("goods_name", goodsName)
                    .addParams("click_dingzhi", "1")
                    .addParams("click_pay", "0")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            customGoodsLog();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
