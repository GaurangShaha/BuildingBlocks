package building.blocks.storage.repository

import android.net.Uri
import building.blocks.storage.StorageLocation
import java.io.InputStream

/**
 * A repository that provides a unified API for file operations across different storage locations.
 *
 * This interface acts as an abstraction over the underlying file storage mechanism. It can be
 * implemented to handle both standard (plaintext) file operations and encrypted file storage
 * without changing the contract for the caller. The choice between a standard or an encrypted
 * implementation is typically handled via dependency injection, allowing consumers of this
 * repository to remain agnostic to the specific storage details.
 */
public interface FileRepository {
    /**
     * Writes data from an InputStream to a file in the specified location.
     * This may require storage permissions on older Android versions for public locations.
     *
     * @param fileName The name of the file to create (e.g., "profile.jpg").
     * @param location The storage location to save the file in.
     * @param inputStream The stream of data to write.
     * @return A [Result] containing the [Uri] of the newly created file, or an exception.
     */
    public suspend fun save(
        fileName: String,
        location: StorageLocation,
        inputStream: InputStream
    ): Result<Uri>

    /**
     * Reads a file from the specified location as an InputStream.
     * This may require storage permissions on older Android versions for public locations.
     *
     * @param fileName The name of the file to read.
     * @param location The location to read the file from.
     * @return A [Result] containing the file's [InputStream], or an exception.
     */
    public suspend fun read(fileName: String, location: StorageLocation): Result<InputStream>

    /**
     * Deletes a file from the specified location.
     * This may require storage permissions on older Android versions for public locations.
     *
     * @param fileName The name of the file to delete.
     * @param location The location to delete the file from.
     * @return A [Result] indicating success or failure.
     */
    public suspend fun delete(fileName: String, location: StorageLocation): Result<Unit>

    /**
     * Appends text content to the end of a plain text file.
     * If the file does not exist, it will be created.
     * This may require storage permissions on older Android versions for public locations.
     *
     * @param fileName The name of the file (e.g., "logs.txt").
     * @param location The storage location of the file.
     * @param text The text content to append.
     * @return A [Result] containing the [Uri] of the updated file, or an exception.
     */
    public suspend fun appendText(
        fileName: String,
        location: StorageLocation,
        text: String
    ): Result<Uri>
}
