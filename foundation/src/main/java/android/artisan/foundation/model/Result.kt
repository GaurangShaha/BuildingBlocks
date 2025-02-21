package android.artisan.foundation.model

/**
 * A sealed class that represents the result of an operation that can either succeed with a value of type [S] or
 * fail with an error of type [Throwable].
 *
 * This class is designed to handle operations that might fail,
 * providing a structured way to represent both success and failure scenarios.
 * It is similar to the `Result` class in the Kotlin standard library but is tailored for use within
 * the `android.artisan.foundation.model` package.
 *
 * @param S The type of the value in case of success.
 *
 * @see Success
 * @see Failure
 */
public sealed class Result<out S> {
    /**
     * Represents a successful operation.
     *
     * Contains the result [value] of type [S].
     *
     * @param value The successful result of the operation.
     */
    public data class Success<out S>(val value: S) : Result<S>()

    /**
     * Represents a failed operation.
     *
     * Contains the [error] that caused the failure.
     *
     * @param error The [Throwable] that represents the error.
     */
    public data class Failure(val error: Throwable) : Result<Nothing>()

    /**
     * Returns `true` if this [Result] is a [Success], `false` otherwise.
     *
     * Use this property to check if the operation was successful.
     */
    public val isSuccess: Boolean get() = this is Success<S>

    /**
     * Returns `true` if this [Result] is a [Failure], `false` otherwise.
     *
     * Use this property to check if the operation failed.
     */
    public val isFailure: Boolean get() = this is Failure
}
