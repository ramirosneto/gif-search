package br.com.gifsearch.android.app.ui.gifs.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.com.gifsearch.android.app.data.source.local.GifsRoomDatabase
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity
import br.com.gifsearch.android.app.data.source.local.repository.GifsRoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesGifsViewModel(application: Application) : AndroidViewModel(application) {

    private val roomRepository: GifsRoomRepository

    // Using LiveData and caching what favoritesGifs returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val favoritesGifs: LiveData<List<GifEntity>>

    init {
        val gifsDao = GifsRoomDatabase.getDatabase(application).gifDao()
        roomRepository =
            GifsRoomRepository(
                gifsDao
            )
        favoritesGifs = roomRepository.favoritesGifs
    }

    /**
     * Launching a new coroutine to delete the data in a non-blocking way
     */
    fun delete(id: String) = CoroutineScope(Dispatchers.IO).launch {
        roomRepository.delete(id)
    }
}