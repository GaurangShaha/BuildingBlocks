package building.blocks.crypto

/**
 * Defines the intended use for a cryptographic key, which determines the alias used to
 * store and retrieve it from the Android KeyStore.
 *
 * Using distinct purposes for different cryptographic operations (e.g., encrypting text vs.
 * encrypting files) helps to isolate keys and prevent key reuse, which is a security best practice.
 *
 * @property keyAlias The unique alias for the key within the Android KeyStore.
 */
public enum class KeyPurpose(public val keyAlias: String) {
    /**
     * A key specifically for encrypting and decrypting short, in-memory strings.
     * Use this for data like preferences or other sensitive text data.
     */
    TEXT("text_key"),

    /**
     * A key specifically for encrypting and decrypting file streams.
     * Use this for securing the contents of files stored on disk.
     */
    FILE("file_key")
}
