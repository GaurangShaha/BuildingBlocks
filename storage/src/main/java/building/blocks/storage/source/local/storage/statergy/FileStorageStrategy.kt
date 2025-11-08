package building.blocks.storage.source.local.storage.statergy

import android.net.Uri
import building.blocks.storage.StorageLocation
import java.io.InputStream

internal interface FileStorageStrategy {
    fun appendTextToFile(location: StorageLocation, fileName: String, text: String): Uri
    fun save(location: StorageLocation, fileName: String, inputStream: InputStream): Uri
    fun read(location: StorageLocation, fileName: String): InputStream
    fun delete(location: StorageLocation, fileName: String)
}
