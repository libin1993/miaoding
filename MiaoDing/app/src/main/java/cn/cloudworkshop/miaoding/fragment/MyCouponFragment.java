package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.CouponBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.MainActivity;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyLinearLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/12/17 11:40
 * Email：1993911441@qq.com
 * Describe：优惠券子界面
 */
public class MyCouponFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @BindView(R.id.img_null_coupon)
    ImageView imgNullCoupon;

    //优惠券状态
    private int couponStatus;
    private List<CouponBean.DataBean> couponList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_coupon, container, false);
        unbinder = ButterKnife.bind(this, view);

        getData();
        initData();
        return view;
    }

    /**
     * 获取数据
     */
    private void initData() {

        OkHttpUtils.get()
                .url(Constant.MY_COUPON)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("status", String.valueOf(couponStatus))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        couponList = new ArrayList<>();
                        CouponBean couponBean = GsonUtils.jsonToBean(response, CouponBean.class);
                        if (couponBean.getData() != null && couponBean.getData().size() > 0) {
                            imgNullCoupon.setVisibility(View.GONE);
                            couponList.addAll(couponBean.getData());
                            initView();
                        } else {
                            imgNullCoupon.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    private void initView() {
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommonAdapter<CouponBean.DataBean> adapter = new CommonAdapter<CouponBean.DataBean>(
                getActivity(), R.layout.listitem_coupon, couponList) {
            @Override
            protected void convert(ViewHolder holder, CouponBean.DataBean dataBean, int position) {
                switch (couponStatus) {
                    case 2:
                        holder.getView(R.id.ll_coupon_bg).setBackgroundResource(R.mipmap.icon_coupon_used);
                        break;
                    case -1:
                        holder.getView(R.id.ll_coupon_bg).setBackgroundResource(R.mipmap.icon_coupon_overdue);
                        break;
                }


                TextView tvMoney = holder.getView(R.id.tv_coupon_money);
                tvMoney.setTypeface(DisplayUtils.setTextType(getActivity()));
                tvMoney.setText("¥" + (int) Float.parseFloat(dataBean.getMoney()));
                holder.setText(R.id.tv_coupon_range, dataBean.getTitle());
                holder.setText(R.id.tv_coupon_discount, dataBean.getSub_title());
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.validity_term)+"：")
                        .append(DateUtils.getDate("yyyy-MM-dd", dataBean.getS_time()))
                        .append(getString(R.string.to))
                        .append(DateUtils.getDate("yyyy-MM-dd", dataBean.getE_time()));
                holder.setText(R.id.tv_coupon_term, sb.toString());

            }
        };

        rvCoupon.setAdapter(adapter);

    }

    /**
     * 优惠券状态
     */
    private void getData() {
        Bundle bundle = getArguments();
        couponStatus = bundle.getInt("coupon");
    }

    public static MyCouponFragment newInstance(int currentPos) {
        Bundle args = new Bundle();
        args.putInt("coupon", currentPos);
        MyCouponFragment fragment = new MyCouponFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
