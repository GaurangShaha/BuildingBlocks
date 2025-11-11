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

    @Volatile
    private var fileRepository: FileRepository? = null
    @Volatile
    private var encryptedFileRepository: FileRepository? = null

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
     * Returns a singleton instance of [FileRepository] for performing standard (unencrypted) file operations.
     *
     * The instance is created on the first call, and the same instance is returned on subsequent calls.
     *
     * @param ioDispatcher The [CoroutineDispatcher] to be used for I/O operations. This parameter is only
     *                     used during the first call to create the instance. It defaults to [Dispatchers.IO].
     * @return An instance of [FileRepository].
     * @throws IllegalStateException if [initialize] has not been called.
     */
    public fun getFileRepository(
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): FileRepository {
        checkIsInitialised()
        return fileRepository ?: synchronized(this) {
            fileRepository ?: DefaultFileRepository(createLocalFileSource(ioDispatcher)).also {
                fileRepository = it
            }
        }
    }

    /**
     * Returns a singleton instance of [FileRepository] that performs on-the-fly encryption and decryption.
     *
     * The instance is created on the first call, and the same instance is returned on subsequent calls.
     *
     * @param allowPublicFileEncryption If `true`, allows encryption of files in public storage directories.
     *                                  This parameter is only used during the first call. Defaults to `false`.
     * @param ioDispatcher The [CoroutineDispatcher] to be used for I/O operations. This parameter is only
     *                     used during the first call. Defaults to [Dispatchers.IO].
     * @return An instance of [FileRepository] with encryption capabilities.
     * @throws IllegalStateException if [initialize] has not been called.
     */
    public fun getEncryptedFileRepository(
        allowPublicFileEncryption: Boolean = false,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): FileRepository {
        checkIsInitialised()
        return encryptedFileRepository ?: synchronized(this) {
            encryptedFileRepository ?: DefaultFileRepository(
                createEncryptedFileSource(ioDispatcher, allowPublicFileEncryption)
            ).also { encryptedFileRepository = it }
        }
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
            mediaStorePublicFileStorageStrategy = mediaStorePublicFileStorageStrategy,
            legacyPublicFileStorageStrategy = legacyPublicFileStorageStrategy,
            ioDispatcher = ioDispatcher
        )
}
