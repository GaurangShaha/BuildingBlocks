package building.blocks.ui.theme

import building.blocks.ui.helper.findActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 *  The light color palette for the application.
 *
 *  This palette defines the colors used in the light theme of the application,
 *  including primary, secondary, background, and surface colors, as well as
 *  their "on" colors (colors used for content displayed on top of them).
 *
 *  - `primary`: The primary color of the theme, used for key elements like the
 *    app bar. In this case, it's a lighter shade of black.
 *  - `primaryVariant`: A variant of the primary color, typically used for
 *    status bars or other elements that need a slightly different shade of the
 *    primary color. Here, it's pure black.
 *  - `secondary`: The secondary color, used for less prominent UI elements.
 *    Here, it's a lighter shade of white.
 *  - `secondaryVariant`: A variant of the secondary color, potentially used
 *    for emphasizing certain secondary elements. Here, it's pure white.
 *  - `background`: The color used for the background of screens. Here, it's a
 *    grey color.
 *  - `surface`: The color used for surfaces, such as cards or sheets. Here, it's
 *    the same grey color as the background.
 *  - `onPrimary`: The color used for content displayed on top of the primary
 *    color. Here, it's white.
 *  - `onSecondary`: The color used for content displayed on top of the
 *    secondary color. Here, it's black.
 *  - `onBackground`: The color used for content displayed on top of the
 *    background color. Here, it's black.
 *  - `onSurface`: The color used for content displayed on top of surfaces.
 *    Here, it's black.
 *  - `error`: The color used for error states, such as */
private val LightColorPalette = lightColors(
    primary = Black200,
    primaryVariant = Color.Black,
    secondary = White200,
    secondaryVariant = Color.White,
    background = Grey,
    surface = Grey,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Peach,
    onError = Color.White
)

/**
 *  This is the dark color palette for the application's UI theme.
 *  It defines the colors used for various UI elements when the dark theme is enabled.
 *
 *  The palette uses a dark background with lighter accents for primary and secondary elements.
 *  Text and icons are typically displayed in white for contrast against the dark background.
 *
 *  Key Color Roles:
 *    - `primary`: The primary color of the application, used for key UI elements. Here, it is set to a
 *    light gray (White200).
 *    - `primaryVariant`: A variant of the primary color, often used for status bars and other accents.
 *    Here, set to white.
 *    - `secondary`: The secondary color, used for elements that provide emphasis or differentiation.
 *    Here, it's a dark gray (Black200).
 *    - `secondaryVariant`: A variant of the secondary color. Here it's black.
 *    - `background`: The color used for the background of screens. Here, it's a dark gray (Black200).
 *    - `surface`: The color used for surfaces like cards and menus. Here, it's also a dark gray (Black200).
 *    - `onPrimary`: The color used for text and icons displayed on top of the primary color. Here it's Black.
 *    - `onSecondary`: The color used for text and icons displayed on top of the secondary color. Here it's White.
 *    - `onBackground`: The color used for text and icons displayed on top of the background color. Here it's White.
 *    - `onSurface`: The color used for text and icons displayed on top of the surface color. Here it's White.
 *    - `error`: The color used to indicate error states. Here it's Peach.
 *    - `onError`: The color used for text and icons displayed on top of the error color. Here it's white */
private val DarkColorPalette = darkColors(
    primary = White200,
    primaryVariant = Color.White,
    secondary = Black200,
    secondaryVariant = Color.Black,
    background = Black200,
    surface = Black200,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Peach,
    onError = Color.White
)

/**
 * FleaMarketTheme is a custom Compose theme for the Flea Market application.
 * It provides a unified look and feel across the app by defining colors, typography,
 * and shapes based on whether the user prefers a dark or light theme.
 * It also handles setting the status bar appearance.
 *
 * @param useDarkTheme Boolean indicating whether to use the dark theme. Defaults to the system's dark theme setting.
 * @param content The composable content that will be styled with this theme.
 */
@Composable
public fun FleaMarketTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extraColorsPalette: ExtraColorsPalette
    val extraTypography: ExtraTypography
    val colorPalette: Colors
    if (useDarkTheme) {
        extraColorsPalette = DarkExtraColorsPalette
        extraTypography = DarkExtraTypography
        colorPalette = DarkColorPalette
    } else {
        extraColorsPalette = LightExtraColorsPalette
        extraTypography = LightExtraTypography
        colorPalette = LightColorPalette
    }

    findActivity()?.let { activity ->
        val view = LocalView.current
        SideEffect {
            val window = activity.window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !useDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalExtraColorsPalette provides extraColorsPalette,
        LocalExtraTypography provides extraTypography,
        LocalExtraShape provides ExtraShapes
    ) {
        MaterialTheme(
            colors = colorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/**
 * A composable function that sets the status bar appearance to dark mode (light text and icons on dark background)
 * and handles the cleanup when the composable leaves the composition.
 *
 * This function utilizes [DisposableEffect] to ensure that the status bar's appearance is reset
 * to the system's default or user-specified preference (light or dark) when the composable is removed.
 *
 * @param useDarkTheme Determines whether to use dark theme for status bar during cleanup.
 * Defaults to [isSystemInDarkTheme].
 *                     - `true`: Status bar will be light (light text and icons on dark background)
 *                     on dispose if `useDarkTheme` is false.
 *                     - `false`: Status bar will be dark (dark text and icons on light background)
 *                     on dispose if `useDarkTheme` is false.
 *
 * Important: This relies on the activity containing the compose view. If no activity is found, it will do nothing.
 */
@Composable
public fun DarkStatusBarDisposableEffect(useDarkTheme: Boolean = isSystemInDarkTheme()) {
    findActivity()?.let { activity ->
        val view = LocalView.current
        DisposableEffect(Unit) {
            val window = activity.window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            onDispose {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !useDarkTheme
            }
        }
    }
}
