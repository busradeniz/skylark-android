package ostmodern.skylark.repository.local

import android.arch.persistence.room.*


@Entity(tableName = "favourites",
        foreignKeys = [(ForeignKey(entity = SetEntity::class, parentColumns = arrayOf("uid"),
                childColumns = arrayOf("setId")))], indices = [(Index("setId"))])
class FavouriteEntity(@field:PrimaryKey @field:ColumnInfo(name = "setId") var setId: String)