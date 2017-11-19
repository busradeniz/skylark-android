package ostmodern.skylark.ui.detail;


import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.ui.common.BasePresenter;

public interface SetDetailContract {

    interface View {

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

    interface Presenter extends BasePresenter {

        /**
         * Sets selected set Id.
         *
         * @param selectedSetId id of set.
         */
        void setSelectedSetId(String selectedSetId);

        /**
         * Updates favourite status, triggered when the favourite button clicked.
         */
        void onFavouriteClicked();
    }
}
