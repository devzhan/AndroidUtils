//package com.android.zone.image;
//
//import android.content.Context;
//import android.os.Environment;
//
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
//import com.bumptech.glide.module.GlideModule;
//
//import java.io.File;
//
///**
// * @author  yong.wu on 2016/3/4.
// */
//public class GlideGlobalConfig implements GlideModule {
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
//                "SD_ROOT" ;
//        builder.setDiskCache(new DiskLruCacheFactory(path, "image", Integer.MAX_VALUE));
//        builder.setMemoryCache(ImageLoader.getInstance().memoryCache);
//        builder.setBitmapPool(ImageLoader.getInstance().bitmapPool);
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//    }
//}
