package weblogic.cluster;

import weblogic.security.HMAC;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.ByteArrayDiffChecker;

final class EncryptionHelper {
   private static final EncryptionService es = SerializedSystemIni.getEncryptionService();
   private static final byte[] SALT = SerializedSystemIni.getSalt();
   private static final byte[] SECRET = SerializedSystemIni.getEncryptedSecretKey();

   static byte[] encrypt(byte[] plain) {
      return es.encryptBytes(plain);
   }

   static byte[] decrypt(byte[] encrypted, AuthenticatedSubject kernelIdentity) {
      SecurityServiceManager.checkKernelIdentity(kernelIdentity);
      return es.decryptBytes(encrypted);
   }

   static byte[] sign(byte[] in) {
      return HMAC.digest(in, SECRET, SALT);
   }

   static boolean verify(byte[] in, byte[] digest) {
      byte[] md5 = HMAC.digest(in, SECRET, SALT);
      return ByteArrayDiffChecker.diffByteArrays(digest, md5) == null;
   }
}
