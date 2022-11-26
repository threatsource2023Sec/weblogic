package weblogic.security;

import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

public final class MBeanSecurityValidator {
   public static void ensureEncrypted(byte[] value) throws IllegalArgumentException {
      EncryptionService es = SerializedSystemIni.getEncryptionService();
      ClearOrEncryptedService ces = new ClearOrEncryptedService(es);
      if (!ces.isEncryptedBytes(value)) {
         throw new IllegalArgumentException(SecurityLogger.getArgumentNotEncrypted());
      }
   }
}
