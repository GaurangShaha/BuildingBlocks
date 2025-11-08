package building.blocks.ui.common.contract.viewmodel

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

/**
 * Contract for a ViewModel that manages UI state and processes user intents.
 *
 * This interface defines the essential components of a ViewModel:
 * - A [StateFlow] representing the current UI state.
 * - A function to process user intents and update the UI state accordingly.
 *
 * @param S The type of the UI state. It should be an immutable data class or a sealed class representing the different
 * states of the UI.
 * @param I The type of the user intent. It should typically be a sealed class representing the different actions or
 * events that can occur in the UI.
 *
 * **Usage:**
 * ```kotlin
 * data class MyUiState(val isLoading: Boolean, val data: String? = null)
 *
 * sealed class MyIntent {
 *     object LoadData : MyIntent()
 *     data class UpdateData(val newData: String) : MyIntent()
 * }
 *
 * class MyViewModel : ViewModel(), ViewModelContract<MyUiState, MyIntent> {
 *     override val uiState: StateFlow<MyUiState> = ... // Initialize your state flow here
 *
 *     override fun processIntent(intent: MyIntent) {
 *         // Handle the different intents and update the uiState
 *         when (intent) {
 *             is MyIntent.LoadData -> { ... }
 *             is MyIntent.UpdateData -> { ... }
 *         }
 *     }
 * }
 * ```
 */
public interface ViewModelContract<S, I> {
    /**
     * Represents the current UI state of the component.
     *
     * This property exposes a [StateFlow] that emits updates whenever the UI state changes.
     * Consumers can collect from this flow to react to these changes and update their UI accordingly.
     *
     * The state is represented by the generic type [S], allowing different types of state data to be used.
     * It's crucial to understand the specific structure of [S] to interpret the emitted values correctly.
     *
     * Common uses include:
     * - Displaying data loaded from a network or database.
     * - Reflecting the result of user interactions.
     * - Showing loading indicators or error messages.
     * - Managing the enabled/disabled state of UI elements.
     *
     * The [StateFlow] guarantees that it will always emit the latest value to new collectors.
     * This ensures that UI components that start observing later will immediately receive the current state.
     */
    public val uiState: StateFlow<S>

    /**
     * Processes the given intent.
     *
     * This function is responsible for handling the logic associated with a specific intent.
     * It takes an intent object as input and performs the necessary actions based on its data or type.
     * The specific actions taken will depend on the implementation within this function.
     *
     * @param intent The intent to be processed. It contains the necessary data or instructions for the
     *               function to carry out its task. The type of the intent is defined by `I`.
     */
    public fun processIntent(intent: I)

    public companion object {
        /**
         * A [SharingStarted] configuration that starts sharing immediately when the first subscriber appears,
         * and stops sharing after a 5-second timeout when there are no more subscribers.
         *
         * This configuration is useful when you want to keep the shared flow active as long as there are active
         * subscribers, but release resources after a short period of inactivity. The 5-second timeout allows for
         * brief periods where all subscribers unsubscribe, without immediately cancelling the upstream flow.
         */
        public val startWithFiveSecStopTimeout: SharingStarted = SharingStarted.WhileSubscribed(5000)
    }
}
