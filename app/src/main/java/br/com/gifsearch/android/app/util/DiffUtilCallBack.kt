package br.com.gifsearch.android.app.util

import androidx.recyclerview.widget.DiffUtil
import br.com.gifsearch.android.app.data.model.GifResponseDTO

class DiffUtilCallBack : DiffUtil.ItemCallback<GifResponseDTO>() {

    override fun areItemsTheSame(oldItem: GifResponseDTO, newItem: GifResponseDTO): Boolean {
        return oldItem.pagination?.offset == newItem.pagination?.offset
    }

    override fun areContentsTheSame(oldItem: GifResponseDTO, newItem: GifResponseDTO): Boolean {
        return oldItem.data == newItem.data
    }
}