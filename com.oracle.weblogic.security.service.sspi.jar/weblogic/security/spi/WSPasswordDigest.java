package weblogic.security.spi;

public interface WSPasswordDigest {
   byte[] getPasswordDigest(String var1, byte[] var2, String var3) throws DigestNotAvailableException;

   byte[] getDerivedKey(String var1, byte[] var2, int var3) throws DigestNotAvailableException;
}
