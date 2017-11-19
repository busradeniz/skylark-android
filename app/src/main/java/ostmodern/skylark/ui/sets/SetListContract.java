package ostmodern.skylark.ui.sets;

import java.util.List;

import io.reactivex.Observable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.ui.common.BasePresenter;
import ostmodern.skylark.ui.common.BaseView;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;

public interface SetListContract {


    interface View extends BaseView {

        /**
         * Shows set list on screen.
         * Triggered by {@link SetListPresenter()} ()}.
         *
         * @param setList list of set
         */
        void showSetList(List<SetUI> setList);

        /**
         * Returns callback for favourite buttons.
         *
         * @return observable set ui.
         */
        Observable<SetUI> getFavouriteObservable();
    }

    abstract class Presenter extends BasePresenter {

        Presenter(SchedulerProvider schedulerProvider,
                  NetworkStatusProvider networkStatusProvider) {
            super(schedulerProvider, networkStatusProvider);
        }
    }
}
