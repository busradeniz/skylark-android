package ostmodern.skylark.ui.detail;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.repository.local.SetEntity;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SetDetailPresenterTest {

    private static final String SET_ID = "test-set-id";

    @Mock
    private SkylarkRepository skylarkRepository;

    @Mock
    private SetDetailContract.View view;

    @Mock
    private NetworkStatusProvider networkStatusProvider;

    private SetDetailPresenter setDetailPresenter;

    @Before
    public void setUp() throws Exception {
        setDetailPresenter = new SetDetailPresenter(skylarkRepository, view,
                new SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline()),
                networkStatusProvider);
        setDetailPresenter.setSelectedSetId(SET_ID);
    }

    @Test
    public void testSubscribe() throws Exception {
        // Given
        given(networkStatusProvider.isConnected()).willReturn(Observable.empty());
        given(skylarkRepository.getSetById(SET_ID)).willReturn(Maybe.empty());

        // When
        setDetailPresenter.subscribe();

        // Then
        assertThat(setDetailPresenter.getDisposables(), hasSize(3));
        then(networkStatusProvider).should().isConnected();
        then(skylarkRepository).should().getSetById(SET_ID);
    }

    @Test
    public void testUnsubscribe() throws Exception {
        // Given
        Disposable disposable = mock(Disposable.class);
        given(disposable.isDisposed()).willReturn(false);
        setDetailPresenter.getDisposables().add(disposable);

        // When
        setDetailPresenter.unsubscribe();

        // Then
        assertThat(setDetailPresenter.getDisposables(), hasSize(0));
        then(disposable).should().isDisposed();
        then(disposable).should().dispose();
    }

    @Test
    public void detailPresenterShouldReadSetUIForInitialSetId() throws Exception {
        // Given
        SetUI setUI = new SetUI(dummySetEntity(SET_ID), false);
        given(networkStatusProvider.isConnected()).willReturn(Observable.empty());
        given(skylarkRepository.getSetById(SET_ID)).willReturn(Maybe.just(setUI));

        // When
        setDetailPresenter.subscribe();

        // Then
        assertThat(setDetailPresenter.getSelectedSet(), equalTo(setUI));
        then(networkStatusProvider).should().isConnected();
        then(skylarkRepository).should().getSetById(SET_ID);
    }

    @Test
    public void detailPresenterShouldBeAbleToUpdateFavoriteSet() throws Exception {
        // Given
        SetUI setUI = new SetUI(dummySetEntity(SET_ID), false);
        given(networkStatusProvider.isConnected()).willReturn(Observable.empty());
        given(skylarkRepository.getSetById(SET_ID)).willReturn(Maybe.empty());
        setDetailPresenter.setSelectedSet(setUI);
        setDetailPresenter.subscribe();

        // When
        setDetailPresenter.onFavouriteClicked();

        // Then
        then(networkStatusProvider).should().isConnected();
        then(skylarkRepository).should().getSetById(SET_ID);
        then(skylarkRepository).should().favorite(SET_ID);
    }

    @Test
    public void detailPresenterShouldBeAbleToUpdateUnfavoriteSet() throws Exception {
        // Given
        SetUI setUI = new SetUI(dummySetEntity(SET_ID), true);
        given(networkStatusProvider.isConnected()).willReturn(Observable.empty());
        given(skylarkRepository.getSetById(SET_ID)).willReturn(Maybe.empty());
        setDetailPresenter.setSelectedSet(setUI);
        setDetailPresenter.subscribe();

        // When
        setDetailPresenter.onFavouriteClicked();

        // Then
        then(networkStatusProvider).should().isConnected();
        then(skylarkRepository).should().getSetById(SET_ID);
        then(skylarkRepository).should().unfavorite(SET_ID);
    }

    @NonNull
    private SetEntity dummySetEntity(String uid) {
        return new SetEntity(uid, "test-title", 5, "test-formatted-body", "test-image-url");
    }
}