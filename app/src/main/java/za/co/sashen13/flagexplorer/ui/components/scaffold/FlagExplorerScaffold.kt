package za.co.sashen13.flagexplorer.ui.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagExplorerScaffold(
    title: String,
    modifier: Modifier = Modifier,
    topBarColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    onBackClick: (() -> Unit)? = null,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor,
                    titleContentColor = contentColor
                ),
                navigationIcon = {
                    onBackClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = contentColor
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier,
        content = content
    )
}