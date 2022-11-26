package weblogic.security.internal.encryption;

public final class JSafeEncryptionServiceFactory implements EncryptionServiceFactory {
   /** @deprecated */
   @Deprecated
   public byte[] createEncryptedSecretKey(byte[] salt, String password) throws EncryptionServiceException {
      char[] passwd = new char[password.length()];
      password.getChars(0, password.length(), passwd, 0);
      return this.createEncryptedSecretKey(salt, passwd);
   }

   public byte[] createEncryptedSecretKey(byte[] salt, char[] password) throws EncryptionServiceException {
      return JSafeEncryptionServiceImpl.createEncryptedSecretKey(password, salt);
   }

   /** @deprecated */
   @Deprecated
   public EncryptionService getEncryptionService(byte[] salt, String password, byte[] encryptedKey) throws EncryptionServiceException {
      char[] passwd = new char[password.length()];
      password.getChars(0, password.length(), passwd, 0);
      return this.getEncryptionService(salt, passwd, encryptedKey);
   }

   public EncryptionService getEncryptionService(byte[] salt, char[] password, byte[] encryptedKey) throws EncryptionServiceException {
      return new JSafeEncryptionServiceImpl(encryptedKey, salt, password, (byte[])null);
   }

   /** @deprecated */
   @Deprecated
   public byte[] reEncryptEncryptedSecretKey(byte[] oldEncryptedKey, byte[] oldSalt, byte[] newSalt, String oldPassword, String newPassword) throws EncryptionServiceException {
      char[] oldPasswd = new char[oldPassword.length()];
      oldPassword.getChars(0, oldPassword.length(), oldPasswd, 0);
      char[] newPasswd = new char[newPassword.length()];
      newPassword.getChars(0, newPassword.length(), newPasswd, 0);
      return this.reEncryptEncryptedSecretKey(oldEncryptedKey, oldSalt, newSalt, oldPasswd, newPasswd);
   }

   public byte[] reEncryptEncryptedSecretKey(byte[] oldEncryptedKey, byte[] oldSalt, byte[] newSalt, char[] oldPassword, char[] newPassword) throws EncryptionServiceException {
      byte[] newKey = JSafeEncryptionServiceImpl.reEncryptSecretKey("3DES_EDE/CBC/PKCS5Padding", oldEncryptedKey, oldPassword, oldSalt, newPassword, newSalt);
      return newKey;
   }

   /** @deprecated */
   @Deprecated
   public byte[] createAESEncryptedSecretKey(byte[] salt, String password) throws EncryptionServiceException {
      char[] passwd = new char[password.length()];
      password.getChars(0, password.length(), passwd, 0);
      return this.createAESEncryptedSecretKey(salt, passwd);
   }

   public byte[] createAESEncryptedSecretKey(byte[] salt, char[] password) throws EncryptionServiceException {
      return JSafeEncryptionServiceImpl.createAESEncryptedSecretKey(password, salt);
   }

   /** @deprecated */
   @Deprecated
   public EncryptionService getEncryptionService(byte[] salt, String password, byte[] encryptedKey, byte[] encryptedAESKey) throws EncryptionServiceException {
      char[] passwd = new char[password.length()];
      password.getChars(0, password.length(), passwd, 0);
      return this.getEncryptionService(salt, passwd, encryptedKey, encryptedAESKey);
   }

   public EncryptionService getEncryptionService(byte[] salt, char[] password, byte[] encryptedKey, byte[] encryptedAESKey) throws EncryptionServiceException {
      return new JSafeEncryptionServiceImpl(encryptedKey, salt, password, encryptedAESKey);
   }
}
