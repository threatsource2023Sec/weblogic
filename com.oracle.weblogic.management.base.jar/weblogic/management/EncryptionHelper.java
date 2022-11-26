package weblogic.management;

import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.service.SecurityServiceManager;

public final class EncryptionHelper {
   private static EncryptionService encryptor = null;
   private static final byte CLEAR_BYTE = 0;
   private static ClearOrEncryptedService cEncryptor = null;

   public static final String decryptString(byte[] encryptedBytes, AuthenticatedSubject kernelIdentity) throws EncryptionServiceException {
      SecurityServiceManager.checkKernelIdentity(kernelIdentity);
      if (encryptedBytes != null && encryptedBytes.length != 0) {
         ClearOrEncryptedService es = getCEncryptionService();
         return es.decrypt(new String(encryptedBytes));
      } else {
         return null;
      }
   }

   public static final byte[] decrypt(byte[] encryptedBytes, AuthenticatedSubject kernelIdentity) throws EncryptionServiceException {
      SecurityServiceManager.checkKernelIdentity(kernelIdentity);
      if (encryptedBytes != null && encryptedBytes.length != 0) {
         ClearOrEncryptedService es = getCEncryptionService();
         return es.decryptBytes(encryptedBytes);
      } else {
         return encryptedBytes;
      }
   }

   public static final byte[] encryptString(String clearTextString) throws EncryptionServiceException {
      if (!KernelStatus.isServer()) {
         throw new EncryptionServiceException("Caller not part of the Server JVM, Access to encrypt passwords is denied");
      } else if (clearTextString != null && clearTextString.length() != 0) {
         ClearOrEncryptedService es = getCEncryptionService();
         return es.encrypt(clearTextString).getBytes();
      } else {
         return null;
      }
   }

   public static final byte[] encrypt(byte[] bytes) throws EncryptionServiceException {
      if (bytes != null && bytes.length != 0) {
         ClearOrEncryptedService es = getCEncryptionService();
         return es.encryptBytes(bytes);
      } else {
         return bytes;
      }
   }

   public static byte[] clear(byte[] bytes) {
      if (bytes != null && bytes.length != 0) {
         for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = 0;
         }

         return bytes;
      } else {
         return bytes;
      }
   }

   public static boolean isEncrypted(String string) {
      if (string == null) {
         return false;
      } else {
         ClearOrEncryptedService es = getCEncryptionService();
         return es.isEncrypted(string);
      }
   }

   private static ClearOrEncryptedService getCEncryptionService() {
      if (cEncryptor == null) {
         if (encryptor == null) {
            encryptor = SerializedSystemIni.getExistingEncryptionService();
         }

         cEncryptor = new ClearOrEncryptedService(encryptor);
      }

      return cEncryptor;
   }
}
