package android.artisan.ui.common.mapper

import android.artisan.foundation.model.InternetDisconnectionException
import android.artisan.ui.component.R

public fun Throwable.toAPIErrorIcon(): Int = if (this is InternetDisconnectionException) {
    R.drawable.ic_network_error
} else {
    R.drawable.ic_server_error
}
