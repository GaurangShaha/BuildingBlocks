package android.artisan.ui.compositionlocal

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * [CompositionLocal] used to provide and retrieve the [NavHostController] within a composable hierarchy.
 *
 * This allows composables deeper in the tree to access the [NavHostController] without having to
 * pass it down through every level of the UI.
 *
 * **Error Handling:**
 *
 * If you try to access `LocalNavController.current` outside of a `CompositionLocalProvider`
 * that provides it, an `IllegalStateException` will be thrown with the message "NavHostController is not provided".
 *
 * **Important Considerations:**
 *
 * - The provided `NavHostController` should be the one associated with your `NavHost`.
 * - Avoid providing multiple `NavHostController` instances within the same composition hierarchy,
 * as it may lead to unexpected behavior.
 * - This should be used in tandem with a composable that uses NavHost.
 *
 * @see androidx.navigation.NavHostController
 * @see androidx.compose.runtime.CompositionLocal
 * @see androidx.compose.runtime.CompositionLocalProvider
 * @see androidx.navigation.compose.NavHost
 */
public val LocalNavController: ProvidableCompositionLocal<NavHostController> =
    staticCompositionLocalOf { error("NavHostController is not provided") }
