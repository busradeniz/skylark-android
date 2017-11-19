package ostmodern.skylark.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM favourites")
    Flowable<List<FavouriteEntity>> getAll();

    @Query("SELECT * FROM favourites WHERE setId = :uid")
    Maybe<FavouriteEntity> getFavouriteById(String uid);

    @Delete
    void deleteFavourite(FavouriteEntity favouriteEntity);
}
