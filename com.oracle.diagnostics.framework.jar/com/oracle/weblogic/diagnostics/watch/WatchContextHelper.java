package com.oracle.weblogic.diagnostics.watch;

public class WatchContextHelper {
   private static ThreadLocal currentContext = new ThreadLocal();

   public static WatchContext getCurrentContext() {
      return (WatchContext)currentContext.get();
   }

   static void setCurrentContext(WatchContext current) {
      currentContext.set(current);
   }
}
