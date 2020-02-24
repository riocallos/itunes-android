package com.riocallos.itunes.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Model for search result.
 *
 */
@Entity(tableName = "search_results")
data class SearchResult(@SerializedName("trackId")
                  @PrimaryKey var id: String = "") {

    @SerializedName("kind")
    var kind: String = ""

    @SerializedName("trackName")
    var name: String = ""

    @SerializedName("shortDescription")
    var description: String = ""

    @SerializedName("longDescription")
    var details: String = ""

    @SerializedName("artistName")
    var artist: String = ""

    @SerializedName("artworkUrl100")
    var image: String = ""

    @SerializedName("previewUrl")
    var preview: String = ""

    @SerializedName("trackPrice")
    var price: String = ""

    @SerializedName("primaryGenreName")
    var genre: String = ""

}