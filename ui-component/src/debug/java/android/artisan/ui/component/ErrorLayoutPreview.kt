package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@FleaMarketPreviews
@Composable
private fun ErrorLayoutPreview() {
    FleaMarketThemePreview {
        ErrorLayout(
            errorMessage = stringResource(id = R.string.retry),
            errorIcon = painterResource(id = R.drawable.ic_network_error),
            onRetry = {}
        )
    }
}
