package weblogic.security.internal.encryption;

public interface EncryptionService {
   byte[] encryptBytes(byte[] var1) throws EncryptionServiceException;

   byte[] decryptBytes(byte[] var1) throws EncryptionServiceException;

   byte[] encryptString(String var1) throws EncryptionServiceException;

   String decryptString(byte[] var1) throws EncryptionServiceException;

   String getAlgorithm();
}
