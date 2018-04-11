package cn.cloudworkshop.miaoding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.constant.Constant;

/**
 * Author：Libin on 2017-06-08 11:25
 * Email：1993911441@qq.com
 * Describe：腔调作品（老版）
 */
public class DesignerWorksFragment2 extends BaseFragment {

    @BindView(R.id.tab_designer_works)
    SlidingTabLayout tabDesignerWorks;
    @BindView(R.id.vp_designer_works)
    ViewPager vpDesignerWorks;
    @BindView(R.id.img_designer_works)
    ImageView imgDesignerWorks;
    @BindView(R.id.app_bar_works)
    AppBarLayout appBar;
    @BindView(R.id.tool_bar)
    CollapsingToolbarLayout toolBar;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_works_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 加载视图
     */
    private void initView() {


        List<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        titleList.add("腔调");
        titleList.add("设计师");
        fragmentList.add(WorksFragment.newInstance());
        fragmentList.add(OldDesignerFragment.newInstance());

        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getChildFragmentManager(),
                fragmentList, titleList);
        vpDesignerWorks.setOffscreenPageLimit(titleList.size());
        vpDesignerWorks.setAdapter(adapter);

        tabDesignerWorks.setViewPager(vpDesignerWorks);
        tabDesignerWorks.setCurrentTab(0);

        //appbar滑动监听，收缩时禁止下拉
//        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                    toolBar.removeAllViews();
//                }
//            }
//        });
    }


    public static DesignerWorksFragment2 newInstance() {
        Bundle args = new Bundle();
        DesignerWorksFragment2 fragment = new DesignerWorksFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
