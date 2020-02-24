package com.riocallos.itunes.api

/**
 * Interface callback for API requests.
 *
 */
interface ApiCallback<T> {

    /**
     * Callback if API request was started.
     *
     */
    fun onStart()

    /**
     * Callback if API request is successful.
     *
     */
    fun onSuccess(obj: T?)

    /**
     * Callback if API request fails.
     *
     */
    fun onError(error: String?)

}