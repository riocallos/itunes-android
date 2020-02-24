package com.riocallos.itunes.search.views

import androidx.recyclerview.widget.RecyclerView
import com.riocallos.itunes.databinding.ItemSearchResultBinding
import com.riocallos.itunes.search.SearchResultsRecyclerViewAdapter

/**
 * Search result list item view.
 *
 * @property binding [ItemSearchResultBinding] for the search results list item view.
 */
class SearchResultViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onActionListener: SearchResultsRecyclerViewAdapter.OnActionListener){

        binding.container.setOnClickListener {
            onActionListener.onClicked(adapterPosition - 1)
        }

    }

}