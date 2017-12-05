package ostmodern.skylark.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface SetDao {

    @get:Query("SELECT * FROM sets")
    val all: Flowable<List<SetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sets: ArrayList<SetEntity>)

    @Query("SELECT * FROM sets WHERE uid =  :setId")
    fun getSetById(setId: String): Maybe<SetEntity>
}
