package by.bsac.services.security.hashing;

/**
 * Available hash function. Length of output string depends on selected hash algorithm.
 * Note: Output in bits; 1 byte = 8 bits; 1 char - 1/2 byte; For example:
 * SHA-512 algorithm output string is 512 bits of length, e.g. 512/8 = 64 bytes, e.g. 64 *2 = 128 chars.
 */
public enum HashAlgorithm {

    /**
     * 128 bits
     */
    MD_5,

    /**
     * 160 bits
     */
    SHA_1,

    /**
     * 256 bits
     */
    SHA_256,

    /**
     * 512 bits
     */
    SHA_512
}
