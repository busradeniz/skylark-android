package ostmodern.skylark.repository.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

import com.google.common.base.MoreObjects

import java.util.Objects

@Entity(tableName = "sets")
data class SetEntity (@field:PrimaryKey @NonNull val uid: String,
                      @field:ColumnInfo(name = "title") val title: String,
                      @field:ColumnInfo(name = "filmCount") val filmCount: Int,
                      @field:ColumnInfo(name = "formattedBody") val formattedBody: String,
                      @field:ColumnInfo(name = "imageUrl") val imageUrl: String)
