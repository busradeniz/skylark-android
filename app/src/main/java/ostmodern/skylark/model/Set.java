package ostmodern.skylark.model;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;


public class Set {

    private final String uid;
    private final String title;
    private final int filmCount;
    private final String formattedBody;
    private final List<String> imageUrls;
    private final List<Episode> items;

    /**
     * Constructor for {@link Set}.
     *
     * @param uid           id for set object
     * @param title         title of set object
     * @param filmCount     count of film in set
     * @param formattedBody formatted description of set
     * @param imageUrls     list of image for set
     * @param items         list of episodes
     */
    public Set(String uid, String title, int filmCount, String formattedBody,
               List<String> imageUrls, List<Episode> items) {
        this.uid = uid;
        this.title = title;
        this.filmCount = filmCount;
        this.formattedBody = formattedBody;
        this.imageUrls = imageUrls;
        this.items = items;
    }


    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public String getFormattedBody() {
        return formattedBody;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public List<Episode> getItems() {
        return items;
    }

    // CHECKSTYLE.OFF: CyclomaticComplexity
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Set set = (Set) obj;
        return filmCount == set.filmCount
                && Objects.equals(uid, set.uid)
                && Objects.equals(title, set.title)
                && Objects.equals(formattedBody, set.formattedBody)
                && Objects.equals(imageUrls, set.imageUrls)
                && Objects.equals(items, set.items);
    }
    // CHECKSTYLE.ON: CyclomaticComplexity

    @Override
    public int hashCode() {
        return Objects.hash(uid, title, filmCount, formattedBody, imageUrls, items);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", uid)
                .add("title", title)
                .add("filmCount", filmCount)
                .add("formattedBody", formattedBody)
                .add("imageUrls", imageUrls)
                .add("items", items)
                .toString();
    }
}
