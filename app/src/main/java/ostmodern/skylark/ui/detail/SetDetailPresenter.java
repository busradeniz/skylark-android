package ostmodern.skylark.ui.detail;

import io.reactivex.subjects.PublishSubject;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.ui.common.BaseView;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetDetailPresenter extends SetDetailContract.Presenter {

    private static final int RETRY_TIMES = 5;


    private final SkylarkRepository skylarkRepository;
    private final SetDetailContract.View view;

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
    public SetDetailPresenter(SkylarkRepository skylarkRepository,
                              SetDetailContract.View view,
                              SchedulerProvider schedulerProvider,
                              NetworkStatusProvider networkStatusProvider) {
        super(schedulerProvider, networkStatusProvider);
        this.skylarkRepository = skylarkRepository;
        this.view = view;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Timber.i("SetDetailPresenter subscribed.");
        subscribeToSkylarkService();
        subscribeUpdateFavorStatus();
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
                .subscribeOn(getSchedulerProvider().getIoScheduler())
                .observeOn(getSchedulerProvider().getUiScheduler())
                .subscribe(setUI -> {
                    this.selectedSet = setUI;
                    view.showSetDetails(setUI);
                }, throwable -> Timber.e(throwable,
                        "Cannot get set from repository. Error Message: %s",
                        throwable.getMessage()));
    }

    private void subscribeUpdateFavorStatus() {
        addDisposable(setUIPublishSubject
                .retry(RETRY_TIMES)
                .subscribeOn(getSchedulerProvider().getIoScheduler())
                .observeOn(getSchedulerProvider().getIoScheduler())
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

    @Override
    public BaseView getContractView() {
        return view;
    }


}
