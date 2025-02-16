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

@Composable
private fun ButtonText(text: String) {
    Text(modifier = Modifier.padding(6.dp), text = text)
}
