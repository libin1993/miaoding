package cn.cloudworkshop.miaoding.ui;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.service.DownloadService;
import cn.cloudworkshop.miaoding.utils.ToastUtils;


/**
 * Author：Libin on 2016/10/26 16:36
 * Email：1993911441@qq.com
 * Describe：关于我们
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_new_version)
    TextView tvNewVersion;
    @BindView(R.id.ll_check_update)
    LinearLayout llCheckUpdate;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    private DownloadService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initView();

    }

    /**
     * 加载视图
     */
    private void initView() {
        tvHeaderTitle.setText("关于妙定");
        tvVersionName.setText("当前版本：" + getVersionName());
        if (!TextUtils.isEmpty(MyApplication.updateUrl)) {
            tvNewVersion.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.img_header_back, R.id.ll_check_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.ll_check_update:
                checkUpdate();
                break;
        }
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        if (!TextUtils.isEmpty(MyApplication.updateUrl)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(AboutUsActivity.this,
                    R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            dialog.setTitle("检测到新版本，请更新");
            dialog.setMessage(MyApplication.updateContent);
            //为“确定”按钮注册监听事件
            dialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadFile();
                }
            });

            //为“取消”按钮注册监听事件
            dialog.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create();
            dialog.show();
        } else {
            ToastUtils.showToast(this, "已是最新版本！");
        }

    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(MyApplication.updateUrl));
        request.setTitle("妙定");
        request.setDescription("正在下载");
        // 设置下载可见
        request.setVisibleInDownloadsUi(true);
        //下载完成后通知栏可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //当前网络状态
        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (mConnectivityManager != null) {
            networkInfo = mConnectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            //允许手机流量下载
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("MOBILE")) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            }
        }
        // 设置下载位置
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "CloudWorkshop", "miaoding.apk");
        if (file.exists()) {
            file.delete();
        }
        request.setDestinationUri(Uri.fromFile(file));
        service = new DownloadService(file);
        registerReceiver(service, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (manager != null) {
            manager.enqueue(request);
        }
    }

    /**
     * 获取版本名称
     */
    private String getVersionName() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi != null ? pi.versionName : null;
    }


    @Override
    protected void onDestroy() {
        if (service != null) {
            unregisterReceiver(service);
        }
        super.onDestroy();
    }

}

