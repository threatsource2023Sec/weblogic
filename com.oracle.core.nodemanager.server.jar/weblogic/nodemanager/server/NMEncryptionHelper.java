package weblogic.nodemanager.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.security.internal.encryption.EncryptionServiceException;

public final class NMEncryptionHelper {
   private static final boolean DEBUG = false;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   private static void p(String msg) {
   }

   public static String getNMSecretHash(String dir, String user, String pass) throws IOException, EncryptionServiceException {
      if (dir != null && user != null && pass != null) {
         DomainDir d = new DomainDir(dir);
         UserInfo u = new UserInfo(d);
         u.set(user, pass);
         return u.getHash();
      } else {
         return null;
      }
   }

   public static synchronized void updateNMHash(String dir, String user, byte[] passBytes) {
      DomainDir domainDir = new DomainDir(dir);
      File secretFile = domainDir.getSecretFile();
      String pass = new String(passBytes);

      try {
         UserInfo u = new UserInfo(domainDir);
         boolean updateNeeded = true;
         if (secretFile.exists()) {
            u.load(secretFile);
            if (u.verify(user, pass)) {
               updateNeeded = false;
            }
         }

         if (updateNeeded) {
            u.set(user, pass);
            u.save(secretFile);
         }
      } catch (Throwable var8) {
         NMServer.nmLog.log(Level.WARNING, nmText.getErrorUpdatingSecretFile(secretFile.getPath()), var8);
      }

   }
}
