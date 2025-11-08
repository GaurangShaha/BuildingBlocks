package building.blocks.storage.source.local.mapper

import android.os.Build
import android.os.Environment
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

@Suppress("CyclomaticComplexMethod", "ThrowsCount")
internal fun StorageLocation.getPublicDirectoryName(): String {
    return when (this) {
        is PublicDownloads -> Environment.DIRECTORY_DOWNLOADS
        is PublicDocuments -> Environment.DIRECTORY_DOCUMENTS
        is PublicPictures -> Environment.DIRECTORY_PICTURES
        is PublicDcim -> Environment.DIRECTORY_DCIM
        is PublicMovies -> Environment.DIRECTORY_MOVIES
        is PublicMusic -> Environment.DIRECTORY_MUSIC
        is PublicAudiobooks -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Environment.DIRECTORY_AUDIOBOOKS
        } else {
            throw UnsupportedOperationException("PublicAudiobooks is not available on API levels lower than Q")
        }

        is PublicNotifications -> Environment.DIRECTORY_NOTIFICATIONS
        is PublicRingtones -> Environment.DIRECTORY_RINGTONES
        is PublicAlarms -> Environment.DIRECTORY_ALARMS
        is PublicPodcasts -> Environment.DIRECTORY_PODCASTS
        is PublicRecordings -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Environment.DIRECTORY_RECORDINGS
        } else {
            throw UnsupportedOperationException("PublicRecordings is not available on API levels lower than S")
        }

        is PublicScreenshots -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Environment.DIRECTORY_SCREENSHOTS
        } else {
            throw UnsupportedOperationException("PublicScreenshots is not available on API levels lower than Q")
        }

        else -> throw UnsupportedOperationException("Location is not a public directory: $this")
    }
}
