package ostmodern.skylark.ui.sets;

import java.util.List;

import io.reactivex.Observable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.ui.common.BasePresenter;

public interface SetListContract {


    interface View {

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

    interface Presenter extends BasePresenter {

    }
}