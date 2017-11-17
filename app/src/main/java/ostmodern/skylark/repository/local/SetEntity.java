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

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "formattedBody")
    public String formattedBody;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    /**
     * Constructor.
     *
     * @param uid           unique id.
     * @param title         title of a set.
     * @param filmCount     film count of a set.
     * @param body          body of a set.
     * @param formattedBody formatted version of a set.
     * @param summary       summary of a set.
     * @param imageUrl      first image from api resource.
     */
    public SetEntity(@NonNull String uid, String title, int filmCount, String body,
                     String formattedBody, String summary, String imageUrl) {
        this.uid = uid;
        this.title = title;
        this.filmCount = filmCount;
        this.body = body;
        this.formattedBody = formattedBody;
        this.summary = summary;
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
                && Objects.equals(body, setEntity.body)
                && Objects.equals(formattedBody, setEntity.formattedBody)
                && Objects.equals(summary, setEntity.summary)
                && Objects.equals(imageUrl, setEntity.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, title, filmCount, body, formattedBody, summary);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", uid)
                .add("title", title)
                .add("filmCount", filmCount)
                .add("body", body)
                .add("formattedBody", formattedBody)
                .add("summary", summary)
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

    public String getBody() {
        return body;
    }

    public String getFormattedBody() {
        return formattedBody;
    }

    public String getSummary() {
        return summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
