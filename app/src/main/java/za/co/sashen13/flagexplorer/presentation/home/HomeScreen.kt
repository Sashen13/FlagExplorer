package za.co.sashen13.flagexplorer.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import za.co.sashen13.flagexplorer.R
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.presentation.navigation.Screen
import za.co.sashen13.flagexplorer.ui.components.griditems.CountryFlagItem
import za.co.sashen13.flagexplorer.ui.components.scaffold.FlagExplorerScaffold

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.screenState.collectAsState()

    HomeContent(
        state = state,
        flagSelected = { country ->
            navController.navigate(Screen.Detail(country))
        },
        retryGetCountry = {
            viewModel.fetchCountries()
        }
    )
}

@Composable
private fun HomeContent(
    state: HomeScreenState,
    flagSelected: (countryName: String) -> Unit = {},
    retryGetCountry: () -> Unit = {}
) {
    FlagExplorerScaffold(title = stringResource(R.string.app_name)) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state.uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> {
                    val countries = state.uiState.countries
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(8.dp),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(countries) { country ->
                            CountryFlagItem(country) {
                                flagSelected(country.name)
                            }
                        }
                    }
                }

                is HomeUiState.Error -> {
                    val message = state.uiState.message
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                stringResource(R.string.error_failed_to_load, message)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            IconButton(
                                onClick = retryGetCountry
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Retry",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleCountries = listOf(
        CountryResponse("Country A", "https://flagcdn.com/br.svg"),
        CountryResponse("Country B", "https://flagcdn.com/br.svg"),
        CountryResponse("Country C", "https://flagcdn.com/br.svg")
    )

    val sampleState = HomeScreenState(
        uiState = HomeUiState.Success(sampleCountries)
    )

    HomeContent(
        state = sampleState
    )
}