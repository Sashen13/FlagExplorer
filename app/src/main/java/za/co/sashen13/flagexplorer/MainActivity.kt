package za.co.sashen13.flagexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import za.co.sashen13.flagexplorer.presentation.navigation.NavGraph
import za.co.sashen13.flagexplorer.ui.theme.FlagExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlagExplorerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
