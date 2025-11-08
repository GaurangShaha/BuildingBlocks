package building.blocks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import building.blocks.ui.modifier.shimmer
import building.blocks.ui.theme.extraColors
import coil.compose.SubcomposeAsyncImage

/**
 * A composable function that displays an image from a given URL.
 * It provides built-in loading and error placeholders.
 *
 * @param url The URL of the image to be loaded.
 * @param modifier Modifier to be applied to the image.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 *                        This should always be provided unless this image is used for decorative purposes,
 *                        and does not represent a meaningful action that a user can take.
 * @param contentScale The scaling logic to be applied to the image when it is drawn in its destination.
 *                     Defaults to [ContentScale.Crop].
 */
@Composable
public fun LazyImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        loading = { Box(modifier = Modifier.shimmer()) },
        error = { Box(modifier = Modifier.background(MaterialTheme.extraColors.darkGrey)) }
    )
}
