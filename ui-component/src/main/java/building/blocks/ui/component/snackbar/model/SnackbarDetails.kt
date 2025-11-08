package building.blocks.ui.component.snackbar.model

import androidx.annotation.StringRes
import androidx.compose.material.SnackbarDuration
import building.blocks.ui.component.snackbar.SnackbarDelegate.SnackbarType

/**
 * Data class representing the details needed to display a Snackbar.
 *
 * @property message The string resource ID of the message to display in the Snackbar.
 *                   This should be a reference to a string defined in your `strings.xml` file.
 * @property snackbarType The type of Snackbar to display, influencing its appearance and behavior.
 *                        Defaults to [SnackbarType.DEFAULT].
 *                        See [SnackbarType] for available options.
 * @property duration The duration for which the Snackbar will be displayed.
 *                    Defaults to [SnackbarDuration.Short].
 *                    See [SnackbarDuration] for available options.
 */
public data class SnackbarDetails(
    @StringRes val message: Int,
    val snackbarType: SnackbarType = SnackbarType.DEFAULT,
    val duration: SnackbarDuration = SnackbarDuration.Short
)
