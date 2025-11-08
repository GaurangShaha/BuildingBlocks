package building.blocks.storage.repository

import android.net.Uri
import building.blocks.storage.StorageLocation
import building.blocks.storage.source.local.FileSource
import java.io.InputStream

internal class DefaultFileRepository(
    private val fileSource: FileSource
) : FileRepository {

    override suspend fun save(
        fileName: String,
        location: StorageLocation,
        inputStream: InputStream
    ): Result<Uri> {
        return fileSource.save(fileName, location, inputStream)
    }

    override suspend fun read(
        fileName: String,
        location: StorageLocation
    ): Result<InputStream> {
        return fileSource.read(fileName, location)
    }

    override suspend fun delete(fileName: String, location: StorageLocation): Result<Unit> {
        return fileSource.delete(fileName, location)
    }

    override suspend fun appendText(
        fileName: String,
        location: StorageLocation,
        text: String
    ): Result<Uri> {
        return fileSource.appendText(fileName, location, text)
    }
}
