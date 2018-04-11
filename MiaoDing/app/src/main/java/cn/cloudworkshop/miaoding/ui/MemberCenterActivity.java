package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.bean.MemberBean;
import cn.cloudworkshop.miaoding.bean.MemberTabBean;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DisplayUtils;
import cn.cloudworkshop.miaoding.utils.GsonUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import cn.cloudworkshop.miaoding.utils.ToastUtils;
import cn.cloudworkshop.miaoding.view.CircleImageView;
import okhttp3.Call;

/**
 * Author：Libin on 2017/2/14 16:56
 * Email：1993911441@qq.com
 * Describe：会员中心
 */
public class MemberCenterActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;
    @BindView(R.id.img_member_icon)
    CircleImageView imgMemberHead;
    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.progress_member_grade)
    ProgressBar progressMember;
    @BindView(R.id.tv_member_score)
    TextView tvMemberScore;
    @BindView(R.id.tv_member_total)
    TextView tvMemberTotal;
    @BindView(R.id.tv_check_member)
    TextView tvCheckMember;
    @BindView(R.id.img_grade_icon)
    ImageView imgGradeIcon;
    @BindView(R.id.tab_member_grade)
    CommonTabLayout tabMemberGrade;
    @BindView(R.id.vp_member_rights)
    ViewPager vpMemberRights;
    @BindView(R.id.img_load_error)
    ImageView imgLoadError;
    private MemberBean memberBean;
    private List<List<MemberBean.DataBean.UserPrivilegeBean>> dataList = new ArrayList<>();
    private CommonAdapter<MemberBean.DataBean.UserPrivilegeBean> adapter;
    //会员成长值
    private int credit;
    //当前会员等级
    private int currentTab;
    //当前礼包
    private int currentGift;
    //生日
    private String birthday;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        ButterKnife.bind(this);
        tvHeaderTitle.setText("会员俱乐部");
        imgHeaderShare.setVisibility(View.VISIBLE);
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        OkHttpUtils.get()
                .url(Constant.MEMBER_GRADE)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        imgLoadError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        imgLoadError.setVisibility(View.GONE);
                        memberBean = GsonUtils.jsonToBean(response, MemberBean.class);
                        if (memberBean.getData() != null) {
                            birthday = memberBean.getData().getUser_info().getBirthday();
                            initView();
                        }
                    }
                });
    }


    /**
     * 加载视图
     */
    private void initView() {
        Glide.with(getApplicationContext())
                .load(memberBean.getData().getUser_info().getAvatar())
                .placeholder(R.mipmap.place_holder_goods)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgMemberHead);
        tvMemberName.setText(memberBean.getData().getUser_info().getName());
        Glide.with(getApplicationContext())
                .load(Constant.IMG_HOST + memberBean.getData().getUser_info().getUser_grade().getImg2())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGradeIcon);

        credit = (int) Float.parseFloat(memberBean.getData().getUser_info().getCredit());

        float maxCredit = Float.parseFloat(memberBean.getData().getUser_info().getUser_grade().getMax_credit());
        progressMember.setProgress((int) (credit / maxCredit * 100));
        tvMemberScore.setText(String.valueOf(credit));
        tvMemberTotal.setText("/" + (int) maxCredit);

        ArrayList<CustomTabEntity> tabList = new ArrayList<>();
        for (int i = 0; i < memberBean.getData().getUser_grade().size(); i++) {
            tabList.add(new MemberTabBean(memberBean.getData().getUser_grade().get(i).getName() + "权益"));
        }

        tabMemberGrade.setTabData(tabList);
        currentTab = memberBean.getData().getUser_info().getUser_grade().getId() - 1;

        initTab();

        vpMemberRights.setOffscreenPageLimit(dataList.size());
        vpMemberRights.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int pos) {
                View view = LayoutInflater.from(MemberCenterActivity.this).inflate(
                        R.layout.viewpager_item_member, null);
                RecyclerView rvMemberRights = (RecyclerView) view.findViewById(R.id.rv_member_rights);
                rvMemberRights.setLayoutManager(new GridLayoutManager(MemberCenterActivity.this, 3));
                adapter = new CommonAdapter<MemberBean.DataBean.UserPrivilegeBean>
                        (MemberCenterActivity.this, R.layout.listitem_member_rights, dataList.get(pos)) {
                    @Override
                    protected void convert(ViewHolder holder, MemberBean.DataBean.UserPrivilegeBean
                            userPrivilegeBean, int position) {
                        holder.setText(R.id.tv_rights_name, userPrivilegeBean.getName());
                        Glide.with(MemberCenterActivity.this)
                                .load(Constant.IMG_HOST + userPrivilegeBean.getImg())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into((ImageView) holder.getView(R.id.img_rights_icon));
                    }
                };
                rvMemberRights.setAdapter(adapter);

                adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        currentGift = position;
                        showGift(pos, position);
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
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

        tabMemberGrade.setCurrentTab(currentTab);
        vpMemberRights.setCurrentItem(currentTab);

        tabMemberGrade.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentTab = position;
                vpMemberRights.setCurrentItem(currentTab);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        vpMemberRights.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTab = position;
                tabMemberGrade.setCurrentTab(currentTab);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * @param grade    等级
     * @param position 礼包
     *                 礼包详情
     */
    private void showGift(final int grade, final int position) {
        View popupView = getLayoutInflater().inflate(R.layout.ppw_receive_gift, null);
        mPopupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }

        DisplayUtils.setBackgroundAlpha(this, true);

        TextView tvTitle = (TextView) popupView.findViewById(R.id.tv_gift_title);
        CircleImageView imgGift = (CircleImageView) popupView.findViewById(R.id.img_gift);
        final TextView tvInfo = (TextView) popupView.findViewById(R.id.tv_gift_info);
        final TextView tvReceive = (TextView) popupView.findViewById(R.id.tv_receive_gift);

        Glide.with(this)
                .load(Constant.IMG_HOST + dataList.get(grade).get(position).getImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGift);

        tvTitle.setText(dataList.get(grade).get(position).getName());
        switch (dataList.get(grade).get(position).getIs_get()) {
            case 1:
                tvInfo.setText(dataList.get(grade).get(position).getDesc());
                tvReceive.setText("关闭");
                tvReceive.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
                break;
            case 2:
                if (!TextUtils.isEmpty(birthday)) {
                    tvInfo.setText(dataList.get(grade).get(position).getDesc());
                    tvReceive.setText("领取");
                    tvReceive.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
                } else {
                    tvInfo.setText("·完善生日资料后，过生日时可获惊喜生日礼包一份\r\n·礼包领取有效期：生日前三天及后四天");
                    tvReceive.setText("去设置");
                    tvReceive.setTextColor(ContextCompat.getColor(this, R.color.dark_red));
                }
                break;
            case 3:
                tvInfo.setText(dataList.get(grade).get(position).getDesc());
                tvReceive.setText("领取");
                tvReceive.setTextColor(ContextCompat.getColor(this, R.color.dark_gray_22));
                break;
        }

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayUtils.setBackgroundAlpha(MemberCenterActivity.this, false);
            }
        });

        tvReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dataList.get(grade).get(position).getIs_get()) {
                    case 1:
                        mPopupWindow.dismiss();
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(birthday)) {
                            isGetGift(Constant.BIRTHDAY_GIFT);
                        } else {
                            Intent intent = new Intent(MemberCenterActivity.this, SetUpActivity.class);
                            intent.putExtra("set_birthday", true);
                            mPopupWindow.dismiss();
                            startActivityForResult(intent, 1);
                        }
                        break;
                    case 3:
                        isGetGift(Constant.UPGRADE_GIFT);
                        break;
                }
            }
        });
    }

    /**
     * 加载tab数据
     */
    private void initTab() {
        for (int i = 0; i < memberBean.getData().getUser_grade().size(); i++) {
            List<MemberBean.DataBean.UserPrivilegeBean> itemList = new ArrayList<>();
            String[] idStr = memberBean.getData().getUser_grade().get(i).getUser_privilege_ids().split(",");
            for (int j = 0; j < memberBean.getData().getUser_privilege().size(); j++) {
                for (String anIdStr : idStr) {
                    if (anIdStr.equals(String.valueOf(memberBean.getData().getUser_privilege()
                            .get(j).getId()))) {
                        itemList.add(memberBean.getData().getUser_privilege().get(j));
                        break;
                    }
                }
            }
            dataList.add(itemList);
        }
    }

    @OnClick({R.id.img_header_back, R.id.img_header_share, R.id.tv_check_member, R.id.img_load_error})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.img_header_share:
                startActivity(new Intent(this, MemberRuleActivity.class));
                break;
            case R.id.tv_check_member:
                Intent intent = new Intent(this, MemberGrowthActivity.class);
                intent.putExtra("value", String.valueOf(credit));
                startActivity(intent);
                break;
            case R.id.img_load_error:
                initData();
                break;
        }
    }


    /**
     * 是否达到领取要求
     */
    private void isGetGift(String url) {
        String privilegeIds = memberBean.getData().getUser_grade().get(memberBean.getData()
                .getUser_info().getUser_grade().getId() - 1).getUser_privilege_ids();
        String[] split = privilegeIds.split(",");
        if (Arrays.asList(split).contains(dataList.get(currentTab).get(currentGift).getId() + "")) {
            submitData(url);
        } else {
            ToastUtils.showToast(this, "您的会员等级未达到领取要求");
            mPopupWindow.dismiss();
        }
    }

    /**
     * @param url 领取礼包
     */
    private void submitData(String url) {
        OkHttpUtils.post()
                .url(url)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            ToastUtils.showToast(MemberCenterActivity.this, msg);
                            mPopupWindow.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            birthday = data.getStringExtra("birthday");
        }
    }


}
