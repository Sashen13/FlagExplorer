package za.co.sashen13.flagexplorer.domain.usecases

import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository

interface IGetAllCountriesUseCase {
    suspend fun execute(): NetworkResult<List<CountryResponse>>
}

class GetAllCountriesUseCase(
    private val repository: CountryRepository
) : IGetAllCountriesUseCase {
    override suspend fun execute(): NetworkResult<List<CountryResponse>> {
        return repository.getCountries()
    }
}