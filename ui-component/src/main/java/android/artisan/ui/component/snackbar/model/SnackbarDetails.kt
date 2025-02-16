package android.artisan.ui.component.snackbar.model

import android.artisan.ui.component.snackbar.SnackbarDelegate.SnackbarType
import androidx.annotation.StringRes
import androidx.compose.material.SnackbarDuration

public data class SnackbarDetails(
    @StringRes val message: Int,
    val snackbarType: SnackbarType = SnackbarType.DEFAULT,
    val duration: SnackbarDuration = SnackbarDuration.Short
)
