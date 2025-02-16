package android.artisan.ui.component.shared

import android.artisan.ui.component.snackbar.model.SnackbarDetails

public interface SharedUIController {
    public fun showSnackbar(snackbarDetails: SnackbarDetails)
    public fun resetSnackbarDetails()
}
