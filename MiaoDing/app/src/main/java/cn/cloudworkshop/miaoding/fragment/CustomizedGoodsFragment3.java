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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.JazzyPagerAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsListBean;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.bean.MemberTabBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.jazzyviewpager.JazzyViewPager;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.ui.ScanCodeActivity;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-09-15 17:40
 * Email：1993911441@qq.com
 * Describe：定制商品（老版）
 */
public class CustomizedGoodsFragment3 extends BaseFragment {
    @BindView(R.id.img_qr_code)
    ImageView imgCode;
    @BindView(R.id.vp_custom)
    JazzyViewPager vpGoods;
    @BindView(R.id.tab_custom)
    CommonTabLayout tabGoods;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private Unbinder unbinder;

    private GoodsTitleBean titleBean;

    // 是否需要系统权限检测
    private boolean isRequireCheck = true;
    static final String[] permissionStr = new String[]{Manifest.permission.CAMERA};
    //是否授权
    private boolean isGrant;
    //监听banner滑动状态
    private boolean isScrolled;
    private int page = 1;
    //加载更多
    private boolean isLoadMore;
    //选择类型
    private boolean isSelectType;
    private int currentItem;
    private List<GoodsListBean.DataBean.itemDataBean> dataList = new ArrayList<>();
    private JazzyPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_custom, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        return view;
    }


    /**
     * 定制商品种类
     */
    private void initTitle() {
        OkHttpUtils.get()
                .url(Constant.GOODS_TITLE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        titleBean = GsonUtils.jsonToBean(response, GoodsTitleBean.class);
                        if (titleBean.getData() != null) {
                            selectType();
                        }
                    }
                });
    }


    /**
     * 选择商品种类
     */
    public void selectType() {

        ArrayList<CustomTabEntity> tabList = new ArrayList<>();
        for (int i = 0; i < titleBean.getData().size(); i++) {
            tabList.add(new MemberTabBean(titleBean.getData().get(i).getName()));
        }

        tabGoods.setTabData(tabList);
        tabGoods.setCurrentTab(0);
        initGoods(titleBean.getData().get(0).getId() + "");

        tabGoods.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                page = 1;
                isSelectType = true;
                isLoadMore = false;
                initGoods(titleBean.getData().get(position).getId() + "");
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * @param id 加载商品
     */
    private void initGoods(String id) {
        OkHttpUtils.get()
                .url(Constant.GOODS_LIST)
                .addParams("type", "1")
                .addParams("classify_id", id)
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        GoodsListBean listBean = GsonUtils.jsonToBean(response, GoodsListBean.class);
                        if (listBean.getData().getData() != null && listBean.getData().getData().size() > 0) {
                            if (isSelectType) {
                                dataList.clear();
                            }
                            dataList.addAll(listBean.getData().getData());
                            if (isLoadMore) {
                                adapter.notifyDataSetChanged();
                                vpGoods.setCurrentItem(currentItem + 1);
                                isLoadMore = false;
                            } else if (isSelectType) {
                                adapter.notifyDataSetChanged();
                                initView();
                                vpGoods.setCurrentItem(0);
                                isSelectType = false;
                            } else {
                                initView();
                            }
                        } else {
                            if (page != 1) {
                                ToastUtils.showToast(getActivity(), "已经是最后一页了");
                                page--;
                            }
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        vpGoods.setOffscreenPageLimit(dataList.size());
        vpGoods.setTransitionEffect(JazzyViewPager.TransitionEffect.ZoomIn);
        adapter = new JazzyPagerAdapter(dataList, getActivity(), vpGoods);
        vpGoods.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //手指左右滑动不超过48px,上下滑动不超过250px
        vpGoods.setOnTouchListener(new View.OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ViewConfiguration configuration = ViewConfiguration.get(getActivity());
                        int mTouchSlop = configuration.getScaledPagingTouchSlop();

                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);

                        if (xDiff < mTouchSlop && yDiff < 250)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {
                            Intent intent = new Intent(getActivity(), CustomizedGoodsActivity.class);
                            intent.putExtra("id", String.valueOf(dataList.get(vpGoods.getCurrentItem()).getId()));
                            startActivity(intent);
                        }
                        break;

                }
                return false;
            }
        });

        vpGoods.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    ToastUtils.showToast(getActivity(), "已经是第一页了");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (!isScrolled && vpGoods.getCurrentItem() == dataList.size() - 1) {
                            isLoadMore = true;
                            currentItem = vpGoods.getCurrentItem();
                            page++;
                            initGoods(titleBean.getData().get(tabGoods.getCurrentTab()).getId() + "");
                        }
                        isScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isScrolled = true;
                        break;

                }
            }
        });

    }


    public static CustomizedGoodsFragment3 newInstance() {
        Bundle args = new Bundle();
        CustomizedGoodsFragment3 fragment = new CustomizedGoodsFragment3();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_qr_code, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_qr_code:
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
