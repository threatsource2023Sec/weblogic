package weblogic.spring.monitoring.instrumentation;

import java.lang.reflect.Method;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public class SpringInstrumentationUtils {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   private static final boolean ENABLED = !Boolean.getBoolean("weblogic.spring.monitoring.instrumentation.disableInstrumentation");

   private SpringInstrumentationUtils() {
   }

   public static boolean isEnabled() {
      return ENABLED;
   }

   public static void addSpringInstrumentor(GenericClassLoader gcl) {
      if (gcl != null) {
         if (!Boolean.getBoolean("weblogic.spring.monitoring.instrumentation.disablePreClassLoader")) {
            String springVersion;
            try {
               Class versionClass = Class.forName("org.springframework.core.SpringVersion", true, gcl);
               if (!(versionClass.getClassLoader() instanceof GenericClassLoader)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Not adding SpringClassPreprocessor. Spring was found, but not in a classloader that can be instrumented: " + versionClass.getClassLoader());
                  }

                  return;
               }

               springVersion = getSpringVersion(versionClass);
               if (StringUtils.isEmptyString(springVersion)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Cannot determine spring's version");
                  }

                  return;
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Spring's version is " + springVersion);
               }
            } catch (ClassNotFoundException var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Spring usage not detected for " + gcl + ", not adding SpringClassPreprocessor");
               }

               return;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Adding SpringClassPreprocessor to " + gcl);
            }

            try {
               SpringClassPreprocessor scp = new SpringClassPreprocessor(gcl, springVersion);
               gcl.addInstanceClassPreProcessor(scp);
            } catch (NoSupportedSpringInstrumentorEngineCreatorFoundException var3) {
               debugLogger.debug(var3.getMessage());
            }

         }
      }
   }

   private static String getSpringVersion(Class versionClass) {
      try {
         Method getVersionMethod = versionClass.getMethod("getVersion");
         return (String)getVersionMethod.invoke((Object)null);
      } catch (Exception var2) {
         return null;
      }
   }
}
