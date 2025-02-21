package android.artisan.ui.component

import android.artisan.ui.component.snackbar.SnackbarDelegate
import android.artisan.ui.component.snackbar.SnackbarDelegate.SnackbarType
import android.artisan.ui.theme.extraColors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * A custom Snackbar host that displays Snackbars with specific styling and icons
 * based on the [SnackbarType] provided by [SnackbarDelegate.currentSnackbarType].
 *
 * This composable provides a consistent look and feel for Snackbars throughout
 * the Flea Market application. It handles displaying error, success, and default
 * message types with corresponding icons and colors.
 *
 * @param snackbarHostState The [SnackbarHostState] that manages the display of Snackbars.
 * @param modifier Modifiers to be applied to the Snackbar host.
 *
 * @see SnackbarType
 * @see SnackbarDelegate
 */
@Composable
public fun FleaMarketSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = modifier.padding(16.dp),
        hostState = snackbarHostState
    ) { snackbarData ->
        val iconColor: Color
        val icon: ImageVector?
        when (SnackbarDelegate.currentSnackbarType) {
            SnackbarType.ERROR -> {
                iconColor = MaterialTheme.colors.error
                icon = Icons.Default.Close
            }

            SnackbarType.SUCCESS -> {
                iconColor = MaterialTheme.extraColors.successColor
                icon = Icons.Default.Check
            }

            SnackbarType.DEFAULT -> {
                icon = null
                iconColor = MaterialTheme.colors.onSurface
            }
        }

        Snackbar(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            action = snackbarData.actionLabel?.let {
                @Composable {
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary),
                        onClick = { snackbarData.performAction() },
                        content = { Text(it) }
                    )
                }
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor
                    )
                }
                Text(text = snackbarData.message)
            }
        }
    }
}
