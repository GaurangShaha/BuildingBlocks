package building.blocks.ui.component

import androidx.compose.runtime.Composable
import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview

@FleaMarketPreviews
@Composable
private fun PageIndicatorPreview() {
    FleaMarketThemePreview {
        PageIndicator(totalPages = 5, currentPage = 1)
    }
}
