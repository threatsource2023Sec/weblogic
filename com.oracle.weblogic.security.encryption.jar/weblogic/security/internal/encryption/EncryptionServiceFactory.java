package weblogic.security.internal.encryption;

public interface EncryptionServiceFactory {
   /** @deprecated */
   @Deprecated
   byte[] createEncryptedSecretKey(byte[] var1, String var2) throws EncryptionServiceException;

   byte[] createEncryptedSecretKey(byte[] var1, char[] var2) throws EncryptionServiceException;

   /** @deprecated */
   @Deprecated
   EncryptionService getEncryptionService(byte[] var1, String var2, byte[] var3) throws EncryptionServiceException;

   EncryptionService getEncryptionService(byte[] var1, char[] var2, byte[] var3) throws EncryptionServiceException;

   /** @deprecated */
   @Deprecated
   byte[] reEncryptEncryptedSecretKey(byte[] var1, byte[] var2, byte[] var3, String var4, String var5) throws EncryptionServiceException;

   byte[] reEncryptEncryptedSecretKey(byte[] var1, byte[] var2, byte[] var3, char[] var4, char[] var5) throws EncryptionServiceException;

   /** @deprecated */
   @Deprecated
   byte[] createAESEncryptedSecretKey(byte[] var1, String var2) throws EncryptionServiceException;

   byte[] createAESEncryptedSecretKey(byte[] var1, char[] var2) throws EncryptionServiceException;

   /** @deprecated */
   @Deprecated
   EncryptionService getEncryptionService(byte[] var1, String var2, byte[] var3, byte[] var4) throws EncryptionServiceException;

   EncryptionService getEncryptionService(byte[] var1, char[] var2, byte[] var3, byte[] var4) throws EncryptionServiceException;
}
