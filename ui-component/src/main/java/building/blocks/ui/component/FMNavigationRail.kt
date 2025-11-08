package building.blocks.ui.component

import building.blocks.ui.compositionlocal.LocalNavController
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.util.Locale

/**
 * A composable function that displays a navigation rail for the application.
 *
 * This function renders a vertical navigation rail with icons and labels for different
 * navigation destinations. It handles navigation item selection and updates the UI accordingly.
 *
 * @param currentDestinationRoute The route of the currently displayed screen. Used to determine if a
 *   navigation item should be automatically selected based on the current route. Can be null.
 * @param navigationBarScreens A list of [NavigationBarScreen] objects representing the available
 *   navigation destinations. Each screen defines its icon, label, and navigation route.
 * @param selectedNavigationItemIndex The index of the currently selected navigation item in the
 *   [navigationBarScreens] list.
 * @param onNavigationItemSelection A lambda function that is called when a navigation item is
 *   selected. It receives the index of the selected item.
 * @param modifier Modifier to be applied to the navigation rail.
 *
 * @see NavigationBarScreen
 *
 */
@Composable
internal fun FMNavigationRail(
    currentDestinationRoute: String?,
    navigationBarScreens: List<NavigationBarScreen>,
    selectedNavigationItemIndex: Int,
    onNavigationItemSelection: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navHost = LocalNavController.current
    NavigationRail(modifier = modifier.padding(end = 2.dp)) {
        navigationBarScreens.forEachIndexed { index, screen ->
            if (currentDestinationRoute?.contains(screen.getSerializedRoute()) == true &&
                index != selectedNavigationItemIndex
            ) {
                onNavigationItemSelection(index)
            }
            if (index == 0) {
                Spacer(modifier = Modifier.statusBarsPadding())
            }
            NavigationRailItem(selected = selectedNavigationItemIndex == index, onClick = {
                navigateToDestinations(
                    index = index,
                    navigationBarScreen = screen,
                    navController = navHost,
                    currentDestinationRoute = currentDestinationRoute,
                    onNavigationItemSelected = onNavigationItemSelection
                )
            }, alwaysShowLabel = false, icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = screen.iconResourceId),
                    contentDescription = null
                )
            }, label = {
                Text(
                    text = stringResource(id = screen.labelResourceId).uppercase(
                        Locale.getDefault()
                    ),
                    overflow = TextOverflow.Visible,
                    softWrap = false,
                    maxLines = 1
                )
            })
        }
    }
}
