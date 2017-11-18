package ostmodern.skylark.repository.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;


@Entity(tableName = "favourites", foreignKeys = @ForeignKey(entity = SetEntity.class,
        parentColumns = "uid", childColumns = "setId"),
        indices = @Index("setId"))
public class FavouriteEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "setId")
    public String setId;

    /**
     * Constructor.
     *
     * @param setId id of set
     */
    public FavouriteEntity(@NonNull String setId) {
        this.setId = setId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FavouriteEntity that = (FavouriteEntity) object;
        return Objects.equals(setId, that.setId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setId);
    }
}
