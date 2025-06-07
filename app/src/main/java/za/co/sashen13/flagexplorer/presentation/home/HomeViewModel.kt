package za.co.sashen13.flagexplorer.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.usecases.IGetAllCountriesUseCase

class HomeViewModel(
    private val getAllCountriesUseCase: IGetAllCountriesUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch(ioDispatcher) {
            when(val response = getAllCountriesUseCase.execute()) {
                is NetworkResult.Error ->  {
                    _uiState.value = HomeUiState.Error(response.message ?: "Unknown error")
                }
                is NetworkResult.Exception ->  {
                    _uiState.value = HomeUiState.Error(response.e.message ?: "Unknown error")
                }
                is NetworkResult.Success ->  {
                    _uiState.value = HomeUiState.Success(response.body)
                }
            }
        }
    }
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val countries: List<CountryResponse>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}