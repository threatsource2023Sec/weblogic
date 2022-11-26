package weblogic.iiop;

import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;

public class RequestUrl {
   private static AuditableThreadLocal threadLocal = AuditableThreadLocalFactory.createThreadLocal();

   public static void set(String url) {
      threadLocal.set(url);
   }

   public static String get() {
      return (String)threadLocal.get();
   }

   public static void clear() {
      threadLocal.set((Object)null);
   }
}
