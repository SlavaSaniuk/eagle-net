package by.bsac.services.security.hashing;

public interface PasswordHash extends Hash {

    byte[] hashPassword(String password, byte[] salt);

    byte[] salt();

}
