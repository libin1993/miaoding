package cn.cloudworkshop.miaoding.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

/**
 * Author：Libin on 2016/7/7 16:58
 * Email：1993911441@qq.com
 * Describe：Fragment基类
 */
public abstract class BaseFragment extends Fragment {
    private BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MobclickAgent.onPageStart("fragment");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("fragment");
    }

}
