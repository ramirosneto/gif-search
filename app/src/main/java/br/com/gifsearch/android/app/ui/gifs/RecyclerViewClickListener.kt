package br.com.gifsearch.android.app.ui.gifs

import android.view.View
import br.com.gifsearch.android.app.data.model.GifDTO

interface RecyclerViewClickListener {

    fun onLikeGif(view: View, gif: GifDTO)
}