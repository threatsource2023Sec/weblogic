package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class LookupOverride extends MethodOverride {
   @Nullable
   private final String beanName;
   @Nullable
   private Method method;

   public LookupOverride(String methodName, @Nullable String beanName) {
      super(methodName);
      this.beanName = beanName;
   }

   public LookupOverride(Method method, @Nullable String beanName) {
      super(method.getName());
      this.method = method;
      this.beanName = beanName;
   }

   @Nullable
   public String getBeanName() {
      return this.beanName;
   }

   public boolean matches(Method method) {
      if (this.method != null) {
         return method.equals(this.method);
      } else {
         return method.getName().equals(this.getMethodName()) && (!this.isOverloaded() || Modifier.isAbstract(method.getModifiers()) || method.getParameterCount() == 0);
      }
   }

   public boolean equals(Object other) {
      if (other instanceof LookupOverride && super.equals(other)) {
         LookupOverride that = (LookupOverride)other;
         return ObjectUtils.nullSafeEquals(this.method, that.method) && ObjectUtils.nullSafeEquals(this.beanName, that.beanName);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 29 * super.hashCode() + ObjectUtils.nullSafeHashCode((Object)this.beanName);
   }

   public String toString() {
      return "LookupOverride for method '" + this.getMethodName() + "'";
   }
}
