package building.blocks.ui.component

import building.blocks.ui.preview.FleaMarketPreviews
import building.blocks.ui.preview.FleaMarketThemePreview
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@FleaMarketPreviews
@Composable
private fun EmptyLayoutPreview() {
    FleaMarketThemePreview {
        EmptyLayout(
            message = stringResource(id = R.string.retry),
            icon = painterResource(id = R.drawable.ic_network_error)
        )
    }
}
