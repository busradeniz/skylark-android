package ostmodern.skylark.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<SetEntity> sets);


    @Query("SELECT * FROM sets")
    Flowable<List<SetEntity>> getAll();
}
