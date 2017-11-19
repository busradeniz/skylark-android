package ostmodern.skylark.ui.common;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;
import timber.log.Timber;

public abstract class BasePresenter {

    private final SchedulerProvider schedulerProvider;
    private final NetworkStatusProvider networkStatusProvider;
    private final List<Disposable> disposables;

    private NetworkStatusProvider.NetworkState previousConnectivity =
            NetworkStatusProvider.NetworkState.CONNECTED;


    /**
     * Constructor.
     *
     * @param schedulerProvider provides rx schedulers.
     * @param networkStatusProvider provides network status change events.
     */
    public BasePresenter(SchedulerProvider schedulerProvider,
                         NetworkStatusProvider networkStatusProvider) {
        this.schedulerProvider = schedulerProvider;
        this.networkStatusProvider = networkStatusProvider;
        this.disposables = new ArrayList<>();
    }


    /**
     * Should be called after creation of presenter.
     * It's supposed to initialize all things to be done when view and presenter binded each other.
     */
    public void subscribe() {
        subscribeNetworkStatus();
    }

    /**
     * Should be called before destroying presenter.
     * Clears presenter elements before unbinded view and presenter.
     */
    public void unsubscribe() {
        Timber.i("Presenter unsubscribed");
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    @VisibleForTesting
    public List<Disposable> getDisposables() {
        return disposables;
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    /**
     * This method is called when network connection is established.
     */
    protected void onConnected() {
        Timber.i("Network connected");
        getContractView().hideNotificationView(ViewNotificationType.CONNECTION_NOTIFICATION);
    }

    /**
     * This method is called when network connection is lost.
     */
    protected void onDisconnected() {
        Timber.w("Network connection lost!");
        getContractView().showNotificationView(ViewNotificationType.CONNECTION_NOTIFICATION);
    }

    protected SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    protected abstract BaseView getContractView();

    private void subscribeNetworkStatus() {
        Timber.i("subscribeNetworkStatus called.");
        addDisposable(networkStatusProvider.isConnected()
                .filter(networkState -> networkState != previousConnectivity)
                .retry()
                .subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getUiScheduler())
                .subscribe(networkState -> {
                    previousConnectivity = networkState;
                    if (networkState == NetworkStatusProvider.NetworkState.CONNECTED) {
                        onConnected();
                    } else {
                        onDisconnected();
                    }
                }));
    }
}
