package za.co.sashen13.flagexplorer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository
import za.co.sashen13.flagexplorer.domain.usecases.GetCountryDetailsUseCase

class GetCountryDetailsUseCaseTest {

    private lateinit var repository: CountryRepository
    private lateinit var useCase: GetCountryDetailsUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetCountryDetailsUseCase(repository)
    }

    @Test
    fun `execute returns success when repository returns success`() = runTest {
        val countryName = "Country1"
        val dummyCountry = CountryResponse(name = countryName, flag = "", capital = "Capital1", population = 1000)

        whenever(repository.getCountryDetails(countryName)).thenReturn(NetworkResult.Success(dummyCountry))

        val result = useCase.execute(countryName)

        assertTrue(result is NetworkResult.Success)
        assertEquals(dummyCountry, (result as NetworkResult.Success).body)
    }

    @Test
    fun `execute returns error when repository returns error`() = runTest {
        val countryName = "Country1"
        val errorMessage = "Failed to fetch"
        whenever(repository.getCountryDetails(countryName)).thenReturn(NetworkResult.Error(500, errorMessage))

        val result = useCase.execute(countryName)

        assertTrue(result is NetworkResult.Error)
        assertEquals(errorMessage, (result as NetworkResult.Error).message)
    }

    @Test
    fun `execute returns exception when repository returns exception`() = runTest {
        val countryName = "Country1"
        val exception = RuntimeException("Exception occurred")
        whenever(repository.getCountryDetails(countryName)).thenReturn(NetworkResult.Exception(exception))

        val result = useCase.execute(countryName)

        assertTrue(result is NetworkResult.Exception)
        assertEquals(exception, (result as NetworkResult.Exception).e)
    }
}
