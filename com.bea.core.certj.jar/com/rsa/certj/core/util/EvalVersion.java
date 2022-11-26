package com.rsa.certj.core.util;

public final class EvalVersion {
   private static final boolean a = a();

   private EvalVersion() {
   }

   private static boolean a() {
      try {
         Class.forName("com.rsa.certj.core.util.EvalVersion$a");
         return false;
      } catch (ClassNotFoundException var1) {
         return true;
      }
   }

   public static boolean isEvalVersion() {
      return a;
   }

   static class a {
   }
}
