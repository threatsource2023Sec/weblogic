package weblogic.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class SplitDirectoryUtils implements SplitDirectoryConstants {
   public static void generatePropFile(File srcdir, File destdir) throws IOException {
      String srcLocation = srcdir.getAbsolutePath();
      if ("\\".equals(File.separator)) {
         srcLocation = srcLocation.replaceAll("\\\\", "/");
      }

      Properties p = new Properties();
      p.setProperty("bea.srcdir", srcLocation);
      FileOutputStream fos = null;

      try {
         fos = new FileOutputStream(new File(destdir, ".beabuild.txt"));
         p.store(fos, "");
      } finally {
         if (fos != null) {
            try {
               fos.close();
            } catch (Exception var11) {
            }
         }

      }

   }
}
