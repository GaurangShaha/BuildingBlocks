package building.blocks.ui.modifier

import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview
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
