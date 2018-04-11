package cn.cloudworkshop.miaoding.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ImageEncodeUtils;
import cn.cloudworkshop.miaoding.utils.MemoryCleanUtils;
import cn.cloudworkshop.miaoding.view.SwipeBackActivity;
import okhttp3.Call;

/**
 * Author：Libin on 2017-09-18 09:42
 * Email：1993911441@qq.com
 * Describe：商品详情
 */
public class GoodsDetailActivity extends SwipeBackActivity {
    @BindView(R.id.img_goods1)
    ImageView imgGoods1;
    @BindView(R.id.img_goods2)
    ImageView imgGoods2;
    @BindView(R.id.img_goods3)
    ImageView imgGoods3;
    @BindView(R.id.img_back_goods)
    ImageView imgBackGoods;
    private String imgUrl;

    private Bitmap bm1, bm2, bm3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        getData();
        initView();


    }

    private void getData() {
        imgUrl = getIntent().getStringExtra("img");
    }

    private void initView() {
        //详情页图片尺寸超过手机支持最大尺寸
        //分割图片显示

        OkHttpUtils.get()
                .url(Constant.IMG_HOST + imgUrl)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        try {
                            if (response != null) {
                                InputStream inputStream = ImageEncodeUtils.bitmap2InputStream(response);
                                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, true);
                                int width = decoder.getWidth();
                                int height = decoder.getHeight();
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                Rect rect = new Rect();

                                rect.set(0, 0, width, height / 3);
                                bm1 = decoder.decodeRegion(rect, opts);
                                imgGoods1.setImageBitmap(bm1);

                                rect.set(0, height / 3, width, height / 3 * 2);
                                bm2 = decoder.decodeRegion(rect, opts);
                                imgGoods2.setImageBitmap(bm2);

                                rect.set(0, height / 3 * 2, width, height);
                                bm3 = decoder.decodeRegion(rect, opts);
                                imgGoods3.setImageBitmap(bm3);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @OnClick(R.id.img_back_goods)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MemoryCleanUtils.bmpRecycle(bm1);
        MemoryCleanUtils.bmpRecycle(bm2);
        MemoryCleanUtils.bmpRecycle(bm3);
    }


}
