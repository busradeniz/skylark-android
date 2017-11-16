package ostmodern.skylark.api;

import java.util.List;

import io.reactivex.Observable;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;


public class SkylarkService {

    private final SkylarkClient skylarkClient;

    public SkylarkService(SkylarkClient skylarkClient) {
        this.skylarkClient = skylarkClient;
    }

    public Observable<List<Set>> getSetList() {
        return skylarkClient.getSetList()
                .map(setApiResponse -> setApiResponse.getObjects());
    }

    public Observable<Image> getImageOfSet(String imagePath) {
        return skylarkClient.getImageOfSet(imagePath);
    }
}
