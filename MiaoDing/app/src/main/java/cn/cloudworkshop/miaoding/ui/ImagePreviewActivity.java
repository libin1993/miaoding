package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.TitleBarUtils;
import cn.cloudworkshop.miaoding.view.PhotoViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author：Libin on 2017/2/7 11:54
 * Email：1993911441@qq.com
 * Describe：图片预览
 */
public class ImagePreviewActivity extends BaseActivity {
    @BindView(R.id.vp_preview)
    PhotoViewPager vpPreview;
    @BindView(R.id.rgs_indicator)
    RadioGroup rgsIndicator;
    //图片路径
    private ArrayList<String> imgList;
    //当期页面
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarUtils.setNoTitleBar(this);
        setContentView(R.layout.activity_image_preview);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    /**
     * 加载视图
     */
    private void initView() {
        vpPreview.setOffscreenPageLimit(imgList.size());
        vpPreview.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = LayoutInflater.from(ImagePreviewActivity.this)
                        .inflate(R.layout.viewpager_item_preview, null);
                final PhotoView photoView = (PhotoView) view.findViewById(R.id.img_preview);
                Glide.with(ImagePreviewActivity.this)
                        .load(Constant.IMG_HOST + imgList.get(position))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(photoView);
                PhotoViewAttacher mAttach = new PhotoViewAttacher(photoView);
                mAttach.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        finish();
                    }
                });

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        vpPreview.setCurrentItem(currentItem);

        for (int i = 0; i < imgList.size(); i++) {
            RadioButton radioButton = new RadioButton(this);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(18, 18);
            layoutParams.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(null);
            radioButton.setClickable(false);
            radioButton.setBackgroundResource(R.drawable.viewpager_indicator);
            rgsIndicator.addView(radioButton);
        }
        ((RadioButton) rgsIndicator.getChildAt(currentItem)).setChecked(true);
        vpPreview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgsIndicator.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 获取数据
     */
    private void getData() {
        Intent intent = getIntent();
        currentItem = intent.getIntExtra("current_pos", 0);
        imgList = intent.getStringArrayListExtra("img_list");
    }

    @OnClick(R.id.rgs_indicator)
    public void onViewClicked() {
    }
}
