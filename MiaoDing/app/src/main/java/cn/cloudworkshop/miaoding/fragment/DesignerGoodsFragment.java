package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
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
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.DesignerInfoBean;
import cn.cloudworkshop.miaoding.bean.NewDesignWorksBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.QuickLoginActivity;
import cn.cloudworkshop.miaoding.ui.WorksDetailActivity;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2017-06-08 15:52
 * Email：1993911441@qq.com
 * Describe：设计师作品
 */
public class DesignerGoodsFragment extends BaseFragment {
    @BindView(R.id.rv_designer_goods)
    RecyclerView rvWorks;
    @BindView(R.id.sv_no_works)
    NestedScrollView svNoWorks;

    private Unbinder unbinder;

    private List<DesignerInfoBean.DataBean.GoodsListBean> worksList;
    private CommonAdapter<DesignerInfoBean.DataBean.GoodsListBean> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initView();
        return view;
    }

    private void initView() {
        if (worksList != null && worksList.size() > 0) {
            rvWorks.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new CommonAdapter<DesignerInfoBean.DataBean.GoodsListBean>(getActivity(),
                    R.layout.listitem_designer_works, worksList) {
                @Override
                protected void convert(ViewHolder holder, final DesignerInfoBean.DataBean.GoodsListBean
                        goodsListBean, final int position) {

                    SimpleDraweeView imgWorks = holder.getView(R.id.img_works_designer);
                    if (!TextUtils.isEmpty(goodsListBean.getImg_info())) {
                        imgWorks.setAspectRatio(Float.parseFloat(goodsListBean.getImg_info()));
                    }
                    imgWorks.setImageURI(Constant.IMG_HOST + goodsListBean.getThumb());
                    holder.setText(R.id.tv_designer_works_name, goodsListBean.getName());

                    ImageView imgShare = (ImageView) holder.itemView.findViewById(R.id.img_designer_works_share);
                    ImageView imgCollect = (ImageView) holder.itemView.findViewById(R.id.img_designer_works_collect);
                    ImageView imgLove = (ImageView) holder.itemView.findViewById(R.id.img_designer_works_love);
                    TextView tvLove = (TextView) holder.itemView.findViewById(R.id.tv_works_love_num);


                    tvLove.setText(String.valueOf(goodsListBean.getLove_num()));


                    if (goodsListBean.getIs_collect() == 1) {
                        imgCollect.setImageResource(R.mipmap.icon_collect_check);
                    } else {
                        imgCollect.setImageResource(R.mipmap.icon_collect_normal);
                    }
                    if (goodsListBean.getIs_love() == 1) {
                        imgLove.setImageResource(R.mipmap.icon_love_check);
                    } else {
                        imgLove.setImageResource(R.mipmap.icon_love_normal);
                    }

                    imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String share_url = Constant.WORKS_SHARE + "?goods_id=" + goodsListBean.getId() + "&type=2";
                            String uid = SharedPreferencesUtils.getStr(getActivity(), "uid");
                            if (!TextUtils.isEmpty(uid)) {
                                share_url += "&shareout_id=" + uid;
                            }
                            ShareUtils.showShare(getActivity(), Constant.IMG_HOST +
                                            goodsListBean.getThumb(), goodsListBean.getName(),
                                    goodsListBean.getSub_name(), share_url);
                        }
                    });

                    imgCollect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                                addCollection(goodsListBean, position);
                            } else {
                                Intent login = new Intent(getActivity(), LoginActivity.class);
                                login.putExtra("page_name", "收藏");
                                startActivity(login);
                            }

                        }
                    });

                    imgLove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
                                addLove(goodsListBean, position, goodsListBean.getLove_num());
                            } else {
                                Intent login = new Intent(getActivity(), LoginActivity.class);
                                login.putExtra("page_name", "喜爱");
                                startActivity(login);
                            }

                        }
                    });

                }

            };
            rvWorks.setAdapter(adapter);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (!TextUtils.isEmpty(worksList.get(position).getId() + "")) {
                        Intent intent = new Intent(getActivity(), WorksDetailActivity.class);
                        intent.putExtra("id", String.valueOf(worksList.get(position).getId()));
                        startActivity(intent);
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        } else {
            svNoWorks.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 添加收藏
     *
     * @param dataBean
     */
    private void addCollection(DesignerInfoBean.DataBean.GoodsListBean dataBean, final int position) {
        OkHttpUtils.get()
                .url(Constant.ADD_COLLECTION)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("type", "2")
                .addParams("cid", String.valueOf(dataBean.getId()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if (code == 1) {
                                worksList.get(position).setIs_collect(1);
                            } else {
                                worksList.get(position).setIs_collect(0);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 添加喜爱
     */
    private void addLove(DesignerInfoBean.DataBean.GoodsListBean dataBean, final int position, final int loveNum) {
        OkHttpUtils.get()
                .url(Constant.ADD_LOVE)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .addParams("news_id", String.valueOf(dataBean.getId()))
                .addParams("is_type", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int data = jsonObject.getInt("data");
                            if (data == 1) {
                                worksList.get(position).setIs_love(1);
                                worksList.get(position).setLove_num(loveNum + 1);
                            } else {
                                worksList.get(position).setIs_love(0);
                                worksList.get(position).setLove_num(loveNum - 1);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getData() {
        Bundle bundle = getArguments();
        worksList = bundle.getParcelableArrayList("works_list");

    }

    public static DesignerGoodsFragment newInstance(List<DesignerInfoBean.DataBean.GoodsListBean> dataList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("works_list", (ArrayList<? extends Parcelable>) dataList);
        DesignerGoodsFragment fragment = new DesignerGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
