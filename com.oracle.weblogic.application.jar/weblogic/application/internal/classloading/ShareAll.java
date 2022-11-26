package weblogic.application.internal.classloading;

import java.io.File;

class ShareAll implements ShareabilityChecker {
   public boolean doShare(File file) {
      return true;
   }
}
