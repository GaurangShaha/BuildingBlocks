package android.artisan.ui.common.mapper

import android.artisan.foundation.model.InternetDisconnectionException
import android.artisan.ui.common.R

public fun Throwable.toAPIErrorMessage(): Int = if (this is InternetDisconnectionException) {
    R.string.network_error
} else {
    R.string.server_error
}
