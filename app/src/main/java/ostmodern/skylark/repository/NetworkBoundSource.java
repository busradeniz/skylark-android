package ostmodern.skylark.repository;


import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public abstract class NetworkBoundSource<L, R> {

    /**
     * Constructor for {@link NetworkBoundSource}.
     *
     * @param emitter flowable emitter instance
     */
    public NetworkBoundSource(FlowableEmitter<L> emitter) {
        Disposable firstDataDisposable = getLocal()
                .subscribe(emitter::onNext);

        getRemote()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(remoteDataType -> {
                    firstDataDisposable.dispose();
                    saveCallResult(remoteDataType);
                    getLocal().subscribe(emitter::onNext);
                }, throwable ->  {
                    Timber.e(throwable, "Could not fetch network bound resource");
                });
    }

    public abstract Observable<R> getRemote();

    public abstract Flowable<L> getLocal();

    public abstract void saveCallResult(R data);

}