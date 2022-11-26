package antlr;

public class Utils {
   private static boolean useSystemExit = true;
   private static boolean useDirectClassLoading = false;

   public static Class loadClass(String var0) throws ClassNotFoundException {
      try {
         ClassLoader var1 = Thread.currentThread().getContextClassLoader();
         return !useDirectClassLoading && var1 != null ? var1.loadClass(var0) : Class.forName(var0);
      } catch (Exception var2) {
         return Class.forName(var0);
      }
   }

   public static Object createInstanceOf(String var0) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      return loadClass(var0).newInstance();
   }

   public static void error(String var0) {
      if (useSystemExit) {
         System.exit(1);
      }

      throw new RuntimeException("ANTLR Panic: " + var0);
   }

   public static void error(String var0, Throwable var1) {
      if (useSystemExit) {
         System.exit(1);
      }

      throw new RuntimeException("ANTLR Panic", var1);
   }

   static {
      if ("true".equalsIgnoreCase(System.getProperty("ANTLR_DO_NOT_EXIT", "false"))) {
         useSystemExit = false;
      }

      if ("true".equalsIgnoreCase(System.getProperty("ANTLR_USE_DIRECT_CLASS_LOADING", "false"))) {
         useDirectClassLoading = true;
      }

   }
}
