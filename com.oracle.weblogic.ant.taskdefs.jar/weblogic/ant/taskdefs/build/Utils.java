package weblogic.ant.taskdefs.build;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public final class Utils {
   public static String joinFileList(List l) {
      StringBuffer sb = new StringBuffer();
      String sep = "";
      Iterator it = l.iterator();

      while(it.hasNext()) {
         sb.append(sep);
         sep = " ";
         sb.append(((File)it.next()).getAbsolutePath());
      }

      return sb.toString();
   }

   public static boolean fileExists(File dir, String subDir, String file) {
      return (new File(dir.getAbsolutePath() + File.separatorChar + subDir, file)).exists();
   }
}
