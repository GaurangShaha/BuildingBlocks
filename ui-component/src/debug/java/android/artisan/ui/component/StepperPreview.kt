package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@Composable
@FleaMarketPreviews
private fun StepperPreview() {
    FleaMarketThemePreview {
        Stepper(quantity = 0, onIncreaseQuantity = {}, onDecreaseQuantity = { })
    }
}
