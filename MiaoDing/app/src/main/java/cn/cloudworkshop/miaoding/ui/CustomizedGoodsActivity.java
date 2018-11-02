package cn.cloudworkshop.miaoding.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomGoodsBean;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.BmpRecycleUtils;
import cn.cloudworkshop.miaoding.utils.NetworkImageHolderView;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.cloudworkshop.miaoding.view.ScrollViewContainer;
import okhttp3.Call;


/**
 * Author：Libin on 2017-04-18 10:59
 * Email：1993911441@qq.com
 * Describe：定制商品详情界面
 */
public class CustomizedGoodsActivity extends BaseActivity {
    @BindView(R.id.tv_goods_sort)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_introduce)
    TextView tvGoodsContent;
    @BindView(R.id.img_tailor_back)
    ImageView imgBack;
    @BindView(R.id.tv_goods_tailor)
    TextView tvTailor;
    @BindView(R.id.banner_goods)
    ConvenientBanner bannerGoods;
    @BindView(R.id.img_add_like)
    ImageView imgAddLike;
    @BindView(R.id.img_tailor_consult)
    ImageView imgConsult;
    @BindView(R.id.ll_goods_tailor)
    LinearLayout llGoodsTailor;
    @BindView(R.id.img_tailor_share)
    ImageView imgShare;
    @BindView(R.id.img_tailor_details)
    ImageView imgDetails;
    @BindView(R.id.tv_custom_goods)
    TextView tvCustomGoods;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.rv_collect_user)
    RecyclerView rvCollectUser;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.tv_all_evaluate)
    TextView tvAllEvaluate;
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
    @BindView(R.id.scroll_container)
    ScrollViewContainer scrollContainer;
    @BindView(R.id.img_user_grade)
    ImageView imgUserGrade;
    @BindView(R.id.img_tailor_details1)
    ImageView imgDetails1;
    @BindView(R.id.img_tailor_details2)
    ImageView imgDetails2;
    @BindView(R.id.ll_no_evaluate)
    LinearLayout llNoEvaluate;
    @BindView(R.id.ll_no_collection)
    LinearLayout llNoCollection;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.view_evaluate)
    View viewEvaluate;
    @BindView(R.id.tv_goods_tip)
    TextView tvGoodsTip;
    @BindView(R.id.view_goods_tip)
    View viewGoodsTip;
    @BindView(R.id.ll_goods_tip)
    LinearLayout llGoodsTip;
    @BindView(R.id.card_goods)
    CardView cardGoods;


    //商品id
    private String id;
    private String shop_id;
    private String market_id;
    private CustomGoodsBean customBean;
    private long enterTime;
    private Bitmap bm0;
    private Bitmap bm1;
    private Bitmap bm2;
    //监听banner滑动状态
    private boolean isScrolled;
    private boolean isShow = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_goods);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    /**
     * 商品详情
     */
    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        shop_id = intent.getStringExtra("shop_id");
        market_id = intent.getStringExtra("market_id");
        enterTime = DateUtils.getCurrentTime();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
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
                        customBean = GsonUtils.jsonToBean(response, CustomGoodsBean.class);
                        if (customBean.getData() != null) {
                            initView();
                        }
                    }
                });

    }


    /**
     * 加载视图
     */
    private void initView() {

        tvGoodsName.setText(customBean.getData().getName());
        tvGoodsContent.setText(customBean.getData().getSub_name());
        if (customBean.getData().getIs_collect() == 1) {
            imgAddLike.setImageResource(R.mipmap.icon_add_like);
        } else {
            imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
        }
        bannerGoods.setCanLoop(false);
        bannerGoods.stopTurning();
        bannerGoods.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, customBean.getData().getImg_list())
                //设置两个点图片作为翻页指示器
                .setPageIndicator(new int[]{R.drawable.dot_black, R.drawable.dot_white})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        bannerGoods.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CustomizedGoodsActivity.this, ImagePreviewActivity.class);
                intent.putExtra("current_pos", position);
                intent.putStringArrayListExtra("img_list", customBean.getData().getImg_list());
                startActivity(intent);
            }
        });

        bannerGoods.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        //划到最后一张，再左滑跳转详情页
                        if (!isScrolled && bannerGoods.getCurrentItem() == customBean.getData()
                                .getImg_list().size() - 1) {
//                            scrollContainer.setAutoUp();
                            Intent intent = new Intent(CustomizedGoodsActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("img", customBean.getData().getContent2());
                            startActivity(intent);
                        }
                        isScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isScrolled = true;
                        break;

                }

            }
        });


        //喜爱人数
        if (customBean.getData().getCollect_num() > 0) {
            tvCollectCount.setText(getString(R.string.love) + "  （" + customBean.getData().getCollect_num() + getString(R.string.person) + "）");
            rvCollectUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            CommonAdapter<CustomGoodsBean.DataBean.CollectUserBean> collectAdapter = new CommonAdapter
                    <CustomGoodsBean.DataBean.CollectUserBean>(this, R.layout.listitem_user_collect,
                    customBean.getData().getCollect_user()) {
                @Override
                protected void convert(ViewHolder holder, CustomGoodsBean.DataBean.CollectUserBean
                        collectUserBean, int position) {
                    Glide.with(CustomizedGoodsActivity.this)
                            .load(Constant.IMG_HOST + collectUserBean.getAvatar())
                            .centerCrop()
                            .into((ImageView) holder.getView(R.id.img_avatar_collect));
                }

            };
            rvCollectUser.setAdapter(collectAdapter);
        } else {
            llNoCollection.setVisibility(View.GONE);
        }

        //评价人数
//        if (customBean.getData().getComment_num() > 0) {
//            tvCommentCount.setText("评价  （" + customBean.getData().getComment_num() + "）");
//            Glide.with(getApplicationContext())
//                    .load(Constant.IMG_HOST + customBean.getData().getNew_comment().getAvatar())
//                    .centerCrop()
//                    .into(imgUser);
//            tvUserName.setText(customBean.getData().getNew_comment().getUser_name());
//            Glide.with(getApplicationContext())
//                    .load(Constant.IMG_HOST + customBean.getData().getNew_comment().getUser_grade().getImg2())
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(imgUserGrade);
//            tvCommentTime.setText(DateUtils.getDate("yyyy-MM-dd", customBean.getData().getNew_comment().getC_time()));
//            tvEvaluateContent.setText(customBean.getData().getNew_comment().getContent());
//            if (customBean.getData().getNew_comment().getImg_list() != null && customBean.getData()
//                    .getNew_comment().getImg_list().size() > 0) {
//                rvEvaluate.setLayoutManager(new GridLayoutManager(CustomizedGoodsActivity.this, 3));
//                CommonAdapter<String> evaluateAdapter = new CommonAdapter<String>(CustomizedGoodsActivity
//                        .this, R.layout.listitem_user_comment, customBean.getData().getNew_comment().getImg_list()) {
//                    @Override
//                    protected void convert(ViewHolder holder, String s, int position) {
//                        Glide.with(CustomizedGoodsActivity.this)
//                                .load(Constant.IMG_HOST + s)
//                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                                .centerCrop()
//                                .into((ImageView) holder.getView(R.id.img_user_comment));
//                    }
//                };
//                rvEvaluate.setAdapter(evaluateAdapter);
//            }
//            tvTypeGoods.setText(customBean.getData().getNew_comment().getGoods_intro());
//        } else {
//            llNoEvaluate.setVisibility(View.GONE);
//        }

        llNoEvaluate.setVisibility(View.GONE);

        //详情页图片尺寸超过部分手机支持最大尺寸
        //分割图片显示

        OkHttpUtils.get()
                .url(Constant.IMG_HOST + customBean.getData().getContent2())
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


//        ViewGroup.LayoutParams layoutParams = llGoodsTip.getLayoutParams();
//
//        layoutParams.height = DisplayUtils.getMetrics(this).heightPixels;
//        llGoodsTip.setLayoutParams(layoutParams);
//        tvGoodsTip.setText(customBean.getData().getContent());
//
//        scrollContainer.getCurrentView(new ScrollViewContainer.CurrentPageListener() {
//            @Override
//            public void getCurrentPage(int page) {
//                if (page == 1) {
//                    ObjectAnimator animator = ObjectAnimator.ofFloat(cardGoods, "translationX", 150, 0);
//                    animator.setDuration(350);
//                    animator.start();
//                }
//            }
//        });
    }

    /**
     * 选择价格
     */
    private void selectGoodsPrice() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_select_price, null);
        final PopupWindow mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        //当前activity后台运行时被回收，会导致弹窗无法显示
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
        DisplayUtils.setBackgroundAlpha(this, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(CustomizedGoodsActivity.this, false);
            }
        });

        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_select_type);
        RecyclerView rvTailor = (RecyclerView) contentView.findViewById(R.id.rv_tailor_price);


        tvTitle.setText(R.string.select_goods_price);
        rvTailor.setLayoutManager(new LinearLayoutManager(CustomizedGoodsActivity.this));

        CommonAdapter<CustomGoodsBean.DataBean.PriceBean> priceAdapter = new CommonAdapter
                <CustomGoodsBean.DataBean.PriceBean>(CustomizedGoodsActivity.this,
                R.layout.listitem_select_price, customBean.getData().getPrice()) {
            @Override
            protected void convert(ViewHolder holder, CustomGoodsBean.DataBean.PriceBean priceBean, int position) {
                TextView tvPrice = holder.getView(R.id.tv_type_item);
                tvPrice.setTypeface(DisplayUtils.setTextType(CustomizedGoodsActivity.this));
                tvPrice.setText("¥" + DisplayUtils.decimalFormat((float) priceBean.getPrice()));
                holder.setText(R.id.tv_type_content, priceBean.getIntroduce());
            }
        };


        priceAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(CustomizedGoodsActivity.this, CustomizeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("goods_name", customBean.getData().getName());
                if (shop_id != null) {
                    bundle.putString("shop_id", shop_id);
                }
                if (market_id != null) {
                    bundle.putString("market_id", market_id);
                }

                bundle.putString("img_url", customBean.getData().getThumb());
                bundle.putString("price", DisplayUtils.decimalFormat((float) customBean.getData()
                        .getPrice().get(position).getPrice()));
                bundle.putString("price_type", customBean.getData().getPrice().get(position).getId() + "");
                bundle.putInt("classify_id", customBean.getData().getClassify_id());
                bundle.putString("log_id", customBean.getId());
                bundle.putLong("goods_time", enterTime);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        rvTailor.setAdapter(priceAdapter);

    }


    @OnClick({R.id.tv_goods_tailor, R.id.img_tailor_back, R.id.img_add_like, R.id.img_tailor_consult,
            R.id.img_tailor_share, R.id.tv_custom_goods, R.id.tv_all_evaluate, R.id.img_load_error,
            R.id.view_goods_tip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_tailor:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "定制");
                    startActivity(login);
                } else {
                    if (customBean != null && customBean.getData() != null) {
//                        selectGoodsPrice();
                        buyGoods();
                    }
                }
                break;
            case R.id.tv_custom_goods:
//                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
//                    Intent login = new Intent(this, QuickLoginActivity.class);
//                    login.putExtra("page_name", "定制");
//                    startActivity(login);
//                } else {
//                    if (customBean != null && customBean.getData() != null) {
//                        selectGoodsType();
//                    }
//                }

                break;
            case R.id.img_tailor_back:
                if (customBean != null && customBean.getData() != null) {
                    customGoodsLog();
                }
                finish();
                break;
            case R.id.img_add_like:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    if (customBean != null && customBean.getData() != null) {
                        addCollection();
                    }
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "收藏");
                    startActivity(login);
                }
                break;
            case R.id.img_tailor_consult:
                ContactService.contactService(this);
                break;
            case R.id.img_tailor_share:

                if (customBean != null && customBean.getData() != null) {
                    String share_url = Constant.CUSTOM_SHARE + "?goods_id=" + id;
                    if (shop_id != null) {
                        share_url += "&shop_id=" + shop_id;
                    }
                    if (market_id != null) {
                        share_url += "&market_id=" + market_id;
                    }
                    share_url += "&type=1";
                    String uid = SharedPreferencesUtils.getStr(this, "uid");
                    if (!TextUtils.isEmpty(uid)) {
                        share_url += "&shareout_id=" + uid;
                    }
                    ShareUtils.showShare(this, Constant.IMG_HOST + customBean.getData().getThumb(),
                            customBean.getData().getName(), customBean.getData().getContent(), share_url);
                }
                break;
            case R.id.tv_all_evaluate:
                if (customBean != null && customBean.getData().getComment_num() > 0) {
                    Intent intent = new Intent(this, AllEvaluationActivity.class);
                    intent.putExtra("goods_id", id);
                    startActivity(intent);
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.view_goods_tip:
//                if (isShow) {
//                    ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(cardGoods,
//                            "translationY", 0, 400);
//                    hideAnimator.setDuration(300);
//                    hideAnimator.start();
//                    isShow = false;
//                } else {
//                    ObjectAnimator showAnimator = ObjectAnimator.ofFloat(cardGoods,
//                            "translationY", 400, 0);
//                    showAnimator.setDuration(300);
//                    showAnimator.start();
//                    isShow = true;
//                }
                break;
        }
    }

    /**
     * 购买商品
     */
    private void buyGoods() {
        Intent intent = new Intent(CustomizedGoodsActivity.this, EmbroideryActivity.class);
        Bundle bundle = new Bundle();

        CustomItemBean customItemBean = new CustomItemBean();
        customItemBean.setId(id);
        customItemBean.setClassify_id(customBean.getData().getClassify_id());
        if (shop_id != null) {
            customItemBean.setShop_id(shop_id);
        }
        if (market_id != null) {
            customItemBean.setMarket_id(market_id);
        }
        customItemBean.setGoods_name(customBean.getData().getName());

        customItemBean.setImg_url(customBean.getData().getThumb());
        customItemBean.setLog_id(customBean.getId());
        customItemBean.setGoods_time(enterTime);
        customItemBean.setDingzhi_time(0);

        customItemBean.setSpec_ids(customBean.getData().getDefault_spec_ids());
        customItemBean.setSpec_content(customBean.getData().getDefault_spec_content());

        bundle.putSerializable("tailor", customItemBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    /**
     * 定制同款选择版型
     */
    private void selectGoodsType() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.ppw_select_price, null);
        final PopupWindow mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        //当前activity后台运行时被回收，会导致弹窗无法显示
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }

        DisplayUtils.setBackgroundAlpha(this, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(CustomizedGoodsActivity.this, false);
            }
        });

        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_select_type);
        RecyclerView rvTailor = (RecyclerView) contentView.findViewById(R.id.rv_tailor_price);

        tvTitle.setText(R.string.select_type);
        rvTailor.setLayoutManager(new LinearLayoutManager(CustomizedGoodsActivity.this));

        CommonAdapter<CustomGoodsBean.DataBean.BanxingListBean> typeAdapter = new CommonAdapter
                <CustomGoodsBean.DataBean.BanxingListBean>(CustomizedGoodsActivity.this,
                R.layout.listitem_select_type, customBean.getData().getBanxing_list()) {
            @Override
            protected void convert(ViewHolder holder, CustomGoodsBean.DataBean.BanxingListBean
                    banxingListBean, int position) {
                holder.setText(R.id.tv_item_type, banxingListBean.getName());
            }
        };

        rvTailor.setAdapter(typeAdapter);

        typeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mPopupWindow.dismiss();
                Intent intent;
                Bundle bundle = new Bundle();
                int classifyId = customBean.getData().getClassify_id();
                if (classifyId == 1 || classifyId == 2) {
                    intent = new Intent(CustomizedGoodsActivity.this, EmbroideryActivity.class);
                    bundle.putInt("classify_id", classifyId);
                } else {
                    intent = new Intent(CustomizedGoodsActivity.this, CustomizeResultActivity.class);
                }

                CustomItemBean tailorItemBean = new CustomItemBean();
                tailorItemBean.setId(id);
                if (shop_id != null) {
                    tailorItemBean.setShop_id(shop_id);
                }
                if (market_id != null) {
                    tailorItemBean.setMarket_id(market_id);
                }
                tailorItemBean.setGoods_name(customBean.getData().getName());
                tailorItemBean.setPrice(DisplayUtils.decimalFormat((float) customBean.getData()
                        .getDefault_price()));
                tailorItemBean.setImg_url(customBean.getData().getThumb());
                tailorItemBean.setPrice_type(customBean.getData().getPrice_type() + "");
                tailorItemBean.setLog_id(customBean.getId());
                tailorItemBean.setGoods_time(enterTime);
                tailorItemBean.setDingzhi_time(0);
                tailorItemBean.setIs_scan(1);
                //面料
                tailorItemBean.setFabric_id(customBean.getData().getDefault_mianliao() + "");
                tailorItemBean.setBanxing_id(customBean.getData().getBanxing_list().get(position).getId() + "");

                tailorItemBean.setSpec_ids(customBean.getData().getDefault_spec_ids());

                String spec_content = customBean.getData().getDefault_spec_content() + ";" + getString(R.string.type) + ":" +
                        customBean.getData().getBanxing_list().get(position).getName() + ";";
                tailorItemBean.setSpec_content(spec_content);
                bundle.putSerializable("tailor", tailorItemBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    /**
     * 商品订制跟踪
     */
    private void customGoodsLog() {
        if (customBean != null && customBean.getData() != null) {
            OkHttpUtils.post()
                    .url(Constant.GOODS_LOG)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("id", customBean.getId())
                    .addParams("goods_time", (DateUtils.getCurrentTime() - enterTime) + "")
                    .addParams("goods_id", id)
                    .addParams("goods_name", customBean.getData().getName())
                    .addParams("click_dingzhi", "0")
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

    /**
     * 添加收藏
     */
    private void addCollection() {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", "2")
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
                                    MobclickAgent.onEvent(CustomizedGoodsActivity.this, "collection");
                                    imgAddLike.setImageResource(R.mipmap.icon_add_like);
                                    ToastUtils.showToast(CustomizedGoodsActivity.this, getString(R.string.collect_success));
                                    break;
                                case 2:
                                    imgAddLike.setImageResource(R.mipmap.icon_cancel_like);
                                    ToastUtils.showToast(CustomizedGoodsActivity.this, getString(R.string.cancel_collect));
                                    break;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmpRecycleUtils.bmpRecycle(bm0);
        BmpRecycleUtils.bmpRecycle(bm1);
        BmpRecycleUtils.bmpRecycle(bm2);
    }

}

