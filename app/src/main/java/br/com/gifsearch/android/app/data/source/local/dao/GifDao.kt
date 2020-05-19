package br.com.gifsearch.android.app.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity

@Dao
interface GifDao {
    @Query("SELECT * FROM gif_table ORDER BY created_date DESC")
    fun getGifs(): LiveData<List<GifEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gif: GifEntity)

    @Query("DELETE FROM gif_table WHERE id = :id")
    suspend fun delete(id: String)
}