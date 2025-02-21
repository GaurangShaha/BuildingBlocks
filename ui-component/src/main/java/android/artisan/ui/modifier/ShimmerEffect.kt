package android.artisan.ui.modifier

import android.artisan.ui.theme.extraColors
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

private const val SHIMMER_ANIMATION_TIME = 1000

/**
 * Applies a shimmer effect to the composable it modifies.
 *
 * This modifier creates a shimmering animation that moves horizontally across the composable.
 * It's commonly used to indicate that content is loading or to draw attention to an element.
 *
 * The shimmer effect is achieved by creating a linear gradient brush that moves horizontally
 * across the composable's area. The gradient consists of a series of colors defined in
 * `MaterialTheme.extraColors.shimmerColors`.
 *
 * The animation is infinite and repeats, creating a continuous shimmer. The speed and
 * intensity of the shimmer are controlled by the `SHIMMER_ANIMATION_TIME` constant and
 * the gradient's color stops.
 *
 * The size of the composable is dynamically measured using `onGloballyPositioned`, ensuring
 * the shimmer animation adapts to the actual dimensions of the element.
 *
 * @return A [Modifier] that applies the shimmer effect.
 */
public fun Modifier.shimmer(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(SHIMMER_ANIMATION_TIME)
        ),
        label = "ShifferStartOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = MaterialTheme.extraColors.shimmerColors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}
