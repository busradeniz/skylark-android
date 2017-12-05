package ostmodern.skylark.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(SetEntity::class), (EpisodeEntity::class), (FavouriteEntity::class)],
        version = 1,
        exportSchema = false)
abstract class SkylarkDatabase : RoomDatabase() {
    abstract fun setDao(): SetDao

    abstract fun episodeDao(): EpisodeDao

    abstract fun favouriteDao(): FavouriteDao
}


