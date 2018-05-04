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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.BmpRecycleUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.cloudworkshop.miaoding.view.ScrollViewContainer;
import okhttp3.Call;

/**
 * Author：Libin on 2017-04-21 11:58
 * Email：1993911441@qq.com
 * Describe：作品详情（老版）
 */
public class WorksDetailActivity1 extends BaseActivity {
    @BindView(R.id.img_goods_like)
    ImageView imgAddLike;
    @BindView(R.id.img_goods_consult)
    ImageView imgConsult;
    @BindView(R.id.ll_buy_works)
    LinearLayout llBuyWorks;
    @BindView(R.id.img_works)
    ImageView imgWorks;
    @BindView(R.id.img_works_designer)
    CircleImageView imgDesigner;
    @BindView(R.id.tv_name_works)
    TextView tvDesignerName;
    @BindView(R.id.tv_works_feature)
    TextView tvDesignerFeature;
    @BindView(R.id.tv_content_works)
    TextView tvDesignerInfo;
    @BindView(R.id.img_works_details)
    ImageView imgDetails;
    @BindView(R.id.img_back_works)
    ImageView imgBack;
    @BindView(R.id.img_share_works)
    ImageView imgShare;
    @BindView(R.id.tv_works_cart)
    TextView tvAddCart;
    @BindView(R.id.tv_works_buy)
    TextView tvBuyWorks;
    @BindView(R.id.tv_count_collect)
    TextView tvCollectCount;
    @BindView(R.id.rv_user_collect)
    RecyclerView rvCollectUser;
    @BindView(R.id.tv_count_comment)
    TextView tvCommentCount;
    @BindView(R.id.tv_check_evaluate)
    TextView tvCheckEvaluate;
    @BindView(R.id.img_user_avatar)
    CircleImageView imgUser;
    @BindView(R.id.tv_name_user)
    TextView tvUserName;
    @BindView(R.id.tv_comment_time)
    TextView tvCommentTime;
    @BindView(R.id.tv_evaluate_content)
    TextView tvEvaluateContent;
    @BindView(R.id.rv_evaluate_picture)
    RecyclerView rvEvaluate;
    @BindView(R.id.tv_goods_comment)
    TextView tvTypeGoods;
    @BindView(R.id.scroll_container_works)
    ScrollViewContainer scrollContainer;
    @BindView(R.id.img_user_grade)
    ImageView imgUserGrade;
    @BindView(R.id.ll_designer_info)
    LinearLayout llDesignerInfo;
    @BindView(R.id.img_works_details1)
    ImageView imgDetails1;
    @BindView(R.id.img_works_details2)
    ImageView imgDetails2;
    @BindView(R.id.ll_null_evaluate)
    LinearLayout llNullEvaluate;
    @BindView(R.id.ll_null_collect)
    LinearLayout llNullCollect;
    @BindView(R.id.view_evaluate)
    View viewEvaluate;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

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
        setContentView(R.layout.activity_works_detail);
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
                        if (worksBean != null) {
                            initView();
                        }
                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {

        Glide.with(getApplicationContext())
                .load(Constant.IMG_HOST + worksBean.getData().getThumb())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgWorks);
        Glide.with(getApplicationContext())
                .load(Constant.IMG_HOST + worksBean.getData().getDesigner().getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgDesigner);

        if (worksBean.getData().getIs_collect() == 1) {
            imgAddLike.setImageResource(R.mipmap.icon_add_like);
        } else {
            imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
        }

        tvDesignerName.setTypeface(DisplayUtils.setTextType(this));
        tvDesignerName.setText(worksBean.getData().getDesigner().getName());
        tvDesignerFeature.setText(worksBean.getData().getDesigner().getTag());
        tvDesignerInfo.setText(worksBean.getData().getDesigner().getIntroduce());

        //喜爱人数
        if (worksBean.getData().getCollect_num() > 0) {
            tvCollectCount.setText("喜爱  （" + worksBean.getData().getCollect_num() + "人）");
            rvCollectUser.setVisibility(View.VISIBLE);
            rvCollectUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            CommonAdapter<WorksDetailBean.DataBean.CollectUserBean> collectAdapter = new CommonAdapter
                    <WorksDetailBean.DataBean.CollectUserBean>(this, R.layout.listitem_user_collect,
                    worksBean.getData().getCollect_user()) {
                @Override
                protected void convert(ViewHolder holder, WorksDetailBean.DataBean.CollectUserBean
                        collectUserBean, int position) {
                    Glide.with(WorksDetailActivity1.this)
                            .load(Constant.IMG_HOST + collectUserBean.getAvatar())
                            .centerCrop()
                            .into((ImageView) holder.getView(R.id.img_avatar_collect));
                }
            };
            rvCollectUser.setAdapter(collectAdapter);
        } else {
            llNullCollect.setVisibility(View.GONE);
        }

        //评价人数
        if (worksBean.getData().getComment_num() > 0) {
            tvCommentCount.setText("评价  （" + worksBean.getData().getComment_num() + "）");
            Glide.with(getApplicationContext())
                    .load(Constant.IMG_HOST + worksBean.getData().getNew_comment().getAvatar())
                    .centerCrop()
                    .into(imgUser);
            tvUserName.setText(worksBean.getData().getNew_comment().getUser_name());
            Glide.with(getApplicationContext())
                    .load(Constant.IMG_HOST + worksBean.getData().getNew_comment().getUser_grade().getImg2())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imgUserGrade);
            tvCommentTime.setText(DateUtils.getDate("yyyy-MM-dd", worksBean.getData().getNew_comment().getC_time()));
            tvEvaluateContent.setText(worksBean.getData().getNew_comment().getContent());
            if (worksBean.getData().getNew_comment().getImg_list() != null && worksBean.getData()
                    .getNew_comment().getImg_list().size() > 0) {
                rvEvaluate.setLayoutManager(new GridLayoutManager(WorksDetailActivity1.this, 3));
                CommonAdapter<String> evaluateAdapter = new CommonAdapter<String>(WorksDetailActivity1
                        .this, R.layout.listitem_user_comment, worksBean.getData().getNew_comment().getImg_list()) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        Glide.with(WorksDetailActivity1.this)
                                .load(Constant.IMG_HOST + s)
                                .centerCrop()
                                .into((ImageView) holder.getView(R.id.img_user_comment));
                    }
                };
                rvEvaluate.setAdapter(evaluateAdapter);

            }

        } else {
            llNullEvaluate.setVisibility(View.GONE);
        }

        tvTypeGoods.setText(worksBean.getData().getNew_comment().getGoods_intro());


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
                                imgDetails.setImageBitmap(bm0);

                                rect.set(0, height / 3, width, height / 3 * 2);
                                bm1 = decoder.decodeRegion(rect, opts);
                                imgDetails1.setImageBitmap(bm1);

                                rect.set(0, height / 3 * 2, width, height);
                                bm2 = decoder.decodeRegion(rect, opts);
                                imgDetails2.setImageBitmap(bm2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        scrollContainer.getCurrentView(new ScrollViewContainer.CurrentPageListener() {
            @Override
            public void getCurrentPage(int page) {
                if (page == 0) {
                    llBuyWorks.setVisibility(View.GONE);
                } else {
                    llBuyWorks.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick({R.id.img_goods_like, R.id.img_goods_consult, R.id.img_back_works, R.id.img_share_works,
            R.id.tv_works_cart, R.id.tv_works_buy, R.id.ll_designer_info, R.id.tv_check_evaluate,
            R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_goods_like:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    addCollection();
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "收藏");
                    startActivity(login);
                }
                break;
            case R.id.img_goods_consult:
                ContactService.contactService(this);
                break;
            case R.id.img_back_works:
                finish();
                break;
            case R.id.img_share_works:
                if (worksBean != null) {
                    ShareUtils.showShare(this, Constant.IMG_HOST + worksBean.getData().getThumb(),
                            worksBean.getData().getName(), worksBean.getData().getContent(),
                            Constant.WORKS_SHARE + "?content=2&id=" + id);
                }
                break;
            case R.id.tv_works_cart:
                index = 2;
                showWorksType();
                break;
            case R.id.tv_works_buy:
                index = 1;
                showWorksType();
                break;
            case R.id.ll_designer_info:
                Intent intent = new Intent(this, DesignerDetailActivity.class);
                intent.putExtra("id", worksBean.getData().getDesigner().getId() + "");
                startActivity(intent);
                break;
            case R.id.tv_check_evaluate:
                if (worksBean.getData().getComment_num() > 0) {
                    Intent intent1 = new Intent(this, AllEvaluationActivity.class);
                    intent1.putExtra("goods_id", id);
                    startActivity(intent1);
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

        View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_select_type, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        //当前activity被销毁会导致
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }

        DisplayUtils.setBackgroundAlpha(this, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(WorksDetailActivity1.this, false);
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
                    = new CommonAdapter<WorksDetailBean.DataBean.SizeListBeanX>(WorksDetailActivity1.this,
                    R.layout.listitem_works_size, worksBean.getData().getSize_list()) {
                @Override
                protected void convert(ViewHolder holder, WorksDetailBean.DataBean.SizeListBeanX positionBean, int position) {
                    TextView tvSize = holder.getView(R.id.tv_works_size);
                    tvSize.setText(positionBean.getSize_name());

                    if (currentSize == position) {
                        tvSize.setTextColor(Color.WHITE);
                        tvSize.setBackgroundResource(R.drawable.circle_black);

                    } else {
                        tvSize.setTextColor(ContextCompat.getColor(WorksDetailActivity1.this, R.color.dark_gray_22));
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
                    (WorksDetailActivity1.this, R.layout.listitem_works_color, colorList) {
                @Override
                protected void convert(ViewHolder holder, WorksDetailBean.DataBean.SizeListBeanX.SizeListBean
                        positionBean, int position) {
                    CircleImageView imgColor = holder.getView(R.id.img_works_color);
                    CircleImageView imgMask = holder.getView(R.id.img_works_mask);
                    Glide.with(WorksDetailActivity1.this)
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
                    ToastUtils.showToast(WorksDetailActivity1.this, colorList.get(position).getColor_name());
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
                    if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(WorksDetailActivity1.this, "token"))) {
                        addToCart();
                    } else {
                        Intent login = new Intent(WorksDetailActivity1.this, LoginActivity.class);
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

    /**
     * 重新选择规格，刷新页面
     */
    private void reSelectWorks() {
        colorAdapter.notifyDataSetChanged();
        tvPrice.setTypeface(DisplayUtils.setTextType(WorksDetailActivity1.this));
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
                            MobclickAgent.onEvent(WorksDetailActivity1.this, "add_cart");
                            if (index == 1) {
                                Intent intent = new Intent(WorksDetailActivity1.this,
                                        ConfirmOrderActivity.class);
                                intent.putExtra("cart_id", cartId);
                                mPopupWindow.dismiss();

                                startActivity(intent);

                            } else if (index == 2) {
                                ToastUtils.showToast(WorksDetailActivity1.this, "加入购物袋成功");
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
                                    MobclickAgent.onEvent(WorksDetailActivity1.this, "collection");
                                    imgAddLike.setImageResource(R.mipmap.icon_add_like);
                                    ToastUtils.showToast(WorksDetailActivity1.this, "收藏成功");
                                    break;
                                case 2:
                                    imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
                                    ToastUtils.showToast(WorksDetailActivity1.this, "已取消收藏");
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
