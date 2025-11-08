package building.blocks.ui.component

import androidx.compose.runtime.Composable
import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview

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
