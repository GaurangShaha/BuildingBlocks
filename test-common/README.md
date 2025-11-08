# Test-Common Module

The `test-common` module provides shared testing utilities that can be used across all other modules in the project. Its purpose is to promote consistency and reduce boilerplate code in unit and integration tests.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
testImplementation "building.blocks:test-common:<latest version>"
```

## Core Features

The module currently provides one key component:

### `MainThreadTestListener`

This is a `TestListener` for the Kotest testing framework. Its primary responsibility is to manage the `Dispatchers.Main` for the duration of a test spec. This is essential for testing components that use `Dispatchers.Main`, such as ViewModels, without having to deal with the complexities of the Android Looper.

- **Automatic Dispatcher Swapping**: Before a test spec runs, `MainThreadTestListener` replaces the main dispatcher with an `UnconfinedTestDispatcher`. This means that any coroutine launched on `Dispatchers.Main` will execute immediately on the test thread.
- **Automatic Cleanup**: After the test spec is complete, the listener restores the original main dispatcher, ensuring that tests are properly isolated and do not interfere with each other.

## How to Use

To use the `MainThreadTestListener`, you simply need to register it in your test class. With Kotest, you can do this by overriding the `listeners` method.

### Example: Testing a ViewModel

Let's say you have a ViewModel that launches a coroutine on `viewModelScope`, which uses `Dispatchers.Main` by default.

**ViewModel:**

```kotlin
class MyViewModel : ViewModel() {
    private val _data = MutableStateFlow<String>("")
    val data: StateFlow<String> = _data

    fun loadData() {
        viewModelScope.launch {
            _data.value = "Loading..."
            delay(1000) // Simulate a network request
            _data.value = "Hello, World!"
        }
    }
}
```

**Test Class:**

Without the `MainThreadTestListener`, testing this ViewModel would be tricky because the coroutine on `viewModelScope` would be dispatched to the real Android main looper, which is not available in a standard unit test. By using the listener, the coroutine will run on the test thread.

```kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class MyViewModelTest : StringSpec() {

    override fun listeners() = listOf(MainThreadTestListener())

    init {
        "loadData should update data" { runTest {
            val viewModel = MyViewModel()

            viewModel.loadData()

            // Because we are using UnconfinedTestDispatcher, the coroutine runs immediately,
            // but the delay will still be active. We need to advance the time.
            advanceTimeBy(1000)

            viewModel.data.value shouldBe "Hello, World!"
        } }
    }
}
```

By using `MainThreadTestListener`, you can write tests for your ViewModels and other components that use `Dispatchers.Main` in a clean and simple way, without needing to manually manage the main dispatcher in every test.
