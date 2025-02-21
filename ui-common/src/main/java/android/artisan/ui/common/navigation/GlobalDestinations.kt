@file:Suppress("MatchingDeclarationName", "Filename")

package android.artisan.ui.common.navigation

import kotlinx.serialization.Serializable

/**
 * Represents the destination for navigating to the product details screen.
 *
 * This data class encapsulates the necessary information to uniquely identify
 * a product and navigate to its dedicated details view.  It is designed to be
 * used within a navigation system that utilizes serializable destinations.
 *
 * @property productId The unique identifier of the product to display details for.
 *                     This should correspond to the product ID within your data source.
 */
@Serializable
public class ProductDetailsDestination(public val productId: Int)
