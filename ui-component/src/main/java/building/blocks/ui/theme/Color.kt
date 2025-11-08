package building.blocks.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val Black200 = Color(0xFF1D1C1C)
internal val White200 = Color(0xFFFDFDFD)
internal val Grey = Color(0xFFEBEAEF)
internal val Peach = Color(0xFFEE3140)

/**
 * Represents a palette of extra colors used in the application's UI.
 *
 * This class encapsulates various colors that are not part of the primary or secondary
 * color schemes but are used for specific UI elements or states.
 *
 * @property darkGrey A darker shade of grey, potentially used for backgrounds or text. Defaults to [Color.Unspecified].
 * @property scrimColor A list of colors representing a scrim effect, such as a semi-transparent overlay.
 *                       Typically, the list contains at least two colors for gradient effects. Defaults to
 *                       a list of two [Color.Unspecified] colors.
 * @property onScrimColor The color used for elements displayed on top of the scrim. Defaults to [Color.Unspecified].
 * @property deselectedPageIndicator The color of the page indicator when a page is not selected.
 * Defaults to [Color.Unspecified].
 * @property dividerColor The color used for dividing lines or separators in the UI. Defaults to [Color.Unspecified].
 * @property successColor The color representing a successful state or action, often a shade of green.
 * Defaults to [Color.Unspecified].
 * @property inActiveStarColor The color of a star when it is in an inactive state (e.g., not filled).
 * Defaults to [Color.Unspecified].
 * @property shimmerColors A list of colors used for shimmer loading effects. Typically contains multiple colors
 * to create the shimmering animation. Defaults to a list of three [Color.Unspecified] colors.
 * @property selectedNavigationItemColor The color of a navigation item when it is selected.
 * Defaults to [Color.Unspecified].
 */
@Immutable
public data class ExtraColorsPalette(
    val darkGrey: Color = Color.Unspecified,
    val scrimColor: List<Color> = listOf(Color.Unspecified, Color.Unspecified),
    val onScrimColor: Color = Color.Unspecified,
    val deselectedPageIndicator: Color = Color.Unspecified,
    val dividerColor: Color = Color.Unspecified,
    val successColor: Color = Color.Unspecified,
    val inActiveStarColor: Color = Color.Unspecified,
    val shimmerColors: List<Color> = listOf(
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
    ),
    val selectedNavigationItemColor: Color = Color.Unspecified
)

/**
 * [CompositionLocal] that provides the current [ExtraColorsPalette].
 *
 * This local is used to access additional color definitions that may not be part of the
 * standard Material Design color scheme. This allows custom colors to be defined and
 * used throughout the composable hierarchy.
 *
 * By default, it provides an empty [ExtraColorsPalette], but it can be overridden at any
 * point in the composition to provide a custom set of extra colors.
 *
 * To access colors from the palette:
 * you should access the current palette using `LocalExtraColorsPalette.current` and then the color you need.
 *
 * To define a new set of colors and provide them to a subtree:
 * Use `CompositionLocalProvider` to override `LocalExtraColorsPalette` with your custom `ExtraColorsPalette` instance.
 * All composables within the `CompositionLocalProvider` block will then have access to the provided colors.
 *
 * @see ExtraColorsPalette
 */
internal val LocalExtraColorsPalette = staticCompositionLocalOf { ExtraColorsPalette() }

/**
 * Defines an additional color palette specific for the light theme.
 *
 * This object contains colors beyond the standard Material Design color scheme,
 * tailored for use in specific UI elements within the light theme.
 *
 * @property darkGrey A dark grey color used for various text or background elements.
 * @property scrimColor A list of colors used for scrim overlays. Usually the first element
 *                     is a semi-transparent color and the second is transparent.
 *                     This is useful for creating gradient scrims.
 * @property onScrimColor The color of content overlaid on top of the scrim.
 * @property deselectedPageIndicator The color used for page indicators when they are not active.
 * @property dividerColor The color used for dividers between UI elements.
 * @property successColor A color to indicate successful actions or states.
 * @property inActiveStarColor The color of a star when it is not active (e.g., in a rating system).
 * @property shimmerColors A list of colors used to create a shimmer effect, commonly used
 *                         during loading or placeholder states. The colors define the
 *                         gradient of the shimmer animation.
 * @property selectedNavigationItemColor The color used for navigation items when they are selected.
 */
internal val LightExtraColorsPalette = ExtraColorsPalette(
    darkGrey = Color(color = 0xFF706E75),
    scrimColor = listOf(Color(color = 0x66000000), Color.Transparent),
    onScrimColor = Color.White,
    deselectedPageIndicator = Color(color = 0x99716F75),
    dividerColor = Color.LightGray,
    successColor = Color.Green,
    inActiveStarColor = Color.LightGray,
    shimmerColors = listOf(
        Color(0xFFD6D6D6),
        Color(0xFFB8B8B8),
        Color(0xFFD6D6D6),
    ),
    selectedNavigationItemColor = Color(0xFFEB914C)
)

/**
 * Represents an additional color palette designed for dark themes.
 *
 * This palette contains colors that complement the standard Material Design dark theme
 * colors and provides specific colors for custom UI elements.
 *
 * @property darkGrey A shade of dark grey often used for backgrounds or surfaces in dark mode.
 * @property scrimColor A list of colors used for scrim overlays. It provides a gradient from semi-transparent black
 * to transparent.
 * @property onScrimColor The color of content placed on top of a scrim. Typically white for good contrast.
 * @property deselectedPageIndicator The color used for page indicators when they are not selected.
 * @property dividerColor The color used for dividers between UI elements.
 * @property successColor The color used to indicate success or positive feedback.
 * @property inActiveStarColor The color of inactive star elements, often used in rating systems.
 * @property shimmerColors A list of colors used to create a shimmer effect, typically for loading indicators.
 * @property selectedNavigationItemColor The color for a selected item in a navigation component.
 */
internal val DarkExtraColorsPalette = ExtraColorsPalette(
    darkGrey = Color(color = 0xFFC7C7CC),
    scrimColor = listOf(Color(color = 0x66000000), Color.Transparent),
    onScrimColor = Color.White,
    deselectedPageIndicator = Color(color = 0x99716F75),
    dividerColor = Color.LightGray,
    successColor = Color(0xFF00E600),
    inActiveStarColor = Color(0xB3CCCCCC),
    shimmerColors = listOf(
        Color(0XFF3A3A3A),
        Color(0XFF4A4A4A),
        Color(0XFF3A3A3A),
    ),
    selectedNavigationItemColor = Color(0xFFFF9800)
)

/**
 * Retrieves the current [ExtraColorsPalette] from the [MaterialTheme].
 *
 * This property provides access to a custom set of colors that extend the standard
 * [MaterialTheme.colorScheme]. You can use these colors to style your UI elements
 * with colors beyond those defined in the default Material Design color system.
 *
 * To define the values for `extraColors`, you typically use a custom `CompositionLocal`
 * (like [LocalExtraColorsPalette]) and provide it within your Composable hierarchy.
 * This allows you to customize the extra color values at different levels of your UI.
 *
 * @see LocalExtraColorsPalette
 */
public val MaterialTheme.extraColors: ExtraColorsPalette
    @Composable @ReadOnlyComposable
    get() = LocalExtraColorsPalette.current
