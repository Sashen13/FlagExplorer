package za.co.sashen13.flagexplorer.ui.components.image

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder

@Composable
fun AnimatedFlagImage(flagUrl: String) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.6f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
    }

    Image(
        painter = rememberAsyncImagePainter(model = flagUrl, imageLoader = imageLoader),
        contentDescription = "Flag of country",
        modifier = Modifier
            .width(180.dp)
            .height(120.dp)
            .scale(scaleAnim.value)
            .alpha(alphaAnim.value)
            .padding(bottom = 16.dp),
        contentScale = ContentScale.Crop
    )
}
