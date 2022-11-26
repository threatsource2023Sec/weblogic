package weblogic.security.internal.encryption;

public interface EncryptionServiceV2 extends EncryptionService {
   byte[] encryptBytes(String var1, byte[] var2) throws EncryptionServiceException;

   byte[] decryptBytes(String var1, byte[] var2) throws EncryptionServiceException;

   byte[] encryptString(String var1, String var2) throws EncryptionServiceException;

   String decryptString(String var1, byte[] var2) throws EncryptionServiceException;

   boolean isKeyContextAvailable(String var1);

   String getDefaultKeyContext();
}
