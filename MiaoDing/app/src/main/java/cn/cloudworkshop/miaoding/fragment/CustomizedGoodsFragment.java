package cn.cloudworkshop.miaoding.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.ScanCodeActivity;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/9/25 18:57
 * Email：1993911441@qq.com
 * Describe：定制商品（当前版本）
 */
public class
CustomizedGoodsFragment extends BaseFragment {
    @BindView(R.id.tab_goods)
    SlidingTabLayout tabGoods;
    @BindView(R.id.img_goods_code)
    ImageView imgGoods;
    @BindView(R.id.vp_goods)
    ViewPager vpGoods;
    @BindView(R.id.img_load_error)
    ImageView imgLoadingError;
    private Unbinder unbinder;

    private GoodsTitleBean titleBean;

    // 是否需要系统权限检测
    private boolean isRequireCheck = true;
    static final String[] permissionStr = new String[]{Manifest.permission.CAMERA};
    //是否授权
    private boolean isGrant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        return view;
    }

    /**
     * 商品分类
     */
    private void initTitle() {
        OkHttpUtils.get()
                .url(Constant.GOODS_TITLE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadingError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadingError.setVisibility(View.GONE);
                        titleBean = GsonUtils.jsonToBean(response, GoodsTitleBean.class);
                        if (titleBean.getData() != null && titleBean.getData().size() > 0) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < titleBean.getData().size(); i++) {
            fragmentList.add(SubGoodsFragment.newInstance(titleBean.getData().get(i).getId()));
            titleList.add(titleBean.getData().get(i).getName());
        }

        //tab个数低于5个平分，不可滑动
        if (titleBean.getData().size() <= 5) {
            tabGoods.setTabSpaceEqual(true);
        } else {
            tabGoods.setTabSpaceEqual(false);
        }
        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getChildFragmentManager(),
                fragmentList, titleList);
        vpGoods.setOffscreenPageLimit(titleList.size());
        vpGoods.setAdapter(adapter);
        tabGoods.setViewPager(vpGoods);
        tabGoods.setCurrentTab(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public static CustomizedGoodsFragment newInstance() {

        Bundle args = new Bundle();

        CustomizedGoodsFragment fragment = new CustomizedGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 检测权限
     */
    private void judgePermission() {
        if (isRequireCheck) {
            //权限没有授权，进入授权界面
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                isGrant = false;
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermissions(permissionStr, 1);
                } else {
                    showPermissionDialog();
                }
            } else {
                isGrant = true;
            }
        } else {
            isRequireCheck = true;
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
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isRequireCheck = false;
            isGrant = true;
            Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
            startActivity(intent);

        } else {
            isRequireCheck = true;
            isGrant = false;
            showPermissionDialog();
        }
    }


    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return false;
    }

    /**
     * 提示对话框
     */
    public void showPermissionDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),
                R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setTitle("帮助");
        dialog.setMessage("当前应用缺少相机权限，请点击\"设置\" - \"权限管理\"，打开所需权限。");
        //为“确定”按钮注册监听事件
        dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 启动应用的设置
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);
            }
        });
        //为“取消”按钮注册监听事件
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据实际情况编写相应代码。
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }

    @OnClick({R.id.img_goods_code, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_goods_code:
                judgePermission();
                if (isGrant) {
                    Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_load_error:
                initTitle();
                break;
        }
    }
}




