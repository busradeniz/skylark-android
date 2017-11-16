package ostmodern.skylark.model;

public class Image {
    private final String uid;
    private final String url;

    /**
     * Constructor for {@link Image}.
     *
     * @param uid id for image
     * @param url link that includes image
     */
    public Image(String uid, String url) {
        this.uid = uid;
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }
}
