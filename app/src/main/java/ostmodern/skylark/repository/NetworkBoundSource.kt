package ostmodern.skylark.repository


import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class NetworkBoundSource<L, R>(emitter: FlowableEmitter<L>) {

    abstract val remote: Observable<R>

    abstract val local: Flowable<L>

    init {
        val firstDataDisposable = local
                .subscribe({ emitter.onNext(it) })

        remote
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({ remoteDataType ->
                    firstDataDisposable.dispose()
                    saveCallResult(remoteDataType)
                    local.subscribe({ emitter.onNext(it) })
                }) { throwable -> Timber.e(throwable, "Could not fetch network bound resource") }
    }

    abstract fun saveCallResult(data: R)

}