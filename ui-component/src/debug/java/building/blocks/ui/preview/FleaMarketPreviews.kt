package building.blocks.ui.preview

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.navigation.compose.rememberNavController
import building.blocks.ui.component.shared.SharedUIController
import building.blocks.ui.component.snackbar.model.SnackbarDetails
import building.blocks.ui.compositionlocal.LocalNavController
import building.blocks.ui.compositionlocal.LocalSharedUIController
import building.blocks.ui.compositionlocal.LocalWindowSizeClass
import building.blocks.ui.theme.FleaMarketTheme

/**
 * [FleaMarketPreviews] is a custom annotation that provides a set of predefined
 * [androidx.compose.ui.tooling.preview.Preview] configurations for a variety of device types and UI modes.
 *
 * This annotation simplifies the process of creating multiple previews for different scenarios,
 * including:
 *
 * - **Day and Night Modes:** Previews are available for both light (day) and dark (night) themes.
 * - **Phone and Tablet Devices:** Previews are tailored for both phone-sized and tablet-sized screens.
 * - **Portrait and Landscape Orientations:** Previews are configured for both portrait and landscape device
 * orientations.
 *
 * By using [FleaMarketPreviews], developers can easily visualize their composables across
 * a wide range of device configurations and quickly identify potential layout or theming issues.
 *
 * **Usage:**
 * ```
 * @FleaMarketPreviews
 * @Composable
 * fun MyComposablePreview() {
 *   MyComposable()
 * }
 * ```
 *
 *This will generate eight previews:
 * - Phone - Portrait (Day)
 * - Phone - Portrait (Night)
 * - Phone - Landscape (Day)
 * - Phone - Landscape (Night)
 * - Tablet - Landscape (Day)
 * - Tablet - Landscape (Night)
 * - Tablet - Portrait (Day)
 * - Tablet - Portrait (Night)
 */
@Preview(group = "Day", name = "Phone - Portrait", device = "spec:width=411dp,height=891dp")
@Preview(
    group = "Night",
    name = "Phone - Portrait",
    device = "spec:width=411dp,height=891dp",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    group = "Day",
    name = "Phone - Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)
@Preview(
    group = "Night",
    name = "Phone - Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    group = "Day",
    name = "Tablet - Landscape",
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Preview(
    group = "Night",
    name = "Tablet - Landscape",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    group = "Day",
    name = "Tablet - Portrait",
    device = "spec:width=1280dp,height=800dp,orientation=portrait"
)
@Preview(
    group = "Night",
    name = "Tablet - Portrait",
    device = "spec:width=1280dp,height=800dp,orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
public annotation class FleaMarketPreviews

/**
 * A composable function used to preview UI elements within the [FleaMarketTheme], providing necessary context for
 * window size and navigation.
 *
 * This function wraps the provided content within the [FleaMarketTheme], ensuring that the UI elements are rendered
 * with the correct theming. It also provides composition-local values for window size class, navigation controller,
 * and a shared UI controller, which are commonly used by the UI components.
 *
 * @param content A composable lambda representing the UI content to be previewed. Defaults to an empty lambda.
 *
 * **Usage Example:**
 *
 * ```kotlin
 * @FleaMarketPreviews
 * @Composable
 * fun MyComponentPreview() {
 *     FleaMarketThemePreview {
 *         MyComponent()
 *     }
 * }
 *
 * @Composable
 * fun MyComponent() {
 *     // Your UI component implementation here
 *     Text(text = "Hello from MyComponent!")
 * }
 * ```
 */
@Suppress("ModifierMissing")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
public fun FleaMarketThemePreview(content: @Composable () -> Unit = {}) {
    FleaMarketTheme {
        BoxWithConstraints {
            val calculateFromSize =
                WindowSizeClass.calculateFromSize(DpSize(width = maxWidth, height = maxHeight))
            CompositionLocalProvider(
                LocalWindowSizeClass provides calculateFromSize,
                LocalNavController provides rememberNavController(),
                LocalSharedUIController provides sharedUIController()
            ) {
                Surface {
                    content()
                }
            }
        }
    }
}

/**
 * Creates and returns a [SharedUIController] instance that provides a default, no-operation implementation.
 *
 * This function is intended for scenarios where a [SharedUIController] is required, but the specific
 * UI actions (like showing a snackbar or resetting its details) are not needed or are handled
 * elsewhere. It provides a safe default implementation to avoid null checks or unexpected behavior.
 *
 * The returned [SharedUIController] instance will:
 *   - Do nothing when `showSnackbar` is called.
 *   - Do nothing when `resetSnackbarDetails` is called.
 *
 * @return A [SharedUIController] instance that performs no operations.
 */
private fun sharedUIController() = object : SharedUIController {
    override fun showSnackbar(snackbarDetails: SnackbarDetails) {
        // do nothing
    }

    override fun resetSnackbarDetails() {
        // do nothing
    }
}
