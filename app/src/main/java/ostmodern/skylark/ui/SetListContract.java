package ostmodern.skylark.ui;

import java.util.List;

import ostmodern.skylark.common.BasePresenter;
import ostmodern.skylark.repository.local.SetEntity;

public interface SetListContract {


    interface View {

        /**
         * Shows set list on screen.
         * Triggered by {@link SetListPresenter()} ()}.
         *
         * @param setList list of set
         */
        void showSetList(List<SetEntity> setList);

    }

    interface Presenter extends BasePresenter {


    }
}
