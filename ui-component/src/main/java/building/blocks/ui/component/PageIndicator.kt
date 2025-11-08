package building.blocks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import building.blocks.ui.theme.extraColors

/**
 * A composable function that displays a row of page indicator dots.
 *
 * This function renders a horizontal row of circular indicators representing the total number of pages.
 * The indicator corresponding to the [currentPage] is highlighted with the primary color,
 * while the other indicators are displayed with a deselected color and a border.
 * If there is only one page then nothing is shown.
 *
 * @param totalPages The total number of pages.
 * @param currentPage The currently selected page (0-based index).
 * @param modifier Modifier to be applied to the Row containing the page indicators.
 *
 * @throws IllegalArgumentException if [totalPages] is less than 1 or [currentPage] is out of range [0, totalPages).
 */
@Composable
public fun PageIndicator(totalPages: Int, currentPage: Int, modifier: Modifier = Modifier) {
    if (totalPages == 1) return
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(totalPages) {
            val color = if (currentPage == it) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.extraColors.deselectedPageIndicator
            }
            Box(
                Modifier
                    .size(24.dp)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )
                    .background(color)
            )
        }
    }
}
