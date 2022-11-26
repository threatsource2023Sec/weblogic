package weblogic.security.internal.encryption;

public final class ClearOrEncryptedService {
   public ClearOrEncryptedService(EncryptionService encryptionService) {
   }

   public boolean isEncrypted(String clearOrEncryptedString) {
      return false;
   }

   public boolean isEncryptedBytes(byte[] clearOrEncryptedBytes) {
      return false;
   }

   public String encrypt(String clearOrEncryptedString) {
      return null;
   }

   public byte[] encryptBytes(byte[] clearOrEncryptedBytes) {
      return null;
   }

   public byte[] decryptBytes(byte[] clearOrEncryptedBytes) {
      return null;
   }
}
