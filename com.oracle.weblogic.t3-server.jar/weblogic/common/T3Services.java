package weblogic.common;

import weblogic.rjvm.JVMID;
import weblogic.t3.srvr.T3Srvr;

/** @deprecated */
@Deprecated
public final class T3Services {
   /** @deprecated */
   @Deprecated
   public static final T3ServicesDef getT3Services() {
      T3ServicesDef services = null;
      if (JVMID.localID().isServer()) {
         services = getServerServices();
         return services;
      } else {
         throw new AssertionError("getT3Services() not available in a client");
      }
   }

   private static T3ServicesDef getServerServices() {
      return T3Srvr.getT3Srvr().getT3Services();
   }
}
