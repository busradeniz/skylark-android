package ostmodern.skylark.ui.detail;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetDetailPresenter implements SetDetailContract.Presenter {

    private static final int RETRY_TIMES = 5;


    private final SkylarkRepository skylarkRepository;
    private final SetDetailContract.View view;
    private final SchedulerProvider schedulerProvider;

    private final List<Disposable> disposables;
    private String selectedSetId;
    private SetUI selectedSet;

    private PublishSubject<SetUI> setUIPublishSubject = PublishSubject.create();

    /**
     * Constructor for {@link SetDetailPresenter}.
     *
     * @param skylarkRepository repository instance for data communication
     * @param view              interface for view communication
     * @param schedulerProvider provides schedulers for different use.
     */
    public SetDetailPresenter(SkylarkRepository skylarkRepository, SetDetailContract.View view,
                              SchedulerProvider schedulerProvider) {
        this.skylarkRepository = skylarkRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.disposables = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        Timber.i("SetDetailPresenter subscribed.");
        subscribeToSkylarkService();
        subscribeUpdateFavorStatus();
    }

    @Override
    public void unsubscribe() {
        Timber.i("SetDetailPresenter unsubscribed");
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    @Override
    public void setSelectedSetId(String selectedSetId) {
        this.selectedSetId = selectedSetId;
    }

    @Override
    public void onFavouriteClicked() {
        selectedSet.setFavourite(!selectedSet.isFavourite());
        view.updateFavouriteView(selectedSet.isFavourite());
        setUIPublishSubject.onNext(selectedSet);
    }

    private void subscribeToSkylarkService() {
        skylarkRepository.getSetById(selectedSetId)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .subscribe(setUI -> {
                    this.selectedSet = setUI;
                    view.showSetDetails(setUI);
                }, throwable -> Timber.e(throwable,
                        "Cannot get set from repository. Error Message: %s",
                        throwable.getMessage()));
    }

    private void subscribeUpdateFavorStatus() {
        disposables.add(setUIPublishSubject
                .retry(RETRY_TIMES)
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getIoScheduler())
                .subscribe(setUI -> updateFavouriteStatus(), throwable ->
                        Timber.e(throwable, "Unable to update favorite status!")));
    }

    private void updateFavouriteStatus() {
        if (selectedSet.isFavourite()) {
            skylarkRepository.favorite(selectedSet.getSetEntity().uid);
        } else {
            skylarkRepository.unfavorite(selectedSet.getSetEntity().uid);
        }
    }

}
