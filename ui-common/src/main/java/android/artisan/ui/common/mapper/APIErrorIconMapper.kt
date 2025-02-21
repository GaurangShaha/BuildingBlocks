package android.artisan.ui.common.mapper

import android.artisan.foundation.model.InternetDisconnectionException
import android.artisan.ui.component.R

/**
 * Converts a Throwable to an appropriate icon resource ID representing the error type.
 *
 * This function checks if the throwable is an instance of [InternetDisconnectionException].
 * If it is, it returns the resource ID for the network error icon (R.drawable.ic_network_error).
 * Otherwise, it defaults to returning the resource ID for a general server error icon (R.drawable.ic_server_error]).
 *
 * @receiver The [Throwable] instance representing the error.
 * @return The integer resource ID of the icon representing the error.
 * @see InternetDisconnectionException
 */
public fun Throwable.toAPIErrorIcon(): Int = if (this is InternetDisconnectionException) {
    R.drawable.ic_network_error
} else {
    R.drawable.ic_server_error
}
