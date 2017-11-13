package ostmodern.skylark.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import ostmodern.skylark.SkylarkApp;

/**
 * Utilities for creating application by using dagger.
 */
public final class AppInjector {

    private AppInjector() {
    }

    /**
     * Initializes application by using dagger.
     * Injects related components into activities when they are created
     *
     * @param skylart application instance
     */
    public static void init(SkylarkApp skylart) {
        DaggerAppComponent.builder().application(skylart)
                .build().inject(skylart);
        skylart.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }


    private static void handleActivity(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment,
                                                              Bundle savedInstanceState) {
                                    if (fragment instanceof Injectable) {
                                        AndroidSupportInjection.inject(fragment);
                                    }
                                }
                            }, true);
        }
    }
}
