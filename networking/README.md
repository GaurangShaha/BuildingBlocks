# Networking Module

The `networking` module provides a robust and modern networking layer for Android applications, built on top of Retrofit. It is designed to simplify network requests, streamline error handling, and promote a clean, testable architecture. The core of this module is a custom Retrofit `CallAdapter` that seamlessly integrates with Kotlin's `Result` type.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:networking:<latest version>"
```

## Core Concepts

### `ResultCallAdapterFactory`

This is the central component of the module. It is a `CallAdapter.Factory` that allows your Retrofit service interfaces to return `Call<Result<T>>` or `Call<Result<Response<T>>>`. This enables you to handle network operations in a more functional and idiomatic Kotlin style.

- **`Call<Result<T>>`**: Use this when you only care about the response body on success. The `Result` will be a `Result.Success<T>` containing the deserialized body, or a `Result.Failure` containing an exception.
- **`Call<Result<Response<T>>>`**: Use this when you need to access the full `Response` object, including headers, status code, etc. The `Result` will be a `Result.Success<Response<T>>` or a `Result.Failure`.

### Custom `Call` Implementations

To power the `ResultCallAdapterFactory`, the module provides two custom `Call` implementations:

- **`ResultWithBodyCall`**: This wraps a standard Retrofit `Call<T>` and handles the conversion of the response to a `Result<T>`. It uses the `executeSafely` function from the `foundation` module to ensure that all exceptions are caught and wrapped in a `Result.Failure`.
- **`ResultWithResponseCall`**: This is similar to `ResultWithBodyCall`, but it wraps the entire `Response<T>` in a `Result`.

### Exception Mapping

The module includes a simple but effective exception mapper (`toInternetConnectionExceptionOrSelf`) that converts common network-related exceptions (`UnknownHostException`, `SocketTimeoutException`) into a custom `InternetDisconnectionException` from the `foundation` module. This allows for more specific and centralized handling of network connectivity issues.

## How to Use

1.  **Add the `ResultCallAdapterFactory` to your Retrofit instance**:

    ```kotlin
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()
    ```

2.  **Define your Retrofit service interface** to return `Call<Result<T>>`:

    ```kotlin
    interface MyApiService {
        @GET("users/{id}")
        fun getUser(@Path("id") userId: String): Call<Result<User>>

        @GET("posts")
        fun getPosts(): Call<Result<List<Post>>>
    }
    ```
