package com.riocallos.itunes.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riocallos.itunes.R
import com.riocallos.itunes.search.SearchResultsRecyclerViewAdapter

/**
 * Custom data binding for ImageView.
 *
 */
class ImageViewDataBinding {

    /**
     * Bind image url to RecyclerView.
     *
     * @property imageView [ImageView] is the view.
     * @property url [String] for the image resource.
     */
    @BindingAdapter("url")
    fun bind(imageView: ImageView, url: String) {

        if(url.isNotEmpty()) {

            Glide.with(imageView.context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.placeholder)).load(url).into(imageView)

        }

    }

}
