package com.riocallos.itunes.api

import com.riocallos.itunes.BuildConfig
import com.riocallos.itunes.models.SearchResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * Interface to call and handle API endpoints.
 *
 */
interface ApiHandler {

    companion object {
        fun create(): ApiHandler {

            val builder = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)

            if(BuildConfig.DEBUG) {
                builder.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
                .create(ApiHandler::class.java)
        }

    }

    /**
     * Search API endpoint.
     *
     * @property term [String] search query.
     * @property country [String] location filter.
     * @property media [String] type filter.
     */
    @GET("search")
    fun search(@Query("term") term: String, @Query("country") country: String, @Query("media") media: String): Single<SearchResponse>


}