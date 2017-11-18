package ostmodern.skylark.util;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * Custom glide url for images.
 * ImageUrls have auto-generated parts. This custom url will ignore these auto-generated parts
 * for cache keys.
 */
public class CustomGlideUrl extends GlideUrl {

    /**
     * Constructor.
     *
     * @param url image url.
     */
    public CustomGlideUrl(String url) {
        super(url);
    }

    @Override
    public String getCacheKey() {
        // TODO: Implement a DiskCache with TTL.
        // Currently, glide does not support a TTL for disk caches.
        // But our urls has expire information for images. We can use these
        // expire info to create a DiskCache with decent TTL.
        return StringUtils.meaningfulPart(toStringUrl());
    }
}
