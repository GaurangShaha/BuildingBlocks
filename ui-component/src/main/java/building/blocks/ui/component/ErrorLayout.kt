package building.blocks.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays an error layout with an error message,
 * an optional error icon, and an optional retry button.
 *
 * @param errorMessage The error message to be displayed.
 * @param errorIcon The painter for the error icon.
 * @param modifier Modifier for styling the layout.
 * @param onRetry An optional lambda to be invoked when the retry button is clicked.
 *                If null, the retry button will not be shown.
 */
@Composable
public fun ErrorLayout(
    errorMessage: String,
    errorIcon: Painter,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = errorIcon,
            contentDescription = null,
            modifier = Modifier.size(82.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,

        )

        Spacer(modifier = Modifier.size(14.dp))

        onRetry?.let {
            FMButton(
                text = stringResource(R.string.retry),
                onClick = it
            )
        }
    }
}
