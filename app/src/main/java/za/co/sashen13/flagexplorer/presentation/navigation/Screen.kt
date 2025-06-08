package za.co.sashen13.flagexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import za.co.sashen13.flagexplorer.presentation.detail.DetailScreen
import za.co.sashen13.flagexplorer.presentation.home.HomeScreen

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data class Detail(
        val countryName: String,
    ) : Screen()
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }

        composable<Screen.Detail> {
            DetailScreen(
                navController = navController,
                viewModel = koinViewModel(),
            )
        }
    }
}