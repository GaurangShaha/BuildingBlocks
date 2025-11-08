# UI-Common Module

The `ui-common` module provides a set of common contracts and extensions for building UIs in an Android application. It is designed to promote a consistent and robust architecture for the UI layer, based on modern Android development patterns.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:ui-common:<latest version>"
```

## Core Features

### `ViewModelContract`

This is an interface that defines a contract for ViewModels that follow a Model-View-Intent (MVI) or similar state management pattern. It enforces a clear separation of concerns within the ViewModel, making it easier to test and reason about.

- **`uiState`**: A `StateFlow` that represents the current state of the UI. This is the single source of truth for the UI.
- **`processIntent`**: A function that takes a user intent and processes it to update the UI state. Intents represent user actions or other events that can change the state of the UI.

By implementing this contract, your ViewModels will have a consistent structure, making them easier to work with and maintain.

### `StateFlow` Extensions

- **`ifInstanceOf`**: This is an inline extension function for `StateFlow` that allows you to conditionally execute a block of code if the current value of the `StateFlow` is an instance of a specific type. This is particularly useful when you have a `StateFlow` that can hold different types of state (e.g., using a sealed class) and you want to perform an action only when the state is of a certain type.

## How to Use

### `ViewModelContract`

1.  **Define your UI state and intents**:

    ```kotlin
    data class MyUiState(val isLoading: Boolean = false, val data: String? = null)

    sealed class MyIntent {
        object LoadData : MyIntent()
        data class UpdateData(val newData: String) : MyIntent()
    }
    ```

2.  **Implement the `ViewModelContract` in your ViewModel**:

    ```kotlin
    class MyViewModel : ViewModel(), ViewModelContract<MyUiState, MyIntent> {

        private val _uiState = MutableStateFlow(MyUiState())
        override val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

        override fun processIntent(intent: MyIntent) {
            when (intent) {
                is MyIntent.LoadData -> {
                    viewModelScope.launch {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        delay(1000)
                        _uiState.value = _uiState.value.copy(isLoading = false, data = "Hello, World!")
                    }
                }
                is MyIntent.UpdateData -> {
                    _uiState.value = _uiState.value.copy(data = intent.newData)
                }
            }
        }
    }
    ```

3.  **Collect the UI state in your UI**:

    ```kotlin
    @Composable
    fun MyScreen(viewModel: MyViewModel) {
        val uiState by viewModel.uiState.collectAsState()

        // Your UI code here...
    }
    ```

### `ifInstanceOf`

This extension can be used to simplify the handling of different states within a `StateFlow`.

**Example:**

```kotlin
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}

val uiState: StateFlow<UiState> = // ...

// Somewhere in your code, you want to perform an action only when the state is Success
uiState.ifInstanceOf<UiState.Success> { successState ->
    println("The data is: ${successState.data}")
}
```
