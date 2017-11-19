package ostmodern.skylark.di;

import dagger.Binds;
import dagger.Module;
import ostmodern.skylark.ui.detail.SetDetailContract;
import ostmodern.skylark.ui.detail.SetDetailFragment;
import ostmodern.skylark.ui.sets.SetListContract;
import ostmodern.skylark.ui.sets.SetListFragment;


@Module
public abstract class MVPViewsModule {

    @Binds
    abstract SetListContract.View provideSetListContractView(SetListFragment setListFragment);

    @Binds
    abstract SetDetailContract.View provideSetDetailContractView(SetDetailFragment setDetailFragment);
}
