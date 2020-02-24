package com.riocallos.itunes.search.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.riocallos.itunes.R
import com.riocallos.itunes.base.BaseActivity
import com.riocallos.itunes.databinding.ActivitySearchResultBinding
import com.riocallos.itunes.search.viewmodels.SearchResultViewModel
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.status_action_bar.*

/**
 * Activity to display search result details.
 *
 */
class SearchResultActivity : BaseActivity() {

    private lateinit var searchResultViewModel: SearchResultViewModel

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        searchResultViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchResultViewModel::class.java]

        val binding: ActivitySearchResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)

        binding.apply {
            lifecycleOwner = this@SearchResultActivity
            searchResultViewModel = this@SearchResultActivity.searchResultViewModel
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initViews()

        searchResultViewModel.get()

    }

    override fun onPause() {
        super.onPause()
        player?.let {
            it.release()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, SearchResultsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun initViews() {

        player = SimpleExoPlayer.Builder(this).build()
        player?.let {
            it.apply {
                playWhenReady = true
            }
        }

        playerView.player = player

        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            null,
            DefaultHttpDataSourceFactory(
                Util.getUserAgent(
                    this,
                    getString(R.string.app_name)
                ), null
            )
        )

        play.setOnClickListener {

            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(searchResultViewModel.searchResult.preview))

            player?.let {
                it.stop(true)
                it.prepare(videoSource)
                playerView.visibility = View.VISIBLE
            }

        }

    }

}
