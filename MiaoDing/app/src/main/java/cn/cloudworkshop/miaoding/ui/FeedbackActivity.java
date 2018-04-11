package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Author：Libin on 2016/8/31 17:15
 * Email：1993911441@qq.com
 * Describe：反馈
 */
public class FeedbackActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.et_feed_back)
    EditText etFeedBack;
    @BindView(R.id.tv_current_count)
    TextView tvCurrentCount;
    @BindView(R.id.img_add_feed_back)
    ImageView imgAddFeedBack;
    @BindView(R.id.rv_feed_back)
    RecyclerView rvFeedBack;
    @BindView(R.id.et_feed_back_phone)
    EditText etFeedBackPhone;
    @BindView(R.id.tv_submit_feed_back)
    TextView tvSubmit;
    @BindView(R.id.scroll_feed_back)
    ScrollView scrollFeedBack;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;


    private CommonAdapter adapter;
    private ArrayList<String> photoList = new ArrayList<>();
    private String imgEncode;

    static final String[] permissionStr = {Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollFeedBack.scrollTo(0, scrollFeedBack.getHeight());
            }
        }, 300);
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Map<String, String> map = new HashMap<>();
            map.put("token", SharedPreferencesUtils.getStr(FeedbackActivity.this, "token"));
            map.put("content", etFeedBack.getText().toString().trim());
            if (!TextUtils.isEmpty(etFeedBackPhone.getText().toString().trim())) {
                map.put("contact", etFeedBackPhone.getText().toString().trim());
            }
            if (msg.what == 1) {
                map.put("img_list", imgEncode);
            }

            OkHttpUtils.post()
                    .url(Constant.FEED_BACK)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //反馈事件监听
                            MobclickAgent.onEvent(FeedbackActivity.this, "feedback");
                            loadingView.smoothToHide();
                            ToastUtils.showToast(FeedbackActivity.this, "提交成功，感谢您的宝贵意见");
                            finish();
                        }
                    });

            return false;
        }
    });

    /**
     * 提交反馈
     */
    private void submitData() {
        if (!TextUtils.isEmpty(etFeedBack.getText().toString().trim())) {
            loadingView.smoothToShow();
            tvSubmit.setEnabled(false);

            new Thread(myRunnable).start();
        } else {
            ToastUtils.showToast(this, "请写下您的宝贵意见");
        }

    }

    /**
     * 开启线程处理图片
     */
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            if (photoList.size() != 0) {
                imgEncode = ImageEncodeUtils.encodeFile(photoList);
                handler.sendEmptyMessage(1);
            } else {
                handler.sendEmptyMessage(2);
            }
        }
    };


    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText("意见反馈");
        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        loadingView.setIndicatorColor(Color.GRAY);
        etFeedBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollFeedBack.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        etFeedBackPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                changeScrollView();
                return false;
            }
        });


        etFeedBack.addTextChangedListener(new TextWatcher() {
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
                tvCurrentCount.setText(editable.length() + "/" + 300);
                selectionStart = etFeedBack.getSelectionStart();
                selectionEnd = etFeedBack.getSelectionEnd();
                if (temp.length() > 300) {
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etFeedBack.setText(editable);
                    etFeedBack.setSelection(tempSelection);//设置光标在最后
                }
            }
        });


        rvFeedBack.setLayoutManager(new LinearLayoutManager(FeedbackActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        adapter = new CommonAdapter<String>(FeedbackActivity.this,
                R.layout.listitem_picker_photo, photoList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                Uri uri = Uri.fromFile(new File(s));

                boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(FeedbackActivity.this);

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

        rvFeedBack.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                PhotoPreview.builder()
                        .setPhotos(photoList)
                        .setCurrentItem(position)
                        .start(FeedbackActivity.this);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    @OnClick({R.id.img_add_feed_back, R.id.tv_submit_feed_back, R.id.img_header_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_add_feed_back:
                if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                    EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                } else {
                    PhotoPicker.builder()
                            .setPhotoCount(4)
                            .setShowCamera(true)
                            .setSelected(photoList)
                            .start(this);
                }

                break;
            case R.id.tv_submit_feed_back:
                submitData();
                break;
            case R.id.img_header_back:
                finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE
                || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            photoList.clear();

            if (photos != null) {
                photoList.addAll(photos);
                adapter.notifyDataSetChanged();
            }

        }
    }


    /**
     * 关闭线程
     */
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
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, "相机");
    }
}
