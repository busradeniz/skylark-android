package ostmodern.skylark.repository.remote;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.util.RetryPolicy;

import static ostmodern.skylark.util.RetryPolicy.DEFAULT_RETRY_TIMES;

public class SkylarkService {

    private static final String DEFAULT_IMAGE_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/static/images/dummy/dummy-film.jpg";
    private static final Image DEFAULT_IMAGE = new Image("", DEFAULT_IMAGE_URL, "");

    private final SkylarkClient skylarkClient;

    public SkylarkService(SkylarkClient skylarkClient) {
        this.skylarkClient = skylarkClient;
    }

    /**
     * A new method to fetch sets with images.
     *
     * @return {@link Observable} list of set and image pair
     */
    public Observable<List<Pair<Set, Image>>> getSetList() {
        return Observable.zip(
                skylarkClient.getSetList().retry(DEFAULT_RETRY_TIMES,
                        RetryPolicy::isRetryableException),
                skylarkClient.getImageList().retry(DEFAULT_RETRY_TIMES,
                        RetryPolicy::isRetryableException),
                this::mergeResponses);

    }

    @NonNull
    private List<Pair<Set, Image>> mergeResponses(SetApiResponse setApiResponse,
                                                  ImageApiResponse imageApiResponse) {
        final Map<String, Image> images = extractToImageMap(imageApiResponse);
        final List<Pair<Set, Image>> setWithImageList = new ArrayList<>();
        for (Set set : setApiResponse.getObjects()) {
            if (set.getImageUrls() == null || set.getImageUrls().isEmpty()) {
                setWithImageList.add(Pair.create(set, DEFAULT_IMAGE));
            } else {
                setWithImageList.add(Pair.create(set,
                        images.get(set.getImageUrls().get(0))));
            }
        }
        return setWithImageList;
    }

    private Map<String, Image> extractToImageMap(ImageApiResponse imageApiResponse) {
        if (imageApiResponse == null
                || imageApiResponse.getObjects() == null
                || imageApiResponse.getObjects().isEmpty()) {
            return ImmutableMap.of();
        }

        Map<String, Image> images = new HashMap<>(imageApiResponse.getObjects().size());
        List<Image> objects = imageApiResponse.getObjects();
        for (Image image : objects) {
            images.put(image.getSelf(), image);
        }

        return images;
    }

    private boolean ioException(Throwable throwable) {
        return IOException.class.isAssignableFrom(throwable.getClass());
    }

}
