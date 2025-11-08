package building.blocks.ui.component

import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable

@FleaMarketPreviews
@Composable
private fun PageIndicatorPreview() {
    FleaMarketThemePreview {
        PageIndicator(totalPages = 5, currentPage = 1)
    }
}
