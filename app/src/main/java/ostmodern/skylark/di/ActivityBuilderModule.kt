package ostmodern.skylark.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ostmodern.skylark.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    internal abstract fun contributeMainActivity(): MainActivity
}
