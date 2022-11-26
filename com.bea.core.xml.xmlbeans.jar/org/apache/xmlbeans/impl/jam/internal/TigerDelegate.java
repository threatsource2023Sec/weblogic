package org.apache.xmlbeans.impl.jam.internal;

import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;
import org.apache.xmlbeans.impl.jam.provider.JamLogger;

public abstract class TigerDelegate {
   private static final String SOME_TIGER_SPECIFIC_JAVADOC_CLASS = "com.sun.javadoc.AnnotationDesc";
   private static final String SOME_TIGER_SPECIFIC_REFLECT_CLASS = "java.lang.annotation.Annotation";
   protected JamLogger mLogger = null;
   /** @deprecated */
   protected ElementContext mContext = null;
   private static boolean m14RuntimeWarningDone = false;
   private static boolean m14BuildWarningDone = false;

   /** @deprecated */
   public void init(ElementContext ctx) {
      this.mContext = ctx;
      this.init(ctx.getLogger());
   }

   public void init(JamLogger log) {
      this.mLogger = log;
   }

   protected TigerDelegate() {
   }

   protected JamLogger getLogger() {
      return this.mLogger;
   }

   protected static void issue14BuildWarning(Throwable t, JamLogger log) {
      if (!m14BuildWarningDone) {
         log.warning("This build of JAM was not made with JDK 1.5.Even though you are now running under JDK 1.5, JSR175-style annotations will not be available");
         if (log.isVerbose(TigerDelegate.class)) {
            log.verbose(t);
         }

         m14BuildWarningDone = true;
      }

   }

   protected static void issue14RuntimeWarning(Throwable t, JamLogger log) {
      if (!m14RuntimeWarningDone) {
         log.warning("You are running under a pre-1.5 JDK.  JSR175-style source annotations will not be available");
         if (log.isVerbose(TigerDelegate.class)) {
            log.verbose(t);
         }

         m14RuntimeWarningDone = true;
      }

   }

   protected static boolean isTigerJavadocAvailable(JamLogger logger) {
      try {
         Class.forName("com.sun.javadoc.AnnotationDesc");
         return true;
      } catch (ClassNotFoundException var2) {
         issue14RuntimeWarning(var2, logger);
         return false;
      }
   }

   protected static boolean isTigerReflectionAvailable(JamLogger logger) {
      try {
         Class.forName("java.lang.annotation.Annotation");
         return true;
      } catch (ClassNotFoundException var2) {
         issue14RuntimeWarning(var2, logger);
         return false;
      }
   }
}
