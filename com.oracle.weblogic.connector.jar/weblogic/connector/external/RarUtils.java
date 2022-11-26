package weblogic.connector.external;

import java.io.File;
import weblogic.j2ee.J2EEUtils;

public final class RarUtils {
   public static boolean isRarBasic(File f) {
      if (!f.isDirectory()) {
         return f.getName().endsWith(".rar");
      } else {
         return (new File(f, "META-INF" + File.separator + "ra.xml")).exists() || (new File(f, "META-INF" + File.separator + "weblogic-ra.xml")).exists();
      }
   }

   public static boolean isRarAdvanced(File f) {
      return f.isDirectory() ? J2EEUtils.detectRARAnnotation(f) : false;
   }
}
