package ostmodern.skylark.repository.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface SkylarkClient {

    @GET("/api/sets/")
    Observable<SetApiResponse> getSetList();

    @GET("/api/images/")
    Observable<ImageApiResponse> getImageList();
}
