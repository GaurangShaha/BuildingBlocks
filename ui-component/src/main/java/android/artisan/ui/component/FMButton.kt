package android.artisan.ui.component

import android.artisan.ui.theme.extraColors
import android.artisan.ui.theme.extraShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * FMButton is a custom button composable that provides a styled button with a capsule shape.
 *
 * @param text The text to be displayed on the button.
 * @param modifier Modifier to be applied to the button.
 * @param onClick Callback to be invoked when the button is clicked.
 */
@Composable
public fun FMButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(modifier = modifier, shape = MaterialTheme.extraShape.capsuleShape, onClick = onClick) {
        ButtonText(text)
    }
}

/**
 * `FMOutlinedButton` is a custom outlined button designed for the application's style.
 *
 * This composable provides an outlined button with a specific style:
 *  - **Shape:** Capsule shape from the application's theme (`MaterialTheme.extraShape.capsuleShape`).
 *  - **Border:** 1dp solid border using the `darkGrey` color from the application's
 *  theme (`MaterialTheme.extraColors.darkGrey`).
 *  - **Text:** Uses the custom `ButtonText` composable for the text content.
 *
 * It leverages the `OutlinedButton` composable from the standard Jetpack Compose library,
 * customizing its `shape` and `border` properties to match the application's design system.
 *
 * @param text The text to be displayed inside the button.
 * @param modifier Modifier for styling the button's layout and appearance.
 * @param onClick Callback invoked when the button is clicked.
 *
 */
@Composable
public fun FMOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        shape = MaterialTheme.extraShape.capsuleShape,
        border = BorderStroke(1.dp, MaterialTheme.extraColors.darkGrey),
        onClick = onClick
    ) {
        ButtonText(text)
    }
}

/**
 * Composable function that displays a text within a button context.
 *
 * This function renders a [Text] composable with default padding, intended to be used
 * as the content of a button or a similar interactive element.
 *
 * @param text The string text to be displayed.
 */
@Composable
private fun ButtonText(text: String) {
    Text(modifier = Modifier.padding(6.dp), text = text)
}
