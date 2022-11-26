package weblogic.security.internal;

import java.io.File;
import weblogic.management.DomainDir;

public class BootStrap {
   private static String getAbsolutePath(String relativePath) {
      File f = new File(relativePath);
      return f.exists() ? f.getAbsolutePath() : null;
   }

   public static boolean isMigrationMode() {
      return false;
   }

   public static String getLocalFileAbsolutePath(String localName) {
      return getAbsolutePath(DomainDir.getPathRelativeRootDir(localName));
   }

   public static String getGlobalFileAbsolutePath(String globalName) {
      return getAbsolutePath(weblogic.management.bootstrap.BootStrap.getPathRelativeWebLogicHome("lib") + File.separator + globalName);
   }
}
