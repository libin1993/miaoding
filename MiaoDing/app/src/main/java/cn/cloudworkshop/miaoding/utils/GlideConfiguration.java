package cn.cloudworkshop.miaoding.utils;


import android.Manifest;
import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

import okhttp3.Cache;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author：Libin on 2016/11/9 15:08
 * Email：1993911441@qq.com
 * Describe：Glide图片清晰度,缓存
 */

public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        if (EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "CloudWorkshop/GlideCache");
                    return DiskLruCacheWrapper.get(file, 1024 * 1024 * 100);
                }
            });
        }

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}