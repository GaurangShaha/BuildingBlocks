package android.artisan.ui.component.shared

import android.artisan.ui.component.snackbar.model.SnackbarDetails

/**
 * Interface for controlling shared UI elements across different parts of the application.
 * This interface provides methods to interact with UI elements that are typically shared
 * at the application level, such as snackbars.
 */
public interface SharedUIController {
    /**
     * Shows a Snackbar with the provided details.
     *
     * @property snackbarDetails Data class to encapsulate details for displaying a Snackbar.
     */
    public fun showSnackbar(snackbarDetails: SnackbarDetails)

    /**
     * Resets the details of the Snackbar, clearing any existing message, action, or
     * duration settings.
     */
    public fun resetSnackbarDetails()
}
