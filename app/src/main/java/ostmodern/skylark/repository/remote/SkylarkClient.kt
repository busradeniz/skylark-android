package ostmodern.skylark.repository.remote

import io.reactivex.Observable
import retrofit2.http.GET


interface SkylarkClient {

    @get:GET("/api/sets/")
    val setList: Observable<SetApiResponse>

    @get:GET("/api/images/")
    val imageList: Observable<ImageApiResponse>
}
