package building.blocks.storage.source.local

import android.net.Uri
import building.blocks.storage.StorageLocation
import java.io.InputStream

internal interface FileSource {
    suspend fun save(
        fileName: String,
        location: StorageLocation,
        inputStream: InputStream
    ): Result<Uri>

    suspend fun read(fileName: String, location: StorageLocation): Result<InputStream>

    suspend fun delete(fileName: String, location: StorageLocation): Result<Unit>
    suspend fun appendText(
        fileName: String,
        location: StorageLocation,
        text: String
    ): Result<Uri>
}
