package ostmodern.skylark.util;

import android.app.Application;
import android.net.NetworkInfo;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.Observable;

/**
 * Util class for connectivity related works.
 */
public class NetworkStatusProvider {

    private final Application application;

    public NetworkStatusProvider(Application application) {
        this.application = application;
    }

    /**
     * Checks internet connection and returns {@link Observable}.
     *
     * @return {@link Observable}
     */
    public Observable<NetworkState> isConnected() {

        return ReactiveNetwork.observeNetworkConnectivity(application)
                .map(connectivity -> {
                    if (connectivity.getState() == NetworkInfo.State.CONNECTED) {
                        return NetworkState.CONNECTED;
                    }
                    return NetworkState.NOT_CONNECTED;
                });
    }

    public enum NetworkState {
        CONNECTED,
        NOT_CONNECTED
    }

}

