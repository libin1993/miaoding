package cn.cloudworkshop.miaoding.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.GoodsFragmentAdapter;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.fragment.CollectionFragment;

/**
 * Author：Libin on 2017-07-18 17:29
 * Email：1993911441@qq.com
 * Describe：收藏
 */
public class CollectionActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tab_collection)
    SlidingTabLayout tabCollection;
    @BindView(R.id.vp_collection)
    ViewPager vpCollection;

    private List<String> titleList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        tvHeaderTitle.setText("我的收藏");
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("商品");
        titleList.add("资讯");
        titleList.add("定制店");

        fragmentList.add(CollectionFragment.newInstance(2));
        fragmentList.add(CollectionFragment.newInstance(1));
        fragmentList.add(CollectionFragment.newInstance(3));

        initView();

    }


    /**
     * 加载视图
     */
    private void initView() {
        tabCollection.setTabSpaceEqual(true);
        GoodsFragmentAdapter adapter = new GoodsFragmentAdapter(getSupportFragmentManager(),
                fragmentList, titleList);
        vpCollection.setOffscreenPageLimit(titleList.size());
        vpCollection.setAdapter(adapter);
        tabCollection.setViewPager(vpCollection);
        tabCollection.setCurrentTab(0);

    }


    @OnClick(R.id.img_header_back)
    public void onViewClicked() {
        finish();
    }
}
