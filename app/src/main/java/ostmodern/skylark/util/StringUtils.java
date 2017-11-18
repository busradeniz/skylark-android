package ostmodern.skylark.util;

/**
 * Utilities for string manipulation.
 */
public final class StringUtils {

    private static final String AUTO_GENERATION_START = "?Signature";

    private StringUtils() {
    }

    /**
     * Extracts meaningful part of an image url.
     * It is application specific. And for skylark api, Image urls contains auto-generated values.
     * In order to compare image urls, we are cutting these auto generated parts out.
     *
     * @param imageUrl image url from api.
     * @return meaningful/auto-generated free parts of an image url.
     */
    public static String meaningfulPart(String imageUrl) {
        int index = imageUrl.indexOf(AUTO_GENERATION_START);
        return index > 0
                ? imageUrl.substring(0, index)
                : imageUrl;
    }
}
