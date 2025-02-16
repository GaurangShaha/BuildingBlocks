package android.artisan.networking.retrofit.remote.mapper

import android.artisan.foundation.model.InternetDisconnectionException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal fun Exception.toInternetConnectionExceptionOrSelf() = when (this) {
    is UnknownHostException, is SocketTimeoutException -> InternetDisconnectionException
    else -> this
}
