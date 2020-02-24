package com.riocallos.itunes.search.views

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.riocallos.itunes.databinding.FragmentSearchResultBinding
import com.riocallos.itunes.search.viewmodels.SearchResultViewModel
import kotlinx.android.synthetic.main.activity_search_result.*

/**
 * Fragment to display search result details.
 *
 */
class SearchResultFragment : Fragment() {

    private lateinit var searchResultViewModel: SearchResultViewModel

    private lateinit var player: ExoPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        searchResultViewModel = ViewModelProviders.of(this, (activity as SearchResultsActivity).viewModelFactory)[SearchResultViewModel::class.java]

        val binding: FragmentSearchResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)

        binding.apply {
            lifecycleOwner = this@SearchResultFragment
            searchResultViewModel = this@SearchResultFragment.searchResultViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        searchResultViewModel.get()
    }

    override fun onPause() {
        super.onPause()
        player.release()
    }

    private fun initViews() {

        player = SimpleExoPlayer.Builder(activity as BaseActivity).build()
        player.playWhenReady = true
        playerView.player = player

        play.setOnClickListener {

            val dataSourceFactory = DefaultDataSourceFactory(
                activity,
                null,
                DefaultHttpDataSourceFactory(
                    Util.getUserAgent(
                        activity as BaseActivity,
                        getString(R.string.app_name)
                    ), null
                )
            )

            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                Uri.parse(searchResultViewModel.searchResult.preview))

            player.prepare(videoSource)

            playerView.visibility = View.VISIBLE

        }

    }

}
