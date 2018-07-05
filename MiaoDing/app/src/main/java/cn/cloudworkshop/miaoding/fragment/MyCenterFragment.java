package cn.cloudworkshop.miaoding.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseFragment;
import cn.cloudworkshop.miaoding.bean.UserInfoBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.ui.ApplyMeasureActivity;
import cn.cloudworkshop.miaoding.ui.AppointmentActivity;
import cn.cloudworkshop.miaoding.ui.CameraFormActivity;
import cn.cloudworkshop.miaoding.ui.CollectionActivity;
import cn.cloudworkshop.miaoding.ui.DressingResultActivity;
import cn.cloudworkshop.miaoding.ui.GiftCardActivity;
import cn.cloudworkshop.miaoding.ui.LoginActivity;
import cn.cloudworkshop.miaoding.ui.MemberCenterActivity;
import cn.cloudworkshop.miaoding.ui.MessageCenterActivity;
import cn.cloudworkshop.miaoding.ui.OrderActivity;
import cn.cloudworkshop.miaoding.ui.SetUpActivity;
import cn.cloudworkshop.miaoding.ui.ShoppingCartActivity;
import cn.cloudworkshop.miaoding.ui.UsableCouponActivity;
import cn.cloudworkshop.miaoding.utils.ContactService;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.MyGridLayoutManager;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.view.BadgeView;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2016/8/8 12:30
 * Email：1993911441@qq.com
 * Describe：个人中心
 */
public class MyCenterFragment extends BaseFragment {

    @BindView(R.id.img_user_icon)
    CircleImageView imgIcon;
    @BindView(R.id.tv_center_name)
    TextView tvCenterName;
    @BindView(R.id.img_center_message)
    ImageView imgMessage;
    @BindView(R.id.rl_user_center)
    RelativeLayout rlCenterUser;
    @BindView(R.id.rv_center)
    RecyclerView rvCenter;
    @BindView(R.id.img_center_set)
    ImageView imgCenterSet;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;

    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.img_grade_center)
    ImageView imgGradeCenter;


    private Unbinder unbinder;
    //未读消息提醒
    BadgeView badgeView;

    private UserInfoBean userInfoBean;
    //邀请好友点击事件处理
    private boolean isClickable = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycenter, container, false);
        unbinder = ButterKnife.bind(this, view);
        badgeView = new BadgeView(getActivity());
        toLogin();
        return view;
    }

    /**
     * 去登录
     */
    private void toLogin() {
        if (TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("log_in", "center");
            intent.putExtra("page_name", "我的");
            startActivity(intent);
        }
    }

    /**
     * 刷新页面
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getStr(getActivity(), "token"))) {
            rlLogout.setVisibility(View.GONE);
            initData();
        } else {
            rlLogout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param hidden Fragment显示隐藏监听
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            toLogin();
        }
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.USER_INFO)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setBackgroundColor(Color.WHITE);
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        userInfoBean = GsonUtils.jsonToBean(response, UserInfoBean.class);
                        if (userInfoBean.getData() != null && userInfoBean.getIcon_list() != null
                                && userInfoBean.getIcon_list().size() > 0) {
                            initView();
                        }
                    }
                });
    }

    /**
     * 加载视图
     */
    protected void initView() {

        Glide.with(getActivity())
                .load(userInfoBean.getData().getAvatar())
                .placeholder(R.mipmap.place_holder_goods)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgIcon);
        tvCenterName.setText(userInfoBean.getData().getName());

        Glide.with(getActivity())
                .load(Constant.IMG_HOST + userInfoBean.getData().getUser_grade().getImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGradeCenter);

        if (userInfoBean.getData().getUnread_message_num() > 0) {
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setTargetView(imgMessage);
            badgeView.setBackgroundResource(R.drawable.btn_red_bg);
            badgeView.setTextSize(8);
            if (userInfoBean.getData().getUnread_message_num() < 99) {
                badgeView.setBadgeCount(userInfoBean.getData().getUnread_message_num());
            } else {
                badgeView.setText("99+");
            }
        } else {
            badgeView.setVisibility(View.GONE);
        }

        MyGridLayoutManager gridManager = new MyGridLayoutManager(getActivity(), 3);
        gridManager.setScrollEnabled(false);
        rvCenter.setLayoutManager(gridManager);
        int widthPixels = DisplayUtils.getMetrics(getActivity()).widthPixels;
        final int itemWidth = (int) ((widthPixels - DisplayUtils.dp2px(getActivity(), 2)) / 3);
        final int itemHeight = itemWidth * 344 / 373;
        final CommonAdapter<UserInfoBean.IconListBean> adapter = new CommonAdapter<UserInfoBean.IconListBean>
                (getActivity(), R.layout.listitem_center, userInfoBean.getIcon_list()) {
            @Override
            protected void convert(ViewHolder holder, UserInfoBean.IconListBean iconListBean, int position) {
                RelativeLayout rlItem = holder.getView(R.id.rl_center_item);
                ViewGroup.LayoutParams layoutParams = rlItem.getLayoutParams();
                layoutParams.width = itemWidth;
                layoutParams.height = itemHeight;
                rlItem.setLayoutParams(layoutParams);

                Glide.with(getActivity())
                        .load(Constant.IMG_HOST + iconListBean.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into((ImageView) holder.getView(R.id.img_center_item));
                holder.setText(R.id.tv_center_item, iconListBean.getName());
                if (position == 8) {
                    holder.setVisible(R.id.img_invite_surprise, true);
                }
            }
        };
        rvCenter.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), OrderActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), UsableCouponActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), CollectionActivity.class));
                        break;
                    case 4:
                        if (userInfoBean.getData().getIs_yuyue() == 1) {
                            Intent intent = new Intent(getActivity(), AppointmentActivity.class);
                            intent.putExtra("type", "appoint_measure");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), ApplyMeasureActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), GiftCardActivity.class));
                        break;
                    case 6:
                        Intent intent = new Intent(getActivity(), CameraFormActivity.class);
                        intent.putExtra("measure_status", userInfoBean.getIs_opencv());
                        startActivity(intent);
                        break;
                    case 7:
                        ContactService.contactService(getActivity());
                        break;
                    case 8:
                        if (isClickable) {
                            isClickable = false;
                            inviteFriends();
                        }

                        break;

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    @OnClick({R.id.img_center_set, R.id.img_center_message, R.id.rl_user_center, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_center_set:
                startActivity(new Intent(getActivity(), SetUpActivity.class));
                break;
            case R.id.img_center_message:
                startActivity(new Intent(getActivity(), MessageCenterActivity.class));
                break;
            case R.id.rl_user_center:
                startActivity(new Intent(getActivity(), MemberCenterActivity.class));
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }

    /**
     * 邀请好友
     */
    private void inviteFriends() {
        OkHttpUtils.get()
                .url(Constant.INVITE_FRIEND)
                .addParams("token", SharedPreferencesUtils.getStr(getActivity(), "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        isClickable = true;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            isClickable = true;
                            JSONObject jsonObject = new JSONObject(response);
                            String url = jsonObject.getString("share_url");
                            JSONObject data = jsonObject.getJSONObject("data");
                            int uid = data.getInt("up_uid");

                            if (uid != 0) {
                                Intent intent = new Intent(getActivity(), DressingResultActivity.class);
                                intent.putExtra("title", "邀请有礼");
                                intent.putExtra("share_title", "Hi，新朋友，妙定为您准备了1000元见面礼");
                                intent.putExtra("share_content", userInfoBean.getData()
                                        .getName() + "邀请您一起做腔调绅士，来吧，用1000元见面礼定制一套体面的行头");
                                intent.putExtra("url", Constant.HOST + url + uid);
                                intent.putExtra("share_url", Constant.INVITE_SHARE + "?id=" + uid);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    public static MyCenterFragment newInstance() {
        Bundle args = new Bundle();
        MyCenterFragment fragment = new MyCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
