package ostmodern.skylark.di

import dagger.Binds
import dagger.Module
import ostmodern.skylark.ui.detail.SetDetailContract
import ostmodern.skylark.ui.detail.SetDetailFragment
import ostmodern.skylark.ui.sets.SetListContract
import ostmodern.skylark.ui.sets.SetListFragment


@Module
abstract class MVPViewsModule {

    @Binds
    internal abstract fun provideSetListContractView(setListFragment: SetListFragment): SetListContract.View

    @Binds
    internal abstract fun provideSetDetailContractView(setDetailFragment: SetDetailFragment): SetDetailContract.View
}
