package android.artisan.foundation.helper

import android.artisan.foundation.model.Result
import android.artisan.foundation.model.Result.Failure
import android.artisan.foundation.model.Result.Success
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

/**
 * Executes the given [operation] safely within a coroutine, handling any exceptions that may occur.
 *
 * This function provides a convenient way to wrap code that might throw exceptions and handle them gracefully.
 * It ensures that the coroutine is still active before propagating any caught exceptions.
 *
 * @param finalAction An optional lambda that will be executed in the `finalAction` operation,
 * regardless of whether an exception occurred.
 * @param operation The operation of code to execute.
 * @return A [Result] object representing the outcome of the operation.
 *         - [Success] if the operation executed successfully, containing the result of the operation.
 *         - [Failure] if an exception occurred, containing the exception.
 */

@Suppress("TooGenericExceptionCaught")
public suspend inline fun <T> executeSafely(finalAction: () -> Unit = {}, operation: () -> T): Result<T> =
    try {
        Success(operation())
    } catch (exception: Exception) {
        coroutineContext.ensureActive()
        Failure(exception)
    } finally {
        finalAction()
    }

/**
 * Executes the given [operation] and returns its result encapsulated in a [Result] object.
 *
 * This function provides a concise way to handle exceptions that might occur during the execution of a block of code.
 * If the [operation] completes successfully, the [Result] will contain the successful result within a [Success] object.
 * If an [Exception] is thrown during the execution of the [operation], the [Result] will contain the caught exception
 * within a [Failure] object.
 *
 * The [finalAction] lambda, if provided, will always be executed after the [operation] attempt, regardless of whether
 * the operation was successful or an exception occurred. This allows for cleanup operations to be performed.
 *
 * @param finalAction A lambda that is executed after the [operation] attempt, regardless of success or failure.
 *                    Defaults to an empty lambda if not provided.
 * @param operation A lambda that represents the operation to be executed.
 * @return A [Result] object containing either the successful result or the caught exception.
 */
@Suppress("TooGenericExceptionCaught")
public inline fun <T> runCatching(finalAction: () -> Unit = {}, operation: () -> T): Result<T> =
    try {
        Success(operation())
    } catch (exception: Exception) {
        Failure(exception)
    } finally {
        finalAction()
    }
