package com.bea.logging;

public class JDKLoggingVersionHelper {
   private static final boolean DEBUG = false;

   public static boolean usePostJdk8() {
      return JDKLoggingVersionHelper.VersionInitializer.usePostJdk8APIs;
   }

   private static boolean initUsePostJdk8ApiValue() {
      boolean usePostJdk8APIs = !System.getProperty("java.version").startsWith("1.");
      if (usePostJdk8APIs) {
      }

      return usePostJdk8APIs;
   }

   public static void main(String[] args) {
      System.out.println("Use Post JDK8 = " + usePostJdk8());
   }

   private static final class VersionInitializer {
      private static final boolean usePostJdk8APIs = JDKLoggingVersionHelper.initUsePostJdk8ApiValue();
   }
}
