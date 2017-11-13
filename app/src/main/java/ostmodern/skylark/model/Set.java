package ostmodern.skylark.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Set {

    private final String uid;
    private final String quoter;
    private final String title;
    @SerializedName("has_price")
    private final boolean hasPrice;
    @SerializedName("temp_image")
    private final String tempImage;
    @SerializedName("film_count")
    private final int filmCount;
    private final String body;
    private final String quote;
    @SerializedName("formatted_body")
    private final String formattedBody;
    @SerializedName("image_urlss")
    private final ArrayList<String> imageUrls;
    private final boolean active;
    private final String slug;
    private final String summary;
    private final ArrayList<Episode> items;

    /**
     * Constructor for {@link Set}.
     *
     * @param uid           id for set object
     * @param quoter        name of the quoter
     * @param title         title of set object
     * @param hasPrice      value that set has price, returns true or false
     * @param tempImage     url of temp image of set
     * @param filmCount     count of film in set
     * @param body          description of set
     * @param quote         short quote of set
     * @param formattedBody formatted description of set
     * @param imageUrls     list of image for set
     * @param active        the value that set is active or not
     * @param slug          slug
     * @param summary       short summary of set
     * @param items         list of episodes
     */
    public Set(String uid, String quoter, String title, boolean hasPrice, String tempImage,
               int filmCount, String body, String quote, String formattedBody,
               ArrayList<String> imageUrls, boolean active, String slug, String summary, ArrayList<Episode> items) {
        this.uid = uid;
        this.quoter = quoter;
        this.title = title;
        this.hasPrice = hasPrice;
        this.tempImage = tempImage;
        this.filmCount = filmCount;
        this.body = body;
        this.quote = quote;
        this.formattedBody = formattedBody;
        this.imageUrls = imageUrls;
        this.active = active;
        this.slug = slug;
        this.summary = summary;
        this.items = items;
    }


    public String getUid() {
        return uid;
    }

    public String getQuoter() {
        return quoter;
    }

    public String getTitle() {
        return title;
    }

    public boolean isHasPrice() {
        return hasPrice;
    }

    public String getTempImage() {
        return tempImage;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public String getBody() {
        return body;
    }

    public String getQuote() {
        return quote;
    }

    public String getFormattedBody() {
        return formattedBody;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public boolean isActive() {
        return active;
    }

    public String getSlug() {
        return slug;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<Episode> getItems() {
        return items;
    }
}
