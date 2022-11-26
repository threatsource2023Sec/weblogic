package weblogic.utils.compiler;

import java.io.File;

public class FileSystemUtil {
   public static void mkdirs(File parent) {
      if (parent != null) {
         Class var1 = FileSystemUtil.class;
         synchronized(FileSystemUtil.class) {
            if (!parent.exists()) {
               boolean succeed = false;

               for(int i = 0; !succeed && i < 5; ++i) {
                  succeed = parent.mkdirs();
               }
            }

         }
      }
   }
}
