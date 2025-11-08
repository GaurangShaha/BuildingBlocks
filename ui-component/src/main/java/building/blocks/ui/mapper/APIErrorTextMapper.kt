package building.blocks.ui.mapper

import building.blocks.foundation.model.InternetDisconnectionException
import building.blocks.ui.component.R

/**
 * Converts a Throwable to an API error message resource ID.
 *
 * This extension function determines the appropriate error message to display to the user
 * based on the type of Throwable encountered. If the Throwable is an InternetDisconnectionException,
 * it indicates a network connectivity issue and returns the resource ID for the network error message.
 * Otherwise, it assumes a general server error and returns the resource ID for the server error message.
 *
 * @receiver Throwable The Throwable object representing the error.
 * @return Int The resource ID of the corresponding error message string.
 *   - R.string.network_error: If the Throwable is an instance of InternetDisconnectionException.
 *   - R.string.server_error: For any other type of Throwable.
 *
 * @see building.blocks.foundation.model.InternetDisconnectionException
 */
public fun Throwable.toAPIErrorMessage(): Int = if (this is InternetDisconnectionException) {
    R.string.network_error
} else {
    R.string.server_error
}
