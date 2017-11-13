package ostmodern.skylark.model;

public class Image {
    private final String uid;
    private final String url;

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
