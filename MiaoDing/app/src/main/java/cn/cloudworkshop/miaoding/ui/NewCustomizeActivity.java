package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
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
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.bean.CustomizeBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2017-04-25 10:52
 * Email：1993911441@qq.com
 * Describe：定制界面
 */
public class NewCustomizeActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.tv_cloth_fabric)
    TextView tvClothFabric;
    @BindView(R.id.tv_cloth_type)
    TextView tvClothType;
    @BindView(R.id.tv_cloth_item)
    TextView tvClothItem;
    @BindView(R.id.ll_select_type)
    LinearLayout llSelectType;
    @BindView(R.id.rv_select_type)
    RecyclerView rvSelectType;
    @BindView(R.id.rv_select_item)
    RecyclerView rvSelectItem;
    @BindView(R.id.rl_cloth_positive)
    RelativeLayout rlClothPositive;
    @BindView(R.id.rl_cloth_back)
    RelativeLayout rlClothBack;
    @BindView(R.id.rl_cloth_inside)
    RelativeLayout rlClothInside;
    @BindView(R.id.rgs_select_cloth)
    RadioGroup rgsSelect;
    @BindView(R.id.img_reset_tailor)
    ImageView imgResetTailor;
    @BindView(R.id.tv_item_introduce)
    TextView tvIntroduce;
    @BindView(R.id.rl_cloth_detail)
    RelativeLayout rlClothDetail;
    @BindView(R.id.tv_item_title)
    TextView tvItemTitle;
    @BindView(R.id.img_cloth_large)
    CircleImageView imgClothLarge;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //商品id
    private String id;
    //商品名
    private String goodsName;
    //商品图
    private String imgUrl;
    //价格
    private String price;
    //价格id
    private String priceType;
    private int classifyId;
    private String logId;
    private long goodsTime;
    private CustomizeBean customizeBean;

    //当前面料
    private int currentFabric = 0;
    //当前版型
    private int currentType = 0;
    //当前部位
    private int currentParts;

    private float x1 = 0;
    private float x2 = 0;

    //稀疏数组
    //选择部件
    private SparseIntArray itemArray = new SparseIntArray();
    //进入页面时间
    private long enterTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_new);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initData();

    }

    /**
     * 商品信息
     */
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            goodsName = bundle.getString("goods_name");
            imgUrl = bundle.getString("img_url");
            price = bundle.getString("price");
            priceType = bundle.getString("price_type");
            classifyId = bundle.getInt("classify_id");
            logId = bundle.getString("log_id");
            goodsTime = bundle.getLong("goods_time");
        }


        enterTime = DateUtils.getCurrentTime();
    }

    private void initData() {

        OkHttpUtils
                .get()
                .url(Constant.NEW_CUSTOMIZE)
                .addParams("goods_id", id)
                .addParams("phone_type", "6")
                .addParams("price_type", priceType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        customizeBean = GsonUtils.jsonToBean(response, CustomizeBean.class);
                        if (customizeBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderNext.setVisibility(View.VISIBLE);
        tvHeaderNext.setText(R.string.next_step);
        tvItemTitle.setTypeface(DisplayUtils.setTextType(this));
        ((RadioButton) rgsSelect.getChildAt(0)).setChecked(true);

        for (int i = 0; i < customizeBean.getData().getBanxin().get(currentType).getPeijian().size(); i++) {
            ImageView imgPositive = new ImageView(this);
            imgPositive.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView imgBack = new ImageView(this);
            imgBack.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView imgInside = new ImageView(this);
            imgInside.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));


            rlClothPositive.addView(imgPositive);
            rlClothBack.addView(imgBack);
            rlClothInside.addView(imgInside);
        }

        initCloth();
        selectFabric();

        rgsSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                for (int m = 0; m < rgsSelect.getChildCount(); m++) {
                    RadioButton rBtn = (RadioButton) radioGroup.getChildAt(m);
                    if (rBtn.getId() == i) {
                        switch (m) {
                            case 0:
                                rlClothPositive.setVisibility(View.VISIBLE);
                                rlClothBack.setVisibility(View.GONE);
                                rlClothInside.setVisibility(View.GONE);
                                break;
                            case 1:
                                rlClothBack.setVisibility(View.VISIBLE);
                                rlClothPositive.setVisibility(View.GONE);
                                rlClothInside.setVisibility(View.GONE);
                                break;
                            case 2:
                                rlClothInside.setVisibility(View.VISIBLE);
                                rlClothPositive.setVisibility(View.GONE);
                                rlClothPositive.setVisibility(View.GONE);
                                break;
                        }


                    }
                }
            }
        });

        rlClothPositive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getRawX();
                    case MotionEvent.ACTION_UP:
                        if (x1 < x2) {
                            ((RadioButton) rgsSelect.getChildAt(1)).setChecked(true);
                        }
                        break;
                }
                return true;
            }
        });

        rlClothBack.setOnTouchListener(new View.OnTouchListener() {
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
                            ((RadioButton) rgsSelect.getChildAt(0)).setChecked(true);
                        } else if (x1 < x2) {
                            if (rgsSelect.getChildAt(2).getVisibility() == View.VISIBLE) {
                                ((RadioButton) rgsSelect.getChildAt(2)).setChecked(true);
                            }
                        }
                        break;
                }
                return true;
            }


        });

        rlClothInside.setOnTouchListener(new View.OnTouchListener() {
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
                            ((RadioButton) rgsSelect.getChildAt(1)).setChecked(true);
                        }
                        break;
                }
                return true;
            }
        });

        rvSelectItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    rlClothDetail.setVisibility(View.GONE);

                }
                return false;
            }

        });

    }


    @OnClick({R.id.img_header_back, R.id.tv_header_next, R.id.img_reset_tailor, R.id.tv_cloth_fabric
            , R.id.tv_cloth_type, R.id.tv_cloth_item, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                customGoodsLog();
                finish();
                break;
            case R.id.tv_header_next:
                nextStep();
                break;
            case R.id.img_reset_tailor:
                resetTailor();
                break;
            case R.id.tv_cloth_fabric:
                selectFabric();
                break;
            case R.id.tv_cloth_type:
                selectType();
                break;
            case R.id.tv_cloth_item:
                selectParts();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 初始化部件
     */
    private void initCloth() {
        for (int i = 0; i < customizeBean.getData().getBanxin().get(currentType).getPeijian().size(); i++) {

            if (customizeBean.getData().getBanxin().get(currentType).getPeijian().get(i)
                    .getSpec_list() != null && customizeBean.getData().getBanxin()
                    .get(currentType).getPeijian().get(i).getSpec_list().size() > 0) {
                int currentItem = 0;
                for (int j = 0; j < customizeBean.getData().getBanxin().get(currentType)
                        .getPeijian().get(i).getSpec_list().size(); j++) {

                    if (customizeBean.getData().getMianliao().get(currentFabric).getId() == customizeBean
                            .getData().getBanxin().get(currentType).getPeijian().get(i)
                            .getSpec_list().get(j).getMianliao_id()) {
                        currentItem = j;
                        //选择正反面
                        int positionId = customizeBean.getData().getBanxin().get(currentType)
                                .getPeijian().get(i).getPosition_id();
                        switch (positionId) {
                            case 1:
                                ImageView imgPositive = (ImageView) rlClothPositive.getChildAt(i);
                                Glide.with(NewCustomizeActivity.this)
                                        .load(Constant.IMG_HOST + customizeBean.getData().getBanxin()
                                                .get(currentType).getPeijian().get(i).getSpec_list()
                                                .get(j).getImg_c())
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgPositive);
                                break;
                            case 2:
                                ImageView imgBack = (ImageView) rlClothBack.getChildAt(i);
                                Glide.with(NewCustomizeActivity.this)
                                        .load(Constant.IMG_HOST + customizeBean.getData().getBanxin()
                                                .get(currentType).getPeijian().get(i).getSpec_list()
                                                .get(j).getImg_c())
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgBack);
                                break;
                            case 3:
                                ImageView imgInSide = (ImageView) rlClothInside.getChildAt(i);
                                Glide.with(NewCustomizeActivity.this)
                                        .load(Constant.IMG_HOST + customizeBean.getData().getBanxin()
                                                .get(currentType).getPeijian().get(i).getSpec_list()
                                                .get(j).getImg_c())
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgInSide);
                                break;
                        }
                        break;
                    }
                }
                itemArray.put(i, currentItem);
            }
        }

    }

    /**
     * 选择面料
     */
    private void selectFabric() {
        if (customizeBean != null && customizeBean.getData().getMianliao() != null) {
            rvSelectItem.setVisibility(View.VISIBLE);
            tvHeaderTitle.setText(R.string.fabric);
            tvClothFabric.setTextColor(Color.WHITE);
            tvClothFabric.setBackgroundResource(R.drawable.circle_black);
            tvClothType.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
            tvClothType.setBackgroundResource(R.drawable.ring_gray);
            final CommonAdapter<CustomizeBean.DataBean.MianliaoBean> fabricAdapter = new
                    CommonAdapter<CustomizeBean.DataBean.MianliaoBean>(NewCustomizeActivity.this,
                            R.layout.listitem_customize_parts, customizeBean.getData().getMianliao()) {
                        @Override
                        protected void convert(ViewHolder holder, CustomizeBean.DataBean.MianliaoBean
                                mianliaoBean, int position) {
                            Glide.with(NewCustomizeActivity.this)
                                    .load(Constant.IMG_HOST + mianliaoBean.getImg_a())
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_tailor_item));
                            if (currentFabric == position) {
                                holder.setVisible(R.id.img_tailor_bg, true);
                            } else {
                                holder.setVisible(R.id.img_tailor_bg, false);
                            }
                        }
                    };
            rvSelectItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvSelectItem.setAdapter(fabricAdapter);


            fabricAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    currentFabric = position;
                    fabricAdapter.notifyDataSetChanged();
                    tvHeaderTitle.setText(customizeBean.getData().getMianliao().get(position).getName());
                    if (rlClothDetail.getVisibility() == View.VISIBLE) {
                        rlClothDetail.setVisibility(View.GONE);
                    }

                    initCloth();

                    imgResetTailor.setVisibility(View.VISIBLE);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                    Glide.with(NewCustomizeActivity.this)
                            .load(Constant.IMG_HOST + customizeBean.getData().getMianliao().get(position).getImg_b())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .fitCenter()
                            .into(imgClothLarge);
                    tvItemTitle.setText(customizeBean.getData().getMianliao().get(position).getName());
                    tvIntroduce.setText(customizeBean.getData().getMianliao().get(position).getIntroduce());
                    if (rlClothDetail.getVisibility() == View.GONE) {
                        rlClothDetail.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
        }

    }

    /**
     * 重置
     */
    private void resetTailor() {
        imgResetTailor.setVisibility(View.GONE);
        itemArray.clear();
        llSelectType.setVisibility(View.VISIBLE);
        rvSelectType.setVisibility(View.INVISIBLE);
        rvSelectItem.setVisibility(View.INVISIBLE);
        currentFabric = 0;
        currentType = 0;
        tvClothFabric.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
        tvClothFabric.setBackgroundResource(R.drawable.ring_gray);
        tvClothType.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
        tvClothType.setBackgroundResource(R.drawable.ring_gray);
        ((RadioButton) rgsSelect.getChildAt(0)).setChecked(true);
        initCloth();
        selectFabric();
    }


    /**
     * 选择版型
     */
    private void selectType() {
        if (customizeBean != null && customizeBean.getData().getBanxin() != null) {
            rvSelectItem.setVisibility(View.VISIBLE);
            tvHeaderTitle.setText(R.string.type);
            tvClothType.setTextColor(Color.WHITE);
            tvClothType.setBackgroundResource(R.drawable.circle_black);
            tvClothFabric.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
            tvClothFabric.setBackgroundResource(R.drawable.ring_gray);
            final CommonAdapter<CustomizeBean.DataBean.BanxinBean> typeAdapter = new
                    CommonAdapter<CustomizeBean.DataBean.BanxinBean>(NewCustomizeActivity.this,
                            R.layout.listitem_customize_parts, customizeBean.getData().getBanxin()) {
                        @Override
                        protected void convert(ViewHolder holder, CustomizeBean.DataBean.BanxinBean
                                banxinBean, int position) {
                            Glide.with(NewCustomizeActivity.this)
                                    .load(Constant.IMG_HOST + banxinBean.getImg_a())
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_tailor_item));
                            if (currentType == position) {
                                holder.setVisible(R.id.img_tailor_bg, true);
                            } else {
                                holder.setVisible(R.id.img_tailor_bg, false);
                            }
                        }
                    };
            rvSelectItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvSelectItem.setAdapter(typeAdapter);
            typeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    currentType = position;
                    typeAdapter.notifyDataSetChanged();
                    tvHeaderTitle.setText(customizeBean.getData().getBanxin().get(position).getName());
                    if (rlClothDetail.getVisibility() == View.VISIBLE) {
                        rlClothDetail.setVisibility(View.GONE);
                    }

                    initCloth();

                    imgResetTailor.setVisibility(View.VISIBLE);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                    Glide.with(NewCustomizeActivity.this)
                            .load(Constant.IMG_HOST + customizeBean.getData().getBanxin().get(position).getImg_b())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .fitCenter()
                            .into(imgClothLarge);
                    tvItemTitle.setText(customizeBean.getData().getBanxin().get(position).getName());
                    tvIntroduce.setText("");
                    if (rlClothDetail.getVisibility() == View.GONE) {
                        rlClothDetail.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
        }

    }

    /**
     * 选择衣服部位
     */
    private void selectParts() {
        if (customizeBean != null && customizeBean.getData() != null) {
            tvHeaderTitle.setText(R.string.details);
            llSelectType.setVisibility(View.INVISIBLE);
            rvSelectType.setVisibility(View.VISIBLE);
            rvSelectItem.setVisibility(View.INVISIBLE);
            imgResetTailor.setVisibility(View.VISIBLE);
            CommonAdapter<CustomizeBean.DataBean.BanxinBean.PeijianBean> partsAdapter = new
                    CommonAdapter<CustomizeBean.DataBean.BanxinBean.PeijianBean>
                            (NewCustomizeActivity.this, R.layout.listitem_customize_parts, customizeBean.getData()
                                    .getBanxin().get(currentType).getPeijian()) {
                        @Override
                        protected void convert(ViewHolder holder, CustomizeBean.DataBean.BanxinBean
                                .PeijianBean peijianBean, int position) {

                            Glide.with(NewCustomizeActivity.this)
                                    .load(Constant.IMG_HOST + peijianBean.getSpec_list().get(itemArray
                                            .get(position)).getImg_a())
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into((ImageView) holder.getView(R.id.img_tailor_item));

                            holder.setVisible(R.id.view_tailor_item, true);

                        }
                    };

            HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(partsAdapter);

            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            layoutParams.setMargins((int) DisplayUtils.dp2px(this, 10), (int) DisplayUtils.dp2px(this, 6),
                    (int) DisplayUtils.dp2px(this, 10), (int) DisplayUtils.dp2px(this, 6));
            layout.setLayoutParams(layoutParams);

            ImageView imgClose = new ImageView(this);
            imgClose.setImageResource(R.mipmap.icon_close);
            ViewGroup.LayoutParams param1 = new ViewGroup.LayoutParams((int) DisplayUtils.dp2px(this, 50)
                    , (int) DisplayUtils.dp2px(this, 50));

            layout.addView(imgClose, param1);
            headerAndFooterWrapper.addFootView(layout);

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvHeaderTitle.setText(R.string.details);
                    llSelectType.setVisibility(View.VISIBLE);
                    rvSelectType.setVisibility(View.INVISIBLE);
                    rvSelectItem.setVisibility(View.INVISIBLE);
                    tvClothFabric.setTextColor(ContextCompat.getColor(NewCustomizeActivity.this, R.color.dark_gray_22));
                    tvClothFabric.setBackgroundResource(R.drawable.ring_gray);
                    tvClothType.setTextColor(ContextCompat.getColor(NewCustomizeActivity.this, R.color.dark_gray_22));
                    tvClothType.setBackgroundResource(R.drawable.ring_gray);
                }
            });

            rvSelectType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                    , false));
            rvSelectType.setAdapter(headerAndFooterWrapper);

            selectItem(0);

            partsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    selectItem(position);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

        }
    }

    /**
     * @param position 选择部件
     */
    private void selectItem(int position) {
        currentParts = position;
        rvSelectItem.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText(customizeBean.getData().getBanxin().get(currentType)
                .getPeijian().get(currentParts).getName());

        ((RadioButton) rgsSelect.getChildAt(customizeBean.getData().getBanxin().get(currentType)
                .getPeijian().get(currentParts).getPosition_id() - 1)).setChecked(true);
        final List<CustomizeBean.DataBean.BanxinBean.PeijianBean.SpecListBean> itemList = new ArrayList<>();

        for (int i = 0; i < customizeBean.getData().getBanxin().get(currentType).getPeijian()
                .get(position).getSpec_list().size(); i++) {
            if (customizeBean.getData().getBanxin().get(currentType).getPeijian().get(position)
                    .getSpec_list().get(i).getMianliao_id() == customizeBean.getData()
                    .getMianliao().get(currentFabric).getId()) {
                itemList.add(customizeBean.getData().getBanxin().get(currentType).getPeijian()
                        .get(position).getSpec_list().get(i));
            }
        }
        final CommonAdapter<CustomizeBean.DataBean.BanxinBean.PeijianBean.SpecListBean>
                itemAdapter = new CommonAdapter<CustomizeBean.DataBean.BanxinBean.PeijianBean
                .SpecListBean>(NewCustomizeActivity.this, R.layout.listitem_customize_parts, itemList) {
            @Override
            protected void convert(ViewHolder holder, CustomizeBean.DataBean
                    .BanxinBean.PeijianBean.SpecListBean specListBean, int position) {

                Glide.with(NewCustomizeActivity.this)
                        .load(Constant.IMG_HOST + specListBean.getImg_a())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_tailor_item));

                if (customizeBean.getData().getBanxin().get(currentType).getPeijian()
                        .get(currentParts).getSpec_list().get(itemArray.get(currentParts)).getId()
                        == specListBean.getId()) {
                    holder.setVisible(R.id.img_tailor_bg, true);
                } else {
                    holder.setVisible(R.id.img_tailor_bg, false);
                }

            }
        };
        rvSelectItem.setLayoutManager(new LinearLayoutManager(NewCustomizeActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        rvSelectItem.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                if (rlClothDetail.getVisibility() == View.VISIBLE) {
                    rlClothDetail.setVisibility(View.GONE);
                }

                CircleImageView imgItem = (CircleImageView) rvSelectType
                        .findViewHolderForAdapterPosition(currentParts)
                        .itemView.findViewById(R.id.img_tailor_item);


                Glide.with(NewCustomizeActivity.this)
                        .load(Constant.IMG_HOST + itemList.get(position).getImg_a())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgItem);


                //选择正反面
                int positionId = customizeBean.getData().getBanxin().get(currentType)
                        .getPeijian().get(currentParts).getPosition_id();
                ((RadioButton) rgsSelect.getChildAt(positionId - 1)).setChecked(true);

                switch (positionId) {
                    case 1:
                        switchParts(rlClothPositive, itemList.get(position).getImg_c());
                        break;
                    case 2:
                        switchParts(rlClothBack, itemList.get(position).getImg_c());
                        break;
                    case 3:
                        switchParts(rlClothInside, itemList.get(position).getImg_c());
                        break;
                }

                tvHeaderTitle.setText(itemList.get(position).getName());

                for (int i = 0; i < customizeBean.getData().getBanxin().get(currentType).getPeijian()
                        .get(currentParts).getSpec_list().size(); i++) {
                    if (customizeBean.getData().getBanxin().get(currentType).getPeijian()
                            .get(currentParts).getSpec_list().get(i).getId() == itemList
                            .get(position).getId()) {
                        itemArray.put(currentParts, i);
                    }
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                Glide.with(NewCustomizeActivity.this)
                        .load(Constant.IMG_HOST + itemList.get(position).getImg_b())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .into(imgClothLarge);
                tvItemTitle.setText(itemList.get(position).getName());
                tvIntroduce.setText(itemList.get(position).getIntroduce());
                if (rlClothDetail.getVisibility() == View.GONE) {
                    rlClothDetail.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }


    /**
     * @param rl
     * @param imgUrl 切换衣服部件图片
     */
    private void switchParts(RelativeLayout rl, String imgUrl) {
        ImageView imageView = (ImageView) rl.getChildAt(currentParts);
        Glide.with(NewCustomizeActivity.this)
                .load(Constant.IMG_HOST + imgUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 下一步
     */
    private void nextStep() {
        Intent intent;
        Bundle bundle = new Bundle();

        if (classifyId == 1 || classifyId == 2) {
            intent = new Intent(this, EmbroideryActivity.class);
            bundle.putInt("classify_id", classifyId);
        } else {
            intent = new Intent(this, CustomizeResultActivity.class);
        }


        CustomItemBean customItemBean = new CustomItemBean();
        customItemBean.setId(id);
        customItemBean.setGoods_name(goodsName);
        customItemBean.setPrice(price);
        customItemBean.setImg_url(imgUrl);
        customItemBean.setPrice_type(priceType);
        customItemBean.setLog_id(logId);
        customItemBean.setGoods_time(goodsTime);
        customItemBean.setDingzhi_time(DateUtils.getCurrentTime() - enterTime);
        customItemBean.setIs_scan(0);
        //面料
        customItemBean.setFabric_id(customizeBean.getData().getMianliao().get(currentFabric).getId() + "");
        customItemBean.setBanxing_id(customizeBean.getData().getBanxin().get(currentType).getId() + "");
        customItemBean.setDefault_img(customizeBean.getData().getMianliao().get(currentFabric).getGoods_img());

        //部件
        List<CustomItemBean.ItemBean> itemList = new ArrayList<>();
        StringBuilder sbIds = new StringBuilder();
        StringBuilder sbContent = new StringBuilder();
        for (int i = 0; i < itemArray.size(); i++) {
            CustomItemBean.ItemBean itemBean = new CustomItemBean.ItemBean();
            int key = itemArray.keyAt(i);
            int value = itemArray.valueAt(i);
            if (customizeBean.getData().getBanxin().get(currentType).getPeijian().get(key)
                    .getSpec_list().get(value) != null) {

                sbIds.append(customizeBean.getData().getBanxin().get(currentType).getPeijian()
                        .get(key).getSpec_list().get(value).getId())
                        .append(",");

                sbContent.append(customizeBean.getData().getBanxin().get(currentType).getPeijian()
                        .get(key).getName())
                        .append(":")
                        .append(customizeBean.getData().getBanxin().get(currentType).getPeijian()
                                .get(key).getSpec_list().get(value).getName())
                        .append(";");

                itemBean.setImg(customizeBean.getData().getBanxin().get(currentType)
                        .getPeijian().get(key).getSpec_list().get(value).getImg_c());
                itemBean.setPosition_id(customizeBean.getData().getBanxin().get(currentType)
                        .getPeijian().get(key).getPosition_id());

                itemList.add(itemBean);

            }
        }

        sbContent.append(getString(R.string.fabric)+":")
                .append(customizeBean.getData().getMianliao().get(currentFabric).getName())
                .append(";"+getString(R.string.type)+":")
                .append(customizeBean.getData().getBanxin().get(currentType).getName())
                .append(";");

        customItemBean.setSpec_ids(sbIds.deleteCharAt(sbIds.length() - 1).toString());
        customItemBean.setSpec_content(sbContent.toString());

        customItemBean.setItemBean(itemList);
        bundle.putSerializable("tailor", customItemBean);

        intent.putExtras(bundle);
        startActivity(intent);

    }


    /**
     * 商品订制跟踪
     */
    private void customGoodsLog() {
        if (customizeBean != null) {
            OkHttpUtils.post()
                    .url(Constant.GOODS_LOG)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("id", logId)
                    .addParams("goods_time", (DateUtils.getCurrentTime() - goodsTime) + "")
                    .addParams("dingzhi_time", (DateUtils.getCurrentTime() - enterTime) + "")
                    .addParams("goods_id", id)
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
