package br.com.gifsearch.android.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentPagerAdapter
import br.com.gifsearch.android.app.R
import br.com.gifsearch.android.app.ui.gifs.GifsPagerAdapter
import com.google.android.material.badge.BadgeDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //OVERRIDE METHODS REGION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPager()
        setSupportActionBar(toolbar)
    }

    //PRIVATE METHODS REGION

    private fun setUpPager() {
        val sectionsPagerAdapter =
            GifsPagerAdapter(
                this,
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    //PUBLIC METHODS REGION

    fun setUpBadges(count: Int?) {
        count?.let {
            val badge: BadgeDrawable? = tabs.getTabAt(1)?.getOrCreateBadge()
            badge?.isVisible = it > 0
            badge?.number = it
            badge?.backgroundColor = ContextCompat.getColor(
                this,
                R.color.colorAccent
            )
        }
    }
}