package android.artisan.ui.component

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays an empty layout with a given message and icon.
 *
 * This function is typically used to indicate that there is no data to display, such as when a
 * list is empty or a search returns no results.
 *
 * @param message The message to display in the empty layout. This should be a user-friendly
 * message explaining why the content is empty.
 * @param icon The icon to display in the empty layout. This should visually represent the empty
 * state.
 * @param modifier Modifier to be applied to the layout. This can be used to customize the
 * appearance and behavior of the layout, such as setting padding or background color. Defaults to
 * an empty modifier.
 */
@Composable
public fun EmptyLayout(
    message: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(82.dp)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(text = message, textAlign = TextAlign.Center)
    }
}
