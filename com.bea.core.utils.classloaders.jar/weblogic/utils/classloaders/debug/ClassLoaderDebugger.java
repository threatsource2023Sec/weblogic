package weblogic.utils.classloaders.debug;

import java.security.SecureClassLoader;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.GenericClassLoader;

public class ClassLoaderDebugger {
   private static final DebugLogger vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
   private static final DebugLogger ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
   private static final DebugLogger consistencyDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingConsistencyChecker");
   private static final DebugLogger archiveDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingArchiveChecker");
   private static RecordTrace recordTrace = new RecordTrace();
   private static Trigger trigger;

   private static boolean doTrace() {
      return ctDebugLogger.isDebugEnabled();
   }

   private static boolean beVerbose() {
      return vDebugLogger.isDebugEnabled();
   }

   public static boolean checkConsistency() {
      return consistencyDebugLogger.isDebugEnabled();
   }

   public static boolean checkArchive() {
      return archiveDebugLogger.isDebugEnabled();
   }

   public static void debug(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg) {
      if (SupportedClassLoader.isSupported(instance.getClass())) {
         Record r = null;
         if (beVerbose()) {
            r = new MethodInvocationRecord(instance, clz, methodName, arg);
            vDebugLogger.debug(r.toString());
         }

         if (doTrace() && !filterRecording(instance.getAnnotation().getAnnotationString())) {
            if (r == null) {
               r = new MethodInvocationRecord(instance, clz, methodName, arg);
            }

            recordTrace.add(r);
         }

      }
   }

   public static void debug(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg, Object returnValue) {
      if (SupportedClassLoader.isSupported(instance.getClass())) {
         Record r = null;
         if (beVerbose()) {
            r = new SuccessfulInvocationRecord(instance, clz, methodName, arg, returnValue);
            vDebugLogger.debug(r.toString());
         }

         if (doTrace() && !filterRecording(instance.getAnnotation().getAnnotationString())) {
            if (r == null) {
               r = new SuccessfulInvocationRecord(instance, clz, methodName, arg, returnValue);
            }

            recordTrace.add(r);
         }

      }
   }

   public static void debug(GenericClassLoader instance, Throwable t) {
      if (SupportedClassLoader.isSupported(instance.getClass())) {
         if (beVerbose()) {
            vDebugLogger.debug("Exception " + t.getClass().getName() + " with a message " + t.getMessage());
         }

         if (doTrace()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String invokerClassName = stackTrace[3].getClassName();
            if (!filterTriggering(instance.getAnnotation().getAnnotationString()) && !isClassLoaderClass(invokerClassName)) {
               StringBuilder builder = new StringBuilder();
               if (trigger.checkAndDump((Throwable)t, builder, stackTrace, 2)) {
                  ctDebugLogger.debug(builder.toString());
               }
            }
         }

      }
   }

   public static void debug(GenericClassLoader instance, String resourceName) {
      if (SupportedClassLoader.isSupported(instance.getClass())) {
         if (beVerbose()) {
            vDebugLogger.debug("Resource lookup failure, null returned for resource " + resourceName);
         }

         if (doTrace()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String invokerClassName = stackTrace[3].getClassName();
            if (!filterTriggering(instance.getAnnotation().getAnnotationString()) && !isClassLoaderClass(invokerClassName)) {
               StringBuilder builder = new StringBuilder();
               if (trigger.checkAndDump((String)resourceName, builder, stackTrace, 2)) {
                  ctDebugLogger.debug(builder.toString());
               }
            }
         }

      }
   }

   public static void verbose(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg, String message, Object... params) {
      if (SupportedClassLoader.isSupported(instance.getClass())) {
         vDebugLogger.debug((new MessageRecord(instance, clz, methodName, arg, message, params)).toString());
      }
   }

   public static void verbose(Object instance, String methodName, String arg, String message) {
      StringBuilder builder = createBasicRecord(instance);
      builder.append("." + methodName + "(" + arg + "): " + message);
      vDebugLogger.debug(builder.toString());
   }

   public static void verbose(Object instance, String methodName, String arg, String message, Throwable t) {
      StringBuilder builder = createBasicRecord(instance);
      builder.append("." + methodName + "(" + arg + "): " + message);
      vDebugLogger.debug(builder.toString(), t);
   }

   private static StringBuilder createBasicRecord(Object instance) {
      StringBuilder builder = new StringBuilder();
      Record.fillBasicRecord(builder, instance.getClass().getSimpleName(), instance.hashCode(), System.currentTimeMillis());
      return builder;
   }

   private static boolean filterRecording(String annotation) {
      return annotation.length() == 0 ? false : _filter(annotation);
   }

   private static boolean filterTriggering(String annotation) {
      return _filter(annotation);
   }

   private static boolean _filter(String annotation) {
      String application = (String)ctDebugLogger.getDebugParameters().get("Application");
      if (application != null && application.length() > 0 && !annotation.startsWith(application)) {
         return true;
      } else {
         String module = (String)ctDebugLogger.getDebugParameters().get("Module");
         return module != null && module.length() > 0 && !annotation.endsWith(module);
      }
   }

   private static boolean isClassLoaderClass(String className) {
      return SupportedClassLoader.isSupported(className) || className.equals(SecureClassLoader.class.getName()) || className.equals(ClassLoader.class.getName());
   }

   static {
      trigger = new Trigger(recordTrace);
   }
}
