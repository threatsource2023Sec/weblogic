package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class RuntimeBeanNameReference implements BeanReference {
   private final String beanName;
   @Nullable
   private Object source;

   public RuntimeBeanNameReference(String beanName) {
      Assert.hasText(beanName, "'beanName' must not be empty");
      this.beanName = beanName;
   }

   public String getBeanName() {
      return this.beanName;
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
      } else if (!(other instanceof RuntimeBeanNameReference)) {
         return false;
      } else {
         RuntimeBeanNameReference that = (RuntimeBeanNameReference)other;
         return this.beanName.equals(that.beanName);
      }
   }

   public int hashCode() {
      return this.beanName.hashCode();
   }

   public String toString() {
      return '<' + this.getBeanName() + '>';
   }
}
