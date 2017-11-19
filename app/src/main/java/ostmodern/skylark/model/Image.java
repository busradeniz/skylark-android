package ostmodern.skylark.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Image {
    private final String uid;
    private final String url;
    private final String self;

    /**
     * Constructor for {@link Image}.
     *
     * @param uid  id for image
     * @param url  link that includes image
     * @param self self url.
     */
    public Image(String uid, String url, String self) {
        this.uid = uid;
        this.url = url;
        this.self = self;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public String getSelf() {
        return self;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Image image = (Image) object;
        return Objects.equals(uid, image.uid)
                && Objects.equals(url, image.url)
                && Objects.equals(self, image.self);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, url, self);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", uid)
                .add("url", url)
                .add("self", self)
                .toString();
    }
}
