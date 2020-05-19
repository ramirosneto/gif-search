package br.com.gifsearch.android.app.ui.gifs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.gifsearch.android.app.R
import br.com.gifsearch.android.app.data.model.GifDTO
import br.com.gifsearch.android.app.databinding.GifListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class GifsAdapter(
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<GifsAdapter.TrendingGifViewHolder>() {

    private var gifs: ArrayList<GifDTO?> = arrayListOf()

    fun submit(items: List<GifDTO>) {
        gifs.clear()
        gifs.addAll(items)
        notifyDataSetChanged()
    }

    fun append(items: List<GifDTO>) {
        gifs.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = gifs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingGifViewHolder {
        return TrendingGifViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.gif_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: TrendingGifViewHolder, position: Int) {
        val holder = viewHolder as TrendingGifViewHolder
        val gif = gifs.get(position)

        Glide.with(holder.itemView)
            .load(gif?.images?.fixed_width_downsampled?.url)
            .thumbnail(
                Glide.with(holder.itemView)
                    .load(gif?.images?.fixed_height_small_still?.url)
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.viewHolderBinding.image)

        gif?.storaged?.let {
            if (it) R.drawable.ic_like_dark else R.drawable.ic_like
        }?.let {
            holder.viewHolderBinding.btnLike.setImageResource(
                it
            )
        }

        gif?.let {
            holder.viewHolderBinding.btnLike.setOnClickListener {
                holder.viewHolderBinding.btnLike.setImageResource(R.drawable.ic_like_dark)
                listener.onLikeGif(
                    holder.viewHolderBinding.root,
                    gif
                )
            }
        }
    }

    inner class TrendingGifViewHolder(
        val viewHolderBinding: GifListItemBinding
    ) : RecyclerView.ViewHolder(viewHolderBinding.root)
}