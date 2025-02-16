package android.artisan.ui.modifier

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@FleaMarketPreviews
private fun ShimmerPreview() {
    FleaMarketThemePreview {
        Box(
            modifier = Modifier
                .size(50.dp)
                .shimmer()
        )
    }
}
