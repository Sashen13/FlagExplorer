package za.co.sashen13.flagexplorer.domain.usecases

import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryDetailsResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository

interface IGetCountryDetailsUseCase {
    suspend fun execute(
        countryName: String
    ): NetworkResult<CountryDetailsResponse>
}

class GetCountryDetailsUseCase(
    private val repository: CountryRepository
) : IGetCountryDetailsUseCase {
    override suspend fun execute(
        countryName: String
    ): NetworkResult<CountryDetailsResponse> {
        return repository.getCountryDetails(
            countryName = countryName
        )
    }
}