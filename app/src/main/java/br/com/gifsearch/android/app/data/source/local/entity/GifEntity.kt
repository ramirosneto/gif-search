package br.com.gifsearch.android.app.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_table")
class GifEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    @ColumnInfo(name = "gif_url") val gifUrl: String?,
    @ColumnInfo(name = "created_date") val createdDate: String
)