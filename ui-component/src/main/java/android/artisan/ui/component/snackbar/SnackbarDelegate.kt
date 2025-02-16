package android.artisan.ui.component.snackbar

import android.artisan.ui.component.snackbar.SnackbarDelegate.SnackbarType.DEFAULT
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult

public object SnackbarDelegate {
    public var currentSnackbarType: SnackbarType = DEFAULT
    public suspend fun showSnackbar(
        snackbarHostState: SnackbarHostState,
        message: String,
        action: String? = null,
        type: SnackbarType,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): SnackbarResult {
        currentSnackbarType = type
        return snackbarHostState.showSnackbar(message, action, duration)
    }

    public enum class SnackbarType { DEFAULT, ERROR, SUCCESS }
}
