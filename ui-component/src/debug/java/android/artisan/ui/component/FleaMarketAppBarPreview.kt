package android.artisan.ui.component

import android.artisan.ui.preview.FleaMarketPreviews
import android.artisan.ui.preview.FleaMarketThemePreview
import android.artisan.ui.theme.extraColors
import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@FleaMarketPreviews
@Composable
private fun FleaMarketAppBarPreview() {
    FleaMarketThemePreview {
        FleaMarketAppBar(navigationIcon = Icons.Default.Menu to {}, title = R.string.retry)
    }
}

@FleaMarketPreviews
@Composable
private fun FleaMarketAppBarNavigationItemPreview() {
    FleaMarketThemePreview {
        FleaMarketAppBar(title = R.string.retry, navigationIcon = Filled.ArrowBack to {})
    }
}

@FleaMarketPreviews
@Composable
private fun FleaMarketAppBarActionIconPreview() {
    FleaMarketThemePreview {
        FleaMarketAppBar(
            title = R.string.retry,
            navigationIcon = Filled.ArrowBack to {},
            actionItems = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )
    }
}

@FleaMarketPreviews
@Composable
private fun FleaMarketAppBarWithScrimPreview() {
    FleaMarketThemePreview {
        FleaMarketAppBar(
            title = R.string.retry,
            modifier = Modifier.background(Brush.verticalGradient(MaterialTheme.extraColors.scrimColor)),
            navigationIcon = Filled.ArrowBack to {},
            actionItems = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            backgroundColor = Color.Transparent
        )
    }
}
