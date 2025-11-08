# Crypto Module

The `crypto` module provides a robust framework for handling cryptographic operations within the application, offering support for both in-memory text and stream-based data encryption and decryption. This module is designed to ensure data security through modern, standardized cryptographic practices.

## Gradle Dependency

To use this module, add the following dependency to your `build.gradle` file:

```groovy
implementation "building.blocks:crypto:<latest version>"
```

## Core Features

- **Text-Based Encryption/Decryption**: Managed by `TextCryptoManager`, this feature is tailored for handling sensitive in-memory strings like API keys, authentication tokens, or confidential user data. It uses a key derived for text-specific purposes to encrypt and decrypt data.

- **Stream-Based Encryption/Decryption**: `StreamCryptoManager` is designed for large data payloads, such as files or network streams. It processes data in chunks, making it highly memory-efficient. A separate key, intended for file operations, is used to ensure a clear separation of concerns.

- **Secure Key Management**: `KeyProvider` centralizes the creation and management of cryptographic keys. It generates distinct keys for text and file encryption, ensuring that different cryptographic purposes do not compromise each other. Keys are securely stored in the Android Keystore, providing hardware-backed protection where available.

- **Authenticated Encryption**: The module employs AES-GCM (Galois/Counter Mode), a modern authenticated encryption algorithm. GCM provides both confidentiality and integrity, protecting against unauthorized access and tampering. This is crucial for ensuring that encrypted data has not been modified in transit or at rest.

- **IV Handling**: For both text and stream operations, the Initialization Vector (IV) is automatically generated and prepended to the ciphertext. During decryption, the IV is extracted and used to initialize the cipher, ensuring that the same plaintext produces a different ciphertext each time, a critical feature for semantic security.

## Usage

The module is designed for internal use within the application, with `TextCryptoManager` and `StreamCryptoManager` providing straightforward `encrypt` and `decrypt` methods. `KeyProvider` works behind the scenes, abstracting away the complexities of key generation and storage.

### Text Encryption Example

To encrypt a sensitive string:

```kotlin
val cryptoManager = TextCryptoManager()
val plainText = "sensitive_api_key"
val encryptedText = cryptoManager.encrypt(plainText)
```

### Stream Encryption Example

To encrypt a file stream:

```kotlin
val cryptoManager = StreamCryptoManager()
val fileInputStream = FileInputStream("path/to/your/file")
val encryptedStream = cryptoManager.encrypt(fileInputStream)
```
