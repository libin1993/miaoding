package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.OrderDetailsBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.PhoneNumberUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2017/1/5 13:32
 * Email：1993911441@qq.com
 * Describe：售后订单修改
 */
public class ChangeOrderActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.et_change_order)
    EditText etChangeOrder;
    @BindView(R.id.tv_input_count)
    TextView tvInputCount;
    @BindView(R.id.img_select_photo)
    ImageView imgSelectPhoto;
    @BindView(R.id.rv_select_photo)
    RecyclerView rvSelectPhoto;
    @BindView(R.id.et_input_name)
    EditText etInputName;
    @BindView(R.id.et_input_tel)
    EditText etInputTel;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;
    @BindView(R.id.sv_change_order)
    ScrollView svChangeOrder;
    @BindView(R.id.tv_consult_phone)
    TextView tvConsultPhone;
    @BindView(R.id.tv_back_sales)
    TextView tvBackSales;
    @BindView(R.id.ll_change_success)
    LinearLayout llChangeSuccess;
    @BindView(R.id.rv_select_goods)
    RecyclerView rvSelectGoods;
    @BindView(R.id.tv_first_next)
    TextView tvFirstNext;
    @BindView(R.id.ll_select_goods)
    LinearLayout llSelectGoods;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;


    //字数限制
    private int num = 300;
    private CommonAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    //订单id
    private String orderId;
    private OrderDetailsBean orderBean;
    //是否选中
    private boolean[] isSelected;
    private StringBuilder sb = new StringBuilder();

    //相机权限
    static final String[] permissionStr = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_order);
        ButterKnife.bind(this);

        getData();
        initData();

    }

    /**
     * 加载数据
     */
    private void initData() {
        tvHeaderTitle.setText("售后服务");
        OkHttpUtils.get()
                .url(Constant.ORDER_DETAIL)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("id", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        orderBean = GsonUtils.jsonToBean(response, OrderDetailsBean.class);
                        if (orderBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }

    private void getData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("order_id");
    }


    /**
     * 加载视图
     */
    private void initView() {
        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        loadingView.setIndicatorColor(Color.GRAY);
        isSelected = new boolean[orderBean.getData().getCar_list().size()];
        for (int i = 0; i < orderBean.getData().getCar_list().size(); i++) {
            isSelected[i] = false;
        }
        rvSelectGoods.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<OrderDetailsBean.DataBean.CarListBean> adapter = new CommonAdapter
                <OrderDetailsBean.DataBean.CarListBean>(this, R.layout.listitem_shopping_cart,
                orderBean.getData().getCar_list()) {
            @Override
            protected void convert(ViewHolder holder, OrderDetailsBean.DataBean.CarListBean carListBean,
                                   final int position) {
                holder.setChecked(R.id.checkbox_goods_select, false);
                Glide.with(ChangeOrderActivity.this)
                        .load(Constant.IMG_HOST + orderBean.getData().getCar_list().get(position).getGoods_thumb())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_item_goods));
                holder.setText(R.id.tv_goods_name, orderBean.getData().getCar_list().get(position)
                        .getGoods_name());
                switch (orderBean.getData().getCar_list().get(position).getGoods_type()) {
                    case 2:
                        holder.setText(R.id.tv_goods_content, orderBean.getData().getCar_list()
                                .get(position).getSize_content());
                        break;
                    default:
                        holder.setText(R.id.tv_goods_content, "定制款");
                        break;
                }
                holder.setText(R.id.tv_goods_price, "¥" + orderBean.getData().getCar_list()
                        .get(position).getPrice());
                holder.setText(R.id.tv_goods_count, "x" + orderBean.getData().getCar_list()
                        .get(position).getNum());
                holder.setVisible(R.id.view_cart, true);

                ((CheckBox) holder.getView(R.id.checkbox_goods_select)).
                        setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                isSelected[position] = b;
                            }
                        });
            }
        };
        rvSelectGoods.setAdapter(adapter);
        tvFirstNext.setVisibility(View.VISIBLE);


        etChangeOrder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                svChangeOrder.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        etInputName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeScrollView();
                return false;
            }
        });
        etInputTel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeScrollView();
                return false;
            }
        });

        etChangeOrder.addTextChangedListener(new TextWatcher() {
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
                int number = num - editable.length();
                tvInputCount.setText(num - number + "/" + num);
                selectionStart = etChangeOrder.getSelectionStart();
                selectionEnd = etChangeOrder.getSelectionEnd();
                if (temp.length() > num) {
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etChangeOrder.setText(editable);
                    etChangeOrder.setSelection(tempSelection);//设置光标在最后
                }
            }
        });

        rvSelectPhoto.setLayoutManager(new LinearLayoutManager(ChangeOrderActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        photoAdapter = new CommonAdapter<String>(ChangeOrderActivity.this,
                R.layout.listitem_picker_photo, selectedPhotos) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                Uri uri = Uri.fromFile(new File(s));

                boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(ChangeOrderActivity.this);

                if (canLoadImage) {
                    Glide.with(mContext)
                            .load(uri)
                            .centerCrop()
                            .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
                            .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into((ImageView) holder.getView(R.id.img_picker_photo));
                }
            }
        };

        rvSelectPhoto.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(ChangeOrderActivity.this);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        tvConsultPhone.setText("客服热线：" + MyApplication.serverPhone);
    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                svChangeOrder.scrollTo(0, svChangeOrder.getHeight());
            }
        }, 300);
    }


    @OnClick({R.id.img_header_back, R.id.img_select_photo, R.id.tv_next_step, R.id.tv_back_sales,
            R.id.tv_first_next, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_select_photo:
                if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                    EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                } else {
                    PhotoPicker.builder()
                            .setPhotoCount(4)
                            .setShowCamera(true)
                            .setSelected(selectedPhotos)
                            .start(this);
                }
                break;
            case R.id.tv_next_step:
                submitData();
                break;
            case R.id.tv_back_sales:
                finish();
                break;
            case R.id.tv_first_next:
                nextStep();
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 提交修改
     */
    private void submitData() {

        if (TextUtils.isEmpty(etChangeOrder.getText().toString().trim())
                || TextUtils.isEmpty(etInputName.getText().toString().trim())
                || !PhoneNumberUtils.judgePhoneNumber(etInputTel.getText().toString().trim())
                || selectedPhotos.size() == 0) {
            ToastUtils.showToast(this, "请按要求填写所有信息");
        } else {
            loadingView.smoothToShow();
            tvNextStep.setEnabled(false);
            OkHttpUtils.post()
                    .url(Constant.AFTER_SALE)
                    .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                    .addParams("order_id", orderId)
                    .addParams("content", etChangeOrder.getText().toString().trim())
                    .addParams("name", etInputName.getText().toString().trim())
                    .addParams("phone", etInputTel.getText().toString().trim())
                    .addParams("img_list", ImageEncodeUtils.encodeFile(selectedPhotos))
                    .addParams("car_id", sb.toString().trim())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            loadingView.smoothToHide();
                            svChangeOrder.setVisibility(View.GONE);
                            llChangeSuccess.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }

    /**
     * 下一步
     */
    private void nextStep() {
        boolean isChecked = false;
        for (int i = 0; i < isSelected.length; i++) {
            if (isSelected[i]) {
                isChecked = true;
                if (i == isSelected.length - 1) {
                    sb.append(orderBean.getData().getCar_list().get(i).getId());
                } else {
                    sb.append(orderBean.getData().getCar_list().get(i).getId()).append(",");
                }
            }
        }
        if (isChecked) {
            llSelectGoods.setVisibility(View.GONE);
            svChangeOrder.setVisibility(View.VISIBLE);
        } else {
            ToastUtils.showToast(this, "请选择商品");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);
                photoAdapter.notifyDataSetChanged();
            }

        }
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
