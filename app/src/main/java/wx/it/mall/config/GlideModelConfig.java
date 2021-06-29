package wx.it.mall.config;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

public class GlideModelConfig implements GlideModule{
    //定义能够使用的磁盘空间大小
    int diskSize=1024*1024*10;
    // 设置可以使用的内存大小，这里设置为设备内存的1/8
    int memorySize=(int)(Runtime.getRuntime().maxMemory())/8;
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置可用磁盘大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,diskSize));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,"cache",diskSize));
        // 设置可用内存大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
       //设置图片池大小
        builder.setBitmapPool(new LruBitmapPool(memorySize));
        //设置图片格式，使其更加清晰
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
