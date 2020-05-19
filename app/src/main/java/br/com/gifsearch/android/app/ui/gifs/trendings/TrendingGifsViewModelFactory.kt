package br.com.gifsearch.android.app.ui.gifs.trendings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.gifsearch.android.app.data.source.remote.repository.GifsRetrofitRepository

class TrendingGifsViewModelFactory(
    private val context: Context,
    private val repository: GifsRetrofitRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrendingGifsViewModel(
            context, repository
        ) as T
    }
}