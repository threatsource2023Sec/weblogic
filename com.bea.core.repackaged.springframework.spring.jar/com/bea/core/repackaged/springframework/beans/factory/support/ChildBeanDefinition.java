package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class ChildBeanDefinition extends AbstractBeanDefinition {
   @Nullable
   private String parentName;

   public ChildBeanDefinition(String parentName) {
      this.parentName = parentName;
   }

   public ChildBeanDefinition(String parentName, MutablePropertyValues pvs) {
      super((ConstructorArgumentValues)null, pvs);
      this.parentName = parentName;
   }

   public ChildBeanDefinition(String parentName, ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
      super(cargs, pvs);
      this.parentName = parentName;
   }

   public ChildBeanDefinition(String parentName, Class beanClass, ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
      super(cargs, pvs);
      this.parentName = parentName;
      this.setBeanClass(beanClass);
   }

   public ChildBeanDefinition(String parentName, String beanClassName, ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
      super(cargs, pvs);
      this.parentName = parentName;
      this.setBeanClassName(beanClassName);
   }

   public ChildBeanDefinition(ChildBeanDefinition original) {
      super(original);
   }

   public void setParentName(@Nullable String parentName) {
      this.parentName = parentName;
   }

   @Nullable
   public String getParentName() {
      return this.parentName;
   }

   public void validate() throws BeanDefinitionValidationException {
      super.validate();
      if (this.parentName == null) {
         throw new BeanDefinitionValidationException("'parentName' must be set in ChildBeanDefinition");
      }
   }

   public AbstractBeanDefinition cloneBeanDefinition() {
      return new ChildBeanDefinition(this);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ChildBeanDefinition)) {
         return false;
      } else {
         ChildBeanDefinition that = (ChildBeanDefinition)other;
         return ObjectUtils.nullSafeEquals(this.parentName, that.parentName) && super.equals(other);
      }
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.parentName) * 29 + super.hashCode();
   }

   public String toString() {
      return "Child bean with parent '" + this.parentName + "': " + super.toString();
   }
}
