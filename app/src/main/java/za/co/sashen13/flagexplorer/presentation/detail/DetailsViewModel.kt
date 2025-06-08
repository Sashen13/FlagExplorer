package za.co.sashen13.flagexplorer.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryDetailsResponse
import za.co.sashen13.flagexplorer.domain.usecases.IGetCountryDetailsUseCase
import za.co.sashen13.flagexplorer.presentation.navigation.Screen

class DetailsViewModel(
    private val getCountryDetailsUseCase: IGetCountryDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val countryName = savedStateHandle.toRoute<Screen.Detail>().countryName

    private val _screenState = MutableStateFlow(
        DetailsScreenState(
            countryName = countryName,
            uiState = DetailsUiState.Loading
        )
    )
    val screenState: StateFlow<DetailsScreenState> = _screenState

    init {
        fetchCountryDetails()
    }

    fun fetchCountryDetails() {
        viewModelScope.launch(ioDispatcher) {
            when(val response = getCountryDetailsUseCase.execute(countryName)) {
                is NetworkResult.Error ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = DetailsUiState.Error(response.message ?: "Something went wrong")
                    )
                }
                is NetworkResult.Exception ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = DetailsUiState.Error(response.e.message ?: "Something went wrong")
                    )
                }
                is NetworkResult.Success ->  {
                    _screenState.value = _screenState.value.copy(
                        uiState = DetailsUiState.Success(response.body)
                    )
                }
            }
        }
    }
}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Success(val country: CountryDetailsResponse) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}

data class DetailsScreenState(
    val countryName: String,
    val uiState: DetailsUiState
)