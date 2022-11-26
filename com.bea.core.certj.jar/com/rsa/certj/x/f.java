package com.rsa.certj.x;

public final class f {
   private static boolean a = false;

   public static boolean a() {
      return a;
   }

   public static void a(boolean var0, String... var1) {
      if (var0) {
         StringBuilder var2 = new StringBuilder();
         String[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            var2.append(var6);
         }

         System.err.println(var2.toString());
      }

   }

   public static void a(String var0) {
      System.err.println(var0);
   }

   static {
      String var0 = System.getProperty("java.security.debug", "");
      var0 = var0.toLowerCase();
      if ("all".equals(var0) || "certpath".equals(var0)) {
         a = true;
      }

   }
}
