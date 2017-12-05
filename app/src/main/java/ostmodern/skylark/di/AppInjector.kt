package ostmodern.skylark.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import ostmodern.skylark.SkylarkApp

/**
 * Utilities for creating application by using dagger.
 */
object AppInjector {

    /**
     * Initializes application by using dagger.
     * Injects related components into activities when they are created
     *
     * @param skylart application instance
     */
    fun init(skylark: SkylarkApp) {
        DaggerAppComponent.builder().application(skylark)
                .build().inject(skylark)
        skylark.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

    }


    private fun handleActivity(activity: Activity) {
        if (activity is AppCompatActivity) {
            AndroidInjection.inject(activity)
        }
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(fragmentManager: FragmentManager?, fragment: Fragment?,
                                                   savedInstanceState: Bundle?) {
                        if (fragment is Injectable) {
                            AndroidSupportInjection.inject(fragment)
                        }
                    }
                }, true)
    }
}
