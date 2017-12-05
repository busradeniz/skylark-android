package ostmodern.skylark.repository.local

import android.arch.persistence.room.*

@Entity(tableName = "episodes",
        foreignKeys = [(ForeignKey(entity = SetEntity::class, parentColumns = arrayOf("uid"),
                childColumns = arrayOf("setId")))], indices = [(Index("setId"))])
data class EpisodeEntity (@field:PrimaryKey val uid: String,
                     @field:ColumnInfo(name = "contentUrl") val contentUrl: String,
                     @field:ColumnInfo(name = "contentType") val contentType: String,
                     @field:ColumnInfo(name = "setId") val setId: String)
