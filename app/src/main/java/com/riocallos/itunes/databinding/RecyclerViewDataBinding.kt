package com.riocallos.itunes.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riocallos.itunes.models.SearchResult
import com.riocallos.itunes.search.SearchResultsRecyclerViewAdapter
import io.reactivex.Observable

/**
 * Custom data binding for RecyclerView.
 *
 */
class RecyclerViewDataBinding {

    /**
     * Bind search results list and adapter to RecyclerView.
     *
     * @property recyclerView [RecyclerView] is the view.
     * @property adapter [SearchResultsRecyclerViewAdapter] for search results list.
     * @property searchResults [<List<SearchResult>] is search results list observable.
     */
    @BindingAdapter("adapter", "data")
    fun bind(recyclerView: RecyclerView, adapter: SearchResultsRecyclerViewAdapter, searchResults: List<SearchResult>) {
        recyclerView.adapter = adapter
        adapter.updateSearchResults(searchResults)
    }
}
