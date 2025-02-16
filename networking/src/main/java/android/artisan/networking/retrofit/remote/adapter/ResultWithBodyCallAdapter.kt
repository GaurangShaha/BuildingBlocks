package android.artisan.networking.retrofit.remote.adapter

import android.artisan.foundation.model.Result
import android.artisan.networking.retrofit.remote.call.ResultWithBodyCall
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResultWithBodyCallAdapter(
    private val resultType: Type,
    private val resultRawType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<Type, Call<Result<Type?>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>) = ResultWithBodyCall(call, resultRawType, coroutineScope)
}
