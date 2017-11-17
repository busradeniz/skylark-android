package ostmodern.skylark.repository.remote;

import io.reactivex.Observable;
import io.reactivex.Single;
import ostmodern.skylark.model.Image;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface SkylarkClient {

    @GET("/api/sets/")
    Observable<SetApiResponse> getSetList();

    @GET
    Single<Image> getImageOfSet(@Url String imagePath);

}
