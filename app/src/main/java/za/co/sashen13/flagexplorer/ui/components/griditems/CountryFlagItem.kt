package za.co.sashen13.flagexplorer.ui.components.griditems

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import za.co.sashen13.flagexplorer.R
import za.co.sashen13.flagexplorer.data.remote.response.CountryResponse

@Composable
fun CountryFlagItem(
    country: CountryResponse,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = country.flag,
        imageLoader = imageLoader
    )

    val painterState = painter.state

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(120.dp)
                .height(80.dp)
        ) {
            when (painterState) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
                is AsyncImagePainter.State.Error -> {
                    Image(
                        painter = painterResource(id = R.mipmap.flag_default),
                        contentDescription = "Flag not available for ${country.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    Image(
                        painter = painter,
                        contentDescription = "Flag of ${country.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}