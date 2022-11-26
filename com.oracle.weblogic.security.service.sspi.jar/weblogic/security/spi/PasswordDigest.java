package weblogic.security.spi;

public interface PasswordDigest {
   byte[] getPasswordDigest(String var1, byte[] var2, int var3) throws DigestNotAvailableException;
}
