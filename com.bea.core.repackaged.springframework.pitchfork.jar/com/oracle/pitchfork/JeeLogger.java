package com.oracle.pitchfork;

import com.oracle.pitchfork.inject.FieldInjection;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class JeeLogger {
   public static String logClassNotFound(InjectionI injection) {
      if (injection instanceof FieldInjection) {
         return "Can not find class " + injection.getType().getName() + " during injection of " + injection.getMember().getName();
      } else if (injection == null) {
         return "Class not found. Injection is null.";
      } else {
         Method injectionMethod = (Method)injection.getMember();
         String fieldName = extractName(injectionMethod);
         return "Can not find class " + injection.getType().getName() + " during injection of " + fieldName;
      }
   }

   public static String logFieldInjectionFailure(InjectionI injection, Object value) {
      return injection == null ? "Dependency injection failure. Injection is null." : "Dependency injection failure: can't inject the value '" + value + "' into the field '" + injection.getMember() + "'";
   }

   public static String logMethodInjectionFailure(InjectionI injection, Object instance, Object value) {
      if (injection == null) {
         return "Method injection failure. Injection is null.";
      } else {
         Method injectionMethod = (Method)injection.getMember();
         String fieldName = extractName(injectionMethod);
         return instance == null ? "Method injection failure. Instance is null." : "Dependency injection failure: can't inject the value '" + value + "' into the field '" + fieldName + "' of class " + instance.getClass().getName();
      }
   }

   public static String logNoSuchBeanDefinition(InjectionI injection) {
      return injection == null ? "No such bean definition. Injection is null." : "Dependency injection failure: can't find the bean definition about class " + injection.getType();
   }

   public static String logLifecycleCallbackFailure(Object bean, Method m, Object[] args) {
      return bean == null ? "Lifecycle callback failure. Bean is null." : "Failure to invoke " + m + " on bean class " + bean.getClass() + " with args: " + Arrays.toString(args);
   }

   public static String logProceedNotInvokedForAroundConstruct(Method m) {
      return "Around Construct interceptor method " + m + " did not invoke InvocationContext.proceed()";
   }

   public static String logTimeoutFailure(Object bean, Method m, Object[] args) {
      return bean == null ? "Timeout failure. Bean is null." : "Failure to invoke timeout method " + m + " on bean class " + bean.getClass() + " with args: " + Arrays.toString(args) + "]";
   }

   private static String extractName(Method m) {
      return Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4);
   }
}
