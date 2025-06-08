package za.co.sashen13.flagexplorer.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import za.co.sashen13.flagexplorer.R
import za.co.sashen13.flagexplorer.data.remote.response.CountryDetailsResponse
import za.co.sashen13.flagexplorer.ui.components.scaffold.FlagExplorerScaffold
import za.co.sashen13.flagexplorer.ui.components.rows.InfoRow

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailsViewModel
) {
    val state by viewModel.screenState.collectAsState()

    CountryDetailsContent(
        state = state,
        onBackClicked = {
            navController.popBackStack()
        },
        retryGetCountryDetails = {
            viewModel.fetchCountryDetails()
        }
    )
}

@Composable
private fun CountryDetailsContent(
    state: DetailsScreenState,
    onBackClicked: () -> Unit = {},
    retryGetCountryDetails: () -> Unit = {},
) {
    FlagExplorerScaffold(
        title = state.countryName,
        onBackClick = onBackClicked
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            when (state.uiState) {
                is DetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailsUiState.Error -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Error: ${state.uiState.message}",
                                color = MaterialTheme.colorScheme.error
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            IconButton(
                                onClick = retryGetCountryDetails
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

                is DetailsUiState.Success -> {
                    val country = state.uiState.country

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = country.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        InfoRow(icon = Icons.Default.LocationOn,
                            label = stringResource(R.string.details_row_capital_title), value = country.capital)

                        InfoRow(icon = Icons.Default.AccountCircle,
                            label = stringResource(R.string.details_row_population_title), value = country.population.toString())
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CountryDetailsContentPreview() {
    val sampleCountry = CountryDetailsResponse(
        name = "Sampleland",
        flag = "https://flagcdn.com/w320/br.svg",
        capital = "Sample City",
        population = 1234567
    )
    val sampleState = DetailsScreenState(
        countryName = sampleCountry.name,
        uiState = DetailsUiState.Success(sampleCountry)
    )

    CountryDetailsContent(
        state = sampleState
    )
}