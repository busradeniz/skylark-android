package ostmodern.skylark.di;

import dagger.Binds;
import dagger.Module;
import ostmodern.skylark.ui.SetListContract;
import ostmodern.skylark.ui.SetListFragment;


@Module
public abstract class MVPViewsModule {

    @Binds
    abstract SetListContract.View provideSetListContractView(SetListFragment setListFragment);
}
