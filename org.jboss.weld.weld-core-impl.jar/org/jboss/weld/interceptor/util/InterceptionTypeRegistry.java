package org.jboss.weld.interceptor.util;

import java.util.Collection;
import java.util.Map;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.util.collections.ImmutableMap;

public final class InterceptionTypeRegistry {
   private static final Map INTERCEPTOR_ANNOTATION_CLASSES;

   private InterceptionTypeRegistry() {
   }

   public static Collection getSupportedInterceptionTypes() {
      return INTERCEPTOR_ANNOTATION_CLASSES.keySet();
   }

   public static boolean isSupported(InterceptionType interceptionType) {
      return INTERCEPTOR_ANNOTATION_CLASSES.containsKey(interceptionType);
   }

   public static Class getAnnotationClass(InterceptionType interceptionType) {
      return (Class)INTERCEPTOR_ANNOTATION_CLASSES.get(interceptionType);
   }

   static {
      ImmutableMap.Builder builder = ImmutableMap.builder();
      InterceptionType[] var1 = InterceptionType.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         InterceptionType interceptionType = var1[var3];

         try {
            builder.put(interceptionType, InterceptionTypeRegistry.class.getClassLoader().loadClass(interceptionType.annotationClassName()));
         } catch (Exception var6) {
            if (InterceptionUtils.isAnnotationClassExpected(interceptionType)) {
               InterceptorLogger.LOG.interceptorAnnotationClassNotFound(interceptionType.annotationClassName());
            }
         }
      }

      INTERCEPTOR_ANNOTATION_CLASSES = builder.build();
   }
}
