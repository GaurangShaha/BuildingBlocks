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
 * @throws ClassCastException if you have a very strange and unlikely edge case where the Stateflow's underlying
 * type changes between reading the value and the execution of the block
 *
 * **Usage:**
 * ```kotlin
 * val myStateFlow: StateFlow<Any> = MutableStateFlow(10)
 *
 * myStateFlow.ifInstanceOf<Int> { intValue ->
 *     println("The value is an integer: $intValue")
 * }
 *
 * myStateFlow.ifInstanceOf<String> { stringValue ->
 *     println("The value is a string: $stringValue")
 * }
 *
 * // Output with initial value of 10:
 * // The value is an integer: 10
 * ```
 */
public inline fun <reified S> StateFlow<*>.ifInstanceOf(block: (S) -> Unit) {
    val currentState = value
    if (currentState is S) {
        block(currentState)
    }
}
