package ostmodern.skylark.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Flowable

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodes: ArrayList<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE setId = :uid")
    fun getEpisodesForSet(uid: String): Flowable<List<EpisodeEntity>>
}
