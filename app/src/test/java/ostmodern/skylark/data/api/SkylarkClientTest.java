package ostmodern.skylark.data.api;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.reactivex.BackpressureStrategy;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.OkHttpClient;
import ostmodern.skylark.di.AppModule;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.repository.remote.SetApiResponse;
import ostmodern.skylark.repository.remote.SkylarkClient;

import static ostmodern.skylark.data.api.SkylarkApiMockService.MOCK_IMAGE_1;
import static ostmodern.skylark.data.api.SkylarkApiMockService.SET_WITHOUT_IMAGE;
import static ostmodern.skylark.data.api.SkylarkApiMockService.SET_WITH_IMAGE;

public class SkylarkClientTest {

    @Rule
    public SkylarkApiMockService skylarkApiMockService = new SkylarkApiMockService();


    private SkylarkClient skylarkClient; // SUT

    @Before
    public void setUp() throws Exception {
        AppModule appModule = new AppModule();
        Gson gson = appModule.gson();
        OkHttpClient httpClient = appModule.okHttpClient();
        skylarkClient = appModule.provideSkylarkClient(httpClient, gson, skylarkApiMockService.getBaseUrl());
    }

    @Test
    public void testGetSetList() throws Exception {
        // Given
        // No initial state beside mock service!

        // When
        TestSubscriber<SetApiResponse> result = skylarkClient.getSetList()
                .toFlowable(BackpressureStrategy.BUFFER)
                .test();

        // Then
        result.assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(new SetApiResponse(ImmutableList.of(SET_WITHOUT_IMAGE, SET_WITH_IMAGE)));
    }

    @Test
    public void testGetImageOfSet() throws Exception {
        // Given
        final String imageUrl = "/api/images/mock-image-1/";

        // When
        TestSubscriber<Image> result = skylarkClient.getImageOfSet(imageUrl)
                .toFlowable()
                .test();

        // Then
        result.assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(MOCK_IMAGE_1);
    }
}
