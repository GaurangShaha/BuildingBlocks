# Storage Module

The `storage` module provides a comprehensive and unified API for managing file storage on Android. It abstracts the complexities of Android's file system, offering a simple, robust, and version-agnostic way to handle file operations in various storage locations.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:storage:<latest version>"
```

## Core Concepts

The module is built around a few key components that work together to provide a seamless file storage experience.

### `StorageLocation`

This sealed class defines all possible storage destinations for files. It provides a type-safe way to specify where a file should be saved or from where it should be read. The available locations include:

- **App-Specific Storage**: These locations are private to your application and are automatically removed when the app is uninstalled.
  - `InternalAppStorage`: The app's private directory on internal storage (`Context.getFilesDir()`).
  - `InternalAppCache`: The app's private cache directory (`Context.getCacheDir()`). The system may clear this directory when space is low.
  - `ExternalAppStorage`: The app's private directory on external storage (`Context.getExternalFilesDir()`).
- **Public Storage**: These are shared directories on the device. Starting from Android 10, access to these directories is managed by Scoped Storage.
  - `PublicDownloads`, `PublicDocuments`, `PublicPictures`, `PublicMovies`, `PublicMusic`, etc.

### `FileStorageStrategy`

To handle the significant differences in file I/O before and after Android 10 (Scoped Storage), the module uses a strategy pattern. The `FileStorageStrategy` interface defines the core file operations, and the system uses the correct implementation based on the Android version and the chosen `StorageLocation`.

- `AppSpecificFileStorageStrategy`: Handles all file operations within the app-specific directories. It uses the standard `java.io.File` APIs.
- `LegacyPublicFileStorageStrategy`: Used for public directories on devices running below Android 10. It also uses the `java.io.File` APIs and relies on `Environment.getExternalStoragePublicDirectory()`.
- `MediaStorePublicFileStorageStrategy`: Used for public directories on Android 10 and above. It interacts with the `MediaStore` API, which is the standard for Scoped Storage.

### `FileRepository` and `FileSource`

The primary entry point for all file operations is the `FileRepository`, which relies on a `FileSource` interface. This abstraction decouples the client code from the underlying storage implementation.

- `LocalFileSource`: The default implementation of `FileSource` that delegates to the appropriate `FileStorageStrategy`.

### Encrypted Storage

For enhanced security, the module includes a decorator, `EncryptedFileLocalSource`, which wraps another `FileSource`. It intercepts all read/write operations and performs on-the-fly encryption and decryption using the `StreamCryptoManager` from the `crypto` module.

By default, encryption is disabled for public directories to prevent accidental encryption of shared files. This can be overridden if needed.

## How to Use

1.  **Initialize the `Storage` object** in your `Application` class:

    ```kotlin
    class MyApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            Storage.initialize(this)
        }
    }
    ```

2.  **Get a `FileRepository` instance**:

    ```kotlin
    // For standard file operations
    val fileRepository = Storage.getFileRepository()

    // For encrypted file operations
    val encryptedFileRepository = Storage.getEncryptedFileRepository()
    ```

3.  **Use the `FileRepository`**:

    ```kotlin
    class MyViewModel(private val fileRepository: FileRepository) : ViewModel() {
        suspend fun saveImage(fileName: String, data: InputStream) {
            fileRepository.save(
                fileName = fileName,
                location = StorageLocation.PublicPictures("MySubfolder"),
                inputStream = data
            )
        }

        suspend fun readTextFile(fileName: String): String? {
            return fileRepository.read(fileName, StorageLocation.InternalAppStorage()).getOrNull()?.reader()?.readText()
        }
    }
    ```
