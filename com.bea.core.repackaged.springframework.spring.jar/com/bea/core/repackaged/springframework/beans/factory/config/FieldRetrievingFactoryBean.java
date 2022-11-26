package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBeanNotInitializedException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Field;

public class FieldRetrievingFactoryBean implements FactoryBean, BeanNameAware, BeanClassLoaderAware, InitializingBean {
   @Nullable
   private Class targetClass;
   @Nullable
   private Object targetObject;
   @Nullable
   private String targetField;
   @Nullable
   private String staticField;
   @Nullable
   private String beanName;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Field fieldObject;

   public void setTargetClass(@Nullable Class targetClass) {
      this.targetClass = targetClass;
   }

   @Nullable
   public Class getTargetClass() {
      return this.targetClass;
   }

   public void setTargetObject(@Nullable Object targetObject) {
      this.targetObject = targetObject;
   }

   @Nullable
   public Object getTargetObject() {
      return this.targetObject;
   }

   public void setTargetField(@Nullable String targetField) {
      this.targetField = targetField != null ? StringUtils.trimAllWhitespace(targetField) : null;
   }

   @Nullable
   public String getTargetField() {
      return this.targetField;
   }

   public void setStaticField(String staticField) {
      this.staticField = StringUtils.trimAllWhitespace(staticField);
   }

   public void setBeanName(String beanName) {
      this.beanName = StringUtils.trimAllWhitespace(BeanFactoryUtils.originalBeanName(beanName));
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws ClassNotFoundException, NoSuchFieldException {
      if (this.targetClass != null && this.targetObject != null) {
         throw new IllegalArgumentException("Specify either targetClass or targetObject, not both");
      } else {
         if (this.targetClass == null && this.targetObject == null) {
            if (this.targetField != null) {
               throw new IllegalArgumentException("Specify targetClass or targetObject in combination with targetField");
            }

            if (this.staticField == null) {
               this.staticField = this.beanName;
               Assert.state(this.staticField != null, "No target field specified");
            }

            int lastDotIndex = this.staticField.lastIndexOf(46);
            if (lastDotIndex == -1 || lastDotIndex == this.staticField.length()) {
               throw new IllegalArgumentException("staticField must be a fully qualified class plus static field name: e.g. 'example.MyExampleClass.MY_EXAMPLE_FIELD'");
            }

            String className = this.staticField.substring(0, lastDotIndex);
            String fieldName = this.staticField.substring(lastDotIndex + 1);
            this.targetClass = ClassUtils.forName(className, this.beanClassLoader);
            this.targetField = fieldName;
         } else if (this.targetField == null) {
            throw new IllegalArgumentException("targetField is required");
         }

         Class targetClass = this.targetObject != null ? this.targetObject.getClass() : this.targetClass;
         this.fieldObject = targetClass.getField(this.targetField);
      }
   }

   @Nullable
   public Object getObject() throws IllegalAccessException {
      if (this.fieldObject == null) {
         throw new FactoryBeanNotInitializedException();
      } else {
         ReflectionUtils.makeAccessible(this.fieldObject);
         return this.targetObject != null ? this.fieldObject.get(this.targetObject) : this.fieldObject.get((Object)null);
      }
   }

   public Class getObjectType() {
      return this.fieldObject != null ? this.fieldObject.getType() : null;
   }

   public boolean isSingleton() {
      return false;
   }
}
