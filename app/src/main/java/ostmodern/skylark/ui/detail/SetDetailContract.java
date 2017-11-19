package ostmodern.skylark.ui.detail;


import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.ui.common.BasePresenter;
import ostmodern.skylark.ui.common.BaseView;
import ostmodern.skylark.util.NetworkStatusProvider;
import ostmodern.skylark.util.SchedulerProvider;

public interface SetDetailContract {

    interface View extends BaseView {

        /**
         * Shows set details on screen.
         * Triggered by {@link SetDetailPresenter ()} ()}.
         *
         * @param setUI instance of set
         */
        void showSetDetails(SetUI setUI);

        /**
         * Updates favourite button according to given input.
         *
         * @param isFavourite boolean value for favourite.
         */
        void updateFavouriteView(boolean isFavourite);

    }

    abstract class Presenter extends BasePresenter {

        Presenter(SchedulerProvider schedulerProvider,
                  NetworkStatusProvider networkStatusProvider) {
            super(schedulerProvider, networkStatusProvider);
        }

        /**
         * Sets selected set Id.
         *
         * @param selectedSetId id of set.
         */
        abstract void setSelectedSetId(String selectedSetId);

        /**
         * Updates favourite status, triggered when the favourite button clicked.
         */
        abstract void onFavouriteClicked();
    }
}
