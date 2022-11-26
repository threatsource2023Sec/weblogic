package weblogic.security.pki.keystore;

import com.bea.security.utils.keystore.AbstractKeyStoreFactory;
import java.security.KeyStore;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;

public final class WLSKeyStoreFactory {
   private static final WLSKeyStoreLogger LOGGER = WLSKeyStoreLogger.getInstance();
   private static final WLSKeyStoreFactoryImpl IMPL = new WLSKeyStoreFactoryImpl();

   public static KeyStore getKeyStoreInstance(AuthenticatedSubject kernelID, String provider, String type, String source, char[] passphrase) {
      return IMPL.getKeyStoreInstance(kernelID, provider, type, source, passphrase);
   }

   public static KeyStore getKeyStoreInstance(AuthenticatedSubject kernelID, String type, String source, char[] passphrase) {
      return IMPL.getKeyStoreInstance(kernelID, (String)null, type, source, passphrase);
   }

   public static long getLastModified(String type, String source) {
      return IMPL.getLastModified(type, source, LOGGER);
   }

   public static boolean isFileBasedKeyStore(String keyStoreType) {
      if (null != keyStoreType && 0 != keyStoreType.trim().length()) {
         return !"KSS".equalsIgnoreCase(keyStoreType);
      } else {
         return false;
      }
   }

   private WLSKeyStoreFactory() {
   }

   private static class WLSKeyStoreFactoryImpl extends AbstractKeyStoreFactory {
      private WLSKeyStoreFactoryImpl() {
      }

      protected KeyStore wrap(KeyStore ks) {
         return (KeyStore)(null != ks && "KSS".equalsIgnoreCase(ks.getType()) ? new WLSKeyStoreWrapper(ks) : ks);
      }

      public KeyStore getKeyStoreInstance(AuthenticatedSubject kernelID, String provider, String type, String source, char[] passphrase) {
         if (!this.checkKeyStoreType(type, source, WLSKeyStoreFactory.LOGGER)) {
            return null;
         } else {
            if ("KSS".equalsIgnoreCase(type)) {
               try {
                  SubjectManager.getSubjectManager().checkKernelIdentity(kernelID);
               } catch (Exception var7) {
                  WLSKeyStoreFactory.LOGGER.logCantLoadKeyStore("KSS", source, var7.getClass().getName(), var7.getMessage());
                  return null;
               }
            }

            KeyStore ks = this.getKeyStoreInstance(provider, type, source, passphrase, WLSKeyStoreFactory.LOGGER);
            return ks;
         }
      }

      // $FF: synthetic method
      WLSKeyStoreFactoryImpl(Object x0) {
         this();
      }
   }
}
