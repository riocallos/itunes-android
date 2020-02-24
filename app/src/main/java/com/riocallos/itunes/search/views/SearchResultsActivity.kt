package com.riocallos.itunes.search.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riocallos.itunes.R
import com.riocallos.itunes.base.BaseActivity
import com.riocallos.itunes.database.AppDatabase
import com.riocallos.itunes.databinding.ActivitySearchResultsBinding
import com.riocallos.itunes.search.SearchResultsRecyclerViewAdapter
import com.riocallos.itunes.search.viewmodels.SearchResultsViewModel
import com.riocallos.itunes.utils.CacheUtil
import kotlinx.android.synthetic.main.activity_search_results.*
import kotlinx.android.synthetic.main.status_action_bar.*

/**
 * Activity to display search results list.
 *
 */
class SearchResultsActivity : BaseActivity() {

    private lateinit var searchResultsViewModel: SearchResultsViewModel

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        searchResultsViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchResultsViewModel::class.java]
        searchResultsViewModel.adapter = SearchResultsRecyclerViewAdapter(this, object : SearchResultsRecyclerViewAdapter.OnActionListener {
            override fun onClicked(position: Int) {
                searchResultsViewModel.searchResults.get()?.let {
                    CacheUtil.invoke(this@SearchResultsActivity).saveSearchResult(it[position].id)
                    showSearchResult()
                }
            }

        })

        val binding: ActivitySearchResultsBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_results)

        binding.apply {
            lifecycleOwner = this@SearchResultsActivity
            searchResultsViewModel = this@SearchResultsActivity.searchResultsViewModel
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.app_name)

        initViews()
        initSubscribers()

        swipeRefreshLayout.isRefreshing = true
        searchResultsViewModel.search("star", "au", "movie")

    }

    /**
     * Initialize views for search results.
     *
     */
    private fun initViews() {

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility = View.INVISIBLE
            searchResultsViewModel.search("star", "au", "movie")
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        twoPane = searchResultFrameLayout != null

        val id = CacheUtil.invoke(this).getSearchResult()

        if(id.isNotEmpty()) {
            showSearchResult()
        }

    }

    private fun initSubscribers()  {

        searchResultsViewModel.searchResults.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

                searchResultsViewModel.searchResults.get()?.let {
                    if(it.isNotEmpty()) {
                        AppDatabase.invoke(this@SearchResultsActivity).searchResultDao().insertAll(it.toTypedArray())
                        if (twoPane) {
                            CacheUtil.invoke(this@SearchResultsActivity).saveSearchResult(it[0].id)
                            val fragment = SearchResultFragment()
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.searchResultFrameLayout, fragment)
                                .commit()
                        }
                    }
                }

                swipeRefreshLayout.isRefreshing = false

                recyclerView.visibility = View.VISIBLE

            }

        })

    }

    private fun showSearchResult() {
        if (twoPane) {
            val fragment = SearchResultFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.searchResultFrameLayout, fragment)
                .commit()
        } else {
            val intent = Intent(this@SearchResultsActivity, SearchResultActivity::class.java)
            startActivity(intent)
        }
    }

}
