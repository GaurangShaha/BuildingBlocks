package building.blocks.ui.compositionlocal

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * [CompositionLocal] that provides the current [WindowSizeClass].
 *
 * This CompositionLocal can be used to access the [WindowSizeClass] of the current window
 * within a composable hierarchy. It's typically provided at a higher level in the
 * composition tree (e.g., within the root composable of a screen or activity).
 *
 * Use the [androidx.compose.material3.windowsizeclass.calculateWindowSizeClass]
 * function to get a [WindowSizeClass] instance and then provide it to this
 * [CompositionLocal] via a [androidx.compose.runtime.CompositionLocalProvider].
 *
 * @see androidx.compose.material3.windowsizeclass.WindowSizeClass
 * @see androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
 */
public val LocalWindowSizeClass: ProvidableCompositionLocal<WindowSizeClass> =
    staticCompositionLocalOf { error("WindowSizeClass is not provided") }
