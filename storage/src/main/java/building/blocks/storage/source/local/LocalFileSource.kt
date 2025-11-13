package building.blocks.storage.source.local

import android.net.Uri
import android.os.Build
import building.blocks.foundation.helper.executeSafely
import building.blocks.storage.StorageLocation
import building.blocks.storage.source.local.storage.statergy.FileStorageStrategy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

internal class LocalFileSource(
    private val appSpecificFileStorageStrategy: FileStorageStrategy,
    private val mediaStorePublicFileStorageStrategy: FileStorageStrategy,
    private val legacyPublicFileStorageStrategy: FileStorageStrategy,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FileSource {

    private fun getStrategy(location: StorageLocation): FileStorageStrategy {
        return if (location.isPublic()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mediaStorePublicFileStorageStrategy
            } else {
                legacyPublicFileStorageStrategy
            }
        } else {
            appSpecificFileStorageStrategy
        }
    }

    override suspend fun save(
        fileName: String,
        location: StorageLocation,
        inputStream: InputStream
    ): Result<Uri> = withContext(ioDispatcher) {
        executeSafely {
            getStrategy(location).save(location, fileName, inputStream)
        }
    }

    override suspend fun read(
        fileName: String,
        location: StorageLocation
    ): Result<InputStream> = withContext(ioDispatcher) {
        executeSafely {
            getStrategy(location).read(location, fileName)
        }
    }

    override suspend fun delete(fileName: String, location: StorageLocation): Result<Unit> =
        withContext(ioDispatcher) {
            executeSafely {
                getStrategy(location).delete(location, fileName)
            }
        }

    override suspend fun appendText(
        fileName: String,
        location: StorageLocation,
        text: String
    ): Result<Uri> = withContext(ioDispatcher) {
        executeSafely {
            if (fileName.substringAfterLast('.', "") in plainTextExtensions) {
                getStrategy(location).appendTextToFile(location, fileName, text)
            } else {
                error("File is not a plain text file")
            }
        }
    }

    private val plainTextExtensions =
        listOf("txt", "text", "log", "conf", "cfg", "ini", "md", "csv")
}
