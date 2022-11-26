package weblogic.application.io;

import java.util.Locale;

public interface JarCopyFilter {
   boolean WIN_32 = System.getProperty("os.name", "unknown").toLowerCase(Locale.US).indexOf("windows") >= 0;
   JarCopyFilter DEFAULT_FILTER = new JarCopyFilter() {
      public boolean copyJars() {
         return WIN_32;
      }
   };
   JarCopyFilter NOCOPY_FILTER = new JarCopyFilter() {
      public boolean copyJars() {
         return false;
      }
   };

   boolean copyJars();
}
