package ostmodern.skylark

import android.app.Activity
import android.app.Application
import android.support.annotation.VisibleForTesting

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ostmodern.skylark.di.AppInjector
import ostmodern.skylark.util.Logs
import timber.log.Timber


class SkylarkApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var tree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        initInjector()
        Timber.plant(tree!!)
        Timber.tag(Logs.APPLICATION_LOG_TAG)
    }

    @VisibleForTesting
    protected fun initInjector() {
        AppInjector.init(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}
