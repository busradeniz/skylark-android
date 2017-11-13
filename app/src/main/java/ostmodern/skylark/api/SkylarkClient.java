package ostmodern.skylark.api;

import java.util.List;

import io.reactivex.Observable;
import ostmodern.skylark.model.Set;
import retrofit2.http.GET;


public interface SkylarkClient {

    @GET("api/sets")
    Observable<List<Set>> getSetList();



}
