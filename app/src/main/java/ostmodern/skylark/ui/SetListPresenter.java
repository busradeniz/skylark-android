package ostmodern.skylark.ui;


import io.reactivex.disposables.Disposable;
import ostmodern.skylark.api.SkylarkService;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetListPresenter implements SetListContract.Presenter {

    private final SkylarkService skylarkService;
    private final SetListContract.View view;
    private final SchedulerProvider schedulerProvider;

    private Disposable setListDisposable;

    public SetListPresenter(SkylarkService skylarkService, SetListContract.View view,
                            SchedulerProvider schedulerProvider) {
        this.skylarkService = skylarkService;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
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
        setListDisposable = skylarkService.getSetList()
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .doOnError(throwable -> {
                    Timber.e("setListDisposable::doOnError called : " + throwable.getMessage());
                })
                .subscribe(sets -> {
                    view.showSetList(sets);
                });
    }
}
