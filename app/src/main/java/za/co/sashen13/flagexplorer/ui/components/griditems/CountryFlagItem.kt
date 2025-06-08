package za.co.sashen13.flagexplorer.ui.components.griditems

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse

@Composable
fun CountryFlagItem(
    country: CountryResponse,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        Image(
            painter = rememberAsyncImagePainter(
                model = country.flag,
                imageLoader = imageLoader
            ),
            contentDescription = "Flag of ${country.name}",
            modifier = Modifier
                .width(120.dp)
                .height(80.dp),
            contentScale = ContentScale.Crop
        )
        // TODO Error maybe show a default image - loading state put a circular indicator
    }
}