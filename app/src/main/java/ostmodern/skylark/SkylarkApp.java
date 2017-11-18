package ostmodern.skylark;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ostmodern.skylark.di.AppInjector;
import ostmodern.skylark.util.Logs;
import timber.log.Timber;


public class SkylarkApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    Timber.Tree tree;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        Timber.plant(tree);
        Timber.tag(Logs.APPLICATION_LOG_TAG);
    }

    @VisibleForTesting
    protected void initInjector() {
        AppInjector.init(this);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
