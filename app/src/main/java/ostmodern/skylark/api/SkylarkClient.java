package ostmodern.skylark.api;

import io.reactivex.Observable;
import ostmodern.skylark.model.Image;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface SkylarkClient {

    @GET("/api/sets/")
    Observable<SetApiResponse> getSetList();

    @GET
    Observable<Image> getImageOfSet(@Url String imagePath);

}
