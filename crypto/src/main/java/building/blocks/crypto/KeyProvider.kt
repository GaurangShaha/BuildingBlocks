package building.blocks.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Manages cryptographic keys within the Android KeyStore.
 *
 * This object is responsible for creating, storing, and retrieving [SecretKey] instances used for
 * encryption and decryption. It ensures that keys are securely handled by leveraging the
 * Android KeyStore system, which provides protection against unauthorized key extraction.
 */
public object KeyProvider {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    /**
     * Retrieves an existing secret key for the given purpose or creates a new one if it doesn't exist.
     *
     * This function checks the Android KeyStore for a key associated with the specified [purpose].
     * If found, it returns the key. Otherwise, it generates a new [SecretKey] with the appropriate
     * specifications, stores it in the KeyStore, and then returns it.
     *
     * @param purpose The intended use for the key (e.g., file or text encryption).
     * @return The existing or newly created [SecretKey].
     */
    public fun getOrCreateSecretKey(purpose: KeyPurpose): SecretKey {
        val existingKey = keyStore.getEntry(purpose.keyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createSecretKey(purpose)
    }

    private fun createSecretKey(purpose: KeyPurpose): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    purpose.keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    /** The cryptographic algorithm used for creating the secret key. Set to AES. */
    public const val ALGORITHM: String = KeyProperties.KEY_ALGORITHM_AES

    /** The block mode for the cipher. Set to GCM (Galois/Counter Mode). */
    public const val BLOCK_MODE: String = KeyProperties.BLOCK_MODE_GCM

    /** The padding scheme for the cipher. Set to None, as GCM does not require padding. */
    public const val PADDING: String = KeyProperties.ENCRYPTION_PADDING_NONE

    /** A string defining the full transformation for the Cipher (algorithm/mode/padding). */
    public const val TRANSFORMATION: String = "$ALGORITHM/$BLOCK_MODE/$PADDING"

    /** The length of the Initialization Vector (IV) in bytes for GCM mode. */
    public const val GCM_IV_LENGTH: Int = 12

    /** The length of the GCM authentication tag in bits. */
    public const val GCM_TAG_LENGTH: Int = 128
}
