package com.bea.core.repackaged.springframework.aop;

import java.io.Serializable;

final class TruePointcut implements Pointcut, Serializable {
   public static final TruePointcut INSTANCE = new TruePointcut();

   private TruePointcut() {
   }

   public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
   }

   public MethodMatcher getMethodMatcher() {
      return MethodMatcher.TRUE;
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public String toString() {
      return "Pointcut.TRUE";
   }
}
