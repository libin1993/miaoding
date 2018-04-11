package cn.cloudworkshop.miaoding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.MemberTabBean;
import cn.cloudworkshop.miaoding.utils.DesignerTabUtils;

/**
 * Author：Libin on 2017-04-21 10:24
 * Email：1993911441@qq.com
 * Describe：腔调成品
 */
public class DesignerWorksFragment extends BaseFragment {
    @BindView(R.id.tab_works_designer)
    CommonTabLayout tabWorks;

    Unbinder unbinder;
    //fragment
    private List<Fragment> fragmentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_works, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }


    private void initView() {
        fragmentList.add(WorksFragment.newInstance());
        fragmentList.add(DesignerFragment.newInstance());

        ArrayList<CustomTabEntity> tabList = new ArrayList<>();

        tabList.add(new MemberTabBean("腔调"));
        tabList.add(new MemberTabBean("设计师"));

        tabWorks.setTabData(tabList);

        new DesignerTabUtils(getChildFragmentManager(), fragmentList, R.id.fl_works, tabWorks);

    }

    public static DesignerWorksFragment newInstance() {
        Bundle args = new Bundle();
        DesignerWorksFragment fragment = new DesignerWorksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
