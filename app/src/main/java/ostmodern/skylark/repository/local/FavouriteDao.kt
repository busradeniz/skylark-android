package ostmodern.skylark.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface FavouriteDao {

    @get:Query("SELECT * FROM favourites")
    val all: Flowable<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM favourites WHERE setId = :uid")
    fun getFavouriteById(uid: String): Maybe<FavouriteEntity>

    @Delete
    fun deleteFavourite(favouriteEntity: FavouriteEntity)
}
