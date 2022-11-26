package weblogic.management.internal;

import java.io.File;
import java.io.IOException;
import weblogic.management.DomainDir;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

public final class EncryptedData {
   public static void initialize() {
      ClearOrEncryptedService temp = EncryptedData.SINGLETON.encryptor;
   }

   public static String decrypt(String s) {
      return EncryptedData.SINGLETON.encryptor.decrypt(s);
   }

   static String encrypt(String s) throws IllegalArgumentException {
      if (s != null && s.length() != 0) {
         return EncryptedData.SINGLETON.encryptor.encrypt(s);
      } else {
         throw new IllegalArgumentException("Can't encrypt null or empty string");
      }
   }

   private static ClearOrEncryptedService createEncryptionService() {
      try {
         File dir = new File(DomainDir.getRootDir());
         EncryptionService es = SerializedSystemIni.getEncryptionService(dir.getCanonicalPath());
         return new ClearOrEncryptedService(es);
      } catch (IOException var2) {
         AssertionError e = new AssertionError("Failed to find SerializedSystemIni.dat");
         e.initCause(var2);
         throw e;
      }
   }

   private static class SINGLETON {
      static final ClearOrEncryptedService encryptor = EncryptedData.createEncryptionService();
   }
}
