package ostmodern.skylark.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {SetEntity.class, EpisodeEntity.class, FavouriteEntity.class}, version = 1, exportSchema = false)
public abstract class SkylarkDatabase extends RoomDatabase {
    public abstract SetDao setDao();

    public abstract EpisodeDao episodeDao();

    public abstract FavouriteDao favouriteDao();
}


