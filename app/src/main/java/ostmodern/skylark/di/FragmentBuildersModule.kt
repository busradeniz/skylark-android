package ostmodern.skylark.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ostmodern.skylark.ui.detail.SetDetailFragment
import ostmodern.skylark.ui.sets.SetListFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = arrayOf(MVPPresenterModule::class, MVPViewsModule::class))
    internal abstract fun contributeSetListFragment(): SetListFragment

    @ContributesAndroidInjector(modules = arrayOf(MVPPresenterModule::class, MVPViewsModule::class))
    internal abstract fun contributeSetDetailFragment(): SetDetailFragment

}
