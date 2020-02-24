package com.riocallos.itunes.models

import com.google.gson.annotations.SerializedName

/**
 * Response body from search API.
 *
 * @property count [Int] size of results
 * @property results [Array<Result>] list
 */
class SearchResponse(

    @SerializedName("resultCount")
    var count: Int = 0,

    @SerializedName("results")
    var results: Array<SearchResult> = emptyArray()

)