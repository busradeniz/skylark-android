package ostmodern.skylark.repository.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "episodes", foreignKeys = @ForeignKey(entity = SetEntity.class,
        parentColumns = "uid", childColumns = "setId"),
        indices = @Index("setId"))
public class EpisodeEntity {

    @PrimaryKey
    @NonNull
    public String uid;
    @ColumnInfo(name = "contentUrl")
    public String contentUrl;
    @ColumnInfo(name = "contentType")
    public String contentType;

    @ColumnInfo(name = "setId")
    public String setId;

    /**
     * Constructor.
     *
     * @param uid unique id.
     * @param contentUrl content url.
     * @param contentType type of the content.
     * @param setId id of related set.
     */
    public EpisodeEntity(@NonNull String uid, String contentUrl,
                         String contentType, String setId) {
        this.uid = uid;
        this.contentUrl = contentUrl;
        this.contentType = contentType;
        this.setId = setId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EpisodeEntity that = (EpisodeEntity) obj;
        return Objects.equals(uid, that.uid)
                && Objects.equals(contentUrl, that.contentUrl)
                && Objects.equals(contentType, that.contentType)
                && Objects.equals(setId, that.setId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, contentUrl, contentType, setId);
    }
}
