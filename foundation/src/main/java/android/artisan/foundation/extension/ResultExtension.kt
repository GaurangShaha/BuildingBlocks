package android.artisan.foundation.extension

import android.artisan.foundation.model.Result
import android.artisan.foundation.model.Result.Failure
import android.artisan.foundation.model.Result.Success

/**
 * Extension functions for the [Result] sealed class.
 *
 * This object provides a set of utility functions to work with [Result] instances,
 * allowing for more expressive and concise code when dealing with operations that
 * can either succeed or fail.
 */
public inline fun <S> Result<S>.fold(onSuccess: (S) -> Unit, onError: (Throwable) -> Unit) {
    when (this) {
        is Success -> onSuccess(value)
        is Failure -> onError(this.error)
    }
}

/**
 * Maps the successful value of a [Result] to a new value.
 *
 * If the [Result] is a [Success], the [onSuccess] function is applied to the value,
 * and a new [Success] is returned with the transformed value.
 * If the [Result] is a [Failure], it is returned unchanged.
 *
 * @param onSuccess The function to apply to the successful value.
 * @return A new [Result] with the mapped value or the original [Failure].
 */
public inline fun <S, V> Result<S>.map(
    onSuccess: (S) -> V
): Result<V> {
    return when (this) {
        is Success -> Success(onSuccess(value))
        is Failure -> this
    }
}

/**
 * Transforms a successful [Result] into another [Result].
 *
 * If the [Result] is a [Success], the [function] is applied to the value,
 * and the resulting [Result] is returned.
 * If the [Result] is a [Failure], it is returned unchanged.
 *
 * @param function The function to apply to the successful value, returning a new [Result].
 * @return The [Result] returned by the function or the original [Failure].
 */
public inline fun <S, T> Result<S>.flatMap(function: (S) -> Result<T>): Result<T> {
    return when (this) {
        is Success -> function(value)
        is Failure -> this
    }
}

/**
 * Maps the error of a [Result] to a new error.
 *
 * If the [Result] is a [Failure], the [onError] function is applied to the error,
 * and a new [Failure] is returned with the transformed error.
 * If the [Result] is a [Success], it is returned unchanged.
 *
 * @param onError The function to apply to the error.
 * @return A new [Result] with the mapped error or the original [Success].
 */
public inline fun <S> Result<S>.mapError(
    onError: (Throwable) -> Throwable
): Result<S> {
    return when (this) {
        is Success -> this
        is Failure -> Failure(onError(error))
    }
}

/**
 * Executes the given [onSuccess] function if the [Result] is a [Success].
 *
 * @param onSuccess The function to execute if the [Result] is a [Success].
 */
public inline fun <S> Result<S>.onSuccess(onSuccess: (S) -> Unit) {
    if (this is Success) onSuccess(value)
}

/**
 * Executes the given [onFailure] function if the [Result] is a [Failure].
 *
 * @param onFailure The function to execute if the [Result] is a [Failure].
 */
public inline fun Result<*>.onFailure(onFailure: (Throwable) -> Unit) {
    if (this is Failure) onFailure(error)
}

/**
 * Returns the value of a [Success] or `null` if it's a [Failure].
 *
 * @return The value if it's a [Success], otherwise `null`.
 */
public fun <S> Result<S>.getOrNull(): S? {
    return when (this) {
        is Success -> value
        is Failure -> null
    }
}

/**
 * Returns the value of a [Success] or a default value if it's a [Failure].
 *
 * @param defaultValue The value to return if it's a [Failure].
 * @return The value if it's a [Success], otherwise the [defaultValue].
 */

public fun <S> Result<S>.getOrElse(defaultValue: S): S {
    return when (this) {
        is Success -> value
        is Failure -> defaultValue
    }
}

/**
 * Returns the value of a [Success] or throws the error if it's a [Failure].
 *
 * @throws Throwable The error if it's a [Failure].
 * @return The value if it's a [Success].
 */
public fun <S> Result<S>.getOrThrow(): S {
    return when (this) {
        is Success -> value
        is Failure -> throw this.error
    }
}
