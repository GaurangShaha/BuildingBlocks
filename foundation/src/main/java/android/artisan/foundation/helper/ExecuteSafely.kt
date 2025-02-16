package android.artisan.foundation.helper

import android.artisan.foundation.model.Result
import android.artisan.foundation.model.Result.Failure
import android.artisan.foundation.model.Result.Success
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

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
