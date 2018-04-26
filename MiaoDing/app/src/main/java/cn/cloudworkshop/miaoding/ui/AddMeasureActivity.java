package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.LogUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2018/4/24 14:53
 * Email：1993911441@qq.com
 * Describe：新增量体数据
 */
public class AddMeasureActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_measure_name)
    EditText etMeasureName;
    @BindView(R.id.et_measure_user_height)
    EditText etMeasureHeight;
    @BindView(R.id.et_measure_user_weight)
    EditText etMeasureWeight;
    @BindView(R.id.tv_measure_help)
    TextView tvMeasureHelp;
    @BindView(R.id.img_measure_camera)
    SimpleDraweeView imgMeasureCamera;
    @BindView(R.id.img_is_measure)
    CircleImageView imgIsMeasure;
    @BindView(R.id.rl_measure_photo)
    RelativeLayout rlMeasurePhoto;
    @BindView(R.id.switch_default_measure)
    Switch switchDefault;
    @BindView(R.id.tv_save_measure)
    TextView tvSaveMeasure;

    private String[] permissionStr = {Manifest.permission.CAMERA};
    //是否默认 1：默认 0:非默认
    private int defaultMeasure;
    //是否有量体数据 1：无数据 2:有数据
    private int measureStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initView();
    }

    private void getData() {
        measureStatus = getIntent().getIntExtra("measure_status", 0);
    }

    private void initView() {
        tvHeaderTitle.setText("添加量体数据");
        switchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    defaultMeasure = 1;
                } else {
                    defaultMeasure = 0;
                }
            }
        });
    }

    @OnClick({R.id.img_header_back, R.id.tv_measure_help, R.id.rl_measure_photo, R.id.tv_save_measure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_measure_help:
                takePhoto(true);
                break;
            case R.id.rl_measure_photo:
                takePhoto(false);
                break;
            case R.id.tv_save_measure:
                if (isMeasureData()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this,
                            R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    dialog.setTitle("上传量体数据");
                    dialog.setMessage("您尚未完善量体照片，是否去拍照？");
                    //为“确定”按钮注册监听事件
                    dialog.setPositiveButton("去拍照", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            takePhoto(false);
                        }
                    });
                    //为“取消”按钮注册监听事件
                    dialog.setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            submitData();
                        }
                    });
                    dialog.create();
                    dialog.show();

                } else {
                    ToastUtils.showToast(this, "请完善基本信息");
                }
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
                .addParams("name", etMeasureName.getText().toString().trim())
                .addParams("height", etMeasureHeight.getText().toString().trim())
                .addParams("weight", etMeasureWeight.getText().toString().trim())
                .addParams("is_index", String.valueOf(defaultMeasure))
                .addParams("scale","1,1,1,1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.log(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            ToastUtils.showToast(AddMeasureActivity.this, msg);
                            if (code == 1) {
                                EventBus.getDefault().post("add_success");
                                finish();
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
                bundle.putString("name", etMeasureName.getText().toString().trim());
                bundle.putString("height", etMeasureHeight.getText().toString().trim());
                bundle.putString("weight", etMeasureWeight.getText().toString().trim());
                bundle.putInt("is_default", defaultMeasure);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        } else {
            ToastUtils.showToast(this, "请输入身高体重");
        }
    }

    /**
     * @return 量体数据是否完善
     */
    private boolean isMeasureData() {

        return !TextUtils.isEmpty(etMeasureName.getText().toString().trim())
                && !TextUtils.isEmpty(etMeasureHeight.getText().toString().trim())
                && !TextUtils.isEmpty(etMeasureWeight.getText().toString().trim());
    }

    /**
     * @param msg 拍照，结束当前页面
     */
    @Subscribe
    public void takeSuccess(String msg) {
        if ("take_success".equals(msg)) {
            EventBus.getDefault().post("add_success");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, "相机");
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
