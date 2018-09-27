package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.ConfirmOrderBean;
import cn.cloudworkshop.miaoding.bean.DeliveryAddressBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.AddressPickTask;
import cn.cloudworkshop.miaoding.utils.PhoneNumberUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/7 16:57
 * Email：1993911441@qq.com
 * Describe：新增地址
 */
public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.et_add_name)
    EditText etAddName;
    @BindView(R.id.et_add_number)
    EditText etAddNumber;
    @BindView(R.id.et_detailed_address)
    EditText etDetailedAddress;
    @BindView(R.id.tv_confirm_address)
    TextView tvConfirmAddress;
    @BindView(R.id.ll_receive_address)
    LinearLayout llSelectAddress;
    @BindView(R.id.tv_receive_address)
    TextView tvSelectAddress;
    @BindView(R.id.switch_address)
    Switch switchAddress;

    //0：非默认地址 1：默认地址
    int defaultAddress = 0;
    //省
    private String provinceAddress;
    //市
    private String cityAddress;
    //区
    private String countAddress;
    private DeliveryAddressBean.DataBean dataBean;

    //1:添加地址 2:修改地址
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
        }

    }

    /**
     * 加载视图
     */
    private void initView() {
        switch (type) {
            case 1:
                tvHeaderTitle.setText("新增地址");
                break;
            case 2:
                tvHeaderTitle.setText("编辑地址");
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    dataBean = (DeliveryAddressBean.DataBean) bundle.getSerializable("edit");
                    if (dataBean != null) {
                        etAddName.setText(dataBean.getName());
                        etAddNumber.setText(dataBean.getPhone());
                        tvSelectAddress.setText(dataBean.getProvince() + dataBean.getCity() + dataBean.getArea());
                        provinceAddress = dataBean.getProvince();
                        cityAddress = dataBean.getCity();
                        countAddress = dataBean.getArea();
                        etDetailedAddress.setText(dataBean.getAddress());
                        if (dataBean.getIs_default() == 1) {
                            switchAddress.setChecked(true);
                            switchAddress.setEnabled(false);
                            defaultAddress = 1;
                        } else {
                            switchAddress.setChecked(false);
                            switchAddress.setEnabled(true);
                        }
                    }


                }

                break;

        }
        switchAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    defaultAddress = 1;
                } else {
                    defaultAddress = 0;
                }
            }
        });

    }


    @OnClick({R.id.img_header_back, R.id.ll_receive_address, R.id.tv_confirm_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.ll_receive_address:
                selectAddress();
                break;
            case R.id.tv_confirm_address:
                addAddress();
                break;
        }
    }


    /**
     * 选择地址
     */
    private void selectAddress() {

        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {

            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                tvSelectAddress.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                provinceAddress = province.getAreaName();
                cityAddress = city.getAreaName();
                countAddress = county.getAreaName();
            }
        });
        if (!TextUtils.isEmpty(provinceAddress)) {
            task.execute(provinceAddress, cityAddress, countAddress);
        } else {
            task.execute("北京市", "北京市", "朝阳区");
        }

    }

    /**
     * 添加地址、编辑地址
     */
    private void addAddress() {
        if (TextUtils.isEmpty(etAddName.getText().toString().trim()) ||
                TextUtils.isEmpty(etAddNumber.getText().toString().trim()) ||
                TextUtils.isEmpty(tvSelectAddress.getText().toString().trim()) ||
                TextUtils.isEmpty(etDetailedAddress.getText().toString().trim())) {

            ToastUtils.showToast(this, "请完善个人信息");
        } else {
            if (PhoneNumberUtils.judgePhoneNumber(etAddNumber.getText().toString().trim())) {
                Map<String, String> map = new HashMap<>();
                map.put("token", SharedPreferencesUtils.getStr(this, "token"));
                map.put("name", etAddName.getText().toString().trim());
                map.put("phone", etAddNumber.getText().toString().trim());
                map.put("is_default", String.valueOf(defaultAddress));
                map.put("province", provinceAddress);
                map.put("city", cityAddress);
                map.put("area", countAddress);
                map.put("address", etDetailedAddress.getText().toString().trim());
                if (type == 2) {
                    map.put("id", String.valueOf(dataBean.getId()));
                }

                OkHttpUtils.post()
                        .url(Constant.ADD_ADDRESS)
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
                                    int code = jsonObject.getInt("code");
                                    String addressId = jsonObject.getString("address_id");
                                    if (code == 1) {
                                        //地址编辑成功
                                        EventBus.getDefault().post("edit_success");
                                        switch (type) {
                                            case 1:
                                                ToastUtils.showToast(AddAddressActivity.this, "添加成功");
                                                Intent intent = new Intent();
                                                ConfirmOrderBean.DataBean.AddressListBean addressListBean
                                                        = new ConfirmOrderBean.DataBean.AddressListBean();
                                                addressListBean.setId(Integer.parseInt(addressId));

                                                addressListBean.setProvince(provinceAddress);
                                                addressListBean.setCity(cityAddress);
                                                addressListBean.setArea(countAddress);
                                                addressListBean.setAddress(etDetailedAddress.getText().toString().trim());
                                                addressListBean.setName(etAddName.getText().toString().trim());
                                                addressListBean.setPhone(etAddNumber.getText().toString().trim());
                                                addressListBean.setIs_default(1);

                                                intent.putExtra("address", addressListBean);

                                                setResult(1, intent);
                                                finish();
                                                break;
                                            case 2:
                                                ToastUtils.showToast(AddAddressActivity.this, "修改成功");
                                                finish();
                                                break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                ToastUtils.showToast(this, "请输入11位手机号码");
            }
        }
    }
}



