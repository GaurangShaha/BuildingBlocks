package building.blocks.ui.component

import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@Composable
@FleaMarketPreviews
private fun StepperPreview() {
    FleaMarketThemePreview {
        Stepper(quantity = 0, onIncreaseQuantity = {}, onDecreaseQuantity = { })
    }
}
