package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;

public abstract class MethodOverride implements BeanMetadataElement {
   private final String methodName;
   private boolean overloaded = true;
   @Nullable
   private Object source;

   protected MethodOverride(String methodName) {
      Assert.notNull(methodName, (String)"Method name must not be null");
      this.methodName = methodName;
   }

   public String getMethodName() {
      return this.methodName;
   }

   protected void setOverloaded(boolean overloaded) {
      this.overloaded = overloaded;
   }

   protected boolean isOverloaded() {
      return this.overloaded;
   }

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public abstract boolean matches(Method var1);

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MethodOverride)) {
         return false;
      } else {
         MethodOverride that = (MethodOverride)other;
         return ObjectUtils.nullSafeEquals(this.methodName, that.methodName) && ObjectUtils.nullSafeEquals(this.source, that.source);
      }
   }

   public int hashCode() {
      int hashCode = ObjectUtils.nullSafeHashCode((Object)this.methodName);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.source);
      return hashCode;
   }
}
