package ostmodern.skylark.repository.local

import android.arch.persistence.room.*

@Entity(tableName = "episodes",
        foreignKeys = [(ForeignKey(entity = SetEntity::class, parentColumns = arrayOf("uid"),
                childColumns = arrayOf("setId")))], indices = [(Index("setId"))])
class EpisodeEntity (@field:PrimaryKey var uid: String,
                     @field:ColumnInfo(name = "contentUrl") var contentUrl: String,
                     @field:ColumnInfo(name = "contentType") var contentType: String,
                     @field:ColumnInfo(name = "setId") var setId: String)
