package ostmodern.skylark.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM favourites")
    Flowable<List<FavouriteEntity>> getAll();

    @Delete
    void deleteFavourite(FavouriteEntity favouriteEntity);
}
