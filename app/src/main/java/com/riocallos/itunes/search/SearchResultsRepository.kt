package com.riocallos.itunes.search

import com.riocallos.itunes.api.ApiCallback
import com.riocallos.itunes.api.ApiService
import com.riocallos.itunes.models.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Repository for search results.
 *
 */
class SearchResultsRepository @Inject constructor() {

    @Inject
    lateinit var apiService: ApiService

    /**
     * Get search results from API.
     *
     * @property term [String] search query.
     * @property country [String] location filter.
     * @property media [String] type filter.
    */
    fun search(term: String, country: String, media: String, callback: ApiCallback<SearchResponse>): DisposableSingleObserver<SearchResponse> {
        return apiService.search(term, country, media)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<SearchResponse>() {

                override fun onStart() {
                    super.onStart()
                    callback.onStart()
                }

                override fun onSuccess(t: SearchResponse) {
                    callback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    callback.onError(e.message)
                }

            })
    }

}
