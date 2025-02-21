package android.artisan.networking.retrofit.remote.mapper

import android.artisan.foundation.model.InternetDisconnectionException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Converts an exception to an [InternetDisconnectionException] if it's an [UnknownHostException]
 * or a [SocketTimeoutException], otherwise returns the original exception.
 *
 * This function is useful for standardizing error handling related to internet connectivity.
 * It simplifies checking for common network-related issues by mapping specific exceptions
 * to a single, more general exception type.
 *
 * @return An [InternetDisconnectionException] if the exception is an [UnknownHostException] or
 *   [SocketTimeoutException], otherwise returns the original exception.
 *
 * @see InternetDisconnectionException
 * @see UnknownHostException
 * @see SocketTimeoutException
 */
internal fun Exception.toInternetConnectionExceptionOrSelf() = when (this) {
    is UnknownHostException, is SocketTimeoutException -> InternetDisconnectionException
    else -> this
}
