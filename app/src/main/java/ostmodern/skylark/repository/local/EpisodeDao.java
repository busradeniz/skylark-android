package ostmodern.skylark.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEpisodes(List<EpisodeEntity> episodes);

    @Query("SELECT * FROM episodes WHERE setId = :uid")
    Flowable<List<EpisodeEntity>> getEpisodesForSet(String uid);
}
