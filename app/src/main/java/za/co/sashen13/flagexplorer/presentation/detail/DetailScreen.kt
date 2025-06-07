package za.co.sashen13.flagexplorer.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse
import za.co.sashen13.flagexplorer.ui.components.image.AnimatedFlagImage
import za.co.sashen13.flagexplorer.ui.components.scaffold.FlagExplorerScaffold

@Composable
fun DetailScreen(
    countryName: String,
    navController: NavController,
    viewModel: DetailsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    CountryDetailsContent(
        state = state,
        navController = navController,
        countryName
    )
}

@Composable
fun CountryDetailsContent(
    state: DetailsUiState,
    navController: NavController,
    countryName: String
) {
    FlagExplorerScaffold(
        title = countryName,
        onBackClick = { navController.popBackStack() }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            when (state) {
                is DetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailsUiState.Error -> {
                    Text(
                        "Error: ${(state as DetailsUiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is DetailsUiState.Success -> {
                    val country = state.country

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AnimatedFlagImage(flagUrl = country.flag)

                        Text(
                            text = country.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        InfoRow(icon = Icons.Default.LocationOn, label = "Capital", value = country.capital ?: "N/A")
                        InfoRow(icon = Icons.Default.AccountCircle, label = "Population", value = country.population?.toString() ?: "N/A")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "$label:", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsContentPreview() {
    val sampleCountry = CountryResponse(
        name = "Sampleland",
        flag = "https://flagcdn.com/w320/br.svg",
        capital = "Sample City",
        population = 1234567
    )
    val state = DetailsUiState.Success(sampleCountry)
    val navController = rememberNavController()

    CountryDetailsContent(
        state = state,
        navController = navController,
        countryName = sampleCountry.name
    )
}
