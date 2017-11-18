package ostmodern.skylark.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Image image = (Image) obj;
        return Objects.equals(uid, image.uid)
                && Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, url);
    }
}
