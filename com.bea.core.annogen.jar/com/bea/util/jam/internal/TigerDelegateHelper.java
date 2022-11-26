package com.bea.util.jam.internal;

import com.bea.util.jam.provider.JamLogger;

public final class TigerDelegateHelper {
   private static final String SOME_TIGER_SPECIFIC_JAVADOC_CLASS = "com.sun.javadoc.AnnotationDesc";
   private static final String SOME_TIGER_SPECIFIC_REFLECT_CLASS = "java.lang.annotation.Annotation";
   private static boolean m14RuntimeWarningDone = false;
   private static boolean m14BuildWarningDone = false;

   public static void issue14BuildWarning(Throwable t, JamLogger log) {
      if (!m14BuildWarningDone) {
         log.warning("This build of JAM was not made with JDK 1.5.Even though you are now running under JDK 1.5, JSR175-style annotations will not be available");
         if (log.isVerbose(TigerDelegateHelper.class)) {
            log.verbose(t);
         }

         m14BuildWarningDone = true;
      }

   }

   public static void issue14RuntimeWarning(Throwable t, JamLogger log) {
      if (!m14RuntimeWarningDone) {
         log.warning("You are running under a pre-1.5 JDK.  JSR175-style source annotations will not be available");
         if (log.isVerbose(TigerDelegateHelper.class)) {
            log.verbose(t);
         }

         m14RuntimeWarningDone = true;
      }

   }

   public static boolean isTigerJavadocAvailable(JamLogger logger) {
      try {
         Class.forName("com.sun.javadoc.AnnotationDesc");
         return true;
      } catch (ClassNotFoundException var2) {
         issue14RuntimeWarning(var2, logger);
         return false;
      }
   }

   public static boolean isTigerReflectionAvailable(JamLogger logger) {
      try {
         Class.forName("java.lang.annotation.Annotation");
         return true;
      } catch (ClassNotFoundException var2) {
         issue14RuntimeWarning(var2, logger);
         return false;
      }
   }
}
