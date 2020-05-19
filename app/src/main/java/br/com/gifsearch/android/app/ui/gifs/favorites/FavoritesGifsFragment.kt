package br.com.gifsearch.android.app.ui.gifs.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gifsearch.android.app.R
import br.com.gifsearch.android.app.data.model.GifDTO
import br.com.gifsearch.android.app.data.model.GifImages
import br.com.gifsearch.android.app.data.model.GifProperties
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity
import br.com.gifsearch.android.app.ui.gifs.GifsAdapter
import br.com.gifsearch.android.app.ui.gifs.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_gifs.*

/**
 * A fragment containing the favorites gifs.
 */
class FavoritesGifsFragment : Fragment(),
    RecyclerViewClickListener {

    private lateinit var viewModel: FavoritesGifsViewModel

    //OVERRIDE METHODS REGION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gifs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FavoritesGifsViewModel::class.java)
        viewModel.favoritesGifs.observe(viewLifecycleOwner, Observer { gifs ->
            recycler_view.also { recyclerView ->
                gifs.let {
                    if (!gifs.isNullOrEmpty()) {
                        recycler_view.visibility = View.VISIBLE
                        text_no_results.visibility = View.GONE
                        recyclerView.layoutManager = GridLayoutManager(context, 3)
                        recyclerView.setHasFixedSize(true)
                        val adapter = GifsAdapter(this)
                        recyclerView.adapter = adapter
                        adapter.submit(transformEntityInDto(it))
                    } else {
                        recycler_view.visibility = View.GONE
                        text_no_results.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    //RecyclerViewClickListener IMPLEMENTATION REGION

    override fun onLikeGif(view: View, gif: GifDTO) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.anim_fade_out)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                viewModel.delete(gif.id)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        view.startAnimation(animation)
    }

    //PRIVATE METHODS REGION

    private fun transformEntityInDto(list: List<GifEntity>): List<GifDTO> {
        val result = arrayListOf<GifDTO>()

        list.forEach {
            val fixed_height_small_still = GifProperties(0, 0, 0, it.thumbnailUrl!!)
            val fixed_width_downsampled = GifProperties(0, 0, 0, it.gifUrl!!)

            result.add(
                GifDTO(
                    it.id,
                    GifImages(fixed_height_small_still, fixed_width_downsampled),
                    true
                )
            )
        }

        return result
    }

    companion object {

        /**
         * Returns a new instance of this fragment
         */
        fun newInstance(): FavoritesGifsFragment {
            return FavoritesGifsFragment()
        }
    }
}