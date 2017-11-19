package ostmodern.skylark.ui.sets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import ostmodern.skylark.MainActivity;
import ostmodern.skylark.di.Injectable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylark.ui.common.NavigationController;
import ostmodern.skylark.ui.common.NotificationView;
import ostmodern.skylark.ui.common.ViewNotificationType;
import ostmodern.skylarkClient.R;

public class SetListFragment extends Fragment implements Injectable, SetListContract.View, SetListItemClickListener {

    @Inject
    SetListContract.Presenter setListPresenter;

    @BindView(R.id.recycler_sets)
    RecyclerView recyclerViewSets;

    @BindView(R.id.view_error)
    NotificationView notificationView;

    @BindView(R.id.txt_fetching_data)
    TextView txtFetchingData;

    @Inject
    NavigationController navigationController;

    private SetListAdapter setListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sets, container, false);
        ButterKnife.bind(this, view);
        initSetsRecyclerView();

        setActionBar();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setListPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        setListPresenter.unsubscribe();
    }

    @Override
    public void showSetList(List<SetUI> setList) {
        updateFetchingDataViewVisibility(setList);
        setListAdapter.updateDataSet(setList);
    }

    private void initSetsRecyclerView() {
        setListAdapter = new SetListAdapter(this);
        recyclerViewSets.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewSets.setAdapter(setListAdapter);
    }

    @Override
    public Observable<SetUI> getFavouriteObservable() {
        return setListAdapter.getFavouriteObservable();
    }

    @Override
    public void itemClicked(String setId) {
        navigationController.navigateToSetDetail(setId);
    }

    private void setActionBar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void hideNotificationView(ViewNotificationType connectionNotification) {
        notificationView.hideErrorMessage(connectionNotification);
    }

    @Override
    public void showNotificationView(ViewNotificationType connectionNotification) {
        notificationView.showErrorMessage(connectionNotification);
    }

    private void updateFetchingDataViewVisibility(List<SetUI> setList) {
        if (setList.size() == 0) {
            txtFetchingData.setVisibility(View.VISIBLE);
        } else {
            txtFetchingData.setVisibility(View.GONE);
        }
    }
}

