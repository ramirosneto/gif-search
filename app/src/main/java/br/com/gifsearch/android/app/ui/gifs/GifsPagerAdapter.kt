package br.com.gifsearch.android.app.ui.gifs

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.gifsearch.android.app.R
import br.com.gifsearch.android.app.ui.gifs.favorites.FavoritesGifsFragment
import br.com.gifsearch.android.app.ui.gifs.trendings.TrendingGifsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class GifsPagerAdapter(val context: Context, fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {
    val NUMBER_OF_FRAGMENTS = 2

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return TrendingGifsFragment.newInstance()
        } else {
            return FavoritesGifsFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return NUMBER_OF_FRAGMENTS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(TAB_TITLES.get(position))
    }
}