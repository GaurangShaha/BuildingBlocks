package building.blocks.storage

import android.annotation.SuppressLint
import android.content.Context
import building.blocks.storage.Storage.initialize
import building.blocks.storage.encrypted.source.local.EncryptedFileLocalSource
import building.blocks.storage.repository.DefaultFileRepository
import building.blocks.storage.repository.FileRepository
import building.blocks.storage.source.local.LocalFileSource
import building.blocks.storage.source.local.storage.statergy.AppSpecificFileStorageStrategy
import building.blocks.storage.source.local.storage.statergy.LegacyPublicFileStorageStrategy
import building.blocks.storage.source.local.storage.statergy.MediaStorePublicFileStorageStrategy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A singleton object that provides access to the main functionalities of the storage module.
 *
 * It is responsible for initializing the required components and providing instances of
 * [FileRepository] for both standard and encrypted file operations.
 *
 * **Important:** You must call [initialize] before using any other methods in this object.
 * This is typically done once in your Application's `onCreate` method.
 */
@SuppressLint("StaticFieldLeak")
public object Storage {
    private lateinit var appSpecificFileStorageStrategy: AppSpecificFileStorageStrategy
    private lateinit var mediaStorePublicFileStorageStrategy: MediaStorePublicFileStorageStrategy
    private lateinit var legacyPublicFileStorageStrategy: LegacyPublicFileStorageStrategy

    /**
     * Initializes the Storage module with the application context.
     *
     * This method sets up the necessary file storage strategies. It should be called once,
     * typically in your Application's `onCreate` method, before any other methods of the `Storage`
     * object are accessed.
     *
     * @param context The application context, used to create storage strategies.
     */
    public fun initialize(context: Context) {
        if (!::appSpecificFileStorageStrategy.isInitialized) {
            appSpecificFileStorageStrategy =
                AppSpecificFileStorageStrategy(context.applicationContext)
        }

        if (!::legacyPublicFileStorageStrategy.isInitialized) {
            legacyPublicFileStorageStrategy =
                LegacyPublicFileStorageStrategy(context.applicationContext)
        }

        if (!::mediaStorePublicFileStorageStrategy.isInitialized) {
            mediaStorePublicFileStorageStrategy =
                MediaStorePublicFileStorageStrategy(context.applicationContext)
        }
    }

    /**
     * Returns a [FileRepository] for performing standard (unencrypted) file operations.
     *
     * @param ioDispatcher The [CoroutineDispatcher] to be used for I/O operations. Defaults to [Dispatchers.IO].
     * @return An instance of [FileRepository].
     * @throws IllegalStateException if [initialize] has not been called.
     */
    public fun getFileRepository(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): FileRepository {
        checkIsInitialised()
        return DefaultFileRepository(createLocalFileSource(ioDispatcher))
    }

    /**
     * Returns a [FileRepository] that performs on-the-fly encryption and decryption for all file operations.
     *
     * @param allowPublicFileEncryption If `true`, allows encryption of files in public storage directories.
     *                                  Defaults to `false` to prevent accidental encryption of shared files.
     * @param ioDispatcher The [CoroutineDispatcher] to be used for I/O operations. Defaults to [Dispatchers.IO].
     * @return An instance of [FileRepository] with encryption capabilities.
     * @throws IllegalStateException if [initialize] has not been called.
     */
    public fun getEncryptedFileRepository(
        allowPublicFileEncryption: Boolean = false,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): FileRepository {
        checkIsInitialised()
        return DefaultFileRepository(
            createEncryptedFileSource(ioDispatcher, allowPublicFileEncryption)
        )
    }

    private fun createEncryptedFileSource(
        ioDispatcher: CoroutineDispatcher,
        allowPublicFileEncryption: Boolean
    ) = EncryptedFileLocalSource(
        localFileSource = createLocalFileSource(ioDispatcher),
        allowPublicFileEncryption = allowPublicFileEncryption,
        ioDispatcher = ioDispatcher
    )

    private fun checkIsInitialised() {
        check(
            ::appSpecificFileStorageStrategy.isInitialized &&
                ::mediaStorePublicFileStorageStrategy.isInitialized &&
                ::legacyPublicFileStorageStrategy.isInitialized
        ) { "Please initialize Storage module before using it." }
    }

    private fun createLocalFileSource(ioDispatcher: CoroutineDispatcher): LocalFileSource =
        LocalFileSource(
            appSpecificFileStorageStrategy = appSpecificFileStorageStrategy,
            mediaStorePublicStorageStrategy = mediaStorePublicFileStorageStrategy,
            legacyPublicStorageStrategy = legacyPublicFileStorageStrategy,
            ioDispatcher = ioDispatcher
        )
}
