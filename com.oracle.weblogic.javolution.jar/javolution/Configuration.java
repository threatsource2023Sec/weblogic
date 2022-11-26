package javolution;

import javolution.lang.Reflection;

public final class Configuration {
   private static final boolean IS_POOR_SYSTEM_HASH;

   private Configuration() {
   }

   public static int concurrency() {
      Reflection.Method var0 = Reflection.getMethod("java.lang.Runtime.availableProcessors()");
      if (var0 != null) {
         Integer var1 = (Integer)var0.invoke(Runtime.getRuntime());
         return var1 - 1;
      } else {
         return 0;
      }
   }

   public static int factories() {
      return 1024;
   }

   public static boolean isPoorSystemHash() {
      return IS_POOR_SYSTEM_HASH;
   }

   static {
      boolean[] var0 = new boolean[32];

      int var1;
      for(var1 = 0; var1 < var0.length; ++var1) {
         var0[(new Object()).hashCode() & var0.length - 1] = true;
      }

      var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (!var0[var2]) {
            ++var1;
         }
      }

      IS_POOR_SYSTEM_HASH = var1 > var0.length >> 1;
   }
}
