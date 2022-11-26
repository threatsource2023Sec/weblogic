package org.apache.velocity.texen.util;

import java.io.File;

public class FileUtil {
   public static String mkdir(String s) {
      try {
         return (new File(s)).mkdirs() ? "Created dir: " + s : "Failed to create dir or dir already exists: " + s;
      } catch (Exception var2) {
         return var2.toString();
      }
   }

   public static File file(String s) {
      File f = new File(s);
      return f;
   }

   public static File file(String base, String s) {
      File f = new File(base, s);
      return f;
   }
}
