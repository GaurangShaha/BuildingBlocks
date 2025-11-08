-dontwarn reactor.blockhound.integration.BlockHoundIntegration

-keep class retrofit2.Call
-keep class retrofit2.CallAdapter* { *; }
-keep class retrofit2.adapter.default.* { *; }

-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
