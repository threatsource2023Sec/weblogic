package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class Pointcuts {
   public static final Pointcut SETTERS;
   public static final Pointcut GETTERS;

   public static Pointcut union(Pointcut pc1, Pointcut pc2) {
      return (new ComposablePointcut(pc1)).union(pc2);
   }

   public static Pointcut intersection(Pointcut pc1, Pointcut pc2) {
      return (new ComposablePointcut(pc1)).intersection(pc2);
   }

   public static boolean matches(Pointcut pointcut, Method method, Class targetClass, Object... args) {
      Assert.notNull(pointcut, (String)"Pointcut must not be null");
      if (pointcut == Pointcut.TRUE) {
         return true;
      } else {
         if (pointcut.getClassFilter().matches(targetClass)) {
            MethodMatcher mm = pointcut.getMethodMatcher();
            if (mm.matches(method, targetClass)) {
               return !mm.isRuntime() || mm.matches(method, targetClass, args);
            }
         }

         return false;
      }
   }

   static {
      SETTERS = Pointcuts.SetterPointcut.INSTANCE;
      GETTERS = Pointcuts.GetterPointcut.INSTANCE;
   }

   private static class GetterPointcut extends StaticMethodMatcherPointcut implements Serializable {
      public static final GetterPointcut INSTANCE = new GetterPointcut();

      public boolean matches(Method method, Class targetClass) {
         return method.getName().startsWith("get") && method.getParameterCount() == 0;
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public String toString() {
         return "Pointcuts.GETTERS";
      }
   }

   private static class SetterPointcut extends StaticMethodMatcherPointcut implements Serializable {
      public static final SetterPointcut INSTANCE = new SetterPointcut();

      public boolean matches(Method method, Class targetClass) {
         return method.getName().startsWith("set") && method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE;
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public String toString() {
         return "Pointcuts.SETTERS";
      }
   }
}
