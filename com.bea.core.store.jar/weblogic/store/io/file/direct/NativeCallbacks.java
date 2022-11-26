package weblogic.store.io.file.direct;

import weblogic.store.common.StoreDebug;

public class NativeCallbacks {
   public static boolean debug(String debugMsg) {
      StoreDebug.storeIOPhysical.debug(debugMsg);
      return true;
   }

   public static boolean verbose(String debugMsg) {
      StoreDebug.storeIOPhysicalVerbose.debug(debugMsg);
      return true;
   }
}
