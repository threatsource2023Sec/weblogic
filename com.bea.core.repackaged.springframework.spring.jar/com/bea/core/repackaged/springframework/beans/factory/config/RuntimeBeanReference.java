package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class RuntimeBeanReference implements BeanReference {
   private final String beanName;
   private final boolean toParent;
   @Nullable
   private Object source;

   public RuntimeBeanReference(String beanName) {
      this(beanName, false);
   }

   public RuntimeBeanReference(String beanName, boolean toParent) {
      Assert.hasText(beanName, "'beanName' must not be empty");
      this.beanName = beanName;
      this.toParent = toParent;
   }

   public String getBeanName() {
      return this.beanName;
   }

   public boolean isToParent() {
      return this.toParent;
   }

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof RuntimeBeanReference)) {
         return false;
      } else {
         RuntimeBeanReference that = (RuntimeBeanReference)other;
         return this.beanName.equals(that.beanName) && this.toParent == that.toParent;
      }
   }

   public int hashCode() {
      int result = this.beanName.hashCode();
      result = 29 * result + (this.toParent ? 1 : 0);
      return result;
   }

   public String toString() {
      return '<' + this.getBeanName() + '>';
   }
}
