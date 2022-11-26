package com.bea.core.repackaged.springframework.beans.factory.groovy;

import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.BeanWrapperImpl;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import groovy.lang.GroovyObjectSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class GroovyBeanDefinitionWrapper extends GroovyObjectSupport {
   private static final String PARENT = "parent";
   private static final String AUTOWIRE = "autowire";
   private static final String CONSTRUCTOR_ARGS = "constructorArgs";
   private static final String FACTORY_BEAN = "factoryBean";
   private static final String FACTORY_METHOD = "factoryMethod";
   private static final String INIT_METHOD = "initMethod";
   private static final String DESTROY_METHOD = "destroyMethod";
   private static final String SINGLETON = "singleton";
   private static final List dynamicProperties = new ArrayList(8);
   private String beanName;
   private Class clazz;
   private Collection constructorArgs;
   private AbstractBeanDefinition definition;
   private BeanWrapper definitionWrapper;
   private String parentName;

   public GroovyBeanDefinitionWrapper(String beanName) {
      this.beanName = beanName;
   }

   public GroovyBeanDefinitionWrapper(String beanName, Class clazz) {
      this.beanName = beanName;
      this.clazz = clazz;
   }

   public GroovyBeanDefinitionWrapper(String beanName, Class clazz, Collection constructorArgs) {
      this.beanName = beanName;
      this.clazz = clazz;
      this.constructorArgs = constructorArgs;
   }

   public String getBeanName() {
      return this.beanName;
   }

   public void setBeanDefinition(AbstractBeanDefinition definition) {
      this.definition = definition;
   }

   public AbstractBeanDefinition getBeanDefinition() {
      if (this.definition == null) {
         this.definition = this.createBeanDefinition();
      }

      return this.definition;
   }

   protected AbstractBeanDefinition createBeanDefinition() {
      AbstractBeanDefinition bd = new GenericBeanDefinition();
      bd.setBeanClass(this.clazz);
      if (!CollectionUtils.isEmpty(this.constructorArgs)) {
         ConstructorArgumentValues cav = new ConstructorArgumentValues();
         Iterator var3 = this.constructorArgs.iterator();

         while(var3.hasNext()) {
            Object constructorArg = var3.next();
            cav.addGenericArgumentValue(constructorArg);
         }

         bd.setConstructorArgumentValues(cav);
      }

      if (this.parentName != null) {
         bd.setParentName(this.parentName);
      }

      this.definitionWrapper = new BeanWrapperImpl(bd);
      return bd;
   }

   public void setBeanDefinitionHolder(BeanDefinitionHolder holder) {
      this.definition = (AbstractBeanDefinition)holder.getBeanDefinition();
      this.beanName = holder.getBeanName();
   }

   public BeanDefinitionHolder getBeanDefinitionHolder() {
      return new BeanDefinitionHolder(this.getBeanDefinition(), this.getBeanName());
   }

   public void setParent(Object obj) {
      if (obj == null) {
         throw new IllegalArgumentException("Parent bean cannot be set to a null runtime bean reference!");
      } else {
         if (obj instanceof String) {
            this.parentName = (String)obj;
         } else if (obj instanceof RuntimeBeanReference) {
            this.parentName = ((RuntimeBeanReference)obj).getBeanName();
         } else if (obj instanceof GroovyBeanDefinitionWrapper) {
            this.parentName = ((GroovyBeanDefinitionWrapper)obj).getBeanName();
         }

         this.getBeanDefinition().setParentName(this.parentName);
         this.getBeanDefinition().setAbstract(false);
      }
   }

   public GroovyBeanDefinitionWrapper addProperty(String propertyName, Object propertyValue) {
      if (propertyValue instanceof GroovyBeanDefinitionWrapper) {
         propertyValue = ((GroovyBeanDefinitionWrapper)propertyValue).getBeanDefinition();
      }

      this.getBeanDefinition().getPropertyValues().add(propertyName, propertyValue);
      return this;
   }

   public Object getProperty(String property) {
      if (this.definitionWrapper.isReadableProperty(property)) {
         return this.definitionWrapper.getPropertyValue(property);
      } else {
         return dynamicProperties.contains(property) ? null : super.getProperty(property);
      }
   }

   public void setProperty(String property, Object newValue) {
      if ("parent".equals(property)) {
         this.setParent(newValue);
      } else {
         AbstractBeanDefinition bd = this.getBeanDefinition();
         if ("autowire".equals(property)) {
            if ("byName".equals(newValue)) {
               bd.setAutowireMode(1);
            } else if ("byType".equals(newValue)) {
               bd.setAutowireMode(2);
            } else if ("constructor".equals(newValue)) {
               bd.setAutowireMode(3);
            } else if (Boolean.TRUE.equals(newValue)) {
               bd.setAutowireMode(1);
            }
         } else if ("constructorArgs".equals(property) && newValue instanceof List) {
            ConstructorArgumentValues cav = new ConstructorArgumentValues();
            List args = (List)newValue;
            Iterator var6 = args.iterator();

            while(var6.hasNext()) {
               Object arg = var6.next();
               cav.addGenericArgumentValue(arg);
            }

            bd.setConstructorArgumentValues(cav);
         } else if ("factoryBean".equals(property)) {
            if (newValue != null) {
               bd.setFactoryBeanName(newValue.toString());
            }
         } else if ("factoryMethod".equals(property)) {
            if (newValue != null) {
               bd.setFactoryMethodName(newValue.toString());
            }
         } else if ("initMethod".equals(property)) {
            if (newValue != null) {
               bd.setInitMethodName(newValue.toString());
            }
         } else if ("destroyMethod".equals(property)) {
            if (newValue != null) {
               bd.setDestroyMethodName(newValue.toString());
            }
         } else if ("singleton".equals(property)) {
            bd.setScope(Boolean.TRUE.equals(newValue) ? "singleton" : "prototype");
         } else if (this.definitionWrapper.isWritableProperty(property)) {
            this.definitionWrapper.setPropertyValue(property, newValue);
         } else {
            super.setProperty(property, newValue);
         }
      }

   }

   static {
      dynamicProperties.add("parent");
      dynamicProperties.add("autowire");
      dynamicProperties.add("constructorArgs");
      dynamicProperties.add("factoryBean");
      dynamicProperties.add("factoryMethod");
      dynamicProperties.add("initMethod");
      dynamicProperties.add("destroyMethod");
      dynamicProperties.add("singleton");
   }
}
