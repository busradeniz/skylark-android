package ostmodern.skylark.data.api;


import android.support.v4.util.Pair;

import com.google.common.collect.ImmutableList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.repository.remote.ImageApiResponse;
import ostmodern.skylark.repository.remote.SetApiResponse;
import ostmodern.skylark.repository.remote.SkylarkClient;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static ostmodern.skylark.data.api.SkylarkApiMockService.DEFAULT_IMAGE;
import static ostmodern.skylark.data.api.SkylarkApiMockService.MOCK_IMAGE_1;
import static ostmodern.skylark.data.api.SkylarkApiMockService.SET_WITHOUT_IMAGE;
import static ostmodern.skylark.data.api.SkylarkApiMockService.SET_WITH_IMAGE;

@RunWith(MockitoJUnitRunner.class)
public class SkylarkServiceTest {

    @Mock
    SkylarkClient skylarkClient;

    @InjectMocks
    SkylarkService skylarkService;

    @Test
    public void testGetSetList() throws Exception {
        // Given
        given(skylarkClient.getSetList())
                .willReturn(Observable.just(new SetApiResponse(ImmutableList.of(SET_WITHOUT_IMAGE, SET_WITH_IMAGE))));
        given(skylarkClient.getImageList())
                .willReturn(Observable.just(new ImageApiResponse(ImmutableList.of(MOCK_IMAGE_1))));

        // When
        TestSubscriber<List<Pair<Set, Image>>> response = skylarkService.getSetList()
                .toFlowable(BackpressureStrategy.BUFFER)
                .test();

        // Then
        response.assertNoErrors()
                .assertComplete()
                .assertValue(ImmutableList.of(
                    Pair.create(SET_WITHOUT_IMAGE, DEFAULT_IMAGE), Pair.create(SET_WITH_IMAGE, MOCK_IMAGE_1)
                ));
        then(skylarkClient).should().getSetList();
        then(skylarkClient).should().getImageList();
    }
}
