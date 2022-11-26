package weblogic.utils.classloaders;

import java.io.File;
import java.net.URL;

public final class BeaHomeHolder {
   private static final String BEA_HOME_PROP = "BEA_HOME";
   private static final String BEA_DOT_HOME_PROP = "bea.home";
   private static final String beaHome;

   public static String getBeaHome() {
      return beaHome;
   }

   static {
      String home = System.getProperty("BEA_HOME");
      if (home == null) {
         home = System.getProperty("bea.home");
      }

      if (home == null) {
         try {
            String file = null;
            URL url = ClassFinderUtils.class.getClassLoader().getResource(ClassFinderUtils.class.getName().replace('.', '/') + ".class");
            if (url != null) {
               String urlPath = url.getPath();
               int jarIndex = urlPath.lastIndexOf(".jar");
               if (jarIndex > -1) {
                  String jarPath = urlPath.substring(0, jarIndex + 4);
                  int fileIndex = jarPath.indexOf("file:/");
                  if (fileIndex > -1) {
                     if (System.getProperty("os.name").startsWith("Windows")) {
                        file = jarPath.substring(fileIndex + 6);
                     } else {
                        file = jarPath.substring(fileIndex + 5);
                     }
                  }
               }
            }

            if (file != null && file.endsWith(".jar")) {
               int fileIndex = file.lastIndexOf("/wlserver/modules/");
               if (fileIndex > -1) {
                  home = file.substring(0, fileIndex);
               } else {
                  fileIndex = file.lastIndexOf("/modules/");
                  if (fileIndex > -1) {
                     home = file.substring(0, fileIndex);
                  }
               }
            }
         } catch (Throwable var7) {
         }
      }

      if (home != null) {
         home = home.replace('/', File.separatorChar);
      }

      beaHome = home;
   }
}
