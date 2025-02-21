package android.artisan.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp)
)

/**
 * Represents a collection of extra shapes beyond the standard ones provided by Compose.
 *
 * This data class holds references to custom shapes that can be used within Compose UI elements.
 * It provides a convenient way to define and reuse custom shapes throughout your application.
 *
 * @property xlargeShape A custom shape, intended for use as an "extra large" shape. Defaults to an empty shape.
 *                       This can be used for elements that need a distinct, large shape.
 *                       For example, a unique background or a custom-styled button.
 * @property capsuleShape A custom shape, designed to represent a capsule. Defaults to an empty shape.
 *                        Capsule shapes are typically rounded rectangles with semi-circular ends,
 *                        often used for buttons, badges, or tags.
 */
@Immutable
public data class ExtraShape(
    val xlargeShape: Shape = GenericShape { _, _ -> },
    val capsuleShape: Shape = GenericShape { _, _ -> }
)

/**
 * [CompositionLocal] used to provide an [ExtraShape] instance to the Compose hierarchy.
 *
 * This allows components deeper in the hierarchy to access and potentially modify
 * the extra shape being used without needing to pass it down through multiple
 * levels of parameters.
 *
 * The default value is an instance of [ExtraShape] with default properties.
 *
 * Components that want to provide a custom [ExtraShape] should use
 * `CompositionLocalProvider` and provide their custom value to the
 * `LocalExtraShape` composition local key.
 *
 * @see ExtraShape
 * @see androidx.compose.runtime.CompositionLocal
 * @see androidx.compose.runtime.CompositionLocalProvider
 */
internal val LocalExtraShape = staticCompositionLocalOf { ExtraShape() }

/**
 * Defines extra shapes beyond the Material Design baseline shape system.
 *
 * This object provides pre-defined shapes that can be used throughout the UI,
 * offering more variety than the standard small, medium, and large shapes.
 *
 * @property xlargeShape A large rounded corner shape with a corner radius of 32dp.
 *                      Useful for large components like dialogs or cards where a prominent
 *                      rounding effect is desired.
 * @property capsuleShape A capsule-shaped shape (a rounded rectangle where the corner radius
 *                       is half the height of the rectangle). It's represented by a [CircleShape]
 *                       in this implementation. This is commonly used for buttons, chips or pill shaped UI elements.
 */
internal val ExtraShapes = ExtraShape(
    xlargeShape = RoundedCornerShape(32.dp),
    capsuleShape = CircleShape
)

/**
 * Retrieves the current [ExtraShape] from the [LocalExtraShape] composition local.
 *
 * This property provides access to custom shapes defined outside of the standard
 * Material Design shapes. It allows you to define and apply additional shapes
 * for components within your application.
 *
 * Use cases include:
 *
 * - Applying unique shapes to specific components not covered by standard
 *   Material Design shapes.
 * - Providing application-wide theming for custom shapes.
 * - Defining variations of standard shapes, like a rounded rectangle with
 *   uneven corner radii.
 *
 * The [ExtraShape] is provided via [CompositionLocalProvider] using the [LocalExtraShape] key.
 * If no [ExtraShape] has been provided, it will default to a [androidx.compose.foundation.shape.RectangleShape]
 * for `someShape`.
 *
 * @see LocalExtraShape
 */
public val MaterialTheme.extraShape: ExtraShape
    @Composable @ReadOnlyComposable
    get() = LocalExtraShape.current
