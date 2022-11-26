package weblogic.protocol;

import weblogic.rjvm.JVMID;

public final class LocalServerIdentity {
   public static ServerIdentity getIdentity() {
      return LocalServerIdentity.SingletonMaker.LOCAL_IDENTITY;
   }

   private static class SingletonMaker {
      private static ServerIdentity LOCAL_IDENTITY = JVMID.localID();
   }
}
