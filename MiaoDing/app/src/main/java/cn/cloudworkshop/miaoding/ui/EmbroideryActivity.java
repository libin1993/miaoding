package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.CustomItemBean;
import cn.cloudworkshop.miaoding.bean.EmbroideryBean;
import cn.cloudworkshop.miaoding.bean.GuideBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.CharacterUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2016/10/13 11:47
 * Email：1993911441@qq.com
 * Describe：个性化定制界面
 */

public class EmbroideryActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.rv_embroidery_position)
    RecyclerView rvEmbroideryPosition;
    @BindView(R.id.rv_embroidery_font)
    RecyclerView rvEmbroideryFont;
    @BindView(R.id.et_embroidery_content)
    EditText etEmbroideryContent;
    @BindView(R.id.rv_embroidery_color)
    RecyclerView rvEmbroideryColor;
    @BindView(R.id.scroll_embroidery)
    ScrollView scrollView;
    @BindView(R.id.tv_confirm_embroidery)
    TextView tvPreview;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    @BindView(R.id.tv_select_type)
    TextView tvType;
    @BindView(R.id.rv_embroidery_type)
    RecyclerView rvType;
    @BindView(R.id.tv_select_fabric)
    TextView tvFabric;
    @BindView(R.id.rv_embroidery_fabric)
    RecyclerView rvFabric;
    @BindView(R.id.tv_select_position)
    TextView tvPosition;
    @BindView(R.id.tv_select_color)
    TextView tvColor;
    @BindView(R.id.tv_select_font)
    TextView tvFont;
    @BindView(R.id.ll_select_embroidery)
    LinearLayout llEmbroidery;
    @BindView(R.id.cb_select_embroidery)
    CheckBox cbSelectEmbroidery;
    @BindView(R.id.tv_customize_price)
    TextView tvPrice;
    @BindView(R.id.tv_add_shop_cart)
    TextView tvAddCart;
    @BindView(R.id.img_large_fabric)
    ImageView imgLargeFabric;
    @BindView(R.id.et_user_height)
    EditText etUserHeight;
    @BindView(R.id.et_user_weight)
    EditText etUserWeight;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;
    @BindView(R.id.tv_photo_help)
    TextView tvPhotoHelp;
    @BindView(R.id.img_is_take)
    CircleImageView imgIsTake;
    @BindView(R.id.rl_user_photo)
    RelativeLayout rlUserPhoto;
    @BindView(R.id.img_custom_guide)
    ImageView imgCustomGuide;
    @BindView(R.id.et_username_measure)
    EditText etUsername;

    private EmbroideryBean embroideryBean;
    //当前绣花位置
    private int flowerPosition = 0;
    //当前绣花颜色
    private int flowerColor = 0;
    //当前字体颜色
    private int flowerFont = 0;
    //当前面料
    private int currentFabric = 0;
    //当前版型
    private int currentType = 0;

    private CustomItemBean customItemBean;
    //部件名称拼接字段
    private String specContent;
    //1:直接购买 2：加入购物袋
    private int type;
    //是否长按
    private boolean isLongPress;
    //是否有量体数据 1：无数据 2:有数据
    private int measureStatus;
    //相机权限
    static final String[] permissionStr = {Manifest.permission.CAMERA};
    //姓名
    private String name;
    //身高
    private String height;
    //体重
    private String weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embroidery);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvHeaderTitle.setText("个性定制");
        tvHeaderNext.setVisibility(View.VISIBLE);
        tvHeaderNext.setText("更多");
        getData();
        initData();
        selectMore();
    }

    /**
     * 商品信息
     */
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            customItemBean = (CustomItemBean) bundle.getSerializable("tailor");
            if (customItemBean != null) {
                specContent = customItemBean.getSpec_content();
            }
        }
    }

    /**
     * 获取网络数据
     */
    private void initData() {
        if (customItemBean != null) {
            OkHttpUtils.get()
                    .url(Constant.EMBROIDERY_CUSTOMIZE)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("goods_id", customItemBean.getId())
                    .addParams("phone_type", "6")
                    .addParams("classify_id", String.valueOf(customItemBean.getClassify_id()))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            imgLoadError.setBackgroundColor(Color.WHITE);
                            imgLoadError.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            imgLoadError.setVisibility(View.GONE);
                            embroideryBean = GsonUtils.jsonToBean(response, EmbroideryBean.class);
                            measureStatus = embroideryBean.getIs_opencv();
                            if (embroideryBean.getData() != null) {
                                initView();
                            }
                        }
                    });
        }

    }

    /**
     * 首次进入，提示更多
     */
    private void selectMore() {
        //默认首次
        boolean isFirst = SharedPreferencesUtils.getBoolean(this, "select_more", true);
        if (isFirst) {
            OkHttpUtils.get()
                    .url(Constant.GUIDE_IMG)
                    .addParams("id", "8")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            GuideBean guideBean = GsonUtils.jsonToBean(response, GuideBean.class);
                            if (guideBean.getData().getImg_urls() != null && guideBean.getData()
                                    .getImg_urls().size() > 0) {
                                SharedPreferencesUtils.saveBoolean(EmbroideryActivity.this,
                                        "select_more", false);
                                imgCustomGuide.setVisibility(View.VISIBLE);
                                Glide.with(EmbroideryActivity.this)
                                        .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(0))
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(imgCustomGuide);
                            }
                        }
                    });
        }
    }


    /**
     * 加载视图
     */
    private void initView() {
        cbSelectEmbroidery.setChecked(false);
        llEmbroidery.setVisibility(View.GONE);
        if (measureStatus == 2) {
            name = embroideryBean.getCv().getName();
            height = embroideryBean.getCv().getHeight();
            weight = embroideryBean.getCv().getWeight();
            if (!TextUtils.isEmpty(name)) {
                etUsername.setText(name);
            }
            if (!TextUtils.isEmpty(height)) {
                etUserHeight.setText(height);
            }
            if (!TextUtils.isEmpty(weight)) {
                etUserWeight.setText(weight);
            }

            if (!TextUtils.isEmpty(embroideryBean.getCv().getImg_list())) {
                imgIsTake.setVisibility(View.VISIBLE);
            } else {
                imgIsTake.setVisibility(View.GONE);
            }

        } else {
            imgIsTake.setVisibility(View.GONE);
        }

        //个性绣花布局收缩
        cbSelectEmbroidery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llEmbroidery.setVisibility(View.VISIBLE);
                } else {
                    llEmbroidery.setVisibility(View.GONE);
                }
            }
        });

        //选择版型
        rvType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final CommonAdapter<EmbroideryBean.DataBean.ClassifyIdBean> typeAdapter = new
                CommonAdapter<EmbroideryBean.DataBean.ClassifyIdBean>(this,
                        R.layout.listitem_embroidery_type, embroideryBean.getData().getClassify_id()) {
                    @Override
                    protected void convert(ViewHolder holder, EmbroideryBean.DataBean.ClassifyIdBean classifyIdBean, int position) {
                        Glide.with(EmbroideryActivity.this)
                                .load(Constant.IMG_HOST + classifyIdBean.getImg_min())
                                .placeholder(R.mipmap.place_holder_goods)
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_embroidery_type));
                        if (currentType == position) {
                            holder.setVisible(R.id.img_current_type, true);
                            tvType.setText(classifyIdBean.getName());
                        } else {
                            holder.setVisible(R.id.img_current_type, false);
                        }
                    }
                };
        rvType.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //当前选择版型
                currentType = holder.getLayoutPosition();
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //选择面料
        rvFabric.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final CommonAdapter<EmbroideryBean.DataBean.MianliaoBean> fabricAdapter = new CommonAdapter
                <EmbroideryBean.DataBean.MianliaoBean>(this, R.layout.listitem_embroidery_fabric,
                embroideryBean.getData().getMianliao()) {
            @Override
            protected void convert(ViewHolder holder, EmbroideryBean.DataBean.MianliaoBean mianliaoBean,
                                   int position) {
                Glide.with(EmbroideryActivity.this)
                        .load(Constant.IMG_HOST + mianliaoBean.getImg_b())
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                if (currentFabric == position) {
                    holder.setVisible(R.id.img_current_fabric, true);
                    tvFabric.setText(mianliaoBean.getName());
                    tvPrice.setText("¥" + DisplayUtils.decimalFormat((float) mianliaoBean.getPrice()));
                } else {
                    holder.setVisible(R.id.img_current_fabric, false);
                }

                holder.setText(R.id.tv_current_fabric, mianliaoBean.getName());
            }

        };
        rvFabric.setAdapter(fabricAdapter);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imgLargeFabric.setVisibility(View.GONE);
                return false;
            }
        });
        //长按显示大图
        rvFabric.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imgLargeFabric.setVisibility(View.GONE);
                return false;
            }

        });
        fabricAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (!isLongPress) {
                    //当前选择面料
                    currentFabric = holder.getLayoutPosition();
                    fabricAdapter.notifyDataSetChanged();
                } else {
                    //长按子配件显示大图，离开屏幕大图消失
                    isLongPress = false;
                    if (imgLargeFabric.getVisibility() == View.VISIBLE) {
                        imgLargeFabric.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Glide.with(EmbroideryActivity.this)
                        .load(Constant.IMG_HOST + embroideryBean.getData().getMianliao().get(position).getImg_b())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgLargeFabric);
                isLongPress = true;
                if (imgLargeFabric.getVisibility() == View.GONE) {
                    imgLargeFabric.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });


        //绣花位置
        rvEmbroideryPosition.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final CommonAdapter<EmbroideryBean.DataBean.PositionBean> positionAdapter
                = new CommonAdapter<EmbroideryBean.DataBean.PositionBean>(EmbroideryActivity.this,
                R.layout.listitem_embroidery_fabric, embroideryBean.getData().getPosition()) {
            @Override
            protected void convert(ViewHolder holder, EmbroideryBean.DataBean.PositionBean positionBean, int position) {
                Glide.with(EmbroideryActivity.this)
                        .load(Constant.IMG_HOST + positionBean.getImg())
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                if (flowerPosition == position) {
                    holder.setVisible(R.id.img_current_fabric, true);
                    tvPosition.setText(positionBean.getName());
                } else {
                    holder.setVisible(R.id.img_current_fabric, false);
                }
                holder.setText(R.id.tv_current_fabric, positionBean.getName());
            }
        };

        rvEmbroideryPosition.setAdapter(positionAdapter);

        positionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //当前绣花位置
                flowerPosition = holder.getLayoutPosition();
                positionAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //绣花颜色
        rvEmbroideryColor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final CommonAdapter<EmbroideryBean.DataBean.ColorBean> colorAdapter = new
                CommonAdapter<EmbroideryBean.DataBean.ColorBean>(EmbroideryActivity.this,
                        R.layout.listitem_embroidery_fabric, embroideryBean.getData().getColor()) {
                    @Override
                    protected void convert(ViewHolder holder, EmbroideryBean.DataBean.ColorBean colorBean, int position) {
                        Glide.with(EmbroideryActivity.this)
                                .load(Constant.IMG_HOST + colorBean.getImg())
                                .placeholder(R.mipmap.place_holder_goods)
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_embroidery_fabric));
                        if (flowerColor == position) {
                            holder.setVisible(R.id.img_current_fabric, true);
                            tvColor.setText(colorBean.getName());
                        } else {
                            holder.setVisible(R.id.img_current_fabric, false);
                        }
                        holder.setText(R.id.tv_current_fabric, colorBean.getName());

                    }

                };
        rvEmbroideryColor.setAdapter(colorAdapter);

        colorAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //当前选择颜色
                flowerColor = holder.getLayoutPosition();
                colorAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        //绣花字体
        rvEmbroideryFont.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final CommonAdapter<EmbroideryBean.DataBean.FontBean> fontAdapter = new CommonAdapter
                <EmbroideryBean.DataBean.FontBean>(EmbroideryActivity.this,
                R.layout.listitem_embroidery_font, embroideryBean.getData().getFont()) {
            @Override
            protected void convert(ViewHolder holder, EmbroideryBean.DataBean.FontBean positionBean, int position) {

                Glide.with(EmbroideryActivity.this)
                        .load(Constant.IMG_HOST + positionBean.getImg())
                        .placeholder(R.mipmap.place_holder_goods)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_embroidery_font));
                if (flowerFont == position) {
                    holder.setVisible(R.id.img_current_font, true);
                    tvFont.setText(positionBean.getName());
                    judgeInputWords();
                } else {
                    holder.setVisible(R.id.img_current_font, false);
                }
                holder.setText(R.id.tv_embroidery_font, positionBean.getName());

            }
        };
        rvEmbroideryFont.setAdapter(fontAdapter);
        fontAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //当前选择字体
                flowerFont = holder.getLayoutPosition();
                fontAdapter.notifyDataSetChanged();
                //输入中英文
                if (position == 0) {
                    etEmbroideryContent.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    etEmbroideryContent.setInputType(InputType.TYPE_CLASS_TEXT);
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        etEmbroideryContent.setFilters(new InputFilter[]{filter});


        etEmbroideryContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollUp();
                return false;
            }
        });

        etEmbroideryContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                selectionStart = etEmbroideryContent.getSelectionStart();
                selectionEnd = etEmbroideryContent.getSelectionEnd();
                //不超过12个字符
                if (temp.length() > 12) {
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etEmbroideryContent.setText(editable);
                    etEmbroideryContent.setSelection(tempSelection);//设置光标在最后
                }

                judgeInputWords();
            }
        });

    }

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals(" ") || source.toString().contentEquals("\n")) return "";
            else return null;
        }
    };


    /**
     * 输入中英文判断
     */
    private void judgeInputWords() {
        if (etEmbroideryContent.getText().toString().trim().length() > 0) {
            //1：英文
            if (embroideryBean.getData().getFont().get(flowerFont).getIs_english() == 1) {
                if (!CharacterUtils.inputEnglish(EmbroideryActivity.this,
                        etEmbroideryContent.getText().toString().trim())) {
                    etEmbroideryContent.setText(null);
                }
            } else {
                if (!CharacterUtils.inputChinese(EmbroideryActivity.this,
                        etEmbroideryContent.getText().toString().trim())) {
                    etEmbroideryContent.setText(null);
                }
            }
        }
    }

    /**
     * 使ScrollView指向底部
     */
    private void scrollUp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, scrollView.getHeight());
            }
        }, 300);
    }

    @OnClick({R.id.img_header_back, R.id.tv_header_next, R.id.tv_confirm_embroidery,
            R.id.tv_add_shop_cart, R.id.img_load_error, R.id.tv_photo_help, R.id.rl_user_photo,
            R.id.img_custom_guide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_header_next:
                if (embroideryBean != null) {
                    if (isMeasureData()) {
                        if (!etUsername.getText().toString().trim().equals(name)
                                || !etUserHeight.getText().toString().trim().equals(height)
                                || !etUserWeight.getText().toString().trim().equals(weight)) {
                            submitData();
                        }
                        customizeData(false);
                        toCustomize();
                    } else {
                        ToastUtils.showToast(this, "请完善基本信息");
                    }
                }
                break;
            case R.id.tv_confirm_embroidery:
                if (embroideryBean != null) {
                    if (isMeasureData()) {
                        if (!etUsername.getText().toString().trim().equals(name)
                                || !etUserHeight.getText().toString().trim().equals(height)
                                || !etUserWeight.getText().toString().trim().equals(weight)) {
                            submitData();
                        }
                        type = 1;
                        customizeData(true);
                        addToCart();
                    } else {
                        ToastUtils.showToast(this, "请完善基本信息");
                    }
                }
                break;
            case R.id.tv_add_shop_cart:
                if (embroideryBean != null) {
                    if (isMeasureData()) {
                        if (!etUsername.getText().toString().trim().equals(name)
                                || !etUserHeight.getText().toString().trim().equals(height)
                                || !etUserWeight.getText().toString().trim().equals(weight)) {
                            submitData();
                        }
                        type = 2;
                        customizeData(true);
                        addToCart();
                    } else {
                        ToastUtils.showToast(this, "请完善基本信息");
                    }
                }
                break;
            case R.id.img_load_error:
                initData();
                break;
            case R.id.tv_photo_help:
                takePhoto(true);
                break;
            case R.id.rl_user_photo:
                takePhoto(false);
                break;
            case R.id.img_custom_guide:
                imgCustomGuide.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 上传量体数据
     */
    private void submitData() {
        OkHttpUtils.post()
                .url(Constant.TAKE_PHOTO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("phone", SharedPreferencesUtils.getStr(this, "phone"))
                .addParams("name", etUsername.getText().toString().trim())
                .addParams("height", etUserHeight.getText().toString().trim())
                .addParams("weight", etUserWeight.getText().toString().trim())
                .addParams("is_index", "1")
                .addParams("scale","1,1,1,1")
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
                                measureStatus = 2;
                                name = etUsername.getText().toString().trim();
                                height = etUserHeight.getText().toString().trim();
                                weight = etUserWeight.getText().toString().trim();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 手动选择配件，跳转定制页面
     */
    private void toCustomize() {
        Intent intent = new Intent(this, CustomizeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tailor", customItemBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    /**
     * @param isDefaultType 是否定制同款
     *                      商品定制所需数据
     */
    private void customizeData(boolean isDefaultType) {
        //面料
        EmbroideryBean.DataBean.MianliaoBean mianliaoBean = embroideryBean.getData().getMianliao().get(currentFabric);
        //部件拼接字段
        String spec_content;
        //是否定制同款
        if (isDefaultType) {
            spec_content = specContent + ";面料:" + mianliaoBean.getName()
                    + ";版型:" + embroideryBean.getData().getClassify_id().get(currentType).getName();
            customItemBean.setIs_scan(1);
        } else {
            spec_content = "面料:" + mianliaoBean.getName()
                    + ";版型:" + embroideryBean.getData().getClassify_id().get(currentType).getName();
            customItemBean.setIs_scan(0);
        }

        if (!TextUtils.isEmpty(etEmbroideryContent.getText().toString().trim())) {
            //个性绣花
            String sb = "位置:" + tvPosition.getText().toString()
                    + ";颜色:" + tvColor.getText().toString()
                    + ";字体:" + tvFont.getText().toString()
                    + ";文字:" + etEmbroideryContent.getText().toString();
            customItemBean.setDiy_contet(sb);
        } else {
            customItemBean.setDiy_contet(null);
        }

        //面料id
        customItemBean.setFabric_id(String.valueOf(mianliaoBean.getId()));
        //默认图片
        customItemBean.setDefault_img(mianliaoBean.getGoods_img());
        //价格id
        customItemBean.setPrice_type(String.valueOf(mianliaoBean.getPrice_id()));
        //价格
        customItemBean.setPrice(DisplayUtils.decimalFormat((float) mianliaoBean.getPrice()));
        //版型
        customItemBean.setBanxing_id(embroideryBean.getData().getClassify_id().get(currentType).getId() + "");
        //配件拼接字段
        customItemBean.setSpec_content(spec_content);
    }

    /**
     * 加入购物车
     */
    private void addToCart() {

        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPreferencesUtils.getStr(this, "token"));
        map.put("type", String.valueOf(type));
        map.put("price_id", customItemBean.getPrice_type());
        map.put("goods_id", customItemBean.getId());
        if (!TextUtils.isEmpty(customItemBean.getShop_id())) {
            map.put("shop_id", customItemBean.getShop_id());
        }
        if (!TextUtils.isEmpty(customItemBean.getMarket_id())) {
            map.put("market_id", customItemBean.getMarket_id());
        }
        map.put("goods_type", "1");
        map.put("price", customItemBean.getPrice());
        map.put("goods_name", customItemBean.getGoods_name());
        if (TextUtils.isEmpty(customItemBean.getDefault_img())) {
            map.put("goods_thumb", customItemBean.getImg_url());
        } else {
            map.put("goods_thumb", customItemBean.getDefault_img());
        }

        if (!TextUtils.isEmpty(customItemBean.getSpec_ids())) {
            map.put("spec_ids", customItemBean.getSpec_ids());
        }
        if (!TextUtils.isEmpty(customItemBean.getSpec_content())) {
            map.put("spec_content", customItemBean.getSpec_content());
        }

        if (!TextUtils.isEmpty(customItemBean.getFabric_id())) {
            map.put("mianliao_id", customItemBean.getFabric_id());
        }

        if (!TextUtils.isEmpty(customItemBean.getBanxing_id())) {
            map.put("banxing_id", customItemBean.getBanxing_id());
        }

        map.put("is_scan", String.valueOf(customItemBean.getIs_scan()));
        if (!TextUtils.isEmpty(customItemBean.getDiy_contet())) {
            map.put("diy_content", customItemBean.getDiy_contet());
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
                                ToastUtils.showToast(EmbroideryActivity.this, msg);
                            }
                            String cartId = jsonObject1.getString("car_id");
                            //直接购买，跳转确认订单页面
                            if (!TextUtils.isEmpty(cartId)) {
                                MobclickAgent.onEvent(EmbroideryActivity.this, "add_cart");
                                if (type == 1) {
                                    Intent intent = new Intent(EmbroideryActivity.this,
                                            ConfirmOrderActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("cart_id", cartId);
                                    bundle.putString("log_id", customItemBean.getLog_id());
                                    bundle.putLong("goods_time", customItemBean.getGoods_time());
                                    bundle.putLong("dingzhi_time", customItemBean.getDingzhi_time());
                                    bundle.putString("goods_id", customItemBean.getId());
                                    bundle.putString("goods_name", customItemBean.getGoods_name());
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
     * @param toCameraGuide 是否点击帮助
     *                      拍照
     */
    private void takePhoto(boolean toCameraGuide) {
        if (isMeasureData()) {
            if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                EasyPermissions.requestPermissions(this, "", 123, permissionStr);
            } else {
                Intent intent;
                if (toCameraGuide) {
                    intent = new Intent(this, CameraGuideActivity.class);
                } else {
                    if (measureStatus == 2) {
                        intent = new Intent(this, NewCameraActivity.class);
                    } else {
                        intent = new Intent(this, CameraGuideActivity.class);
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("name", etUsername.getText().toString().trim());
                bundle.putString("height", etUserHeight.getText().toString().trim());
                bundle.putString("weight", etUserWeight.getText().toString().trim());
                bundle.putInt("is_default", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        } else {
            ToastUtils.showToast(this, "请完善基本信息");
        }
    }


    /**
     * @return 量体数据是否完善
     */
    private boolean isMeasureData() {
        return !TextUtils.isEmpty(etUsername.getText().toString().trim())
                && !TextUtils.isEmpty(etUserHeight.getText().toString().trim())
                && !TextUtils.isEmpty(etUserWeight.getText().toString().trim());
    }

    /**
     * @param msg 下单成功，结束当前页面
     */
    @Subscribe
    public void orderSuccess(String msg) {
        if ("order_success".equals(msg)) {
            finish();
        }

        if ("take_success".equals(msg)) {
            measureStatus = 2;
            imgIsTake.setVisibility(View.VISIBLE);
            name = etUsername.getText().toString().trim();
            height = etUserHeight.getText().toString().trim();
            weight = etUserWeight.getText().toString().trim();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }


    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, "相机");
    }

}
