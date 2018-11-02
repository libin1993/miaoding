package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2017-06-08 17:53
 * Email：1993911441@qq.com
 * Describe：订单评价
 */
public class EvaluateActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_goods_icon)
    CircleImageView imgGoods;
    @BindView(R.id.tv_name_goods)
    TextView tvGoodsName;
    @BindView(R.id.img_select_picture)
    ImageView imgSelectPicture;
    @BindView(R.id.rv_goods_picture)
    RecyclerView rvGoodsPicture;
    @BindView(R.id.tv_submit_evaluate)
    TextView tvSubmit;
    @BindView(R.id.et_evaluate)
    EditText etEvaluate;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;
    @BindView(R.id.tv_type_product)
    TextView tvGoodsType;

    private String orderId;
    private String cartId;
    private String goodsId;
    private String goodsName;
    private String goodsImg;
    private String goodsType;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private String imgEncode;
    private CommonAdapter<String> adapter;
    //相机权限
    static final String[] permissionStr = {Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.publish_evaluate);
        Glide.with(getApplicationContext())
                .load(Constant.IMG_HOST + goodsImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(imgGoods);
        tvGoodsName.setText(goodsName);
        tvGoodsType.setText(goodsType);

        rvGoodsPicture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new CommonAdapter<String>(this, R.layout.listitem_picker_photo, selectedPhotos) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                Uri uri = Uri.fromFile(new File(s));

                boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(EvaluateActivity.this);

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

        rvGoodsPicture.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(EvaluateActivity.this);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        loadingView.setIndicatorColor(Color.GRAY);
    }

    private void getData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("order_id");
        cartId = intent.getStringExtra("cart_id");
        goodsId = intent.getStringExtra("goods_id");
        goodsImg = intent.getStringExtra("goods_img");
        goodsName = intent.getStringExtra("goods_name");
        goodsType = intent.getStringExtra("goods_type");
    }

    @OnClick({R.id.img_header_back, R.id.img_select_picture, R.id.tv_submit_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_select_picture:
                if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                    EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                } else {
                    PhotoPicker.builder()
                            .setPhotoCount(5)
                            .setShowCamera(true)
                            .setSelected(selectedPhotos)
                            .start(this);
                }
                break;
            case R.id.tv_submit_evaluate:
                submitData();
                break;
        }
    }

    /**
     * 提交评论
     */
    private void submitData() {
        if (!TextUtils.isEmpty(etEvaluate.getText().toString().trim())) {
            loadingView.smoothToShow();
            tvSubmit.setEnabled(false);

            new Thread(myRunnable).start();
        } else {
            ToastUtils.showToast(this, getString(R.string.please_input_evaluate));
        }
    }

    /**
     * 开启线程处理图片
     */
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            if (selectedPhotos.size() != 0) {
                imgEncode = ImageEncodeUtils.encodeFile(selectedPhotos);
                handler.sendEmptyMessage(1);
            } else {
                handler.sendEmptyMessage(2);
            }
        }
    };


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Map<String, String> map = new HashMap<>();
            map.put("order_id", orderId);
            map.put("car_id", cartId);
            map.put("goods_id", goodsId);
            map.put("token", SharedPreferencesUtils.getStr(EvaluateActivity.this, "token"));
            map.put("content", etEvaluate.getText().toString().trim());
            if (msg.what == 1) {
                map.put("img_list", imgEncode);
            }

            OkHttpUtils.post()
                    .url(Constant.EVALUATE)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            loadingView.smoothToHide();
                            ToastUtils.showToast(EvaluateActivity.this, getString(R.string.evaluate_success));
                            finish();
                        }
                    });

            return false;
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE
                || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
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
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, getString(R.string.camera));
    }
}
