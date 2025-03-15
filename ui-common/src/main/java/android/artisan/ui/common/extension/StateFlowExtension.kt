package android.artisan.ui.common.extension

import kotlinx.coroutines.flow.StateFlow

/**
 * Executes the given [block] if the current value of the [StateFlow] is an instance of [S].
 *
 * This function allows you to conditionally perform an action based on the type of the current value
 * held by a [StateFlow]. It safely checks if the current value is an instance of the specified
 * reified type [S] and executes the provided [block] only if the type matches.
 *
 * @param S The type to check the current value against. This is a reified type parameter.
 * @param block The lambda function to execute if the current value is an instance of [S].
 *              The lambda receives the current value, cast to type [S], as its parameter.
 *
 */
public inline fun <reified S> StateFlow<*>.ifInstanceOf(block: (S) -> Unit) {
    val currentState = value
    if (currentState is S) {
        block(currentState)
    }
}
