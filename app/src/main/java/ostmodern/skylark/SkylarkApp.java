package ostmodern.skylark;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ostmodern.skylark.di.AppInjector;
import timber.log.Timber;


public class SkylarkApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    Timber.Tree tree;

    private static final String TAG = "SkylarkApp";

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        Timber.plant(tree);
        Timber.tag(TAG);
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
