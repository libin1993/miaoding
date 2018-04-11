package cn.cloudworkshop.miaoding.service;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Author：Libin on 2017/2/27 09:36
 * Email：1993911441@qq.com
 * Describe：App更新
 */
public class DownloadService extends BroadcastReceiver {

    private File file;

    public DownloadService(File file) {
        this.file = file;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                contentUri = FileProvider.getUriForFile(context, "cn.cloudworkshop.miaoding.fileprovider", file);

                List<ResolveInfo> resInfoList = context.getPackageManager()
                        .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            } else {
                contentUri = Uri.fromFile(file);
            }
            install.setDataAndType(contentUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        }
    }
}
