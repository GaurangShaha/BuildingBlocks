package building.blocks.storage.source.local.storage.statergy

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import building.blocks.storage.StorageLocation
import building.blocks.storage.source.local.mapper.getPublicDirectoryName
import java.io.File
import java.io.IOException
import java.io.InputStream

internal class LegacyPublicFileStorageStrategy(private val context: Context) : FileStorageStrategy {
    override fun save(location: StorageLocation, fileName: String, inputStream: InputStream): Uri {
        val file = getPublicFile(location, fileName)
        file.parentFile?.mkdirs()
        file.outputStream().use { outputStream ->
            inputStream.use { it.copyTo(outputStream) }
        }
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    override fun read(location: StorageLocation, fileName: String): InputStream {
        val file = getPublicFile(location, fileName)
        return file.inputStream()
    }

    override fun delete(location: StorageLocation, fileName: String) {
        val file = getPublicFile(location, fileName)
        if (file.exists() && !file.delete()) {
            throw IOException("Failed to delete file: ${file.path}")
        }
    }

    private fun getPublicFile(
        location: StorageLocation,
        fileName: String
    ): File {
        return File(
            Environment.getExternalStoragePublicDirectory(location.getPublicDirectoryName()),
            getChildPath(location, fileName)
        )
    }

    private fun getChildPath(
        location: StorageLocation,
        fileName: String
    ): String = buildString {
        if (location.subDirectory.isNotEmpty()) {
            append(location.subDirectory.trim('/'))
            append("/")
        }
        append(fileName)
    }

    override fun appendTextToFile(location: StorageLocation, fileName: String, text: String): Uri {
        val file = getPublicFile(location, fileName)
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            file.createNewFile()
        }
        file.appendText(text)
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}
