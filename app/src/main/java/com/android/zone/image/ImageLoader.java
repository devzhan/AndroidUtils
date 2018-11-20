package com.android.zone.image;

import android.content.Context;
import android.widget.ImageView;

import com.android.zone.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;


/**
 * @author  yong.wu on 2015/12/8.
 */
public class ImageLoader {
//    public static final int noScaleType = 0;
//    public static final int fitCenter = 1;
//    public static final int centerCrop = 2;

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    final LruResourceCache memoryCache;
    final LruBitmapPool bitmapPool;
    private ImageLoader() {
        LogUtil.i("GlideGlobalConfig", "max heap size: %dMB, max cache size: %dMB",
                MAX_HEAP_SIZE >> 20,
                MAX_MEMORY_CACHE_SIZE >> 20);


        memoryCache = new InnerMemoryCache(MAX_MEMORY_CACHE_SIZE);
        bitmapPool = new LruBitmapPool(ImageLoader.MAX_MEMORY_CACHE_SIZE);
    }
    private static class InnerMemoryCache extends LruResourceCache {

        /**
         * Constructor for LruResourceCache.
         *
         * @param size The maximum size in bytes the in memory cache can use.
         */
        InnerMemoryCache(int size) {
            super(size);
        }

        @Override
        protected void onItemEvicted(Key key, Resource<?> item) {
            Object o = item.get();
            int size = item.getSize();
            int current = getCurrentSize();
            LogUtil.i("GlideGlobalConfig", "onResourceRemoved %s, size: %dMB, cache: %dMB",
                    o, size >> 20, current >> 20);
            super.onItemEvicted(key, item);
        }
    }
    private static class Holder {
        private static ImageLoader _instance = new ImageLoader();
    }

    public static ImageLoader getInstance() {
        return Holder._instance;
    }
    //默认加载
    public  void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }

//    //加载指定大小
//    public  void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
//        Glide.with(mContext).load(path).override(width, height).into(mImageView);
//    }

    //设置加载中以及加载失败图片
    public  void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }



    public  void loadImageViewLodingCornersTransform(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).transform(new CornersTransform(mContext)).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

//    public  void loadImageViewLodingCornersTransform(Context mContext, String path, CornersTransform cornersTransform ,ImageView mImageView, int lodingImage, int errorImageView) {
//        Glide.with(mContext).load(path).transform(cornersTransform).placeholder(lodingImage).error(errorImageView).into(mImageView);
//    }
//    //设置加载中以及加载失败图片并且指定大小
//    public  void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
//        Glide.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
//    }
//
//    //设置跳过内存缓存
//    public  void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
//    }
//
//    //设置下载优先级
//    public  void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
//    }

//    /**
//     * 策略解说：
//     * <p>
//     * all:缓存源资源和转换后的资源
//     * <p>
//     * none:不作任何磁盘缓存
//     * <p>
//     * source:缓存源资源
//     * <p>
//     * result：缓存转换后的资源
//     */
//
//    //设置缓存策略
//    public  void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
//    }
//
//    /**
//     * api也提供了几个常用的动画：比如crossFade()
//     */
//
//    //设置加载动画
//    public  void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
//        Glide.with(mContext).load(path).animate(anim).into(mImageView);
//    }
//
//    /**
//     * 会先加载缩略图
//     */
//
//    //设置缩略图支持
//    public  void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
//    }
//
//    /**
//     * api提供了比如：centerCrop()、fitCenter()等
//     */
//
//    //设置动态转换
//    public  void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).centerCrop().into(mImageView);
//    }
//
//    //设置动态GIF加载方式
//    public  void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).asGif().into(mImageView);
//    }
//
//    //设置静态GIF加载方式
//    public  void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
//        Glide.with(mContext).load(path).asBitmap().into(mImageView);
//    }
//
//    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘
//
//    //设置监听请求接口
//    public  void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
//        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
//    }
//
//    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排
//
//    //设置要加载的内容
//    public  void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
//        Glide.with(mContext).load(path).centerCrop().into(simpleTarget);
//    }
//
//    //清理磁盘缓存
//    public  void GuideClearDiskCache(Context mContext) {
//        //理磁盘缓存 需要在子线程中执行
//        Glide.get(mContext).clearDiskCache();
//    }
//
//    //清理内存缓存
//    public  void GuideClearMemory(Context mContext) {
//        //清理内存缓存  可以在UI主线程中进行
//        Glide.get(mContext).clearMemory();
//    }


}
