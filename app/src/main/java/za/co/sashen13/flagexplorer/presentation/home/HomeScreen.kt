package za.co.sashen13.flagexplorer.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.presentation.navigation.Screen
import za.co.sashen13.flagexplorer.ui.components.griditems.CountryFlagItem
import za.co.sashen13.flagexplorer.ui.components.scaffold.FlagExplorerScaffold

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state by viewModel.uiState.collectAsState()

    HomeContent(
        state = state,
        navController = navController
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    navController: NavController
) {
    FlagExplorerScaffold(title = "Flag Explorer") { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is HomeUiState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> {
                    val countries = (state as HomeUiState.Success).countries
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(8.dp),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(countries) { country ->
                            CountryFlagItem(country) {
                                navController.navigate(Screen.Detail.createRoute(country.name))
                            }
                        }
                    }
                }

                is HomeUiState.Error -> {
                    val message = (state as HomeUiState.Error).message
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Failed to load countries: $message",
                            modifier = Modifier.align(Alignment.Center)
                        )
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
        CountryResponse("Country A", "https://flagcdn.com/br.svg", "Capital A", 1000000),
        CountryResponse("Country B", "https://flagcdn.com/br.svg", "Capital B", 500000),
        CountryResponse("Country C", "https://flagcdn.com/br.svg", "Capital C", 750000)
    )

    val state = HomeUiState.Success(sampleCountries)
    val navController = rememberNavController()

    HomeContent(state = state, navController = navController)
}