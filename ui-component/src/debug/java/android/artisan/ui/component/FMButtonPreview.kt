package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@FleaMarketPreviews
@Composable
private fun FMButtonPreview() {
    FleaMarketThemePreview {
        FMButton("Click me..") {}
    }
}

@FleaMarketPreviews
@Composable
private fun FMOutlinedButtonPreview() {
    FleaMarketThemePreview {
        FMOutlinedButton("Click me..") {}
    }
}
