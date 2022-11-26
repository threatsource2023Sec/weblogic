package org.glassfish.grizzly.threadpool;

public class Threads {
   private static final ThreadLocal SERVICE_THREAD = new ThreadLocal();

   public static boolean isService() {
      return Boolean.TRUE.equals(SERVICE_THREAD.get());
   }

   public static void setService(boolean isService) {
      if (isService) {
         SERVICE_THREAD.set(isService);
      } else {
         SERVICE_THREAD.remove();
      }

   }
}
