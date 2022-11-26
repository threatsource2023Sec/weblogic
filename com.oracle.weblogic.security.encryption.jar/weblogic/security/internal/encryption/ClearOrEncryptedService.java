package weblogic.security.internal.encryption;

import java.io.IOException;
import weblogic.security.SecurityLogger;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;

public final class ClearOrEncryptedService {
   private String encryptedPrefix = null;
   private byte[] encryptedStartBytes = null;
   private EncryptionServiceV2 encryptionService = null;

   public ClearOrEncryptedService(EncryptionService encryptionService) {
      if (!(encryptionService instanceof EncryptionServiceV2)) {
         throw new EncryptionServiceException("IllegalStateException: Invalid Service");
      } else {
         this.encryptionService = (EncryptionServiceV2)encryptionService;
         this.encryptedPrefix = this.encryptionService.getDefaultKeyContext();
         this.encryptedStartBytes = "{".getBytes();
      }
   }

   public boolean isEncrypted(String clearOrEncryptedString) {
      String prefix = this.findPrefix(clearOrEncryptedString);
      return prefix == null ? false : this.encryptionService.isKeyContextAvailable(prefix);
   }

   public boolean isEncryptedBytes(byte[] clearOrEncryptedBytes) {
      return !this.startsWithBytes(this.encryptedStartBytes, clearOrEncryptedBytes) ? false : this.isEncrypted(new String(clearOrEncryptedBytes));
   }

   public String encrypt(String clearOrEncryptedString) {
      if (this.isEncrypted(clearOrEncryptedString)) {
         return clearOrEncryptedString;
      } else {
         byte[] encrypted = this.encryptionService.encryptString(this.encryptedPrefix, clearOrEncryptedString);
         String encodedEncrypted = (new BASE64Encoder()).encodeBuffer(encrypted);
         return this.encryptedPrefix + encodedEncrypted;
      }
   }

   public byte[] encryptBytes(byte[] clearOrEncryptedBytes) {
      if (this.isEncryptedBytes(clearOrEncryptedBytes)) {
         return clearOrEncryptedBytes;
      } else {
         byte[] encrypted = this.encryptionService.encryptBytes(this.encryptedPrefix, clearOrEncryptedBytes);
         String encodedEncrypted = (new BASE64Encoder()).encodeBuffer(encrypted);
         return (this.encryptedPrefix + encodedEncrypted).getBytes();
      }
   }

   public String decrypt(String clearOrEncryptedString) {
      String prefix = this.findPrefix(clearOrEncryptedString);
      if (prefix == null) {
         return clearOrEncryptedString;
      } else if (!this.encryptionService.isKeyContextAvailable(prefix)) {
         return clearOrEncryptedString;
      } else {
         String encodedEncrypted = clearOrEncryptedString.substring(prefix.length());

         try {
            byte[] encrypted = (new BASE64Decoder()).decodeBuffer(encodedEncrypted);
            return this.encryptionService.decryptString(prefix, encrypted);
         } catch (IOException var5) {
            throw new EncryptionServiceException(SecurityLogger.getDecodingError("" + var5));
         }
      }
   }

   public byte[] decryptBytes(byte[] clearOrEncryptedBytes) {
      if (!this.startsWithBytes(this.encryptedStartBytes, clearOrEncryptedBytes)) {
         return clearOrEncryptedBytes;
      } else {
         String value = new String(clearOrEncryptedBytes);
         String prefix = this.findPrefix(value);
         if (prefix == null) {
            return clearOrEncryptedBytes;
         } else if (!this.encryptionService.isKeyContextAvailable(prefix)) {
            return clearOrEncryptedBytes;
         } else {
            String encodedEncrypted = value.substring(prefix.length());

            try {
               byte[] encrypted = (new BASE64Decoder()).decodeBuffer(encodedEncrypted);
               return this.encryptionService.decryptBytes(prefix, encrypted);
            } catch (IOException var6) {
               throw new EncryptionServiceException(SecurityLogger.getDecodingError("" + var6));
            }
         }
      }
   }

   private boolean startsWithBytes(byte[] prefix, byte[] data) {
      if (data.length < prefix.length) {
         return false;
      } else {
         for(int i = 0; i < prefix.length; ++i) {
            if (data[i] != prefix[i]) {
               return false;
            }
         }

         return true;
      }
   }

   private byte[] bytesSubstring(byte[] data, int position) {
      int newLength = data.length - position;
      byte[] bytes = new byte[newLength];

      for(int i = 0; i < newLength; ++i) {
         bytes[i] = data[i + position];
      }

      return bytes;
   }

   private String findPrefix(String value) {
      if (value != null && value.length() != 0) {
         if (value.charAt(0) != '{') {
            return null;
         } else {
            int endIdx = value.indexOf(125);
            return endIdx == -1 ? null : value.substring(0, endIdx + 1);
         }
      } else {
         return null;
      }
   }
}
