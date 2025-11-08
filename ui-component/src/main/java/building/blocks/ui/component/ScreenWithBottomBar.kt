package building.blocks.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.DrawerState
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A composable function that creates a screen layout with a bottom navigation bar.
 * It utilizes a [Scaffold] to manage the overall structure, including a bottom navigation bar,
 * a content area, and optional drawer and snackbar.
 *
 * @param navHost A composable lambda that represents the navigation host. This is where
 *                the content of each screen, as determined by the navigation, will be displayed.
 *                It receives no parameters and should handle the navigation logic.
 * @param drawerState The state of the drawer. This allows managing whether the drawer is
 *                    open or closed. Usually managed by a [rememberDrawerState].
 * @param snackbarHostState The state of the snackbar host. This allows showing snackbars
 *                          within the scaffold. Usually managed by a [rememberSnackbarHostState].
 * @param screens A list of [NavigationBarScreen] objects that define the items to be
 *                displayed in the bottom navigation bar. Each item typically represents a
 *                different screen or destination.
 * @param modifier A [Modifier] that can be applied to the root [Scaffold] composable.
 *                 It allows customizing the layout and appearance of the entire screen.
 *                 Defaults to [Modifier].
 *
 * */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
public fun ScreenWithBottomBar(
    navHost: @Composable () -> Unit,
    drawerState: DrawerState,
    snackbarHostState: SnackbarHostState,
    screens: List<NavigationBarScreen>,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberScaffoldState(
        drawerState = drawerState,
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        modifier = modifier.navigationBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = { FleaMarketSnackbarHost(scaffoldState.snackbarHostState) },
        bottomBar = { FleaMarketNavigationBar(screens) }
    ) { _ ->
        navHost()
    }
}
