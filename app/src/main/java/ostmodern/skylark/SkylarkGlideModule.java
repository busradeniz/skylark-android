package ostmodern.skylark;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.manager.DefaultConnectivityMonitorFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by busradeniz on 16/11/2017.
 */
@GlideModule
public class SkylarkGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // CHECKSTYLE.OFF: MagicNumber
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        // CHECKSTYLE.ON: MagicNumber
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        builder.setDefaultRequestOptions(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA));

        builder.setConnectivityMonitorFactory(new DefaultConnectivityMonitorFactory());
    }
}
