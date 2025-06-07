package za.co.sashen13.flagexplorer.domain.usecases

import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository

interface IGetCountryDetailsUseCase {
    suspend fun execute(
        countryName: String
    ): NetworkResult<CountryResponse>
}

class GetCountryDetailsUseCase(
    private val repository: CountryRepository
) : IGetCountryDetailsUseCase {
    override suspend fun execute(
        countryName: String
    ): NetworkResult<CountryResponse> {
        return repository.getCountryDetails(
            countryName = countryName
        )
    }
}