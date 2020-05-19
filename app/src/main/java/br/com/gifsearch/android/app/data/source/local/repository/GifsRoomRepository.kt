package br.com.gifsearch.android.app.data.source.local.repository

import androidx.lifecycle.LiveData
import br.com.gifsearch.android.app.data.source.local.dao.GifDao
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity

class GifsRoomRepository(private val gifDao: GifDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val favoritesGifs: LiveData<List<GifEntity>> = gifDao.getGifs()

    suspend fun insert(gif: GifEntity) {
        gifDao.insert(gif)
    }

    suspend fun delete(id: String) {
        gifDao.delete(id)
    }
}