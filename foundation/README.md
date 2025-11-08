# Foundation Module

The `foundation` module serves as the bedrock of the BuildingBlocks library. It provides essential, cross-cutting utilities and base components that are used by other modules. Its primary goal is to offer robust, reusable, and foundational pieces of code that simplify common development tasks.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:foundation:<latest version>"
```

## Core Features

The module is intentionally kept lightweight and focused on providing universally applicable tools.

### Coroutine Exception Handling

- **`executeSafely`**: This is a higher-order suspend function designed to wrap any operation that might throw an exception within a coroutine. It ensures that exceptions are caught gracefully and returned as a `Result` type, which is a standard and idiomatic way to handle errors in Kotlin.

  - **Coroutine-Aware**: `executeSafely` is specifically designed for coroutines. If an exception is caught, it checks if the coroutine is still active (`coroutineContext.ensureActive()`) before propagating the exception. This is a crucial safety measure to prevent work from continuing in a cancelled coroutine.
  - **`finally` Block**: It includes an optional `finalAction` lambda that is executed in a `finally` block. This is useful for cleanup operations, such as closing a stream or hiding a loading indicator, regardless of whether the operation succeeded or failed.

### Custom Exceptions

- **`InternetDisconnectionException`**: A simple, custom `Throwable` object that can be used to represent a specific type of error: the loss of internet connectivity. This allows for more specific error handling in higher-level modules (like the `networking` module or in your UI layer) compared to catching a generic `IOException`.

## How to Use

### `executeSafely`

This function is ideal for use in repositories or any class that performs I/O operations, such as network requests or file access.

**Example: In a Repository**

Instead of a traditional `try-catch` block, you can wrap your suspend functions to ensure safety and consistent error handling.

```kotlin
class MyRepository(private val apiService: MyApiService) {

    suspend fun fetchData(): Result<MyData> {
        return executeSafely {
            // This block is executed within a try-catch
            val response = apiService.getData()
            if (!response.isSuccessful) {
                throw IOException("API call failed")
            }
            response.body() ?: throw NoSuchElementException("Response body is null")
        }
    }

    suspend fun updateData(data: MyData): Result<Unit> {
        return executeSafely(
            finalAction = { println("Update operation finished.") }
        ) {
            apiService.update(data)
        }
    }
}
```

By using `executeSafely`, the calling code (e.g., a ViewModel) can handle both success and failure in a clean and functional way using `Result`'s `fold`, `onSuccess`, and `onFailure` methods.

