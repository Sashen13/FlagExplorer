package za.co.sashen13.flagexplorer.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDetailsResponse(
    @Json(name = "name")
    val name: String,

    @Json(name = "flag")
    val flag: String,

    @Json(name = "capital")
    val capital: String,

    @Json(name = "population")
    val population: Long
)