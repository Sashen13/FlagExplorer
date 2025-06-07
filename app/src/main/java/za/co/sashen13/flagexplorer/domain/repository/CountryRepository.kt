package za.co.sashen13.flagexplorer.domain.repository

import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse

interface CountryRepository {
    suspend fun getCountries(): NetworkResult<List<CountryResponse>>
    suspend fun getCountryDetails(countryName: String): NetworkResult<CountryResponse>
}