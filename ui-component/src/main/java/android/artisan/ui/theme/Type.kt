package android.artisan.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

// Set of Material typography styles to start with
internal val Typography = Typography()

/**
 * Defines a set of extra typography styles that can be used in addition to the standard Material Design typography.
 *
 * This class provides styles for specific text elements that are commonly used but not included in the default
 * typography set.  It is designed to enhance the flexibility and customization of text styles within the application.
 *
 * @property captionDarkGray A [TextStyle] for captions with a dark gray color. Suitable for small text descriptions,
 *           hints, or secondary information. Defaults to [TextStyle.Default].
 * @property body1Bold A [TextStyle] for body text with a bold font weight. Suitable for emphasizing key phrases or
 *           important body text sections. Defaults to [TextStyle.Default].
 * @property body1DarkGray A [TextStyle] for body text with a dark gray color. Suitable for slightly de-emphasized
 *           body text or when a softer contrast is desired. Defaults to [TextStyle.Default].
 * @property h6Bold A [TextStyle] for level 6 headers with a bold font weight. Useful for subheadings within a
 *           section. Defaults to [TextStyle.Default].
 */
@Immutable
public data class ExtraTypography(
    val captionDarkGray: TextStyle = TextStyle.Default,
    val body1Bold: TextStyle = TextStyle.Default,
    val body1DarkGray: TextStyle = TextStyle.Default,
    val h6Bold: TextStyle = TextStyle.Default
)

/**
 * [CompositionLocal] containing the [ExtraTypography] to provide typography styles
 * beyond the basic Material Design types.
 *
 * Use this CompositionLocal to access custom typography styles defined in your app's
 * [ExtraTypography] instance. These can be used to extend the default Material
 * typography system with styles like titles, captions, or other custom text treatments.
 *
 * To provide a custom [ExtraTypography] instance, use [CompositionLocalProvider].
 *
 * If a custom [ExtraTypography] is not provided, the default [ExtraTypography] with empty styles will be used.
 *
 * @see ExtraTypography
 * @see androidx.compose.material3.Typography
 * @see androidx.compose.runtime.CompositionLocalProvider
 * @see androidx.compose.runtime.staticCompositionLocalOf
 */
internal val LocalExtraTypography = staticCompositionLocalOf { ExtraTypography() }

/**
 * Defines extra typography styles used in the light theme.
 *
 * This object contains a set of typography styles that extend the default Material
 * Design typography. These styles are specifically tailored for use with the
 * light color palette.
 *
 * @property captionDarkGray A caption style with a dark grey color.
 * @property body1Bold A body1 style with bold font weight.
 * @property body1DarkGray A body1 style with a dark grey color.
 * @property h6Bold An h6 style with bold font weight.
 */
internal val LightExtraTypography = ExtraTypography(
    captionDarkGray = Typography.caption.copy(color = LightExtraColorsPalette.darkGrey),
    body1Bold = Typography.body1.copy(fontWeight = FontWeight.Bold),
    body1DarkGray = Typography.body1.copy(color = LightExtraColorsPalette.darkGrey),
    h6Bold = Typography.h6.copy(fontWeight = FontWeight.Bold)
)

/**
 * Defines a set of extra typography styles specifically for dark themes.
 *
 * This object contains additional text styles beyond the standard Material Design
 * typography set, customized for use in a dark color scheme.
 *
 * Properties:
 *
 * @property captionDarkGray: A caption text style with a dark grey color. This is suitable for
 *   secondary or less prominent information in a dark theme. It is based on the default
 *   [Typography.caption] style.
 * @property body1Bold: A bold version of the body1 text style. This style is useful for
 *   emphasizing body text, such as section headings or key information within a paragraph.
 *   It is based on the default [Typography.body1] style.
 * @property body1DarkGray: A body1 text style with a dark grey color. This style is useful for
 *   body text that needs to stand out slightly less than the primary text color in a
 *   dark theme. It is based on the default [Typography.body1] style.
 * @property h6Bold: A bold version of the h6 text style. This is ideal for subheadings or
 *   titles that require more emphasis. It is based on the default [Typography.h6] style.
 */
internal val DarkExtraTypography = ExtraTypography(
    captionDarkGray = Typography.caption.copy(color = DarkExtraColorsPalette.darkGrey),
    body1Bold = Typography.body1.copy(fontWeight = FontWeight.Bold),
    body1DarkGray = Typography.body1.copy(color = DarkExtraColorsPalette.darkGrey),
    h6Bold = Typography.h6.copy(fontWeight = FontWeight.Bold)
)

public val MaterialTheme.extraTypography: ExtraTypography
    @Composable @ReadOnlyComposable
    get() = LocalExtraTypography.current
