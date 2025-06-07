package za.co.sashen13.flagexplorer.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import za.co.sashen13.flagexplorer.data.common.wrappers.NetworkResult
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.domain.usecases.IGetCountryDetailsUseCase

class DetailsViewModel(
    private val getCountryDetailsUseCase: IGetCountryDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState

    private val countryName: String = savedStateHandle["countryName"] ?: ""

    init {
        fetchCountryDetails(
            countryName = countryName
        )
    }

    private fun fetchCountryDetails(
        countryName: String
    ) {
        viewModelScope.launch(ioDispatcher) {
            when(val response = getCountryDetailsUseCase.execute(countryName)) {
                is NetworkResult.Error ->  {
                    _uiState.value = DetailsUiState.Error(response.message ?: "Unknown error")
                }
                is NetworkResult.Exception ->  {
                    _uiState.value = DetailsUiState.Error(response.e.message ?: "Unknown error")
                }
                is NetworkResult.Success ->  {
                    _uiState.value = DetailsUiState.Success(response.body)
                }
            }
        }
    }
}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Success(val country: CountryResponse) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}