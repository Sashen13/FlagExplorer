package za.co.sashen13.flagexplorer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.repository.CountryRepository
import za.co.sashen13.flagexplorer.domain.usecases.GetAllCountriesUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllCountriesUseCaseTest {

    private lateinit var repository: CountryRepository
    private lateinit var useCase: GetAllCountriesUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetAllCountriesUseCase(repository)
    }

    @Test
    fun `execute returns success when repository returns success`() = runTest {
        val dummyCountries = listOf(
            CountryResponse(name = "Country1", flag = "", capital = "Capital1", population = 1000),
            CountryResponse(name = "Country2", flag = "", capital = "Capital2", population = 2000)
        )

        whenever(repository.getCountries()).thenReturn(NetworkResult.Success(dummyCountries))

        val result = useCase.execute()

        assertTrue(result is NetworkResult.Success)
        assertEquals(dummyCountries, (result as NetworkResult.Success).body)
    }

    @Test
    fun `execute returns error when repository returns error`() = runTest {
        val errorMessage = "Failed to fetch"
        whenever(repository.getCountries()).thenReturn(NetworkResult.Error(500,errorMessage))

        val result = useCase.execute()

        assertTrue(result is NetworkResult.Error)
        assertEquals(errorMessage, (result as NetworkResult.Error).message)
    }

    @Test
    fun `execute returns exception when repository returns exception`() = runTest {
        val exception = RuntimeException("Exception occurred")
        whenever(repository.getCountries()).thenReturn(NetworkResult.Exception(exception))

        val result = useCase.execute()

        assertTrue(result is NetworkResult.Exception)
        assertEquals(exception, (result as NetworkResult.Exception).e)
    }
}
