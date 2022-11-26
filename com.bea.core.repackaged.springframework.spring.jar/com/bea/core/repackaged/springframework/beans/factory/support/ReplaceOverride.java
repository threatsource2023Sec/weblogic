package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class ReplaceOverride extends MethodOverride {
   private final String methodReplacerBeanName;
   private List typeIdentifiers = new LinkedList();

   public ReplaceOverride(String methodName, String methodReplacerBeanName) {
      super(methodName);
      Assert.notNull(methodName, (String)"Method replacer bean name must not be null");
      this.methodReplacerBeanName = methodReplacerBeanName;
   }

   public String getMethodReplacerBeanName() {
      return this.methodReplacerBeanName;
   }

   public void addTypeIdentifier(String identifier) {
      this.typeIdentifiers.add(identifier);
   }

   public boolean matches(Method method) {
      if (!method.getName().equals(this.getMethodName())) {
         return false;
      } else if (!this.isOverloaded()) {
         return true;
      } else if (this.typeIdentifiers.size() != method.getParameterCount()) {
         return false;
      } else {
         Class[] parameterTypes = method.getParameterTypes();

         for(int i = 0; i < this.typeIdentifiers.size(); ++i) {
            String identifier = (String)this.typeIdentifiers.get(i);
            if (!parameterTypes[i].getName().contains(identifier)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equals(Object other) {
      if (other instanceof ReplaceOverride && super.equals(other)) {
         ReplaceOverride that = (ReplaceOverride)other;
         return ObjectUtils.nullSafeEquals(this.methodReplacerBeanName, that.methodReplacerBeanName) && ObjectUtils.nullSafeEquals(this.typeIdentifiers, that.typeIdentifiers);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hashCode = super.hashCode();
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.methodReplacerBeanName);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.typeIdentifiers);
      return hashCode;
   }

   public String toString() {
      return "Replace override for method '" + this.getMethodName() + "'";
   }
}
