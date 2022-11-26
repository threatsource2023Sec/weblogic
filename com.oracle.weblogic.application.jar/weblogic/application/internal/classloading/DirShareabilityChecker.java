package weblogic.application.internal.classloading;

import java.io.File;

public class DirShareabilityChecker implements ShareabilityChecker {
   private final String dirSuffix;

   DirShareabilityChecker(String dirSuffix) {
      this.dirSuffix = dirSuffix;
   }

   public boolean doShare(File f) {
      return f.getPath().endsWith(this.dirSuffix);
   }
}
