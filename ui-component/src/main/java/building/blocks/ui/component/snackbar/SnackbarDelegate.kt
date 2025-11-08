package building.blocks.ui.component.snackbar

import building.blocks.ui.component.snackbar.SnackbarDelegate.SnackbarType.DEFAULT
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult

/**
 * `SnackbarDelegate` is a singleton object responsible for managing and displaying
 * snackbars within the application. It provides a centralized way to show snackbars
 * with consistent styling and behavior.
 */
public object SnackbarDelegate {
    /**
     * The type of the currently displayed Snackbar.
     *
     * This property holds the [SnackbarType] of the Snackbar that is currently being
     * shown, or [DEFAULT] if no Snackbar is active. This can be used to determine
     * the style and behavior of the visible Snackbar.
     *
     * The possible types are defined in the [SnackbarType] enum, such as:
     * - [SnackbarType.DEFAULT]: A standard, non-critical Snackbar.
     * - [SnackbarType.ERROR]: A Snackbar indicating an error or failure.
     * - [SnackbarType.SUCCESS]: A Snackbar indicating successful completion of an action.
     *
     * @see SnackbarType
     * @see showSnackbar
     */
    public var currentSnackbarType: SnackbarType = DEFAULT

    /**
     * Displays a Snackbar with the specified message, action, duration, and type.
     *
     * This function is a wrapper around [SnackbarHostState.showSnackbar] that also tracks the
     * current [SnackbarType] displayed. It allows for consistent styling and behavior of
     * Snackbars throughout the application based on the [SnackbarType].
     *
     * @param snackbarHostState The [SnackbarHostState] to use for displaying the Snackbar.
     * @param message The message to display in the Snackbar.
     * @param action The optional action label to display on the Snackbar. If null, no action will be shown.
     * @param type The [SnackbarType] of the Snackbar, which can be used for custom styling or behavior.
     * @param duration The duration the Snackbar should be displayed for. Defaults to [SnackbarDuration.Short].
     * @return The [SnackbarResult] representing the result of the Snackbar being displayed. This can be
     *         either [SnackbarResult.ActionPerformed] if the user clicked the action button or
     *         [SnackbarResult.Dismissed] if the Snackbar was dismissed or timed out.
     */
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

    /**
     * Enum class representing the different types of Snackbars that can be displayed.
     *
     * Each type corresponds to a specific visual style and potentially different behavior.
     */
    public enum class SnackbarType {
        /**
         * Represents the default state.
         */
        DEFAULT,

        /**
         * Represents an error state.
         */
        ERROR,

        /**
         * Represents an success state.
         */
        SUCCESS
    }
}
