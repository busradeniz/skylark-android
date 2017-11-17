package ostmodern.skylark.repository.remote;

import android.util.Pair;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;


public class SkylarkService {

    private static final String DEFAULT_IMAGE_URL = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/static/images/dummy/dummy-film.jpg";
    private static final Image DEFAULT_IMAGE = new Image("", DEFAULT_IMAGE_URL);

    private final SkylarkClient skylarkClient;

    public SkylarkService(SkylarkClient skylarkClient) {
        this.skylarkClient = skylarkClient;
    }

    /**
     * Fetches the latest sets.
     *
     * @return {@link Observable} list of set and image pair
     */
    public Observable<List<Pair<Set, Image>>> getSetList() {
        return skylarkClient.getSetList()
                .map(setApiResponse -> setApiResponse.getObjects())
                .flatMap(Observable::fromIterable)
                .flatMap(set -> getSetImagePair(set))
                .toList()
                .toObservable();
    }

    private Observable<Pair<Set, Image>> getSetImagePair(Set set) {
        if (set.getImageUrls() != null && !set.getImageUrls().isEmpty()) {
            return getImageOfSet(set.getImageUrls().get(0))
                    .map(image -> Pair.create(set, image))
                    .toObservable();

        } else {
            return Observable.just(Pair.create(set, DEFAULT_IMAGE));
        }
    }

    public Single<Image> getImageOfSet(String imagePath) {
        return skylarkClient.getImageOfSet(imagePath);
    }
}
