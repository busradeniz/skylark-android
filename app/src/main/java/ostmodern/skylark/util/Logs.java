package ostmodern.skylark.util;

/**
 * Utility class for logs.
 */
public final class Logs {

    private Logs() {
    }

    /**
     * Timber tag for application logs.
     */
    public static final String APPLICATION_LOG_TAG = "SkylarkApp";

    /**
     * Timber tag for wire logging.
     * Everything going through okhttp will be logged under this tag.
     * This tag will be only available for DEBUG.
     */
    public static final String WIRE_LOG_TAG = "SkylarkWire";
}
