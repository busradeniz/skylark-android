package ostmodern.skylark.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ostmodern.skylark.di.Injectable;
import ostmodern.skylark.model.SetUI;
import ostmodern.skylarkClient.R;

public class SetListFragment extends Fragment implements Injectable, SetListContract.View {

    @Inject
    SetListContract.Presenter setListPresenter;

    @BindView(R.id.recycler_sets)
    RecyclerView recyclerViewSets;

    private SetListAdapter setListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sets, container, false);
        ButterKnife.bind(this, view);
        initSetsRecyclerView();

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
        setListAdapter.updateDataSet(setList);
    }

    private void initSetsRecyclerView() {
        setListAdapter = new SetListAdapter();
        recyclerViewSets.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewSets.setAdapter(setListAdapter);
    }
}

