package cn.cloudworkshop.miaoding.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.BmpRecycleUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017/2/9 11:34
 * Email：1993911441@qq.com
 * Describe：用户规则，加载长图
 */
public class UserRuleActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_user_rule)
    ImageView imgUserRule;
    @BindView(R.id.img_user_rule1)
    ImageView imgUserRule1;
    @BindView(R.id.img_user_rule2)
    ImageView imgUserRule2;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private String title;
    private String imgUrl;
    private Bitmap bm0;
    private Bitmap bm1;
    private Bitmap bm2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rule);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        title = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("img_url");
    }

    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText(title);
        OkHttpUtils.get()
                .url(Constant.IMG_HOST + imgUrl)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        try {
                            if (response != null) {
                                InputStream inputStream = ImageEncodeUtils.bitmap2InputStream(response);
                                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, true);
                                int width = decoder.getWidth();
                                int height = decoder.getHeight();
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                Rect rect = new Rect();

                                rect.set(0, 0, width, height / 3);
                                bm0 = decoder.decodeRegion(rect, opts);
                                imgUserRule.setImageBitmap(bm0);

                                rect.set(0, height / 3, width, height / 3 * 2);
                                bm1 = decoder.decodeRegion(rect, opts);
                                imgUserRule1.setImageBitmap(bm1);

                                rect.set(0, height / 3 * 2, width, height);
                                bm2 = decoder.decodeRegion(rect, opts);
                                imgUserRule2.setImageBitmap(bm2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmpRecycleUtils.bmpRecycle(bm0);
        BmpRecycleUtils.bmpRecycle(bm1);
        BmpRecycleUtils.bmpRecycle(bm2);
    }

    @OnClick({R.id.img_header_back, R.id.img_load_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_load_error:
                initView();
                break;
        }
    }
}

