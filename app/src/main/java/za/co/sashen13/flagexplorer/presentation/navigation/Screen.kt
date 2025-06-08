package za.co.sashen13.flagexplorer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel
import za.co.sashen13.flagexplorer.presentation.detail.DetailScreen
import za.co.sashen13.flagexplorer.presentation.home.HomeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{countryName}") {
        fun createRoute(countryName: String) = "detail/$countryName"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: "" //TODO - Remove from here use it from the state
            DetailScreen(
                countryName,
                navController,
                viewModel = koinViewModel()
            )
        }
    }
}