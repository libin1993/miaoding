package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.StoreInfoBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2017/12/7 13:57
 * Email：1993911441@qq.com
 * Describe：店铺
 */
public class StoreInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.img_shop_back)
    ImageView imgShopBack;
    @BindView(R.id.tv_buy_goods)
    TextView tvBuyGoods;
    @BindView(R.id.img_store_bg)
    ImageView imgStoreBg;
    @BindView(R.id.img_store_icon)
    ImageView imgStoreIcon;
    @BindView(R.id.tv_name_store)
    TextView tvStoreName;
    @BindView(R.id.tv_store_love)
    TextView tvLoveNum;
    @BindView(R.id.rv_store_img)
    RecyclerView rvStore;
    @BindView(R.id.img_focus_store)
    ImageView imgFocusStore;
    @BindView(R.id.ll_focus_store)
    LinearLayout llFocusStore;
    private int shopId;
    private StoreInfoBean.DataBean storeBean;

    //定位权限（运行时权限）
    static final String[] permissionStr = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        ButterKnife.bind(this);
        getData();
        initData();

    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }


    private void initData() {
        OkHttpUtils.get()
                .url(Constant.STORE_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("shop_id", String.valueOf(shopId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        storeBean = GsonUtils.jsonToBean(response, StoreInfoBean.class).getData();
                        if (storeBean != null) {
                            initView();
                        }

                    }
                });

    }

    /**
     * 加载视图
     */
    private void initView() {
        Glide.with(this)
                .load(Constant.IMG_HOST + storeBean.getBgimg())
                .placeholder(R.mipmap.place_holder_banner)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgStoreBg);
        Glide.with(this)
                .load(Constant.IMG_HOST + storeBean.getImg())
                .placeholder(R.mipmap.place_holder_goods)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgStoreIcon);
        tvStoreName.setText(storeBean.getName());
        tvLoveNum.setText(getString(R.string.fans)+"： " + storeBean.getLovenum());
        tvStoreAddress.setText(getString(R.string.store_address)+"： " + storeBean.getAddress());
        if (storeBean.getIs_collect() == 1) {
            imgFocusStore.setImageResource(R.mipmap.icon_collect_check);
        } else {
            imgFocusStore.setImageResource(R.mipmap.icon_collect_normal);
        }

        rvStore.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<String> adapter = new CommonAdapter<String>(StoreInfoActivity.this,
                R.layout.listitem_sotre_img, storeBean.getFactory_img_arr()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                SimpleDraweeView img = (SimpleDraweeView) holder.itemView.findViewById(R.id.img_store_info);
                if (!TextUtils.isEmpty(storeBean.getImg_info_arr().get(position))) {
                    img.setAspectRatio(Float.parseFloat(storeBean.getImg_info_arr().get(position)));
                }
                img.setImageURI(Constant.IMG_HOST + s);

            }
        };
        rvStore.setAdapter(adapter);

    }

    @OnClick({R.id.tv_store_address, R.id.img_shop_back, R.id.tv_buy_goods, R.id.ll_focus_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_store_address:
                if (!TextUtils.isEmpty(storeBean.getLatitude()) && !TextUtils.isEmpty(storeBean.getLongitude())) {
                    if (!EasyPermissions.hasPermissions(this, permissionStr)) {
                        EasyPermissions.requestPermissions(this, "", 123, permissionStr);
                    } else {
                        Intent intent = new Intent(this, StoreAddressActivity.class);
                        intent.putExtra("latitude", storeBean.getLatitude());
                        intent.putExtra("longitude", storeBean.getLongitude());
                        intent.putExtra("store_name", storeBean.getName());
                        intent.putExtra("store_address", storeBean.getAddress());
                        startActivity(intent);
                    }
                }

                break;
            case R.id.img_shop_back:
                finish();
                break;
            case R.id.tv_buy_goods:
                if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "店铺");
                    startActivity(login);
                } else {
                    Intent intent = new Intent(this, StoreOrderActivity.class);
                    intent.putExtra("shop_id", storeBean.getId());
                    intent.putExtra("shop_name", storeBean.getName());
                    intent.putExtra("shop_icon", storeBean.getImg());
                    startActivity(intent);
                }

                break;
            case R.id.ll_focus_store:
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(this, "token"))) {
                    addCollection();
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    login.putExtra("page_name", "收藏");
                    startActivity(login);
                }
                break;
        }
    }


    /**
     * 添加收藏
     */
    private void addCollection() {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("type", "3")
                .addParams("cid", String.valueOf(shopId))
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
                                imgFocusStore.setImageResource(R.mipmap.icon_collect_check);
                            } else {
                                imgFocusStore.setImageResource(R.mipmap.icon_collect_normal);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
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
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        PermissionUtils.showPermissionDialog(this, getString(R.string.location));
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
    }
}



