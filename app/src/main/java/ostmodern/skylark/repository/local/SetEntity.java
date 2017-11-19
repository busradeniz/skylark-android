package ostmodern.skylark.repository.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.common.base.MoreObjects;

import java.util.Objects;

@Entity(tableName = "sets")
public class SetEntity {

    @PrimaryKey
    @NonNull
    public String uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "filmCount")
    public int filmCount;

    @ColumnInfo(name = "formattedBody")
    public String formattedBody;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    /**
     * Constructor.
     *
     * @param uid           unique id.
     * @param title         title of a set.
     * @param filmCount     film count of a set.
     * @param formattedBody formatted version of a set.
     * @param imageUrl      first image from api resource.
     */
    public SetEntity(@NonNull String uid, String title, int filmCount,
                     String formattedBody, String imageUrl) {
        this.uid = uid;
        this.title = title;
        this.filmCount = filmCount;
        this.formattedBody = formattedBody;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SetEntity setEntity = (SetEntity) obj;
        return Objects.equals(uid, setEntity.uid)
                && filmCount == setEntity.filmCount
                && Objects.equals(title, setEntity.title)
                && Objects.equals(formattedBody, setEntity.formattedBody)
                && Objects.equals(imageUrl, setEntity.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, title, filmCount, formattedBody);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", uid)
                .add("title", title)
                .add("filmCount", filmCount)
                .add("formattedBody", formattedBody)
                .add("imageUrl", imageUrl)
                .toString();
    }

    @NonNull
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

    public String getImageUrl() {
        return imageUrl;
    }
}
