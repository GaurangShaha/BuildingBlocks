package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@FleaMarketPreviews
@Composable
private fun LazyImagePreview() {
    FleaMarketThemePreview {
        @Suppress("MaxLineLength")
        LazyImage(
            url = "https://www.gstatic.com/devrel-devsite/prod/v0f868bacf787bf31b228952b4e9f9c852485b2025a1f6f6571309b6d62ea4de2/android/images/lockup.svg",
            modifier = Modifier.size(50.dp)
        )
    }
}
