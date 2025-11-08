package building.blocks.ui.component

import building.blocks.ui.compositionlocal.LocalNavController
import building.blocks.ui.compositionlocal.LocalWindowSizeClass
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

/**
 * [FleaMarketNavigationBar] is a composable function that renders either a [FMBottomNavigation]
 * or a [FMNavigationRail] based on the current window width size class. It manages the navigation
 * state and handles item selection for the navigation bar.
 *
 * @param screens A list of [NavigationBarScreen] representing the items to be displayed in the
 * navigation bar. Each screen defines the route, icon, and label for a navigation item.
 * @param modifier Modifier for customizing the layout and appearance of the navigation bar.
 * Defaults to [Modifier].
 *
 * @see NavigationBarScreen
 *
 */
@Composable
internal fun FleaMarketNavigationBar(
    screens: List<NavigationBarScreen>,
    modifier: Modifier = Modifier
) {
    val navHost = LocalNavController.current
    val navBackStackEntry by navHost.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route
    var selectedNavigationItemIndex by rememberSaveable { mutableIntStateOf(0) }

    if (LocalWindowSizeClass.current.widthSizeClass == WindowWidthSizeClass.Compact) {
        FMBottomNavigation(
            currentDestinationRoute = currentDestinationRoute,
            navigationBarScreens = screens,
            selectedNavigationItemIndex = selectedNavigationItemIndex,
            onNavigationItemSelection = { selectedNavigationItemIndex = it },
            modifier = modifier
        )
    } else {
        FMNavigationRail(
            currentDestinationRoute = currentDestinationRoute,
            navigationBarScreens = screens,
            selectedNavigationItemIndex = selectedNavigationItemIndex,
            onNavigationItemSelection = { selectedNavigationItemIndex = it },
            modifier = modifier
        )
    }
}

/**
 * Represents a screen that can be displayed in a navigation bar.
 *
 * This data class holds the information necessary to represent a destination within a navigation bar,
 * including its unique route, a label for display, and an icon.
 *
 * @property route The unique identifier for this screen's destination. This can be any data type, but
 *                 it's used to navigate to this screen within the application.
 * @property labelResourceId The string resource ID representing the label text for this screen
 *                           in the navigation bar. This will typically be used to display a
 *                           text label under the icon.
 * @property iconResourceId The drawable resource ID representing the icon for this screen in the
 *                          navigation bar. This icon will be displayed alongside the label.
 */
public data class NavigationBarScreen(
    val route: Any,
    @StringRes val labelResourceId: Int,
    @DrawableRes val iconResourceId: Int
) {
    /**
     * Retrieves the fully qualified name of the Kotlin serialization descriptor for the route's class.
     *
     * This function leverages Kotlin Serialization's internal and experimental APIs to obtain the
     * serial name associated with the route's class.  The serial name represents the fully qualified
     * name of the class as registered with the serialization system, which is effectively its
     * identifier in the context of serialization and deserialization.
     *
     * **Important Considerations:**
     *
     * *   **Internal and Experimental APIs:** This function relies on `@InternalSerializationApi` and
     *     `@ExperimentalSerializationApi`. These annotations indicate that the APIs used are not considered
     *     stable or part of the public contract of Kotlin Serialization.  Future versions may change
     *     or remove these APIs without notice, potentially breaking code that uses them. Use with caution
     *     and be prepared for potential maintenance in the future.
     * *   **Class-Based Route:** This method assumes `route` is a class instance (e.g., a data class). It retrieves
     *      serialization information based on the type of the `route` object.
     * * **Purpose:** This method is useful when a unique, fully-qualified identifier is needed for the route in
     * a serialized context. For instance, it can be used to:
     *      * Register or look up serializers/deserializers programmatically based on route types.
     *      * Generate unique keys for storage or caching related to specific routes.
     *      * Debugging or logging purposes to identify the exact route type.
     *
     * @return The fully qualified serial name (e.g., "com.example.MyRoute" or "MyRoute" if no package) of
     * the route's class.
     */
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    public fun getSerializedRoute(): String {
        return route::class.serializer().descriptor.serialName
    }
}
