package ostmodern.skylark.model;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Episode {

    private final String uid;
    private final String self;
    @SerializedName("content_url")
    private final String contentUrl;
    @SerializedName("set_url")
    private final String setUrl;
    @SerializedName("content_type")
    private final String contentType;
    private final int position;

    /**
     * Constructor for {@link Episode}.
     *
     * @param uid         value for id
     * @param self        url of episode
     * @param contentUrl  content url of episode
     * @param setUrl      url of episode's set
     * @param contentType content type of episode
     * @param position    position of episode
     */
    public Episode(String uid, String self, String contentUrl, String setUrl, String contentType,
                   int position) {
        this.uid = uid;
        this.self = self;
        this.contentUrl = contentUrl;
        this.setUrl = setUrl;
        this.contentType = contentType;
        this.position = position;
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

    public String getSetUrl() {
        return setUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public int getPosition() {
        return position;
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
        return position == episode.position
                && Objects.equals(uid, episode.uid)
                && Objects.equals(self, episode.self)
                && Objects.equals(contentUrl, episode.contentUrl)
                && Objects.equals(setUrl, episode.setUrl)
                && Objects.equals(contentType, episode.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, self, contentUrl, setUrl, contentType, position);
    }
}
