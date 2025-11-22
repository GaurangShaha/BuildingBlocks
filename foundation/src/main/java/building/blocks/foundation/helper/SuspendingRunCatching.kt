package building.blocks.foundation.helper

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

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
public suspend inline fun <T> suspendingRunCatching(finalAction: () -> Unit = {}, operation: () -> T): Result<T> =
    try {
        Result.success(operation())
    } catch (exception: Exception) {
        currentCoroutineContext().ensureActive()
        Result.failure(exception)
    } finally {
        finalAction()
    }
