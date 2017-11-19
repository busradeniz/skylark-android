package ostmodern.skylark.ui.sets;

import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.ui.common.BaseView;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.RetryPolicy;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetListPresenter extends SetListContract.Presenter {

    private final SkylarkRepository skylarkRepository;
    private final SetListContract.View view;

    /**
     * Constructor for {@link SetListPresenter}.
     *
     * @param skylarkRepository repository instance for data communication
     * @param view              interface for view communication
     * @param schedulerProvider provides schedulers for different use.
     */
    public SetListPresenter(SkylarkRepository skylarkRepository,
                            SetListContract.View view,
                            SchedulerProvider schedulerProvider,
                            NetworkStatusProvider networkStatusProvider) {
        super(schedulerProvider, networkStatusProvider);
        this.skylarkRepository = skylarkRepository;
        this.view = view;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Timber.i("SetListPresenter subscribed.");
        subscribeToSkylarkService();
        subscribeUpdateFavorStatus();

    }

    @Override
    protected BaseView getContractView() {
        return view;
    }

    private void subscribeUpdateFavorStatus() {
        addDisposable(view.getFavouriteObservable()
                .retry(RetryPolicy.DEFAULT_RETRY_TIMES)
                .subscribeOn(getSchedulerProvider().getIoScheduler())
                .observeOn(getSchedulerProvider().getIoScheduler())
                .subscribe(this::updateFavouriteStatus, throwable ->
                        Timber.e(throwable, "Unable to update favorite status!")));
    }

    private void subscribeToSkylarkService() {
        addDisposable(skylarkRepository.getSets()
                .retry(RetryPolicy.DEFAULT_RETRY_TIMES)
                .subscribeOn(getSchedulerProvider().getIoScheduler())
                .observeOn(getSchedulerProvider().getUiScheduler())
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
}
