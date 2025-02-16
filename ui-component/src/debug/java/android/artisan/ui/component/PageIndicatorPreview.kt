package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@FleaMarketPreviews
@Composable
private fun PageIndicatorPreview() {
    FleaMarketThemePreview {
        PageIndicator(totalPages = 5, currentPage = 1)
    }
}
