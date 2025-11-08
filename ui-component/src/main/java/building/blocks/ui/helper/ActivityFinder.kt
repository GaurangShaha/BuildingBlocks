package building.blocks.ui.helper

import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Finds the nearest [AppCompatActivity] in the context hierarchy.
 *
 * This function traverses the context hierarchy starting from the current context
 * (obtained from [LocalContext.current]) and checks each context to see if it's an
 * instance of [AppCompatActivity]. It continues to move up the hierarchy through the
 * base context until it finds an [AppCompatActivity] or reaches the root of the
 * hierarchy.
 *
 * @return The nearest [AppCompatActivity] found in the context hierarchy, or null
 *         if no [AppCompatActivity] is found.
 */
@Composable
public fun findActivity(): AppCompatActivity? {
    var context = LocalContext.current
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) return context
        context = context.baseContext
    }
    return null
}
