package ostmodern.skylark.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ostmodern.skylark.di.Injectable;
import ostmodern.skylark.model.Set;
import ostmodern.skylark.ui.common.NavigationController;
import ostmodern.skylarkClient.R;

public class SetListFragment extends Fragment implements Injectable, SetListContract.View {

    @Inject
    NavigationController navigationController;

    @Inject
    SetListPresenter setListPresenter;

    @BindView(R.id.recycler_sets)
    RecyclerView recyclerViewSets;

    private SetListAdapter setListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sets, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSetsRecyclerView();
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
    public void showSetList(List<Set> setList) {
        setListAdapter.updateDataSet(setList);
    }

    private void initSetsRecyclerView() {
        setListAdapter = new SetListAdapter();
        recyclerViewSets.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSets.setAdapter(setListAdapter);
    }
}

