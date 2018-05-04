package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.WorksDetailBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.BmpRecycleUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2017-08-15 15:26
 * Email：1993911441@qq.com
 * Describe：作品详情（老版）
 */
public class WorksDetailActivity2 extends BaseActivity {
    @BindView(R.id.img_works_detail1)
    ImageView imgDetail1;
    @BindView(R.id.img_works_detail2)
    ImageView imgDetail2;
    @BindView(R.id.img_works_detail3)
    ImageView imgDetail3;
    @BindView(R.id.img_works_collect)
    ImageView imgWorksCollect;
    @BindView(R.id.img_works_consult)
    ImageView imgWorksConsult;
    @BindView(R.id.tv_add_cart)
    TextView tvAddCart;
    @BindView(R.id.tv_confirm_buy)
    TextView tvConfirmBuy;
    @BindView(R.id.img_works_back)
    ImageView imgWorksBack;
    @BindView(R.id.img_works_share)
    ImageView imgWorksShare;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //商品id
    private String id;

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
    private int index;
    private PopupWindow mPopupWindow;
    //购物车id
    private String cartId;
    private TextView tvPrice;
    private TextView tvStock;
    private TextView tvCount;
    private TextView tvBuy;
    private Bitmap bm0;
    private Bitmap bm1;
    private Bitmap bm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_detail_new);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void getData() {
        id = getIntent().getStringExtra("id");
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

        //详情页图片尺寸超过手机支持最大尺寸
        //分割图片显示

        OkHttpUtils.get()
                .url(Constant.IMG_HOST + worksBean.getData().getContent2())
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        try {
                            if (response != null) {
                                InputStream inputStream = ImageEncodeUtils.bitmap2InputStream(response);
                                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, true);
                                int width = decoder.getWidth();
                                int height = decoder.getHeight();
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                Rect rect = new Rect();

                                //平分三份
                                rect.set(0, 0, width, height / 3);
                                bm0 = decoder.decodeRegion(rect, opts);
                                imgDetail1.setImageBitmap(bm0);

                                rect.set(0, height / 3, width, height / 3 * 2);
                                bm1 = decoder.decodeRegion(rect, opts);
                                imgDetail2.setImageBitmap(bm1);

                                rect.set(0, height / 3 * 2, width, height);
                                bm2 = decoder.decodeRegion(rect, opts);
                                imgDetail3.setImageBitmap(bm2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @OnClick({R.id.img_works_collect, R.id.img_works_consult, R.id.tv_add_cart, R.id.tv_confirm_buy,
            R.id.img_works_back, R.id.img_works_share, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_works_collect:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    if (worksBean != null && worksBean.getData() != null) {
                        addCollection();
                    }
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "收藏");
                    startActivity(login);
                }
                break;
            case R.id.img_works_consult:
                ContactService.contactService(this);
                break;
            case R.id.tv_add_cart:
                index = 2;
                if (worksBean != null) {
                    showWorksType();
                }
                break;
            case R.id.tv_confirm_buy:
                index = 1;
                if (worksBean != null && worksBean.getData() != null) {
                    showWorksType();
                }
                break;
            case R.id.img_works_back:
                finish();
                break;
            case R.id.img_works_share:
                if (worksBean != null && worksBean.getData() != null) {
                    ShareUtils.showShare(this, Constant.IMG_HOST + worksBean.getData().getThumb(),
                            worksBean.getData().getName(), worksBean.getData().getContent(),
                            Constant.WORKS_SHARE + "?content=2&id=" + id);
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
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
                    DisplayUtils.setBackgroundAlpha(WorksDetailActivity2.this, false);
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
                    .load(Constant.IMG_HOST + worksBean.getData().getThumb())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
            tvPrice.setTypeface(DisplayUtils.setTextType(this));
            if (worksBean.getData().getSize_list() != null) {
                tvPrice.setText("¥" + worksBean.getData().getSize_list().get(0).getSize_list().get(0).getPrice());
                tvStock.setText("库存：" + worksBean.getData().getSize_list().get(0).getSize_list()
                        .get(0).getSku_num() + "件");
                tvCount.setText("1");
                currentSize = 0;
                currentColor = 0;
                stock = worksBean.getData().getSize_list().get(0).getSize_list().get(0).getSku_num();
                remainGoodsCount(stock);
                colorList.addAll(worksBean.getData().getSize_list().get(0).getSize_list());

                //尺码
                rvSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                final CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX> sizeAdapter
                        = new CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX>(WorksDetailActivity2.this,
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
                            tvSize.setTextColor(ContextCompat.getColor(WorksDetailActivity2.this, R.color.dark_gray_22));
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
                        (WorksDetailActivity2.this, R.layout.listitem_works_color, colorList) {
                    @Override
                    protected void convert(ViewHolder holder, WorksDetailBean.DataBean.SizeListBeanX.SizeListBean
                            positionBean, int position) {
                        CircleImageView imgColor = holder.getView(R.id.img_works_color);
                        CircleImageView imgMask = holder.getView(R.id.img_works_mask);
                        Glide.with(WorksDetailActivity2.this)
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
                        ToastUtils.showToast(WorksDetailActivity2.this, colorList.get(position).getColor_name());
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
                        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(WorksDetailActivity2.this, "token"))) {
                            addToCart();
                        } else {
                            Intent login = new Intent(WorksDetailActivity2.this, LoginActivity.class);
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
        tvPrice.setTypeface(DisplayUtils.setTextType(WorksDetailActivity2.this));
        tvPrice.setText("¥" + worksBean.getData().getSize_list().get(currentSize).getSize_list()
                .get(currentColor).getPrice());
        tvStock.setText("库存：" + worksBean.getData().getSize_list()
                .get(currentSize).getSize_list().get(currentColor).getSku_num() + "件");
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

        OkHttpUtils.post()
                .url(Constant.ADD_CART)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", index + "")
                .addParams("goods_id", id)
                .addParams("goods_type", "2")
                .addParams("price", worksBean.getData().getSize_list().get(currentSize)
                        .getSize_list().get(currentColor).getPrice())
                .addParams("goods_name", worksBean.getData().getName())
                .addParams("goods_thumb", worksBean.getData().getThumb())
                .addParams("size_ids", String.valueOf(worksBean.getData().getSize_list()
                        .get(currentSize).getSize_list().get(currentColor).getId()))
                .addParams("size_content", "颜色:" + worksBean.getData().getSize_list().get(currentSize)
                        .getSize_list().get(currentColor).getColor_name() + ";尺码:" + worksBean.getData()
                        .getSize_list().get(currentSize).getSize_name())
                .addParams("num", tvCount.getText().toString().trim())
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
                            cartId = jsonObject1.getString("car_id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (cartId != null) {
                            MobclickAgent.onEvent(WorksDetailActivity2.this, "add_cart");
                            if (index == 1) {
                                Intent intent = new Intent(WorksDetailActivity2.this,
                                        ConfirmOrderActivity.class);
                                intent.putExtra("cart_id", cartId);
                                mPopupWindow.dismiss();

                                startActivity(intent);

                            } else if (index == 2) {
                                ToastUtils.showToast(WorksDetailActivity2.this, "加入购物袋成功");
                                mPopupWindow.dismiss();
                            }
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

    /**
     * 添加收藏
     */
    private void addCollection() {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", String.valueOf(2))
                .addParams("cid", id)
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
                            switch (code) {
                                case 1:
                                    MobclickAgent.onEvent(WorksDetailActivity2.this, "collection");
                                    imgWorksCollect.setImageResource(R.mipmap.icon_add_like);
                                    ToastUtils.showToast(WorksDetailActivity2.this, "收藏成功");
                                    break;
                                case 2:
                                    imgWorksCollect.setImageResource(R.mipmap.icon_cancel_like);
                                    ToastUtils.showToast(WorksDetailActivity2.this, "已取消收藏");
                                    break;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmpRecycleUtils.bmpRecycle(bm0);
        BmpRecycleUtils.bmpRecycle(bm1);
        BmpRecycleUtils.bmpRecycle(bm2);
    }
}
