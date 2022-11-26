package weblogic.store.common;

import java.io.IOException;
import java.util.concurrent.Callable;

public class StoreRCMUtils {
   public static void accountAsGlobal(Runnable runnable) throws Exception {
      runnable.run();
   }

   public static Object accountAsGlobal(Callable callable) throws Exception {
      return callable.call();
   }

   public static void throwIOorRuntimeException(Exception e) throws IOException {
      if (e instanceof RuntimeException) {
         Throwable t = e.getCause();
         if (t instanceof IOException) {
            throw (IOException)t;
         } else {
            throw (RuntimeException)e;
         }
      } else if (e instanceof IOException) {
         throw (IOException)e;
      } else {
         throw new RuntimeException(e);
      }
   }
}
