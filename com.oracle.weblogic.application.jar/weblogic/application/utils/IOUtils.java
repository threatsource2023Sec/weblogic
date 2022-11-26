package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.utils.jars.VirtualJarFile;

public final class IOUtils {
   private IOUtils() {
   }

   public static void forceClose(VirtualJarFile vjf) {
      if (vjf != null) {
         try {
            vjf.close();
         } catch (IOException var2) {
         }
      }

   }

   public static void forceClose(VirtualJarFile[] vjfs) {
      if (vjfs != null) {
         for(int count = 0; count < vjfs.length; ++count) {
            forceClose(vjfs[count]);
         }
      }

   }

   public static void forceClose(OutputStream os) {
      if (os != null) {
         try {
            os.close();
         } catch (IOException var2) {
         }
      }

   }

   public static File checkCreateParent(File f) throws IOException {
      if (f != null) {
         File parent = f.getParentFile();
         if (!parent.exists()) {
            parent.mkdirs();
         }
      }

      return f;
   }
}
