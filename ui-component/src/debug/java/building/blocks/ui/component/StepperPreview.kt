package building.blocks.ui.component

import androidx.compose.runtime.Composable
import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview

@Composable
@FleaMarketPreviews
private fun StepperPreview() {
    FleaMarketThemePreview {
        Stepper(quantity = 0, onIncreaseQuantity = {}, onDecreaseQuantity = { })
    }
}
