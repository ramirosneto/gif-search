package br.com.gifsearch.android.app.ui.gifs.trendings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gifsearch.android.app.data.model.GifResponseDTO
import br.com.gifsearch.android.app.data.source.local.GifsRoomDatabase
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity
import br.com.gifsearch.android.app.data.source.local.repository.GifsRoomRepository
import br.com.gifsearch.android.app.data.source.remote.repository.GifsRetrofitRepository
import kotlinx.coroutines.*

class TrendingGifsViewModel(
    context: Context,
    private val retrofitRepository: GifsRetrofitRepository
) : ViewModel() {

    private lateinit var job: Job

    private val roomRepository: GifsRoomRepository
    private val _gifs = MutableLiveData<GifResponseDTO>()
    private val _gifsError = MutableLiveData<String>()

    var query = MutableLiveData<String?>()
    var offset = MutableLiveData<Int>()
    val gifsError: LiveData<String>
        get() = _gifsError
    val gifs: LiveData<GifResponseDTO>
        get() = _gifs

    init {
        val gifsDao = GifsRoomDatabase.getDatabase(context).gifDao()
        roomRepository =
            GifsRoomRepository(
                gifsDao
            )
        offset.value = 0
    }

    fun getTrendingsGifs() {
        job = CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                if (query.value != null) {
                    retrofitRepository.searchGifs(offset.value!!, query.value)
                } else {
                    retrofitRepository.trendingsGifs(offset.value!!)
                }
            }.await().apply {
                if (this is GifResponseDTO) {
                    _gifs.value = this
                } else {
                    _gifsError.value = this.toString()
                }
            }
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gif: GifEntity) = CoroutineScope(Dispatchers.IO).launch {
        roomRepository.insert(gif)
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}