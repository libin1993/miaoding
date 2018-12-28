package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soundcloud.android.crop.Crop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DataManagerUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.ImageDisposeUtils;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2016/8/31 16:48
 * Email：1993911441@qq.com
 * Describe：设置
 */
public class SetUpActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.ll_deliver_address)
    LinearLayout llDeliverAddress;
    @BindView(R.id.tv_log_out)
    TextView tvLogOut;
    @BindView(R.id.tv_set_name)
    TextView tvSetName;
    @BindView(R.id.img_circle_icon)
    CircleImageView imgCircleIcon;
    @BindView(R.id.ll_user_icon)
    LinearLayout llUserIcon;
    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.ll_measure_data)
    LinearLayout llMeasureData;
    @BindView(R.id.ll_user_birthday)
    LinearLayout llUserBirthday;
    @BindView(R.id.ll_clean_data)
    LinearLayout llCleanData;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.tv_set_birthday)
    TextView tvSetBirthday;
    @BindView(R.id.ll_feed_back)
    LinearLayout llFeedBack;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    //用户头像
    private String userIcon;
    //用户名
    private String userName;
    //用户生日
    private String userBirthday;
    //会员中心跳转,设置生日
    private boolean setBirthday;

    private ArrayList<String> selectedPhotos = new ArrayList<>();

    //相机权限
    static final String[] permissionStr = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.bind(this);

        tvHeaderTitle.setText(R.string.set);
        getData();
        initData();
    }

    private void getData() {
        setBirthday = getIntent().getBooleanExtra("set_birthday", false);
    }

    /**
     * 加载视图
     */
    private void initView() {
        Glide.with(getApplicationContext())
                .load(userIcon)
                .placeholder(R.mipmap.place_holder_goods)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgCircleIcon);
        tvSetName.setText(userName);

        if (!TextUtils.isEmpty(userBirthday)) {
            int age = Calendar.getInstance().get(Calendar.YEAR) -
                    Integer.parseInt(userBirthday.split("-")[0]);
            tvSetBirthday.setText(String.valueOf(age));
        }

        if (setBirthday) {
            changeBirthday();
        }
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.USER_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            userIcon = jsonObject1.getString("avatar");
                            userName = jsonObject1.getString("name");
                            SharedPreferencesUtils.saveStr(SetUpActivity.this, "avatar", userIcon);
                            SharedPreferencesUtils.saveStr(SetUpActivity.this, "username", userName);
                            userBirthday = jsonObject1.getString("birthday");
                            initView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 退出登录
     */
    public void logout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.logout));
        dialog.setMessage(R.string.is_logout);
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OkHttpUtils.get()
                        .url(Constant.LOGOUT)
                        .addParams("token", SharedPreferencesUtils.getStr(SetUpActivity.this, "token"))
                        .addParams("device_id", SharedPreferencesUtils.getStr(SetUpActivity.this, "client_id"))
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
                                        SharedPreferencesUtils.deleteStr(SetUpActivity.this, "token");
                                        SharedPreferencesUtils.deleteStr(SetUpActivity.this, "uid");
                                        SharedPreferencesUtils.deleteStr(SetUpActivity.this, "username");
                                        SharedPreferencesUtils.deleteStr(SetUpActivity.this, "avatar");
                                        SharedPreferencesUtils.deleteStr(SetUpActivity.this, "phone");
                                        Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                                        intent.putExtra("page", 0);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }

    @OnClick({R.id.img_header_back, R.id.ll_deliver_address, R.id.tv_log_out, R.id.ll_user_icon,
            R.id.ll_user_name, R.id.ll_measure_data, R.id.img_load_error,
            R.id.ll_user_birthday, R.id.ll_clean_data, R.id.ll_about_us, R.id.ll_feed_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.ll_deliver_address:
                Intent intent = new Intent(SetUpActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_log_out:
                logout();
                break;
            case R.id.ll_clean_data:
                cleanData();
                break;
            case R.id.ll_about_us:
                startActivity(new Intent(SetUpActivity.this, AboutUsActivity.class));
                break;
            case R.id.ll_user_icon:
                changeIcon();
                break;
            case R.id.ll_user_name:
                changeName();
                break;
            case R.id.ll_measure_data:
                Intent intent1 = new Intent(SetUpActivity.this, MeasureDataActivity.class);
                intent1.putExtra("type", 1);
                startActivity(intent1);
                break;
            case R.id.ll_user_birthday:
                changeBirthday();
                break;
            case R.id.ll_feed_back:
                startActivity(new Intent(SetUpActivity.this, UserHelpActivity.class));
                break;
            case R.id.img_load_error:
                initData();
                break;
        }

    }


    /**
     * 修改生日
     */
    private void changeBirthday() {
        DatePicker picker = new DatePicker(this);
        picker.setOffset(2);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        picker.setRangeStart(1900, 1, 1);
        picker.setRangeEnd(year, month, day);
        if (!TextUtils.isEmpty(userBirthday)) {
            String[] split = userBirthday.split("-");
            picker.setSelectedItem(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                    Integer.parseInt(split[2]));
        } else {
            picker.setSelectedItem(1980, 1, 1);
        }

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                changeInfo("birthday", year + "-" + month + "-" + day);
            }
        });
        picker.show();

    }

    /**
     * 修改姓名
     */
    private void changeName() {
        View popupView = getLayoutInflater().inflate(R.layout.ppw_change_name, null);
        final PopupWindow mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, -300);
        DisplayUtils.setBackgroundAlpha(this, true);

        final EditText etName = (EditText) popupView.findViewById(R.id.et_change_name);
        TextView tvConfirm = (TextView) popupView.findViewById(R.id.tv_confirm_change);
        TextView tvCancel = (TextView) popupView.findViewById(R.id.tv_cancel_change);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    ToastUtils.showToast(SetUpActivity.this, getString(R.string.nicknane_is_empty));
                } else {
                    changeInfo("name", etName.getText().toString().trim());
                    mPopupWindow.dismiss();
                }
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(SetUpActivity.this, false);
            }
        });

    }


    /**
     * 提交修改
     */
    private void changeInfo(final String key, final String value) {
        OkHttpUtils.post()
                .url(Constant.CHANGE_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams(key, value)
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
                                if (key.equals("birthday") && setBirthday) {
                                    Intent intent = new Intent();
                                    intent.putExtra("birthday", value);
                                    setResult(1, intent);
                                    finish();
                                }
                                ToastUtils.showToast(SetUpActivity.this, getString(R.string.edit_success));
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 修改头像
     */
    private void changeIcon() {
        if (!EasyPermissions.hasPermissions(this, permissionStr)) {
            EasyPermissions.requestPermissions(this, "", 123, permissionStr);
        } else {
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)
                    .start(this);
        }
    }

    /**
     * 清除缓存
     */
    private void cleanData() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle(getString(R.string.clear_cache));
        dialog.setMessage(R.string.is_clear_cache);
        //确定
        dialog.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DataManagerUtils.cleanApplicationCache(SetUpActivity.this);
                ToastUtils.showToast(SetUpActivity.this, getString(R.string.clear_success));
            }
        });
        //取消
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE ||
                requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {

                selectedPhotos.addAll(photos);
                if (selectedPhotos.size() != 0) {
                    Uri destination = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + File.separator + "CloudWorkshop", "avatar.png"));
                    Crop.of(Uri.fromFile(new File(selectedPhotos.get(0))), destination).asSquare().start(this);
                }
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            try {
                if (data != null) {
                    ImageDisposeUtils.rotatingImageView(Crop.getOutput(data).getPath());
                    imgCircleIcon.setImageURI(Crop.getOutput(data));
                    changeInfo("avatar", ImageEncodeUtils.fileToBase64(Crop.getOutput(data).getPath()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        PermissionUtils.showPermissionDialog(this, getString(R.string.camera));
    }
}

