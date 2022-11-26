package com.bea.core.repackaged.springframework.aop;

import java.io.Serializable;

final class TrueClassFilter implements ClassFilter, Serializable {
   public static final TrueClassFilter INSTANCE = new TrueClassFilter();

   private TrueClassFilter() {
   }

   public boolean matches(Class clazz) {
      return true;
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public String toString() {
      return "ClassFilter.TRUE";
   }
}
