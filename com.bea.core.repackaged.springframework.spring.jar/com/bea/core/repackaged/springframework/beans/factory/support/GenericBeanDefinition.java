package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class GenericBeanDefinition extends AbstractBeanDefinition {
   @Nullable
   private String parentName;

   public GenericBeanDefinition() {
   }

   public GenericBeanDefinition(BeanDefinition original) {
      super(original);
   }

   public void setParentName(@Nullable String parentName) {
      this.parentName = parentName;
   }

   @Nullable
   public String getParentName() {
      return this.parentName;
   }

   public AbstractBeanDefinition cloneBeanDefinition() {
      return new GenericBeanDefinition(this);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof GenericBeanDefinition)) {
         return false;
      } else {
         GenericBeanDefinition that = (GenericBeanDefinition)other;
         return ObjectUtils.nullSafeEquals(this.parentName, that.parentName) && super.equals(other);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Generic bean");
      if (this.parentName != null) {
         sb.append(" with parent '").append(this.parentName).append("'");
      }

      sb.append(": ").append(super.toString());
      return sb.toString();
   }
}
