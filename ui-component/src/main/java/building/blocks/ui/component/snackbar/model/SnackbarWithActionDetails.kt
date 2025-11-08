package building.blocks.ui.component.snackbar.model

import building.blocks.ui.component.snackbar.SnackbarDelegate.SnackbarType
import androidx.annotation.StringRes
import androidx.compose.material.SnackbarDuration

/**
 * Data class representing the details needed to display a snackbar with an action.
 *
 * This class encapsulates the message, action label, type, and duration
 * of a snackbar, allowing for easy creation and customization of snackbar
 * instances.
 *
 * @property message The string resource ID of the message to be displayed in the snackbar.
 *                   This is mandatory and must be a valid string resource.
 * @property actionLabel The string resource ID of the label for the action button in the snackbar.
 *                       This is mandatory and must be a valid string resource.
 * @property snackbarType The type of the snackbar, which can be used to change its appearance
 *                        or behavior. Defaults to [SnackbarType.DEFAULT].
 * @property duration The duration the snackbar should be displayed. Defaults to [SnackbarDuration.Short].
 */
public data class SnackbarWithActionDetails(
    @StringRes val message: Int,
    @StringRes val actionLabel: Int,
    val snackbarType: SnackbarType = SnackbarType.DEFAULT,
    val duration: SnackbarDuration = SnackbarDuration.Short
)
