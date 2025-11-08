package building.blocks.networking.retrofit.remote.adapter

import building.blocks.networking.retrofit.remote.call.ResultWithBodyCall
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * A [CallAdapter] that adapts a Retrofit [Call] to a [Call] that returns a [Result] with a body.
 *
 * This adapter handles the conversion of a successful or failed Retrofit call into a [Result] object.
 * If the underlying network call is successful (HTTP status code in the 200-299 range), the
 * [Result] will be a [Result.Success] containing the response body.
 * If the call fails (e.g., network error, HTTP error status), the [Result] will be a
 * [Result.Failure] containing an exception describing the error.
 *
 * This adapter is particularly useful when you want to handle both successful and failed network
 * requests uniformly, without needing to check for null bodies or exceptions separately.
 *
 * @property resultType The type of the body that the [Result] will contain.
 * @property resultRawType The raw type of the body that the [Result] will contain.
 * This is often the same as [resultType], but can be different in cases where
 * [resultType] is a parameterized type (e.g. `List<String>`, `Map<String, Int>`).
 * @property coroutineScope The [CoroutineScope] used to execute the network call when
 *  `await()` is called on the adapted [Call].
 */
internal class ResultWithBodyCallAdapter(
    private val resultType: Type,
    private val resultRawType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<Type, Call<Result<Type?>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>) = ResultWithBodyCall(call, resultRawType, coroutineScope)
}
