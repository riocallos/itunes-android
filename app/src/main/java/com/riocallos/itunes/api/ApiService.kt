package com.riocallos.itunes.api

import com.riocallos.itunes.models.SearchResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiService @Inject constructor() {

    private val handler = ApiHandler.create()

    /**
     * Call search API endpoint.
     *
     * @property term [String] search query.
     * @property country [String] location filter.
     * @property media [String] type filter.
     */
    fun search(term: String, country: String, media: String): Single<SearchResponse> {
        return handler.search(term, country, media)
    }

}