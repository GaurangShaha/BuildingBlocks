package building.blocks.crypto

import android.util.Base64
import building.blocks.crypto.KeyProvider.GCM_IV_LENGTH
import building.blocks.crypto.KeyProvider.GCM_TAG_LENGTH
import building.blocks.crypto.KeyProvider.TRANSFORMATION
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

/**
 * Manages in-memory cryptographic operations for encrypting and decrypting short strings.
 *
 * This class is designed for handling sensitive text data, such as API keys or user preferences,
 * that needs to be stored securely but does not warrant a file-based stream approach. All data
 * is processed in memory.
 */
public class TextCryptoManager {
    /**
     * Encrypts a plaintext string and returns it as a Base64-encoded ciphertext.
     *
     * The process involves generating a new Initialization Vector (IV), encrypting the text, and
     * then combining the IV and ciphertext. The final result is Base64-encoded for safe storage
     * and transmission as a string.
     *
     * @param plainText The plaintext string to encrypt.
     * @return A Base64-encoded string representing the IV and encrypted data.
     */
    public fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, KeyProvider.getOrCreateSecretKey(KeyPurpose.TEXT))

        val iv = cipher.iv
        val encryptedText = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))

        val result = ByteArray(iv.size + encryptedText.size)
        System.arraycopy(iv, 0, result, 0, iv.size)
        System.arraycopy(encryptedText, 0, result, iv.size, encryptedText.size)
        return Base64.encodeToString(result, Base64.DEFAULT)
    }

    /**
     * Decrypts a Base64-encoded ciphertext string into its original plaintext form.
     *
     * This function first decodes the Base64 string, then separates the Initialization Vector (IV)
     * from the ciphertext. It uses the IV to initialize the cipher and decrypts the data.
     *
     * @param encryptedString The Base64-encoded string containing the IV and ciphertext.
     * @return The original decrypted plaintext string.
     */
    public fun decrypt(encryptedString: String): String {
        val encryptedData = Base64.decode(encryptedString, Base64.DEFAULT)

        val iv = encryptedData.copyOfRange(0, GCM_IV_LENGTH)
        val encryptedText = encryptedData.copyOfRange(GCM_IV_LENGTH, encryptedData.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, KeyProvider.getOrCreateSecretKey(KeyPurpose.TEXT), spec)

        val decryptedTextBytes = cipher.doFinal(encryptedText)
        return String(decryptedTextBytes, StandardCharsets.UTF_8)
    }
}
