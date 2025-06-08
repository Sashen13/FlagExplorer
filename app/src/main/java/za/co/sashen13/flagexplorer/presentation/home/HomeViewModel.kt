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

    private val _screenState = MutableStateFlow(
        HomeScreenState(
            uiState = HomeUiState.Loading
        )
    )
    val screenState: StateFlow<HomeScreenState> = _screenState

    init {
        fetchCountries()
    }

     fun fetchCountries() {
        viewModelScope.launch(ioDispatcher) {
            when(val response = getAllCountriesUseCase.execute()) {
                is NetworkResult.Error ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = HomeUiState.Error(response.message ?: "Something went wrong")
                    )
                }
                is NetworkResult.Exception ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = HomeUiState.Error(response.e.message ?: "Something went wrong")
                    )
                }
                is NetworkResult.Success ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = HomeUiState.Success(response.body)
                    )
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

data class HomeScreenState(
    val uiState: HomeUiState
)