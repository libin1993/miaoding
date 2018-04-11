package cn.cloudworkshop.miaoding.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.List;

import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;

/**
 * 主界面 底部切换tab工具类（加载本地图片）
 */
public class OldFragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private RadioGroup rgs; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中当前fragment的区域的id
    private int currentTab; // 当前Tab页面索引
    private Context mContext;


    /**
     * @param fragmentManager
     * @param fragmentList
     * @param fragmentContentId
     * @param rgs
     */
    public OldFragmentTabUtils(Context context, FragmentManager fragmentManager, List<Fragment> fragmentList,
                               int fragmentContentId, RadioGroup rgs) {
        this.mContext = context;
        this.fragmentList = fragmentList;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        rgs.setOnCheckedChangeListener(this);
        ((RadioButton) rgs.getChildAt(0)).setChecked(true);
        MyApplication.homeEnterTime = DateUtils.getCurrentTime();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgs.getChildCount(); i++) {
            RadioButton rBtn = ((RadioButton) rgs.getChildAt(i));
            if (i == 0) {
                MyApplication.homeEnterTime = DateUtils.getCurrentTime();
            }

            if (rBtn.getId() == checkedId) {
                initFragment(i);
            }
        }

    }

    /**
     * @param i 加载fragment
     */
    private void initFragment(int i) {
        Fragment fragment = fragmentList.get(i);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentList.get(currentTab).onStop(); // 暂停当前tab
        if (fragment.isAdded()) {
            fragment.onStart(); // 启动目标tab的fragment onStart()
        } else {
            ft.add(fragmentContentId, fragment, fragment.getClass().getName());
            ft.commit();
        }
        showTab(i); // 显示目标tab
    }

    /**
     * @param position 设置当前fragment
     */
    public void setCurrentFragment(int position) {
        ((RadioButton) rgs.getChildAt(position)).setChecked(true);
    }


    /**
     * 切换tab
     * @param index
     */
    private void showTab(int index) {
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (index == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = index; // 更新目标tab为当前tab
    }
}