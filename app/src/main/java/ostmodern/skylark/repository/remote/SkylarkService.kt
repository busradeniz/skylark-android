package ostmodern.skylark.repository.remote

import android.support.annotation.NonNull
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ostmodern.skylark.model.Image
import ostmodern.skylark.model.Set
import ostmodern.skylark.util.RetryPolicy
import ostmodern.skylark.util.RetryPolicy.DEFAULT_RETRY_TIMES
import android.support.v4.util.Pair


class SkylarkService(val skylarkClient: SkylarkClient) {

    companion object {
        private const val DEFAULT_IMAGE_URL: String = "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/static/images/dummy/dummy-film.jpg"
        private val DEFAULT_IMAGE: Image = Image("", DEFAULT_IMAGE_URL, "")
    }

    fun getSetList(): Observable<List<Pair<Set, Image>>> {
        return Observable.zip(skylarkClient.setList.retry(DEFAULT_RETRY_TIMES, RetryPolicy::isRetryableException),
                skylarkClient.imageList.retry(DEFAULT_RETRY_TIMES, RetryPolicy::isRetryableException),
                BiFunction { setApiResponse, imageApiResponse ->
                    mergeResponses(setApiResponse, imageApiResponse)
                })
    }

    @NonNull
    private fun mergeResponses(setApiResponse: SetApiResponse, imageApiResponse: ImageApiResponse):
            List<Pair<Set, Image>> {

        val images: HashMap<String, Image> = extractToImageMap(imageApiResponse);
        val setWithImageList: MutableList<Pair<Set, Image>> = ArrayList();

        for (set: Set in setApiResponse.objects) {
            if (set.imageUrls.isEmpty()) {
                setWithImageList.add(Pair(set, DEFAULT_IMAGE))
            } else {
                setWithImageList.add(Pair(set, images.get(set.imageUrls[0])) as Pair<Set, Image>)
            }
        }
        return setWithImageList
    }


    private fun extractToImageMap(imageApiResponse: ImageApiResponse): HashMap<String, Image> {

        var imageMap: HashMap<String, Image> = HashMap()
        for (image: Image in imageApiResponse.objects) {
            imageMap.put(image.self, image)
        }

        return imageMap;
    }
}