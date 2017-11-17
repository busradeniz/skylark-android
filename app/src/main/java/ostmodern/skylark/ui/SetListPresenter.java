package ostmodern.skylark.ui;


import io.reactivex.disposables.Disposable;
import ostmodern.skylark.repository.SkylarkRepository;
import ostmodern.skylark.repository.local.SkylarkDatabase;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetListPresenter implements SetListContract.Presenter {

    private final SkylarkRepository skylarkRepository;
    private final SetListContract.View view;
    private final SchedulerProvider schedulerProvider;
    private final SkylarkDatabase skylarkDatabase;

    private Disposable setListDisposable;

    /**
     * Constructor for {@link SetListPresenter}.
     *
     * @param skylarkRepository repository instance for data communication
     * @param view              interface for view communication
     * @param schedulerProvider provides schedulers for RX.
     */
    public SetListPresenter(SkylarkRepository skylarkRepository, SetListContract.View view,
                            SchedulerProvider schedulerProvider, SkylarkDatabase skylarkDatabase) {
        this.skylarkRepository = skylarkRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.skylarkDatabase = skylarkDatabase;
    }

    @Override
    public void subscribe() {
        Timber.i("subscribe called");
        subscribeToSkylarkService();

    }

    @Override
    public void unsubscribe() {
        Timber.i("unsubscribe called");

        if (setListDisposable != null && !setListDisposable.isDisposed()) {
            setListDisposable.dispose();
            Timber.i("setListDisposable disposed!");

        }
    }

    private void subscribeToSkylarkService() {

        setListDisposable = skylarkRepository.getSets()
                .doOnError(throwable -> {
                    Timber.e(throwable, "setListDisposable::doOnError called : %s", throwable.getMessage());
                })
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .subscribe(view::showSetList);
    }

}
