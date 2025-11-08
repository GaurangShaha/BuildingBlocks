package building.blocks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable function that provides a screen layout with a navigation rail on the left side.
 * It also includes a content area for the main screen content and a Snackbar host for displaying
 * snackbars.
 *
 * @param navHost A composable function representing the navigation host. This is where the
 *        different screens of the app will be displayed based on the navigation state.
 * @param snackbarHostState The state object to control and manage the display of snackbars.
 * @param screens A list of [NavigationBarScreen] objects, each representing a screen accessible
 *        via the navigation rail. These screens will be displayed as items in the rail.
 * @param modifier Modifier to be applied to the root Row.
 *
 * This function arranges the UI elements in the following structure:
 * - A [Row] to lay out the navigation rail and the content area horizontally.
 * - A [FleaMarketNavigationBar] on the left, displaying the navigation rail items.
 * - A [Box] on the right, containing the [navHost] content and the [FleaMarketSnackbarHost].
 *   - [navHost] is the main content area where the current screen is displayed.
 *   - [FleaMarketSnackbarHost] is positioned at the bottom of the Box to show snackbars.
 *
 * The root Row also has a background color from [MaterialTheme.colors.surface] and
 * applies [navigationBarsPadding] for safe area insets.
 *
 * */
@Composable
public fun ScreenWithNavigationRail(
    navHost: @Composable () -> Unit,
    snackbarHostState: SnackbarHostState,
    screens: List<NavigationBarScreen>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .navigationBarsPadding()
    ) {
        FleaMarketNavigationBar(screens)

        Box(contentAlignment = Alignment.BottomCenter) {
            navHost()
            FleaMarketSnackbarHost(snackbarHostState)
        }
    }
}
