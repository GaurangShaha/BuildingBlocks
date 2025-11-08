package building.blocks.crypto

import building.blocks.crypto.KeyProvider.GCM_IV_LENGTH
import building.blocks.crypto.KeyProvider.GCM_TAG_LENGTH
import building.blocks.crypto.KeyProvider.TRANSFORMATION
import building.blocks.crypto.KeyPurpose.FILE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.GCMParameterSpec

/**
 * Manages stream-based cryptographic operations for encrypting and decrypting large data, such as files.
 *
 * This class is designed to handle encryption and decryption on-the-fly without loading the entire
 * content into memory. It uses piped streams to connect the encryption/decryption process to the
 * reading/writing process, making it memory-efficient.
 *
 * @property encryptionScope The [CoroutineScope] on which the encryption process is performed.
 * Defaults to [Dispatchers.Default] to avoid blocking the main thread.
 */
internal class StreamCryptoManager(
    private val encryptionScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    /**
     * Encrypts an [InputStream] and returns a new [InputStream] that provides the encrypted data.
     *
     * The encryption process is launched in a coroutine. The Initialization Vector (IV) is prepended
     * to the start of the output stream, followed by the encrypted data.
     *
     * @param data The plaintext [InputStream] to be encrypted.
     * @return A [PipedInputStream] from which the encrypted data (IV + ciphertext) can be read.
     */
    fun encrypt(data: InputStream): InputStream {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, KeyProvider.getOrCreateSecretKey(FILE))

        val pipedInputStream = PipedInputStream()
        val pipedOutputStream = PipedOutputStream(pipedInputStream)

        encryptionScope.launch {
            pipedOutputStream.use { outputStream ->
                outputStream.write(cipher.iv)

                CipherOutputStream(outputStream, cipher).use { cipherStream ->
                    data.copyTo(cipherStream)
                }
            }
        }

        return pipedInputStream
    }

    /**
     * Decrypts an [InputStream] containing encrypted data.
     *
     * This function assumes the input stream is structured with the Initialization Vector (IV) at the
     * beginning, followed by the ciphertext. It reads the IV, initializes the cipher for decryption,
     * and returns a [CipherInputStream] that decrypts the remaining data as it is read.
     *
     * @param encryptedData The [InputStream] containing the IV and the encrypted ciphertext.
     * @return A [CipherInputStream] that provides the decrypted plaintext data.
     * @throws java.lang.IllegalStateException if the stream is too short to contain a valid IV.
     */
    fun decrypt(encryptedData: InputStream): InputStream {
        val iv = ByteArray(GCM_IV_LENGTH)
        val bytesRead = encryptedData.read(iv)
        if (bytesRead < GCM_IV_LENGTH) {
            error("Invalid encrypted data: stream is too short for an IV.")
        }

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(
            Cipher.DECRYPT_MODE,
            KeyProvider.getOrCreateSecretKey(FILE),
            spec
        )

        return CipherInputStream(encryptedData, cipher)
    }
}
