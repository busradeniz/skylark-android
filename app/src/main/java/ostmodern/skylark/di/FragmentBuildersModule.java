
package ostmodern.skylark.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ostmodern.skylark.ui.detail.SetDetailFragment;
import ostmodern.skylark.ui.sets.SetListFragment;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {MVPPresenterModule.class, MVPViewsModule.class})
    abstract SetListFragment contributeSetListFragment();

    @ContributesAndroidInjector(modules = {MVPPresenterModule.class, MVPViewsModule.class})
    abstract SetDetailFragment contributeSetDetailFragment();

}
