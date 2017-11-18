package ostmodern.skylark.ui;

import android.support.annotation.NonNull;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.repository.local.SetEntity;
import ostmodern.skylark.util.SchedulerProvider;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SetListPresenterTest {

    @Mock
    private SkylarkRepository skylarkRepository;

    @Mock
    private SetListContract.View view;

    private SetListPresenter setListPresenter; // SUT

    @Before
    public void setUp() throws Exception {
        setListPresenter = new SetListPresenter(skylarkRepository, view,
                new SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()));
    }

    @Test
    public void testSubscribe() throws Exception {
        // Given
        given(view.getFavouriteObservable()).willReturn(Observable.empty());
        given(skylarkRepository.getSets()).willReturn(Flowable.empty());

        // When
        setListPresenter.subscribe();

        // Then
        assertThat(setListPresenter.getDisposables(), hasSize(2));
        then(view).should().getFavouriteObservable();
        then(skylarkRepository).should().getSets();
    }

    @Test
    public void testUnsubscribe() throws Exception {
        // Given
        Disposable disposable = mock(Disposable.class);
        given(disposable.isDisposed()).willReturn(false);
        setListPresenter.getDisposables().add(disposable);

        // When
        setListPresenter.unsubscribe();

        // Then
        assertThat(setListPresenter.getDisposables(), hasSize(0));
        then(disposable).should().isDisposed();
        then(disposable).should().dispose();
    }

    @Test
    public void testSetListsUpdateShouldTriggerViewUpdates() throws Exception {
        // Given
        List<SetUI> setUIs = ImmutableList.of(new SetUI(dummySetEntity("test-uid"), false));
        given(skylarkRepository.getSets()).willReturn(Flowable.just(setUIs));
        given(view.getFavouriteObservable()).willReturn(Observable.empty());

        // When
        setListPresenter.subscribe();

        // Then
        assertThat(setListPresenter.getDisposables(), hasSize(2));
        then(view).should().getFavouriteObservable();
        then(skylarkRepository).should().getSets();
        then(view).should().showSetList(setUIs);
    }

    @Test
    public void testFavoriteEventsShouldTriggerRepositoryUpdate() throws Exception {
        // Given
        String uid = "test-uid";
        SetUI setUI = new SetUI(dummySetEntity(uid), true); // Should trigger favorite
        given(view.getFavouriteObservable()).willReturn(Observable.just(setUI));
        given(skylarkRepository.getSets()).willReturn(Flowable.empty());

        // When
        setListPresenter.subscribe();

        // Then
        assertThat(setListPresenter.getDisposables(), hasSize(2));
        then(view).should().getFavouriteObservable();
        then(skylarkRepository).should().getSets();
        then(skylarkRepository).should().favorite(uid);
    }

    @Test
    public void testUnfavoriteEventsShouldTriggerRepositoryUpdate() throws Exception {
        // Given
        String uid = "test-uid";
        SetUI setUI = new SetUI(dummySetEntity(uid), false); // Should trigger unfavorite
        given(view.getFavouriteObservable()).willReturn(Observable.just(setUI));
        given(skylarkRepository.getSets()).willReturn(Flowable.empty());

        // When
        setListPresenter.subscribe();

        // Then
        assertThat(setListPresenter.getDisposables(), hasSize(2));
        then(view).should().getFavouriteObservable();
        then(skylarkRepository).should().getSets();
        then(skylarkRepository).should().unfavorite(uid);
    }

    @NonNull
    private SetEntity dummySetEntity(String uid) {
        return new SetEntity(uid, "test-title", 5, "test-body", "test-formatted-body", "test-summary", "test-image-url");
    }
}