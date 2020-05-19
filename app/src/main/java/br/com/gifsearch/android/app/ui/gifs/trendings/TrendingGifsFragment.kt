package br.com.gifsearch.android.app.ui.gifs.trendings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.gifsearch.android.app.R
import br.com.gifsearch.android.app.data.model.GifDTO
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity
import br.com.gifsearch.android.app.data.source.remote.network.GifsApi
import br.com.gifsearch.android.app.data.source.remote.repository.GifsRetrofitRepository
import br.com.gifsearch.android.app.ui.gifs.GifsAdapter
import br.com.gifsearch.android.app.ui.gifs.RecyclerViewClickListener
import br.com.gifsearch.android.app.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_gifs.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * A fragment containing the trending gifs and search results.
 */
class TrendingGifsFragment : Fragment(),
    RecyclerViewClickListener, SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private val SPAN_COUNT = 3
    private lateinit var factory: TrendingGifsViewModelFactory
    private lateinit var viewModel: TrendingGifsViewModel
    private var gifsAdapter: GifsAdapter? = null

    //OVERRIDE METHODS REGION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_gifs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val api =
            GifsApi()
        val repository =
            GifsRetrofitRepository(
                api
            )

        factory =
            TrendingGifsViewModelFactory(
                requireContext(),
                repository
            )

        initializeViewModel()
    }

    //SEARCHVIEW IMPLEMENTATION REGION

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        viewModel.query.value?.let { searchView.setQuery(it, false) }
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true;
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.query.value = newText
        viewModel.offset.value = 0
        callGifsApi()
        return true
    }

    override fun onClose(): Boolean {
        viewModel.query.value = null
        viewModel.offset.value = 0
        callGifsApi()
        hideKeyboard()
        return true
    }

    //RecyclerViewClickListener IMPLEMENTATION REGION

    override fun onLikeGif(view: View, gif: GifDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.insert(
                GifEntity(
                    gif.id,
                    gif.images.fixed_height_small_still.url,
                    gif.images.fixed_width_downsampled.url,
                    Date().toString()
                )
            )
        }
    }

    //PRIVATE METHODS REGION

    private fun initializeViewModel() {
        val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
        gifsAdapter = GifsAdapter(this)

        recycler_view.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            //addOnScrollListener(InfiniteScrollListener({ callGifsApi(true) }, gridLayoutManager))
            adapter = gifsAdapter
        }

        viewModel = ViewModelProvider(this, factory).get(TrendingGifsViewModel::class.java)
        viewModel.gifs.observe(viewLifecycleOwner, Observer { gifs ->
            viewModel.offset.value = gifs.pagination?.offset
            progress_bar.visibility = View.GONE

            if (!gifs.data.isNullOrEmpty()) {
                recycler_view.visibility = View.VISIBLE
                text_no_results.visibility = View.GONE
                gifsAdapter?.submit(checkGifIsStoraged(gifs.data!!))
            } else {
                recycler_view.visibility = View.GONE
                text_no_results.visibility = View.VISIBLE
            }
        })

        callGifsApi()
    }

    private fun callGifsApi() {
        progress_bar.visibility = View.VISIBLE

        viewModel.getTrendingsGifs()
        viewModel.gifs.value?.let { response ->
            viewModel.offset.value = response.pagination?.offset
            progress_bar.visibility = View.GONE

            response.data?.let { gifs ->
                gifsAdapter?.submit(checkGifIsStoraged(gifs))
            }
        }

        viewModel.gifsError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    // TODO - check if gif is stored in room
    private fun checkGifIsStoraged(gifs: List<GifDTO>): List<GifDTO> {
        gifs.forEach { gif ->
            gif.storaged = false
        }

        return gifs
    }

    companion object {

        /**
         * Returns a new instance of this fragment
         */
        fun newInstance(): TrendingGifsFragment {
            return TrendingGifsFragment()
        }
    }
}