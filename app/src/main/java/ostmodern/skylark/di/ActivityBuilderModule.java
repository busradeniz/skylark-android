package ostmodern.skylark.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ostmodern.skylark.MainActivity;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

}
