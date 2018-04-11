package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.adapter.MyPagerAdapter;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.PopDesignerBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.DesignerDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.FadePageTransformer;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/9/25 16:09
 * Email：1993911441@qq.com
 * Describe：腔调设计师（当前版）
 */
public class DesignerFragment extends BaseFragment {
    @BindView(R.id.vp_designers)
    ViewPager vpDesigner;
    @BindView(R.id.rgs_designer)
    RadioGroup rgsDesigner;
    private Unbinder unbinder;
    private PopDesignerBean designerBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.DESIGNER_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        designerBean = GsonUtils.jsonToBean(response, PopDesignerBean.class);
                        if (designerBean.getData() != null) {
                            initView();
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {

        ViewGroup.LayoutParams layoutParams1 = vpDesigner.getLayoutParams();
        int widthPixels = DisplayUtils.getMetrics(getParentFragment().getActivity()).widthPixels;
        layoutParams1.width = (int) ((widthPixels - DisplayUtils.dp2px(getActivity(), 50)));
        layoutParams1.height = layoutParams1.width * 365 / 280;
        vpDesigner.setLayoutParams(layoutParams1);

        vpDesigner.setOffscreenPageLimit(designerBean.getData().size());
        vpDesigner.setPageTransformer(false, new FadePageTransformer());

        MyPagerAdapter adapter = new MyPagerAdapter(designerBean.getData(), getParentFragment().getActivity());
        vpDesigner.setAdapter(adapter);
        vpDesigner.setCurrentItem(0);

        for (int i = 0; i < designerBean.getData().size(); i++) {
            RadioButton radioButton = new RadioButton(getParentFragment().getActivity());

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(18, 18);
            layoutParams.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(null);
            radioButton.setClickable(false);
            radioButton.setBackgroundResource(R.drawable.vp_indicator);
            rgsDesigner.addView(radioButton);
        }
        ((RadioButton) rgsDesigner.getChildAt(0)).setChecked(true);

        //手指左右滑动不超过48px,上下滑动不超过250px

        vpDesigner.setOnTouchListener(new View.OnTouchListener() {
            boolean isClick = true;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isClick = true;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ViewConfiguration configuration = ViewConfiguration.get(getActivity());
                        int mTouchSlop = configuration.getScaledPagingTouchSlop();


                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        isClick = xDiff < mTouchSlop && yDiff < 250;
                        break;

                    case MotionEvent.ACTION_UP:

                        if (isClick) {
                            int currentItem = vpDesigner.getCurrentItem();
                            if (designerBean.getData().get(currentItem).getId() != 0 &&
                                    currentItem < designerBean.getData().size() - 1) {
                                Intent intent = new Intent(getParentFragment().getActivity(),
                                        DesignerDetailActivity.class);
                                intent.putExtra("id", String.valueOf(designerBean.getData()
                                        .get(currentItem).getId()));
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(getParentFragment().getActivity(), "敬请期待");
                            }
                        }
                        break;

                }
                return false;
            }
        });



        vpDesigner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgsDesigner.getChildAt(position)).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static DesignerFragment newInstance() {
        Bundle args = new Bundle();
        DesignerFragment fragment = new DesignerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
