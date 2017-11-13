
package ostmodern.skylark.di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ostmodern.skylark.ui.SetListFragment;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector (modules = {MVPViewsModule.class, MVPPresenterModule.class})
    abstract SetListFragment contributeSetListFragment();

}
