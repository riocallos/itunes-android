package com.riocallos.itunes.search

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.riocallos.itunes.R
import com.riocallos.itunes.databinding.ItemSearchHeaderBinding
import com.riocallos.itunes.databinding.ItemSearchResultBinding
import com.riocallos.itunes.models.SearchResult
import com.riocallos.itunes.search.viewmodels.SearchHeaderViewModel
import com.riocallos.itunes.search.viewmodels.SearchResultViewModel
import com.riocallos.itunes.search.views.SearchHeaderViewHolder
import com.riocallos.itunes.search.views.SearchResultViewHolder
import com.riocallos.itunes.search.views.SearchResultsActivity
import com.riocallos.itunes.utils.CacheUtil
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for notifications list.
 *
 * @property context [Context] of the activity.
 * @property onActionListener  [OnActionListener] handles actions for search results.
 */
class SearchResultsRecyclerViewAdapter(val context: Context, private var onActionListener: OnActionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SEARCH_HEADER = 0
        const val VIEW_TYPE_SEARCH_RESULT = 1
    }

    private var searchResults: List<SearchResult> = listOf()

    /**
     * Interface for search result actions.
     *
     */
    interface OnActionListener {
        fun onClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            VIEW_TYPE_SEARCH_HEADER -> {
                val binding: ItemSearchHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search_header, parent, false)
                SearchHeaderViewHolder(binding)
            }
            else -> {
                val binding: ItemSearchResultBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search_result, parent, false)
                SearchResultViewHolder(binding)
            }
        }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(position == 0) {

            var retrieved = CacheUtil.invoke(context).getRetrieved()
            var retrievedText = ""
            var noResultsText = ""

            if(searchResults.isEmpty()) {
                noResultsText = "\nNo results found."
            }

            if(retrieved.isNotEmpty()) {
                val sdf = SimpleDateFormat("MMM. d, yyyy h:mm a", Locale.ENGLISH)
                retrievedText = "Retrieved ${sdf.format(Date(retrieved.toLong()))}. "
            }

            val searchHeaderViewModel = ViewModelProviders.of(context as FragmentActivity, (context as SearchResultsActivity).viewModelFactory)[SearchHeaderViewModel::class.java]

            val searchHeaderViewHolder = holder as SearchHeaderViewHolder
            searchHeaderViewHolder.binding.searchHeaderViewModel = searchHeaderViewModel

            searchHeaderViewHolder.binding.header.text = "${retrievedText}Pull down to refresh.$noResultsText"

        } else {

            val searchResult = searchResults[position - 1]

            val searchResultViewModel = ViewModelProviders.of(context as FragmentActivity, (context as SearchResultsActivity).viewModelFactory)[SearchResultViewModel::class.java]
            searchResultViewModel.searchResult = searchResult

            val searchResultViewHolder = holder as SearchResultViewHolder

            searchResultViewHolder.binding.searchResultViewModel = searchResultViewModel
            searchResultViewHolder.binding.executePendingBindings()

            searchResultViewHolder.bind(onActionListener)

        }

    }

    /**
     * Get search header or result view type.
     *
     */
    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return VIEW_TYPE_SEARCH_HEADER
        }
        return VIEW_TYPE_SEARCH_RESULT
    }

    /**
     * Get size of search results list.
     *
     */
    override fun getItemCount() = searchResults.size + 1

    /**
     * Update search results list.
     *
     */
    fun updateSearchResults(searchResults: List<SearchResult>) {

        this.searchResults = searchResults
        notifyDataSetChanged()

    }

}
