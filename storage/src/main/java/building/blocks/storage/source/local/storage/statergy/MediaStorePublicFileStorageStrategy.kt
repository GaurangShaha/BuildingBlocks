package building.blocks.storage.source.local.storage.statergy

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import building.blocks.storage.StorageLocation
import building.blocks.storage.StorageLocation.PublicAlarms
import building.blocks.storage.StorageLocation.PublicAudiobooks
import building.blocks.storage.StorageLocation.PublicDcim
import building.blocks.storage.StorageLocation.PublicDocuments
import building.blocks.storage.StorageLocation.PublicDownloads
import building.blocks.storage.StorageLocation.PublicMovies
import building.blocks.storage.StorageLocation.PublicMusic
import building.blocks.storage.StorageLocation.PublicNotifications
import building.blocks.storage.StorageLocation.PublicPictures
import building.blocks.storage.StorageLocation.PublicPodcasts
import building.blocks.storage.StorageLocation.PublicRecordings
import building.blocks.storage.StorageLocation.PublicRingtones
import building.blocks.storage.StorageLocation.PublicScreenshots
import building.blocks.storage.source.local.mapper.getPublicDirectoryName
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

internal class MediaStorePublicFileStorageStrategy(private val context: Context) :
    FileStorageStrategy {
    override fun save(location: StorageLocation, fileName: String, inputStream: InputStream): Uri {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, fileName.getMimeTypeForLocation())
            put(MediaStore.MediaColumns.RELATIVE_PATH, getRelativePath(location))
        }
        val contentUri = getMediaStoreContentUri(location)
        val uri = resolver.insert(contentUri, contentValues)
            ?: throw IOException("Failed to create MediaStore entry in $location")
        resolver.openOutputStream(uri)?.use { outputStream ->
            inputStream.use { it.copyTo(outputStream) }
        } ?: throw IOException("Failed to open output stream")
        return uri
    }

    override fun read(location: StorageLocation, fileName: String): InputStream {
        val uri = findPublicFileUri(location, fileName)
            ?: throw IOException("File not found in $location: $fileName")
        return context.contentResolver.openInputStream(uri)
            ?: throw IOException("Failed to open input stream for $fileName")
    }

    override fun delete(location: StorageLocation, fileName: String) {
        val uri = findPublicFileUri(location, fileName) ?: return
        if (context.contentResolver.delete(uri, null, null) <= 0) {
            if (findPublicFileUri(location, fileName) != null) {
                throw IOException("Failed to delete file from MediaStore: $fileName")
            }
        }
    }

    override fun appendTextToFile(location: StorageLocation, fileName: String, text: String): Uri {
        val uri = findPublicFileUri(location, fileName)
            ?: "".byteInputStream().use { emptyStream ->
                save(location, fileName, emptyStream)
            }

        context.contentResolver.openFileDescriptor(uri, "wa")?.use { file ->
            FileOutputStream(file.fileDescriptor).use { it.write(text.toByteArray()) }
        } ?: throw IOException("Failed to open file for appending")
        return uri
    }

    private fun findPublicFileUri(location: StorageLocation, fileName: String): Uri? {
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val selection =
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ? AND ${MediaStore.MediaColumns.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf(fileName, "%${getRelativePath(location)}%")
        val contentUri = getMediaStoreContentUri(location)

        context.contentResolver.query(contentUri, projection, selection, selectionArgs, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val id =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    return ContentUris.withAppendedId(contentUri, id)
                }
            }
        return null
    }

    private fun getRelativePath(location: StorageLocation) = buildString {
        append(location.getPublicDirectoryName())
        if (location.subDirectory.isNotEmpty()) {
            append("/")
            append(location.subDirectory.trim('/'))
        }
    }

    private fun String.getMimeTypeForLocation(): String {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.substringAfterLast('.', ""))
            ?: "application/octet-stream"
    }

    @SuppressLint("NewApi")
    private fun getMediaStoreContentUri(location: StorageLocation): Uri {
        return when (location) {
            is PublicDownloads -> MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
            is PublicDocuments -> MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
            is PublicPictures,
            is PublicScreenshots,
            is PublicDcim -> MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

            is PublicMovies -> MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            is PublicMusic,
            is PublicNotifications,
            is PublicRingtones,
            is PublicAlarms,
            is PublicPodcasts,
            is PublicAudiobooks,
            is PublicRecordings -> MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

            else -> throw IllegalArgumentException("Location is not a public directory: $location")
        }
    }
}
