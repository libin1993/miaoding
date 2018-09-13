package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.TitleBarUtils;

/**
 * Author：Libin on 2016/11/9 09:33
 * Email：1993911441@qq.com
 * Describe：启动页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.vp_splash_page)
    ViewPager vpSplash;
    @BindView(R.id.rgs_splash)
    RadioGroup rgsSplash;
    @BindView(R.id.rl_splash_page)
    RelativeLayout rlSplash;
    @BindView(R.id.img_splash)
    ImageView imgSplash;
    @BindView(R.id.iv_start_app)
    ImageView ivStart;
    
    //引导图当前页
    private int currentItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtils.setNoTitleBar(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        boolean isFirstIn = SharedPreferencesUtils.getBoolean(this, "first_install", true);
        if (isFirstIn) {
            rlSplash.setVisibility(View.VISIBLE);
            SharedPreferencesUtils.saveBoolean(this, "first_install", false);
            final int[] imgArr;
            DisplayMetrics metrics = DisplayUtils.getMetrics(this);
            if (metrics.heightPixels / metrics.widthPixels > 1.778) {
                imgArr = new int[]{R.mipmap.img_large_splash1, R.mipmap.img_large_splash2, R.mipmap.img_large_splash3};
            } else {
                imgArr = new int[]{R.mipmap.img_normal_splash1, R.mipmap.img_normal_splash2, R.mipmap.img_normal_splash3};
            }

            vpSplash.setOffscreenPageLimit(imgArr.length);
            vpSplash.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return imgArr.length;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    View view = LayoutInflater.from(SplashActivity.this)
                            .inflate(R.layout.vp_splash_item, null);
                    ImageView imgGuide = (ImageView) view.findViewById(R.id.iv_splash_item);
                    imgGuide.setImageResource(imgArr[position]);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
            vpSplash.setCurrentItem(currentItem);

            for (int i = 0; i < imgArr.length; i++) {
                RadioButton radioButton = new RadioButton(this);

                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(30, 30);
                layoutParams.setMargins(15, 0, 15, 0);
                radioButton.setLayoutParams(layoutParams);
                radioButton.setButtonDrawable(null);
                radioButton.setClickable(false);
                radioButton.setBackgroundResource(R.drawable.vp_indicator_splash);
                rgsSplash.addView(radioButton);
            }
            ((RadioButton) rgsSplash.getChildAt(currentItem)).setChecked(true);
            vpSplash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ((RadioButton) rgsSplash.getChildAt(position)).setChecked(true);
                    currentItem = position;
                    if (currentItem < imgArr.length - 1) {
                        ivStart.setVisibility(View.GONE);
                    } else {
                        ivStart.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } else {
            imgSplash.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2500);
        }
    }

    @OnClick(R.id.iv_start_app)
    public void onViewClicked() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}

