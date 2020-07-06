package service.hashing;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.security.hashing.PasswordHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {ServicesConfiguration.class, PersistenceConfiguration.class, DatasourcesConfiguration.class})
class PasswordHashIntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PasswordHash hasher;
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordHashIntegrationTest.class);
    private MessageDigest digest;

    @BeforeEach
    void setUpBeforeEach() throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance("SHA-512");
    }


    @Test
    void hashPassword_newPassword_shouldGeneratePasswordHash() {

        String password = "Hello world!";
        LOGGER.debug("Password: " +password);

        //Generate salt
        byte[] salt = this.hasher.salt();
        LOGGER.debug("Generated salt: " + DatatypeConverter.printHexBinary(salt));

        //Automatic generate password hash
        byte[] generated_with_hasher = this.hasher.hashPassword(password, salt);
        LOGGER.debug("Automatic generated password hash with hasher: " +DatatypeConverter.printHexBinary(generated_with_hasher));

        //Assert generated password hash
        Assertions.assertNotNull(generated_with_hasher);
        Assertions.assertEquals(64, generated_with_hasher.length);
        Assertions.assertEquals(128, DatatypeConverter.printHexBinary(generated_with_hasher).length());

        //Manual password generation
        byte[] pass_hash = this.hasher.hash(password);

        //Concatenate password with salt
        byte[] concatenated = new byte[ pass_hash.length + salt.length];

        System.arraycopy(pass_hash, 0, concatenated, 0, pass_hash.length);
        System.arraycopy(salt, 0, concatenated, pass_hash.length-1, salt.length);

        //Hash concatenated array
        byte[] manual_hash = this.digest.digest(concatenated);

        Assertions.assertNotNull(manual_hash);
        Assertions.assertEquals(64, manual_hash.length);
        Assertions.assertEquals(128, DatatypeConverter.printHexBinary(manual_hash).length());

        LOGGER.debug("Manual generated hash: " +DatatypeConverter.printHexBinary(manual_hash));

        //Compare two hashes
        Assertions.assertArrayEquals(generated_with_hasher, manual_hash);
        Assertions.assertEquals(DatatypeConverter.printHexBinary(generated_with_hasher), DatatypeConverter.printHexBinary(manual_hash));

    }

}
