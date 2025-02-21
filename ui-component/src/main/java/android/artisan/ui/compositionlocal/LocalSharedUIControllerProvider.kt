package android.artisan.ui.compositionlocal

import android.artisan.ui.component.shared.SharedUIController
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal that provides a [SharedUIController] instance.
 *
 * This CompositionLocal allows components within the composition to access a shared
 * [SharedUIController] instance without explicitly passing it down through every level.
 *
 * The [SharedUIController] is typically used to manage shared UI state or actions across
 * different parts of the application, such as showing dialogs, snackbars, or other global
 * UI elements.
 *
 * To provide a [SharedUIController] instance, use the `CompositionLocalProvider` with this
 * [LocalSharedUIController] as the key:
 *
 * If the [SharedUIController] is not provided, accessing `LocalSharedUIController.current` will
 * result in an [IllegalStateException] with the message "SharedUIController is not provided".
 *
 * @see SharedUIController
 * @see androidx.compose.runtime.CompositionLocalProvider
 * @see androidx.compose.runtime.staticCompositionLocalOf
 */
public val LocalSharedUIController: ProvidableCompositionLocal<SharedUIController> =
    staticCompositionLocalOf { error("SharedUIController is not provided") }
