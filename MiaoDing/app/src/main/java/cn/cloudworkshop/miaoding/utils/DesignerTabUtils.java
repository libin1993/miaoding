package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import java.util.List;

/**
 * Author：Libin on 2017/9/25 15:49
 * Email：1993911441@qq.com
 * Describe：腔调 头部切换tab工具类
 */
public class DesignerTabUtils implements OnTabSelectListener {

    private List<Fragment> fragmentList; // 一个tab页面对应一个Fragment
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中当前fragment的区域的id
    private int currentTab; // 当前Tab页面索引


    /**
     * @param fragmentManager
     * @param fragmentList
     * @param fragmentContentId
     * @param tabLayout
     */
    public DesignerTabUtils(FragmentManager fragmentManager, List<Fragment> fragmentList,
                            int fragmentContentId, CommonTabLayout tabLayout) {
        this.fragmentList = fragmentList;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        tabLayout.setOnTabSelectListener(this);
        tabLayout.setCurrentTab(0);
        initFragment(0);
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
            ft.commitAllowingStateLoss();
        }
        showTab(i); // 显示目标tab
    }



    /**
     * 切换fragment
     *
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
            ft.commitAllowingStateLoss();
        }
        currentTab = index; // 更新目标tab为当前tab
    }

    @Override
    public void onTabSelect(int position) {
        initFragment(position);
    }

    @Override
    public void onTabReselect(int position) {

    }
}
