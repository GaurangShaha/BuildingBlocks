package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@FleaMarketPreviews
@Composable
public fun FMButtonPreview() {
    FleaMarketThemePreview {
        FMButton("Click me..") {}
    }
}

@FleaMarketPreviews
@Composable
public fun FMOutlinedButtonPreview() {
    FleaMarketThemePreview {
        FMOutlinedButton("Click me..") {}
    }
}
