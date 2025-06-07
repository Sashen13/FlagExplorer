package za.co.sashen13.flagexplorer.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse

interface CountryApi {
    @GET("countries")
    suspend fun getCountries(): Response<List<CountryResponse>>

    @GET("countries/{name}")
    suspend fun getCountryDetails(@Path("name") name: String): Response<CountryResponse>
}