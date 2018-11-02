package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2018/4/10 10:07
 * Email：1993911441@qq.com
 * Describe：量体用户信息
 */
public class CameraFormActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;
    @BindView(R.id.et_measure_username)
    EditText etUsername;
    @BindView(R.id.et_measure_height)
    EditText etHeight;
    @BindView(R.id.et_measure_weight)
    EditText etWeight;
    @BindView(R.id.cb_measure_info)
    CheckBox cbMeasure;
    @BindView(R.id.et_measure_store)
    EditText etStore;
    @BindView(R.id.et_measure_bust)
    EditText etBust;
    @BindView(R.id.et_measure_waist)
    EditText etWaist;
    @BindView(R.id.et_measure_hip)
    EditText etHip;
    @BindView(R.id.ll_measure_info)
    LinearLayout llMeasure;
    @BindView(R.id.tv_confirm_camera)
    TextView tvConfirm;

    //是否有量体数据 1：无数据 2:有数据
    private int measureStatus;
    private String[] permissionStr = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_form);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.user_info);
        cbMeasure.setChecked(false);
        cbMeasure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llMeasure.setVisibility(View.VISIBLE);
                } else {
                    llMeasure.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        measureStatus = getIntent().getIntExtra("measure_status", 0);
    }

    @OnClick({R.id.img_header_back, R.id.tv_confirm_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.tv_confirm_camera:
                if (isMeasureData()) {
                    if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                        EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                    } else {
                        Intent intent;
                        if (measureStatus == 2) {
                            intent = new Intent(this, NewCameraActivity.class);
                        } else {
                            intent = new Intent(this, CameraGuideActivity.class);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("name", etUsername.getText().toString().trim());
                        bundle.putString("height", etHeight.getText().toString().trim());
                        bundle.putString("weight", etWeight.getText().toString().trim());

                        if (!TextUtils.isEmpty(etStore.getText().toString().trim())) {
                            bundle.putString("store", etStore.getText().toString().trim());
                        }
                        if (!TextUtils.isEmpty(etBust.getText().toString().trim())) {
                            bundle.putString("bust", etBust.getText().toString().trim());
                        }
                        if (!TextUtils.isEmpty(etWaist.getText().toString().trim())) {
                            bundle.putString("waist", etWaist.getText().toString().trim());
                        }
                        if (!TextUtils.isEmpty(etHip.getText().toString().trim())) {
                            bundle.putString("hip", etHip.getText().toString().trim());
                        }

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    ToastUtils.showToast(this, getString(R.string.please_input_common_info));
                }

                break;
        }
    }

    /**
     * @return 量体数据是否完善
     */
    private boolean isMeasureData() {

        return !TextUtils.isEmpty(etUsername.getText().toString().trim())
                && DisplayUtils.isNumberDecimal(etHeight.getText().toString().trim())
                && DisplayUtils.isNumberDecimal(etWeight.getText().toString().trim());
    }

    /**
     * @param msg 拍照，结束当前页面
     */
    @Subscribe
    public void takeSuccess(String msg) {
        if ("take_success".equals(msg)) {
            measureStatus = 2;
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
        PermissionUtils.showPermissionDialog(this, getString(R.string.camera));
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
