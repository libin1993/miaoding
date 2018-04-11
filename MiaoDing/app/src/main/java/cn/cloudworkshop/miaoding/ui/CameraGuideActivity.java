package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.GuideBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.TitleBarUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2018/4/4 13:08
 * Email：1993911441@qq.com
 * Describe：拍照引导
 */
public class CameraGuideActivity extends BaseActivity {
    @BindView(R.id.vp_camera_guide)
    ViewPager vpCameraGuide;
    @BindView(R.id.rgs_camera_guide)
    RadioGroup rgsCameraGuide;
    @BindView(R.id.img_camera_back)
    ImageView imgCameraBack;
    @BindView(R.id.img_next_guide)
    ImageView imgNextGuide;

    private GuideBean guideBean;

    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtils.setNoTitleBar(this);
        setContentView(R.layout.activity_camera_guide);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        OkHttpUtils.get()
                .url(Constant.GUIDE_IMG)
                .addParams("id", "7")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        guideBean = GsonUtils.jsonToBean(response, GuideBean.class);
                        if (guideBean.getData().getImg_urls() != null && guideBean.getData()
                                .getImg_urls().size() > 0) {
                            initView();
                        }
                    }
                });
    }

    private void initView() {
        vpCameraGuide.setOffscreenPageLimit(guideBean.getData().getImg_urls().size());
        vpCameraGuide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guideBean.getData().getImg_urls().size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = LayoutInflater.from(CameraGuideActivity.this)
                        .inflate(R.layout.viewpager_item_camera, null);
                ImageView imgGuide = (ImageView) view.findViewById(R.id.img_camera_guide);
                Glide.with(CameraGuideActivity.this)
                        .load(Constant.IMG_HOST + guideBean.getData().getImg_urls().get(position))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgGuide);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vpCameraGuide.setCurrentItem(currentItem);

        for (int i = 0; i < guideBean.getData().getImg_urls().size(); i++) {
            RadioButton radioButton = new RadioButton(this);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(18, 18);
            layoutParams.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(null);
            radioButton.setClickable(false);
            radioButton.setBackgroundResource(R.drawable.viewpager_indicator);
            rgsCameraGuide.addView(radioButton);
        }
        ((RadioButton) rgsCameraGuide.getChildAt(currentItem)).setChecked(true);
        vpCameraGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgsCameraGuide.getChildAt(position)).setChecked(true);
                currentItem = position;
                if (currentItem < guideBean.getData().getImg_urls().size() - 1) {
                    imgNextGuide.setImageResource(R.mipmap.camera_guide1);
                } else {
                    imgNextGuide.setImageResource(R.mipmap.camera_guide2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.img_camera_back, R.id.img_next_guide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_camera_back:
                finish();
                break;
            case R.id.img_next_guide:
                if (guideBean.getData() != null && guideBean.getData().getImg_urls() != null) {
                    if (currentItem < guideBean.getData().getImg_urls().size() - 1) {
                        currentItem++;
                        vpCameraGuide.setCurrentItem(currentItem);
                    } else {
                        Intent intent = new Intent(this, NewCameraActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        if (bundle != null){
                            intent.putExtras(bundle);
                        }
                        startActivity(intent);
                        finish();
                    }
                }

                break;
        }
    }
}
