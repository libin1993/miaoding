package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
import cn.cloudworkshop.miaoding.bean.WorksDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.jazzyviewpager.JazzyViewPager;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.cloudworkshop.miaoding.view.TyperTextView;
import okhttp3.Call;


/**
 * Author：Libin on 2017-09-08 13:34
 * Email：1993911441@qq.com
 * Describe：设计师作品详情（当前版）
 */
public class WorksDetailActivity extends BaseActivity {
    @BindView(R.id.vp_works_detail)
    JazzyViewPager vpWorks;
    @BindView(R.id.img_add_bag)
    ImageView imgAddBag;
    @BindView(R.id.img_buy_works)
    ImageView imgBuyWorks;
    @BindView(R.id.rgs_indicator_works)
    RadioGroup rgsIndicator;
    @BindView(R.id.img_goods_back)
    ImageView imgBack;
    @BindView(R.id.img_goods_share)
    ImageView imgShare;
    @BindView(R.id.tv_content_goods)
    TyperTextView tvContent;
    @BindView(R.id.img_works_info)
    ImageView imgWorksInfo;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //商品id
    private String id;
    private String shop_id;
    private String market_id;
    private WorksDetailBean worksBean;

    private List<WorksDetailBean.DataBean.SizeListBeanX.SizeListBean> colorList = new ArrayList<>();
    private CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX.SizeListBean> colorAdapter;
    //库存
    private int stock;
    //购买数量
    private int count = 1;
    //尺码
    private int currentSize = 0;
    //颜色
    private int currentColor = 0;
    //1、直接购买  2、加入购物车
    private int type;
    private PopupWindow mPopupWindow;

    private TextView tvPrice;
    private TextView tvStock;
    private TextView tvCount;
    private TextView tvBuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_new);
        ButterKnife.bind(this);

        getData();
        initData();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        rgsIndicator.removeAllViews();
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        initData();
    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shop_id = intent.getStringExtra("shop_id");
        market_id = intent.getStringExtra("market_id");

    }


    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.GOODS_DETAILS)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("goods_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        worksBean = GsonUtils.jsonToBean(response, WorksDetailBean.class);
                        if (worksBean.getData() != null) {
                            initView();
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {
        if (!TextUtils.isEmpty(worksBean.getData().getImg_introduce().get(0))) {
            typerText(worksBean.getData().getImg_introduce().get(0));
        }

        vpWorks.setOffscreenPageLimit(worksBean.getData().getImg_list().size());
        vpWorks.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return worksBean.getData().getImg_list().size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = LayoutInflater.from(WorksDetailActivity.this)
                        .inflate(R.layout.viewpager_goods_details, null);
                final ImageView img = (ImageView) view.findViewById(R.id.img_goods_picture);
                Glide.with(WorksDetailActivity.this)
                        .load(Constant.IMG_HOST + worksBean.getData().getImg_list().get(position))
                        .placeholder(R.mipmap.place_holder_news)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(img);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vpWorks.setCurrentItem(0);


        for (int i = 0; i < worksBean.getData().getImg_list().size(); i++) {
            RadioButton radioButton = new RadioButton(this);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(18, 18);
            layoutParams.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(null);
            radioButton.setClickable(false);
            radioButton.setBackgroundResource(R.drawable.viewpager_indicator);
            rgsIndicator.addView(radioButton);
        }
        ((RadioButton) rgsIndicator.getChildAt(0)).setChecked(true);

        vpWorks.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvContent.setAlpha(1 - (float) positionOffsetPixels / vpWorks.getWidth());

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgsIndicator.getChildAt(position)).setChecked(true);
                if (!TextUtils.isEmpty(worksBean.getData().getImg_introduce().get(position))) {
                    typerText(worksBean.getData().getImg_introduce().get(position));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * @param content 逐字显示，首个文字字体变大
     */
    private void typerText(String content) {

        String str = "<font><big><big>" +
                content.charAt(0) +
                "</big></big></font>" +
                content.substring(1);

        tvContent.animateText(Html.fromHtml(str));
    }

    @OnClick({R.id.img_add_bag, R.id.img_buy_works, R.id.img_goods_back, R.id.img_goods_share,
            R.id.img_works_info,R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_bag:
                type = 2;
                showWorksType();
                break;
            case R.id.img_buy_works:
                type = 1;
                showWorksType();
                break;
            case R.id.img_goods_back:
                finish();
                break;
            case R.id.img_goods_share:
                if (worksBean != null && worksBean.getData() != null) {
                    String share_url = Constant.WORKS_SHARE + "?goods_id=" + id;
                    if (shop_id != null) {
                        share_url += "&shop_id=" + shop_id;
                    }
                    if (market_id != null) {
                        share_url += "&market_id=" + market_id;
                    }
                    share_url += "&type=2";
                    String uid = SharedPreferencesUtils.getStr(this, "uid");
                    if (!TextUtils.isEmpty(uid)) {
                        share_url += "&shareout_id=" + uid;
                    }
                    ShareUtils.showShare(this, Constant.IMG_HOST + worksBean.getData().getThumb(),
                            worksBean.getData().getName(), worksBean.getData().getContent(), share_url);
                }
                break;
            case R.id.img_works_info:
                if (worksBean != null && worksBean.getData() != null) {
                    showWorksDetail();
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 商品详情
     */
    private void showWorksDetail() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_works_detail, null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (!isFinishing()) {
            popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.LEFT & Gravity.TOP, 0, 0);
        }

        DisplayUtils.setBackgroundAlpha(this, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(WorksDetailActivity.this, false);
            }
        });

        TextView tvWorks = (TextView) contentView.findViewById(R.id.tv_works_info);
        tvWorks.setText(worksBean.getData().getChengping_canshu());

    }


    /**
     * 商品规格
     */
    private void showWorksType() {
        if (worksBean != null && worksBean.getData() != null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_select_type, null);
            mPopupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setContentView(contentView);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
            if (!isFinishing()) {
                mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }

            DisplayUtils.setBackgroundAlpha(this, true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    DisplayUtils.setBackgroundAlpha(WorksDetailActivity.this, false);
                    currentColor = 0;
                    currentSize = 0;
                    count = 1;
                    colorList.clear();
                }
            });

            final ImageView imageView = (ImageView) contentView.findViewById(R.id.img_works_icon);
            tvPrice = (TextView) contentView.findViewById(R.id.tv_works_price);
            tvStock = (TextView) contentView.findViewById(R.id.tv_works_stock);
            ImageView imgCancel = (ImageView) contentView.findViewById(R.id.img_cancel_buy);
            RecyclerView rvSize = (RecyclerView) contentView.findViewById(R.id.rv_works_size);
            RecyclerView rvColor = (RecyclerView) contentView.findViewById(R.id.rv_works_color);
            TextView tvReduce = (TextView) contentView.findViewById(R.id.tv_reduce_works);
            tvCount = (TextView) contentView.findViewById(R.id.tv_buy_count);
            TextView tvAdd = (TextView) contentView.findViewById(R.id.tv_add_works);
            tvBuy = (TextView) contentView.findViewById(R.id.tv_buy_works);

            Glide.with(getApplicationContext())
                    .load(Constant.IMG_HOST + worksBean.getData().getImg_often())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.place_holder_goods)
                    .dontAnimate()
                    .into(imageView);
            tvPrice.setTypeface(DisplayUtils.setTextType(this));
            if (worksBean.getData().getSize_list() != null && worksBean.getData().getSize_list().size() > 0) {
                tvPrice.setText("¥" + worksBean.getData().getSize_list().get(0).getSize_list()
                        .get(0).getPrice());
                tvStock.setText(getString(R.string.stock)+"：" + worksBean.getData().getSize_list().get(0).getSize_list()
                        .get(0).getSku_num() + getString(R.string.piece));
                tvCount.setText("1");
                currentSize = 0;
                currentColor = 0;
                stock = worksBean.getData().getSize_list().get(0).getSize_list().get(0).getSku_num();
                remainGoodsCount(stock);
                colorList.addAll(worksBean.getData().getSize_list().get(0).getSize_list());

                //尺码
                rvSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                final CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX> sizeAdapter
                        = new CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX>(WorksDetailActivity.this,
                        R.layout.listitem_works_size, worksBean.getData().getSize_list()) {
                    @Override
                    protected void convert(ViewHolder holder, WorksDetailBean.DataBean.SizeListBeanX
                            positionBean, int position) {
                        TextView tvSize = holder.getView(R.id.tv_works_size);
                        tvSize.setText(positionBean.getSize_name());

                        if (currentSize == position) {
                            tvSize.setTextColor(Color.WHITE);
                            tvSize.setBackgroundResource(R.drawable.circle_black);

                        } else {
                            tvSize.setTextColor(ContextCompat.getColor(WorksDetailActivity.this,
                                    R.color.dark_gray_22));
                            tvSize.setBackgroundResource(R.drawable.ring_gray);
                        }
                    }
                };
                rvSize.setAdapter(sizeAdapter);

                sizeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        currentSize = holder.getLayoutPosition();
                        currentColor = 0;
                        colorList.clear();
                        colorList.addAll(worksBean.getData().getSize_list().get(currentSize).getSize_list());
                        sizeAdapter.notifyDataSetChanged();

                        reSelectWorks();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });


                //颜色
                rvColor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                colorAdapter = new CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX.SizeListBean>
                        (WorksDetailActivity.this, R.layout.listitem_works_color, colorList) {
                    @Override
                    protected void convert(ViewHolder holder, WorksDetailBean.DataBean.SizeListBeanX
                            .SizeListBean positionBean, int position) {
                        CircleImageView imgColor = holder.getView(R.id.img_works_color);
                        CircleImageView imgMask = holder.getView(R.id.img_works_mask);
                        Glide.with(WorksDetailActivity.this)
                                .load(Constant.IMG_HOST + positionBean.getColor_img())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(imgColor);
                        if (currentColor == position) {
                            imgMask.setVisibility(View.VISIBLE);
                        } else {
                            imgMask.setVisibility(View.GONE);
                        }
                    }
                };
                rvColor.setAdapter(colorAdapter);
                colorAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        currentColor = holder.getLayoutPosition();
                        ToastUtils.showToast(WorksDetailActivity.this, colorList.get(position).getColor_name());
                        reSelectWorks();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });


                //增加数量
                tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count < stock) {
                            count++;
                            tvCount.setText(String.valueOf(count));
                        }
                    }
                });

                //减少数量
                tvReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count > 1) {
                            count--;
                            tvCount.setText(String.valueOf(count));
                        }
                    }
                });

                //确定购买
                tvBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(WorksDetailActivity.this, "token"))) {
                            addToCart();
                        } else {
                            Intent login = new Intent(WorksDetailActivity.this, LoginActivity.class);
                            login.putExtra("page_name", "立即购买");
                            startActivity(login);
                        }
                    }
                });
            }

            //取消购买
            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }

    }

    /**
     * 重新选择规格，刷新页面
     */
    private void reSelectWorks() {
        colorAdapter.notifyDataSetChanged();
        tvPrice.setTypeface(DisplayUtils.setTextType(WorksDetailActivity.this));
        tvPrice.setText("¥" + worksBean.getData().getSize_list().get(currentSize).getSize_list()
                .get(currentColor).getPrice());
        tvStock.setText(getString(R.string.stock)+"：" + worksBean.getData().getSize_list()
                .get(currentSize).getSize_list().get(currentColor).getSku_num() + getString(R.string.piece));
        stock = worksBean.getData().getSize_list().get(currentSize)
                .getSize_list().get(currentColor).getSku_num();
        remainGoodsCount(worksBean.getData().getSize_list().get(currentSize)
                .getSize_list().get(currentColor).getSku_num());
        count = 1;
        tvCount.setText(String.valueOf(count));
    }

    /**
     * 加入购物车
     */
    private void addToCart() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("type", String.valueOf(type));
        map.put("goods_id", id);
        map.put("goods_type", "2");

        if (shop_id != null) {
            map.put("shop_id", shop_id);
        }
        if (market_id != null) {
            map.put("market_id", market_id);
        }
        WorksDetailBean.DataBean.SizeListBeanX.SizeListBean sizeListBean = worksBean.getData()
                .getSize_list().get(currentSize).getSize_list().get(currentColor);
        map.put("price", sizeListBean.getPrice());
        map.put("goods_name", worksBean.getData().getName());
        map.put("goods_thumb", worksBean.getData().getImg_often());
        map.put("size_ids", String.valueOf(sizeListBean.getId()));
        map.put("size_content", getString(R.string.color)+":" + sizeListBean.getColor_name() + ";"+getString(R.string.size)+":" + worksBean.getData()
                .getSize_list().get(currentSize).getSize_name());
        map.put("num", tvCount.getText().toString().trim());
        map.put("size_type", String.valueOf(sizeListBean.getType()));
        if (!TextUtils.isEmpty(worksBean.getData().getLt_data()) && currentSize == 0) {
            map.put("lt_data_id", worksBean.getData().getLt_data());
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
                            if (type == 2) {
                                String msg = jsonObject.getString("msg");
                                ToastUtils.showToast(WorksDetailActivity.this, msg);
                            }

                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String cartId = jsonObject1.getString("car_id");

                            if (cartId != null) {
                                MobclickAgent.onEvent(WorksDetailActivity.this, "add_cart");
                                mPopupWindow.dismiss();
                                if (type == 1) {
                                    Intent intent = new Intent(WorksDetailActivity.this, ConfirmOrderActivity.class);
                                    intent.putExtra("cart_id", cartId);
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
     * 剩余库存
     *
     * @param counts
     */
    private void remainGoodsCount(int counts) {
        if (counts == 0) {
            tvBuy.setEnabled(false);
            tvBuy.setBackgroundColor(0xffbdbdbd);
        } else {
            tvBuy.setEnabled(true);
            tvBuy.setBackgroundColor(0xff2e2e2e);
        }
    }
}
