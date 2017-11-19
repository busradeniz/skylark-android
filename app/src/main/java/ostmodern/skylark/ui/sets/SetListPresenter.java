package ostmodern.skylark.ui.sets;


import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetListPresenter implements SetListContract.Presenter {

    private static final int RETRY_TIMES = 5;
    private final SkylarkRepository skylarkRepository;
    private final SetListContract.View view;
    private final SchedulerProvider schedulerProvider;

    private final List<Disposable> disposables;

    /**
     * Constructor for {@link SetListPresenter}.
     *
     * @param skylarkRepository repository instance for data communication
     * @param view              interface for view communication
     * @param schedulerProvider provides schedulers for different use.
     */
    public SetListPresenter(SkylarkRepository skylarkRepository, SetListContract.View view,
                            SchedulerProvider schedulerProvider) {
        this.skylarkRepository = skylarkRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.disposables = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        Timber.i("SetListPresenter subscribed.");
        subscribeToSkylarkService();
        subscribeUpdateFavorStatus();

    }

    private void subscribeUpdateFavorStatus() {
        disposables.add(view.getFavouriteObservable()
                .retry(RETRY_TIMES)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getIoScheduler())
                .subscribe(this::updateFavouriteStatus, throwable ->
                        Timber.e(throwable, "Unable to update favorite status!")));
    }

    @Override
    public void unsubscribe() {
        Timber.i("SetListPresenter unsubscribed");
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    private void subscribeToSkylarkService() {
        disposables.add(skylarkRepository.getSets()
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .subscribe(view::showSetList, throwable -> Timber.e(throwable,
                        "Cannot get set list from repository. Error Message: %s",
                        throwable.getMessage())));
    }

    private void updateFavouriteStatus(SetUI setUI) {
        if (setUI.isFavourite()) {
            skylarkRepository.favorite(setUI.getSetEntity().uid);
        } else {
            skylarkRepository.unfavorite(setUI.getSetEntity().uid);
        }
    }

    @VisibleForTesting
    List<Disposable> getDisposables() {
        return disposables;
    }
}
