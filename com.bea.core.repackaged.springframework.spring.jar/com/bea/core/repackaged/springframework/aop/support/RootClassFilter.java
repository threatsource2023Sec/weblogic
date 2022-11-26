package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class RootClassFilter implements ClassFilter, Serializable {
   private final Class clazz;

   public RootClassFilter(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      this.clazz = clazz;
   }

   public boolean matches(Class candidate) {
      return this.clazz.isAssignableFrom(candidate);
   }

   public boolean equals(Object other) {
      return this == other || other instanceof RootClassFilter && this.clazz.equals(((RootClassFilter)other).clazz);
   }

   public int hashCode() {
      return this.clazz.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.clazz.getName();
   }
}
