package building.blocks.storage

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Represents different storage locations available on an Android device for file operations.
 * This sealed class allows for locations to be simple singletons (like [InternalAppCache])
 * or to carry data, such as a custom subdirectory path.
 */
public sealed class StorageLocation(public open val subDirectory: String) {
    /**
     * Represents the internal app-specific storage directory.
     * Files are private to the app and stored in a location corresponding to `Context.getFilesDir()`.
     * @param subDirectory An optional subdirectory within the internal storage.
     */
    public class InternalAppStorage(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Represents the application-specific cache directory on internal storage.
     * This location is for temporary files and the system may delete them. It is a singleton
     * and does not support custom subdirectories via this class.
     * Corresponds to `Context.getCacheDir()`.
     */
    public class InternalAppCache(override val subDirectory: String = "") : StorageLocation("")

    /**
     * External storage for app-specific files. Files are removed when the app is uninstalled.
     * Corresponds to `Context.getExternalFilesDir()`.
     * @param subDirectory An optional subdirectory within the external app-specific storage.
     */
    public class ExternalAppStorage(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing files that have been downloaded by the user.
     * Corresponds to `Environment.DIRECTORY_DOWNLOADS`.
     * @param subDirectory An optional subdirectory within the downloads directory.
     */
    public class PublicDownloads(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing documents that have been created by the user.
     * Corresponds to `Environment.DIRECTORY_DOCUMENTS`.
     * @param subDirectory An optional subdirectory within the documents directory.
     */
    public class PublicDocuments(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing pictures that are available to the user.
     * Corresponds to `Environment.DIRECTORY_PICTURES`.
     * @param subDirectory An optional subdirectory within the pictures directory.
     */
    public class PublicPictures(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * The traditional location for pictures and videos when mounting the device as a camera.
     * Corresponds to `Environment.DIRECTORY_DCIM`.
     * @param subDirectory An optional subdirectory within the DCIM directory.
     */
    public class PublicDcim(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing movies that are available to the user.
     * Corresponds to `Environment.DIRECTORY_MOVIES`.
     * @param subDirectory An optional subdirectory within the movies directory.
     */
    public class PublicMovies(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing music that should be in the user's music list.
     * Corresponds to `Environment.DIRECTORY_MUSIC`.
     * @param subDirectory An optional subdirectory within the music directory.
     */
    public class PublicMusic(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing audiobooks.
     * Corresponds to `Environment.DIRECTORY_AUDIOBOOKS`. Available on Android Q (API 29) and above.
     * @param subDirectory An optional subdirectory within the audiobooks directory.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    public class PublicAudiobooks(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing custom notification sounds.
     * Corresponds to `Environment.DIRECTORY_NOTIFICATIONS`.
     * @param subDirectory An optional subdirectory within the notifications directory.
     */
    public class PublicNotifications(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing custom ringtones.
     * Corresponds to `Environment.DIRECTORY_RINGTONES`.
     * @param subDirectory An optional subdirectory within the ringtones directory.
     */
    public class PublicRingtones(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing custom alarm sounds.
     * Corresponds to `Environment.DIRECTORY_ALARMS`.
     * @param subDirectory An optional subdirectory within the alarms directory.
     */
    public class PublicAlarms(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing podcasts.
     * Corresponds to `Environment.DIRECTORY_PODCASTS`.
     * @param subDirectory An optional subdirectory within the podcasts directory.
     */
    public class PublicPodcasts(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing recorded audio files.
     * Corresponds to `Environment.DIRECTORY_RECORDINGS`. Available on Android S (API 31) and above.
     * @param subDirectory An optional subdirectory within the recordings directory.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    public class PublicRecordings(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Standard directory for placing screenshots.
     * Corresponds to `Environment.DIRECTORY_SCREENSHOTS`. Available on Android Q (API 29) and above.
     * @param subDirectory An optional subdirectory within the screenshots directory.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    public class PublicScreenshots(override val subDirectory: String = "") :
        StorageLocation(subDirectory)

    /**
     * Checks if the storage location is a public, shared directory.
     * @return `true` if it is a public directory, `false` otherwise.
     */
    public fun isPublic(): Boolean {
        return this !is InternalAppStorage && this !is InternalAppCache && this !is ExternalAppStorage
    }
}
