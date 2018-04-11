package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.GoodsListBean;
import cn.cloudworkshop.miaoding.bean.GoodsTitleBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.pagecurl.CurlView;
import cn.cloudworkshop.miaoding.pagecurl.ImagePageProvider;
import cn.cloudworkshop.miaoding.ui.CustomizedGoodsActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-04-17 09:22
 * Email：1993911441@qq.com
 * Describe：定制（老版）
 */
public class CustomizedGoodsFragment1 extends BaseFragment {
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.curl_page)
    CurlView curlPage;
    @BindView(R.id.img_goods_type)
    ImageView imgGoodsType;
    @BindView(R.id.view_loading)
    AVLoadingIndicatorView loadingView;

    private Unbinder unbinder;
    private int page = 1;

    private ArrayList<Bitmap> bitmapList = new ArrayList<>();
    private GoodsListBean listBean;
    private GoodsTitleBean titleBean;
    private GoodsTitleBean.DataBean currentGoods;
    private int currentItem = 0;
    private TreeMap<Integer, Bitmap> treeMap = new TreeMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customize_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTitle();
        return view;
    }

    /**
     * 加载商品种类
     */
    private void initTitle() {
        loadingView.setIndicator(new BallSpinFadeLoaderIndicator());
        loadingView.setIndicatorColor(Color.GRAY);
        OkHttpUtils
                .get()
                .url(Constant.GOODS_TITLE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        titleBean = GsonUtils.jsonToBean(response, GoodsTitleBean.class);
                        if (titleBean.getData() != null) {
                            currentGoods = titleBean.getData().get(0);
                            initGoods();
                        }
                    }
                });
    }


    /**
     * 加载商品
     */
    public void initGoods() {
        OkHttpUtils.get()
                .url(Constant.GOODS_LIST)
                .addParams("type", "1")
                .addParams("classify_id", currentGoods.getId() + "")
                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        listBean = GsonUtils.jsonToBean(response, GoodsListBean.class);
                        if (listBean.getData().getData() != null && listBean.getData().getData().size() > 0) {
                            bitmapList.clear();
                            treeMap.clear();
                            loadingView.smoothToShow();
                            for (int i = 0; i < listBean.getData().getData().size(); i++) {
                                initBitmap(Constant.IMG_HOST + listBean.getData().getData().get(i).getThumb(), i);
                            }
                        } else {
                            imgGoodsType.setEnabled(true);
                        }
                    }
                });

    }

    /**
     * @param s 网络获取图片存为bitmap
     */
    private void initBitmap(String s, final int i) {
        OkHttpUtils.get()
                .url(s)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        treeMap.put(i, response);
                        if (treeMap.size() == listBean.getData().getData().size()) {
                            Iterator title = treeMap.entrySet().iterator();
                            while (title.hasNext()) {
                                Map.Entry entry = (Map.Entry) title.next();
                                Bitmap bitmap = (Bitmap) entry.getValue();
                                //控件宽高
                                int width = curlPage.getWidth();
                                int height = curlPage.getHeight() + DisplayUtils.getNavigationBarHeight(getActivity());
                                //等比列缩放，铺满控件
                                float sx = (float) width / bitmap.getWidth();
                                float sy = (float) height / bitmap.getHeight();
                                Matrix matrix = new Matrix();
                                float s = Math.max(sx, sy);
                                matrix.postScale(s, s);
                                if (!bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
                                    Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                            bitmap.getHeight(), matrix, true);
                                    //截取中间部分，居中显示
                                    Bitmap newBitmap = Bitmap.createBitmap(resizeBmp,
                                            (resizeBmp.getWidth() - width) / 2,
                                            (resizeBmp.getHeight() - height) / 2, width, height);
                                    bitmapList.add(newBitmap);
                                    if (!bitmap.isRecycled() && !resizeBmp.isRecycled()) {
                                        bitmap.recycle();
                                        resizeBmp.recycle();
                                    }
                                }
                            }
                            loadingView.smoothToHide();
                            imgGoodsType.setEnabled(true);
                            initView();
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {
        tvGoodsTitle.setText(currentGoods.getName());

//        curlPage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ImagePageProvider imagePageProvider = new ImagePageProvider(curlPage.getWidth(),
                curlPage.getHeight() + DisplayUtils.getNavigationBarHeight(getActivity()));
        imagePageProvider.setBitmaps(bitmapList);
        curlPage.setPageProvider(imagePageProvider);

        curlPage.setAllowLastPageCurl(false);
        curlPage.setCurrentIndex(0);
        curlPage.setOnPageChangeListener(new CurlView.OnPageChangeListener() {
            @Override
            public void currentPos(final int position) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (position == bitmapList.size() - 1) {
                            ToastUtils.showToast(getActivity(), "已经是最后一页了");
                        }
                        if (position == 0) {
                            ToastUtils.showToast(getActivity(), "已经是第一页了");
                        }
                    }
                });
            }
        });
        curlPage.setOnPageClickListener(new CurlView.OnPageClickListener() {
            @Override
            public void currentItem(int position) {
                Intent intent = new Intent(getActivity(), CustomizedGoodsActivity.class);
                intent.putExtra("id", String.valueOf(listBean.getData().getData().get(position).getId()));
                startActivity(intent);
            }
        });

    }

    public static CustomizedGoodsFragment1 newInstance() {
        Bundle args = new Bundle();
        CustomizedGoodsFragment1 fragment = new CustomizedGoodsFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_goods_type)
    public void onViewClicked() {
        if (titleBean != null) {
            showPopWindow();
        }
    }

    /**
     * 选择种类
     */
    private void showPopWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.ppw_goods_type, null);
        final PopupWindow mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(imgGoodsType);

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.rv_goods_type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonAdapter<GoodsTitleBean.DataBean> adapter = new CommonAdapter<GoodsTitleBean.DataBean>
                (getActivity(), R.layout.listitem_goods_type, titleBean.getData()) {
            @Override
            protected void convert(ViewHolder holder, GoodsTitleBean.DataBean dataBean, int position) {
                TextView tvGoods = holder.getView(R.id.tv_goods_type);
                tvGoods.setText(dataBean.getName());
                if (currentItem == position) {
                    tvGoods.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tvGoods.setTypeface(Typeface.DEFAULT);
                }
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                currentGoods = titleBean.getData().get(position);
                currentItem = position;
                imgGoodsType.setEnabled(false);
                initGoods();
                mPopupWindow.dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

}

