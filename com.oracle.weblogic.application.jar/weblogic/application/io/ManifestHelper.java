package weblogic.application.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ManifestHelper {
   private ManifestHelper() {
   }

   static String[] getMFClassPathElements(File f) {
      String[] rtn = null;
      ManifestFinder finder = null;

      try {
         finder = new ManifestFinder.ClassPathFinder(f);
         rtn = finder.getPathElements();
      } finally {
         finder.close();
      }

      return rtn;
   }

   static File[] getExistingMFClassPathElements(File in) {
      String[] s = getMFClassPathElements(in);
      List rtn = new ArrayList(s.length);

      for(int i = 0; i < s.length; ++i) {
         File f = new File(s[i]);
         if (f.exists()) {
            rtn.add(f);
         }
      }

      return (File[])((File[])rtn.toArray(new File[rtn.size()]));
   }
}
