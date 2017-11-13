package ostmodern.skylark.util;

import android.util.Log;

import timber.log.Timber;

/**
 * Tree class for Timber Release mode.
 */
public class ReleaseTree extends Timber.Tree {


    @Override
    protected boolean isLoggable(String tag, int priority) {
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable throwable) {
        if (isLoggable(tag, priority)) {
            return;
        }

        if (priority == Log.ASSERT) {
            Log.w(tag, message);
        } else {
            // We can read this through crashlytics
            Log.println(priority, tag, message);
        }
    }
}
