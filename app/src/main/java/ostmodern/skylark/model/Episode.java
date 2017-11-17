package ostmodern.skylark.model;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Episode {

    private final String uid;
    private final String self;
    @SerializedName("content_url")
    private final String contentUrl;
    @SerializedName("content_type")
    private final String contentType;

    /**
     * Constructor for {@link Episode}.
     *
     * @param uid         value for id
     * @param self        url of episode
     * @param contentUrl  content url of episode
     * @param contentType content type of episode
     */
    public Episode(String uid, String self, String contentUrl, String contentType) {
        this.uid = uid;
        this.self = self;
        this.contentUrl = contentUrl;
        this.contentType = contentType;
    }

    public String getUid() {
        return uid;
    }

    public String getSelf() {
        return self;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Episode episode = (Episode) object;
        return Objects.equals(uid, episode.uid)
                && Objects.equals(self, episode.self)
                && Objects.equals(contentUrl, episode.contentUrl)
                && Objects.equals(contentType, episode.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, self, contentUrl, contentType);
    }
}
