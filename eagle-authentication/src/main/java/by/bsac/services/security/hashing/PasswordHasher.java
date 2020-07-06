package by.bsac.services.security.hashing;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class PasswordHasher implements PasswordHash, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordHasher.class);

    //Hash algorithm
    @Getter private HashAlgorithm hash_algorithm;
    private MessageDigest message_digest;
    private int salt_length;
    private final SecureRandom rnd = new SecureRandom();

    //Default constructor
    public PasswordHasher() {
        LOGGER.info("Create " +getClass().getSimpleName() +" bean");
    }


    @Override
    public byte[] hashPassword(String password, byte[] salt) {

        //hash password
        byte[] password_hash = this.hash(password);

        //Concatenate hash and salt
        byte[] concatenated = new byte[password_hash.length + salt_length];
        System.arraycopy(password_hash, 0, concatenated,0, password_hash.length); //Copy password hash
        System.arraycopy(salt, 0, concatenated, password_hash.length-1, salt_length); //Copy salt

        //hash concatenated
        return this.message_digest.digest(concatenated);
    }

    @Override
    public byte[] salt() {
        byte[] salt = new byte[this.salt_length];
        rnd.nextBytes(salt);
        return salt;
    }

    @Override
    public byte[] hash(String original_word) {

        //hash string
        return  this.message_digest.digest(original_word.getBytes());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (hash_algorithm == null) throw new Exception(new BeanCreationException("Hash algorithm not set. Set available hash algorithm."));
    }

    public void setHashAlgorithm(HashAlgorithm algorithm) {
        this.hash_algorithm = algorithm;
        //Initialize message digest
        try {
            this.setHashFunction();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setHashFunction() throws NoSuchAlgorithmException {

        switch (this.hash_algorithm) {
            case MD_5:
                this.message_digest = MessageDigest.getInstance("MD5");
                this.salt_length = 16;
                break;
            case SHA_1:
                this.message_digest = MessageDigest.getInstance("SHA-1");
                this.salt_length = 20;
                break;
            case SHA_256:
                this.message_digest = MessageDigest.getInstance("SHA-256");
                this.salt_length = 32;
                break;
            case SHA_512:
                this.message_digest = MessageDigest.getInstance("SHA-512");
                this.salt_length = 64;
                break;
        }

    }
}
