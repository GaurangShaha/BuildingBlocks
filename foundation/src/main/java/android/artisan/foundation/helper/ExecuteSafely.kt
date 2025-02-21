package android.artisan.foundation.helper

import android.artisan.foundation.model.Result
import android.artisan.foundation.model.Result.Failure
import android.artisan.foundation.model.Result.Success
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

/**
 * Executes the given [block] safely within a coroutine, handling any exceptions that may occur.
 *
 * This function provides a convenient way to wrap code that might throw exceptions and handle them gracefully.
 * It ensures that the coroutine is still active before propagating any caught exceptions.
 *
 * @param finally An optional lambda that will be executed in the `finally` block,
 * regardless of whether an exception occurred.
 * @param block The block of code to execute.
 * @return A [Result] object representing the outcome of the operation.
 *         - [Success] if the block executed successfully, containing the result of the block.
 *         - [Failure] if an exception occurred, containing the exception.
 */

@Suppress("TooGenericExceptionCaught")
public suspend inline fun <T> executeSafely(finally: () -> Unit = {}, block: () -> T): Result<T> =
    try {
        Success(block())
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Failure(e)
    } finally {
        finally()
    }
