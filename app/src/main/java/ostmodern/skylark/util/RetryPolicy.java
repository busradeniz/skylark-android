package ostmodern.skylark.util;

import java.io.IOException;

/**
 * Retry related policies and utilities.
 */
public final class RetryPolicy {

    private static final Class[] RETRYABLE_EXCEPTIONS =
            new Class[]{IOException.class};

    /**
     * Each service call will retry this amount of times.
     */
    public static final long DEFAULT_RETRY_TIMES = 5;

    /**
     * Checks if an exception is retryable or not.
     *
     * @param throwable exception that causes error.
     * @return true if exception is retryable.
     */
    public static boolean isRetryableException(Throwable throwable) {
        for (Class retryableException : RETRYABLE_EXCEPTIONS) {
            if (retryableException.isAssignableFrom(throwable.getClass())) {
                return true;
            }
        }

        return false;
    }

    private RetryPolicy() {
    }
}
