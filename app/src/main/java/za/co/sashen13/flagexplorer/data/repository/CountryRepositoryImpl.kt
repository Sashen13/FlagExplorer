package za.co.sashen13.flagexplorer.data.repository

import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.common.wrappers.handleApi
import za.co.sashen13.flagexplorer.data.remote.CountryApi
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val api: CountryApi
) : CountryRepository {
    override suspend fun getCountries(): NetworkResult<List<CountryResponse>> {
        return handleApi {api.getCountries()}
    }

    override suspend fun getCountryDetails(countryName: String): NetworkResult<CountryResponse> {
        return handleApi {api.getCountryDetails(countryName)}
    }
}