package weblogic.security.internal;

import weblogic.security.internal.encryption.EncryptionService;

public final class SerializedSystemIni {
   public static boolean exists() {
      return false;
   }

   public static String getPath() {
      return null;
   }

   public static byte[] getSalt(String path) {
      return null;
   }

   public static byte[] getSalt() {
      return null;
   }

   public static byte[] getEncryptedSecretKey() {
      return null;
   }

   public static EncryptionService getExistingEncryptionService() {
      return null;
   }

   public static EncryptionService getEncryptionService(String domainDir) {
      return null;
   }

   public static EncryptionService getEncryptionService() {
      return null;
   }
}
