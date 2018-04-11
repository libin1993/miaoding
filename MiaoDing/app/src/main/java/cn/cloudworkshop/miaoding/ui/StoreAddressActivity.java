package cn.cloudworkshop.miaoding.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.utils.PermissionUtils;

/**
 * Author：Libin on 2018/3/2 09:46
 * Email：1993911441@qq.com
 * Describe：商铺地址
 */
public class StoreAddressActivity extends BaseActivity implements LocationSource, AMapLocationListener {
    @BindView(R.id.map_store_address)
    MapView mapView;
    @BindView(R.id.img_map_back)
    ImageView img_back;

    AMap aMap;
    private OnLocationChangedListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //经度
    private String latitude;
    //纬度
    private String longitude;
    //店铺名称
    private String storeName;
    //店铺地址
    private String storeAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_address);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);

        getData();
        initMap();
        //开始定位
        initLocation();
        addMarker();
    }

    private void getData() {
        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        storeName = intent.getStringExtra("store_name");
        storeAddress = intent.getStringExtra("store_address");
    }

    /**
     * 定制店marker
     */
    private void addMarker() {
        LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        //自定义点标记
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng).title(storeName).snippet(storeAddress);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.icon_current_address)));//设置图标
        aMap.addMarker(markerOptions);
        //将地图移动到指定位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
    }


    /**
     * 初始化map
     */
    private void initMap() {
        aMap = mapView.getMap();

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mark1));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLUE);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(30, 0, 0, 40));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0.5f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);

        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);

    }


    //定位
    private void initLocation() {
        //初始化定位
        AMapLocationClient mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @OnClick(R.id.img_map_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {

                    //点击定位按钮 能够将地图的中心移动到定位点
//                    mListener.onLocationChanged(aMapLocation);
                    setMarker(aMapLocation);
                    isFirstLoc = false;
                }
            }
        }

    }

    /**
     * @param aMapLocation
     * @return 自定义定位marker
     */
    private void setMarker(AMapLocation aMapLocation) {

        ArrayList<BitmapDescriptor> gifList = new ArrayList<>();
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mark1));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mark2));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_mark3));

        MarkerOptions markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f)
                .position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()))
                .icons(gifList)
                .period(4);

        aMap.addMarker(markerOption1);

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }
}
