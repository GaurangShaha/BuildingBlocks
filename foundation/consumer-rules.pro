# With R8 full mode generic signatures are stripped for classes that are not kept.
#This configuration is needed so retrofit will be able to create call adapter of Result type
-keep,allowobfuscation,allowshrinking class android.artisan.foundation.model.Result