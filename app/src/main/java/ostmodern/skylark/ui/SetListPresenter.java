package ostmodern.skylark.ui;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ostmodern.skylark.api.SkylarkService;
import ostmodern.skylark.model.Image;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public class SetListPresenter implements SetListContract.Presenter {

    private final SkylarkService skylarkService;
    private final SetListContract.View view;
    private final SchedulerProvider schedulerProvider;

    private Disposable setListDisposable;

    /**
     * Constructor for {@link SetListPresenter}.
     *
     * @param skylarkService    service instance for api calls
     * @param view              interface for view communication
     * @param schedulerProvider provides schedulers for RX.
     */
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
                .concatMap(Observable::fromIterable)
                .flatMap(this::getImageForASet, SetUI::new)
                .toList()
                .doOnError(throwable -> {
                    Timber.e(throwable, "setListDisposable::doOnError called : %s", throwable.getMessage());
                })
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .subscribe(sets -> {
                    view.showSetList(sets);
                });
    }

    private Observable<Image> getImageForASet(Set set) {
        if (set.getImageUrls() == null || set.getImageUrls().size() == 0) {
            // TODO: Handle empty image urls!
            Image image = new Image("", "http://feature-code-test.skylark-cms.qa.aws.ostmodern.co.uk:8000/static/images/dummy/dummy-film.jpg");
            return Observable.just(image);
        } else {
            return skylarkService.getImageOfSet(set.getImageUrls().get(0));
        }
    }
}
