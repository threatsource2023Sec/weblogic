package org.jboss.weld.interceptor.reader;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.interceptor.InvocationContext;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.interceptor.util.InterceptionTypeRegistry;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.reflection.Formats;

public class InterceptorMetadataUtils {
   protected static final String OBJECT_CLASS_NAME = Object.class.getName();

   private InterceptorMetadataUtils() {
   }

   public static boolean isInterceptorMethod(InterceptionType interceptionType, Method method, boolean forTargetClass) {
      if (interceptionType.isLifecycleCallback()) {
         return forTargetClass ? isValidTargetClassLifecycleInterceptorMethod(interceptionType, method) : isValidInterceptorClassLifecycleInterceptorMethod(interceptionType, method);
      } else {
         return isValidBusinessMethodInterceptorMethod(interceptionType, method);
      }
   }

   private static boolean isValidTargetClassLifecycleInterceptorMethod(InterceptionType interceptionType, Method method) {
      if (!Void.TYPE.equals(method.getReturnType()) && !Object.class.equals(method.getReturnType())) {
         throw ValidatorLogger.LOG.interceptorMethodDoesNotHaveVoidReturnType(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), Void.TYPE.getName(), Formats.formatAsStackTraceElement((Member)method));
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         if (parameterTypes.length == 1) {
            ValidatorLogger.LOG.interceptorMethodDoesNotHaveZeroParameters(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName());
         }

         if (parameterTypes.length > 1) {
            throw ValidatorLogger.LOG.interceptorMethodDeclaresMultipleParameters(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), Formats.formatAsStackTraceElement((Member)method));
         } else {
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length != 0) {
               Class[] var4 = exceptionTypes;
               int var5 = exceptionTypes.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Class exceptionType = var4[var6];
                  if (!RuntimeException.class.isAssignableFrom(exceptionType)) {
                     ValidatorLogger.LOG.interceptorMethodShouldNotThrowCheckedExceptions(method.getName(), method.getDeclaringClass().getName(), exceptionType.getName(), Formats.formatAsStackTraceElement((Member)method));
                  }
               }
            }

            return parameterTypes.length == 0;
         }
      }
   }

   private static boolean isValidInterceptorClassLifecycleInterceptorMethod(InterceptionType interceptionType, Method method) {
      if (!Object.class.equals(method.getReturnType()) && !Void.TYPE.equals(method.getReturnType())) {
         throw ValidatorLogger.LOG.interceptorMethodDoesNotReturnObjectOrVoid(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), Void.TYPE.getName(), OBJECT_CLASS_NAME, Formats.formatAsStackTraceElement((Member)method));
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         if (parameterTypes.length == 0) {
            return false;
         } else if (parameterTypes.length == 1) {
            if (InvocationContext.class.isAssignableFrom(parameterTypes[0])) {
               return true;
            } else {
               throw ValidatorLogger.LOG.interceptorMethodDoesNotHaveCorrectTypeOfParameter(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), InvocationContext.class.getName(), Formats.formatAsStackTraceElement((Member)method));
            }
         } else {
            throw ValidatorLogger.LOG.interceptorMethodDoesNotHaveExactlyOneParameter(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), Formats.formatAsStackTraceElement((Member)method));
         }
      }
   }

   private static boolean isValidBusinessMethodInterceptorMethod(InterceptionType interceptionType, Method method) {
      if (!Object.class.equals(method.getReturnType())) {
         throw ValidatorLogger.LOG.interceptorMethodDoesNotReturnObject(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), OBJECT_CLASS_NAME, Formats.formatAsStackTraceElement((Member)method));
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         if (parameterTypes.length != 1) {
            throw ValidatorLogger.LOG.interceptorMethodDoesNotHaveExactlyOneParameter(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), Formats.formatAsStackTraceElement((Member)method));
         } else if (!InvocationContext.class.isAssignableFrom(parameterTypes[0])) {
            throw ValidatorLogger.LOG.interceptorMethodDoesNotHaveCorrectTypeOfParameter(method.getName(), method.getDeclaringClass().getName(), interceptionType.annotationClassName(), InvocationContext.class.getName(), Formats.formatAsStackTraceElement((Member)method));
         } else {
            return true;
         }
      }
   }

   public static Map buildMethodMap(EnhancedAnnotatedType type, boolean forTargetClass, BeanManagerImpl manager) {
      ImmutableMap.Builder builder = null;
      Iterator var4 = InterceptionTypeRegistry.getSupportedInterceptionTypes().iterator();

      while(var4.hasNext()) {
         InterceptionType interceptionType = (InterceptionType)var4.next();
         List value = BeanMethods.getInterceptorMethods(type, interceptionType, forTargetClass);
         if (!value.isEmpty()) {
            if (builder == null) {
               builder = ImmutableMap.builder();
            }

            builder.put(interceptionType, value);
         }
      }

      if (builder == null) {
         return Collections.emptyMap();
      } else {
         return builder.build();
      }
   }
}
