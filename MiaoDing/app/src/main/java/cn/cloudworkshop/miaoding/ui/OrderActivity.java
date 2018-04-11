package cn.cloudworkshop.miaoding.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.fragment.MyOrderFragment;

/**
 * Author: Libin on 2016/9/1 13:32
 * Email：1993911441@qq.com
 * Describe：我的订单
 */
public class OrderActivity extends BaseActivity implements MyOrderFragment.OnStateChangeListener {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.tv_header_next)
    TextView tvHeaderNext;
    @BindView(R.id.tab_my_order)
    SlidingTabLayout tabMyOrder;
    @BindView(R.id.vp_my_order)
    ViewPager vpMyOrder;

    //当前页
    int currentPage;
    private List<String> titleList;
    private List<Fragment> fragmentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        ButterKnife.bind(this);

        getData();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }


    private void getData() {
        currentPage = getIntent().getIntExtra("page", 0);
    }


    /**
     * 加载数据
     */
    private void initData() {
        tvHeaderTitle.setText("我的订单");
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("待付款");
        titleList.add("待发货");
        titleList.add("已发货");
//        titleList.add("售后");
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(MyOrderFragment.newInstance(i));
        }
        initView();
    }

    /**
     * 加载视图
     */
    private void initView() {
        if (titleList.size() <= 5) {
            tabMyOrder.setTabSpaceEqual(true);
        } else {
            tabMyOrder.setTabSpaceEqual(false);
        }
        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getSupportFragmentManager(),
                fragmentList, titleList);
        vpMyOrder.setOffscreenPageLimit(titleList.size());
        vpMyOrder.setAdapter(adapter);
        tabMyOrder.setViewPager(vpMyOrder);
        tabMyOrder.setCurrentTab(currentPage);
        tabMyOrder.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentPage = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }


    @OnClick(R.id.img_header_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onStateChange(int page) {
        currentPage = page;
        initData();
    }
}
