package cn.cloudworkshop.miaoding.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomResultBean;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/31 10:23
 * Email：1993911441@qq.com
 * Describe：订制商品结果
 */
public class CustomizeResultActivity extends BaseActivity {

    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;
    @BindView(R.id.rl_tailor_positive)
    RelativeLayout rlTailorPositive;
    @BindView(R.id.tv_tailor_name)
    TextView tvTailorName;
    @BindView(R.id.rv_tailor_name)
    RecyclerView rvTailorName;
    @BindView(R.id.tv_tailor_price)
    TextView tvTailorPrice;
    @BindView(R.id.tv_add_bag)
    TextView tvAddBag;
    @BindView(R.id.img_header_share)
    ImageView imgShoppingBag;
    @BindView(R.id.rl_tailor_back)
    RelativeLayout rlTailorBack;
    @BindView(R.id.rl_tailor_inside)
    RelativeLayout rlTailorInside;
    @BindView(R.id.rgs_tailor_position)
    RadioGroup rgsTailorPosition;
    @BindView(R.id.img_default_item)
    ImageView imgDefaultItem;
    @BindView(R.id.rl_tailor_position)
    RelativeLayout rlTailorPosition;
    @BindView(R.id.rl_custom_result)
    RelativeLayout rlCustomResult;

    //1:直接购买 2：加入购物袋
    private int type;

    private float x1 = 0;
    private float x2 = 0;

    private CustomItemBean customBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_result);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        tvHeaderTitle.setText("定制详情");
        imgShoppingBag.setVisibility(View.VISIBLE);
        imgShoppingBag.setImageResource(R.mipmap.icon_shopping_bag);
        getData();
    }

    /**
     * 商品详情
     */

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        customBean = (CustomItemBean) bundle.getSerializable("tailor");
        initView();
    }


    /**
     * 加载视图
     */
    private void initView() {

        //部件图展示
        switch (customBean.getIs_scan()) {
            //自选定制配件，展示配件拼接图片
            case 0:
                for (int i = 0; i < customBean.getItemBean().size(); i++) {
                    ImageView img = new ImageView(this);
                    Glide.with(getApplicationContext())
                            .load(Constant.IMG_HOST + customBean.getItemBean().get(i).getImg())
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(img);
                    switch (customBean.getItemBean().get(i).getPosition_id()) {
                        case 1:
                            rlTailorPositive.addView(img);
                            break;
                        case 2:
                            rgsTailorPosition.getChildAt(1).setVisibility(View.VISIBLE);
                            rlTailorBack.addView(img);
                            break;
                        case 3:
                            rgsTailorPosition.getChildAt(2).setVisibility(View.VISIBLE);
                            rlTailorInside.addView(img);
                            break;
                    }

                }

                //正面
                ((RadioButton) rgsTailorPosition.getChildAt(0)).setChecked(true);
                rgsTailorPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {

                        for (int m = 0; m < rgsTailorPosition.getChildCount(); m++) {
                            RadioButton rBtn = (RadioButton) radioGroup.getChildAt(m);
                            if (rBtn.getId() == i) {
                                switch (m) {
                                    case 0:
                                        rlTailorPositive.setVisibility(View.VISIBLE);
                                        rlTailorBack.setVisibility(View.GONE);
                                        rlTailorInside.setVisibility(View.GONE);
                                        break;
                                    case 1:
                                        rlTailorBack.setVisibility(View.VISIBLE);
                                        rlTailorPositive.setVisibility(View.GONE);
                                        rlTailorInside.setVisibility(View.GONE);
                                        break;
                                    case 2:
                                        rlTailorInside.setVisibility(View.VISIBLE);
                                        rlTailorPositive.setVisibility(View.GONE);
                                        rlTailorBack.setVisibility(View.GONE);
                                        break;
                                }
                            }
                        }
                    }
                });
                //正面，滑动监听
                rlTailorPositive.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x1 = motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                x2 = motionEvent.getX();
                            case MotionEvent.ACTION_UP:
                                if (x1 < x2) {
                                    if (rgsTailorPosition.getChildAt(1).getVisibility() == View.VISIBLE) {
                                        ((RadioButton) rgsTailorPosition.getChildAt(1)).setChecked(true);
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });
                //反面，滑动监听
                rlTailorBack.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x1 = motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                x2 = motionEvent.getX();
                            case MotionEvent.ACTION_UP:
                                if (x1 > x2) {
                                    ((RadioButton) rgsTailorPosition.getChildAt(0)).setChecked(true);
                                } else if (x1 < x2) {
                                    if (rgsTailorPosition.getChildAt(2).getVisibility() == View.VISIBLE) {
                                        ((RadioButton) rgsTailorPosition.getChildAt(2)).setChecked(true);
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });

                //里子，滑动监听
                rlTailorInside.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x1 = motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                x2 = motionEvent.getX();
                            case MotionEvent.ACTION_UP:
                                if (x1 > x2) {
                                    ((RadioButton) rgsTailorPosition.getChildAt(1)).setChecked(true);
                                }
                                break;
                        }
                        return true;
                    }
                });
                break;
            //定制同款，显示默认图片
            case 1:
                rgsTailorPosition.setVisibility(View.GONE);
                rlTailorPosition.setVisibility(View.GONE);
                imgDefaultItem.setVisibility(View.VISIBLE);

                Glide.with(getApplicationContext())
                        .load(Constant.IMG_HOST + customBean.getDefault_img())
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgDefaultItem);
                break;
        }


        tvTailorName.setText(customBean.getGoods_name());
        tvTailorPrice.setText("¥" + customBean.getPrice());
        rvTailorName.setFocusable(false);
        //配件字符串和个性绣花字符串拼接
        if (!TextUtils.isEmpty(customBean.getSpec_content())) {
            String customStr = customBean.getSpec_content();
            if (!TextUtils.isEmpty(customBean.getDiy_contet())) {
                customStr += ";" + customBean.getDiy_contet();
            }

            List<CustomResultBean> itemList = new ArrayList<>();
            String[] typeStr = customStr.split(";");
            for (String aTypeStr : typeStr) {
                String[] nameStr = aTypeStr.split(":");
                CustomResultBean tailorInfoBean = new CustomResultBean();
                tailorInfoBean.setType(nameStr[0]);
                tailorInfoBean.setName(nameStr[1]);
                itemList.add(tailorInfoBean);
            }


            MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
            linearLayoutManager.setScrollEnabled(false);
            rvTailorName.setLayoutManager(linearLayoutManager);
            CommonAdapter<CustomResultBean> adapter = new CommonAdapter<CustomResultBean>(this,
                    R.layout.listitem_customize_result, itemList) {
                @Override
                protected void convert(ViewHolder holder, CustomResultBean tailorInfoBean, int position) {
                    holder.setText(R.id.tv_tailor_type, tailorInfoBean.getType());
                    holder.setText(R.id.tv_tailor_item, tailorInfoBean.getName());
                }
            };
            rvTailorName.setAdapter(adapter);
        }
    }


    @OnClick({R.id.img_header_back, R.id.tv_buy_now, R.id.tv_add_bag, R.id.img_header_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_buy_now:
                type = 1;
                addToCart();
                break;
            case R.id.tv_add_bag:
                type = 2;
                addToCart();
                break;
            case R.id.img_header_share:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
        }
    }


    /**
     * 加入购物车
     */
    private void addToCart() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("type", String.valueOf(type));
        map.put("price_id", customBean.getPrice_type());
        map.put("goods_id", customBean.getId());
        if (!TextUtils.isEmpty(customBean.getShop_id())) {
            map.put("shop_id", customBean.getShop_id());
        }
        if (!TextUtils.isEmpty(customBean.getMarket_id())) {
            map.put("market_id", customBean.getMarket_id());
        }
        map.put("goods_type", "1");
        map.put("price", customBean.getPrice());
        map.put("goods_name", customBean.getGoods_name());
        if (TextUtils.isEmpty(customBean.getDefault_img())) {
            map.put("goods_thumb", customBean.getImg_url());
        } else {
            map.put("goods_thumb", customBean.getDefault_img());
        }

        if (!TextUtils.isEmpty(customBean.getSpec_ids())) {
            map.put("spec_ids", customBean.getSpec_ids());
        }
        if (!TextUtils.isEmpty(customBean.getSpec_content())) {
            map.put("spec_content", customBean.getSpec_content());
        }

        if (!TextUtils.isEmpty(customBean.getFabric_id())) {
            map.put("mianliao_id", customBean.getFabric_id());
        }

        if (!TextUtils.isEmpty(customBean.getBanxing_id())) {
            map.put("banxing_id", customBean.getBanxing_id());
        }

        map.put("is_scan", String.valueOf(customBean.getIs_scan()));
        if (!TextUtils.isEmpty(customBean.getDiy_contet())) {
            map.put("diy_content", customBean.getDiy_contet());
        }

        OkHttpUtils.post()
                .url(Constant.ADD_CART)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String msg = jsonObject.getString("msg");
                            //加入购物车成功
                            if (type == 2) {
                                ToastUtils.showToast(CustomizeResultActivity.this, msg);
                                aadCartAnim();
                            }
                            String cartId = jsonObject1.getString("car_id");
                            if (!TextUtils.isEmpty(cartId)) {
                                MobclickAgent.onEvent(CustomizeResultActivity.this, "add_cart");
                                //直接购买，跳转确认订单页面
                                if (type == 1) {
                                    Intent intent = new Intent(CustomizeResultActivity.this,
                                            ConfirmOrderActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("cart_id", cartId);
                                    bundle.putString("log_id", customBean.getLog_id());
                                    bundle.putLong("goods_time", customBean.getGoods_time());
                                    bundle.putLong("dingzhi_time", customBean.getDingzhi_time());
                                    bundle.putString("goods_id", customBean.getId());
                                    bundle.putString("goods_name", customBean.getGoods_name());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 加入购物车动画效果，经过一个抛物线（贝塞尔曲线），移动到购物车里
     */
    private void aadCartAnim() {
        //1、添加执行动画效果的图片
        final ImageView imgGoods = new ImageView(this);
        String imgUrl;
        if (TextUtils.isEmpty(customBean.getDefault_img())) {
            imgUrl = Constant.IMG_HOST + customBean.getImg_url();
        } else {
            imgUrl = Constant.IMG_HOST + customBean.getDefault_img();
        }
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGoods);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
        rlCustomResult.addView(imgGoods, params);


        //2、动画开始/结束点的坐标
        //贝塞尔曲线中间过程的点的坐标
        final float[] mCurrentPosition = new float[2];

        //父布局的起始点坐标
        int[] parentLocation = new int[2];
        rlCustomResult.getLocationInWindow(parentLocation);

        //商品图片的坐标
        int startLoc[] = new int[2];
        tvAddBag.getLocationInWindow(startLoc);

        //购物车图片的坐标
        int endLoc[] = new int[2];
        imgShoppingBag.getLocationInWindow(endLoc);

        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + tvAddBag.getWidth() / 3;
        float startY = startLoc[1] - parentLocation[1];

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的一半
        float toX = endLoc[0] - parentLocation[0] + imgShoppingBag.getWidth() / 4;
        float toY = endLoc[1] - parentLocation[1] + imgShoppingBag.getHeight() / 2;

        //3、计算中间动画的插值坐标（贝塞尔曲线）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二阶萨贝尔曲线
        path.quadTo(startX, toY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(700);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                imgGoods.setTranslationX(mCurrentPosition[0]);
                imgGoods.setTranslationY(mCurrentPosition[1]);
            }
        });
        //4、开始执行动画
        valueAnimator.start();

        //5、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 把移动的图片从父布局里移除
                rlCustomResult.removeView(imgGoods);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(250);
                imgShoppingBag.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

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
