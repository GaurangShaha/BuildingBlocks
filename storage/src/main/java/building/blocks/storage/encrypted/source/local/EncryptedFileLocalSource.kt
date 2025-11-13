package building.blocks.storage.encrypted.source.local

import android.net.Uri
import building.blocks.crypto.StreamCryptoManager
import building.blocks.foundation.helper.executeSafely
import building.blocks.storage.StorageLocation
import building.blocks.storage.source.local.FileSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.InputStream

internal class EncryptedFileLocalSource(
    private val localFileSource: FileSource,
    private val allowPublicFileEncryption: Boolean = false,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FileSource {
    private val cryptoManager: StreamCryptoManager = StreamCryptoManager()
    override suspend fun save(
        fileName: String,
        location: StorageLocation,
        inputStream: InputStream
    ): Result<Uri> {
        return withContext(ioDispatcher) {
            executeSafely {
                checkPublicFileEncryptionAllowed(location)

                cryptoManager.encrypt(inputStream).use { encryptedStream ->
                    localFileSource.save(fileName, location, encryptedStream)
                }.getOrThrow()
            }
        }
    }

    override suspend fun read(
        fileName: String,
        location: StorageLocation
    ): Result<InputStream> {
        return withContext(ioDispatcher) {
            executeSafely {
                checkPublicFileEncryptionAllowed(location)

                localFileSource.read(fileName, location).map { encryptedInputStream ->
                    cryptoManager.decrypt(encryptedInputStream)
                }.getOrThrow()
            }
        }
    }

    override suspend fun delete(fileName: String, location: StorageLocation): Result<Unit> {
        return withContext(ioDispatcher) {
            executeSafely {
                checkPublicFileEncryptionAllowed(location)

                localFileSource.delete(fileName, location).getOrThrow()
            }
        }
    }

    override suspend fun appendText(
        fileName: String,
        location: StorageLocation,
        text: String
    ): Result<Uri> {
        return withContext(ioDispatcher) {
            executeSafely {
                checkPublicFileEncryptionAllowed(location)

                val existingContent = read(fileName, location).fold(
                    { readingStream ->
                        readingStream.use { it.reader().readText() }.also {
                            localFileSource.delete(fileName, location)
                        }
                    },
                    { if (it is FileNotFoundException) "" else throw it }
                )

                (existingContent + text).byteInputStream().use { inputStream ->
                    save(fileName, location, inputStream).getOrThrow()
                }
            }
        }
    }

    private fun checkPublicFileEncryptionAllowed(location: StorageLocation) {
        require(!location.isPublic() || allowPublicFileEncryption) {
            "Encryption of public files is disabled by default. " +
                "Set 'allowPublicFileEncryption' to true if you intend to encrypt files in public storage."
        }
    }
}
