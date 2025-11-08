package building.blocks.storage.source.local.storage.statergy

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import building.blocks.storage.StorageLocation
import building.blocks.storage.StorageLocation.ExternalAppStorage
import building.blocks.storage.StorageLocation.InternalAppCache
import building.blocks.storage.StorageLocation.InternalAppStorage
import java.io.File
import java.io.IOException
import java.io.InputStream

internal class AppSpecificFileStorageStrategy(private val context: Context) : FileStorageStrategy {
    override fun appendTextToFile(
        location: StorageLocation,
        fileName: String,
        text: String
    ): Uri {
        val file = getAppSpecificFile(location, fileName)

        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
        file.appendText(text)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    override fun save(
        location: StorageLocation,
        fileName: String,
        inputStream: InputStream
    ): Uri {
        val file = getAppSpecificFile(location, fileName)
        file.parentFile?.mkdirs()

        file.outputStream().use { outputStream ->
            inputStream.use { it.copyTo(outputStream) }
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    override fun read(location: StorageLocation, fileName: String): InputStream {
        return getAppSpecificFile(location, fileName).inputStream()
    }

    override fun delete(
        location: StorageLocation,
        fileName: String
    ) {
        val file = getAppSpecificFile(location, fileName)
        if (file.exists() && !file.delete()) {
            throw IOException("Failed to delete file: ${file.path}")
        }
    }

    private fun getAppSpecificFile(location: StorageLocation, fileName: String) = File(
        getAppSpecificDirectory(location),
        getChildPath(location, fileName)
    )

    private fun getChildPath(location: StorageLocation, fileName: String) = buildString {
        if (location.subDirectory.isNotEmpty()) {
            append(location.subDirectory.trim('/'))
            append("/")
        }
        append(fileName)
    }

    private fun getAppSpecificDirectory(location: StorageLocation): File {
        return when (location) {
            is InternalAppStorage -> context.filesDir
            is InternalAppCache -> context.cacheDir
            is ExternalAppStorage -> context.getExternalFilesDir(null)
                ?: error("External storage not available")

            else -> error("This method is for app-specific storage only")
        }
    }
}
