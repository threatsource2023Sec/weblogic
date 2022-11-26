package com.oracle.injection.integration.utils;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;

public class InjectionBeanCreator {
   private final InjectionContainer m_injectionContainer;
   private final String m_moduleName;

   public InjectionBeanCreator(InjectionContainer injectionContainer, String moduleName) {
      if (injectionContainer == null) {
         throw new IllegalArgumentException("InjectionContainer instance cannot be null");
      } else if (moduleName == null) {
         throw new IllegalArgumentException("Module name cannot be null");
      } else {
         this.m_injectionContainer = injectionContainer;
         this.m_moduleName = moduleName;
      }
   }

   public InjectionContainer getInjectionContainer() {
      return this.m_injectionContainer;
   }

   public Object createInstance(Class clazz) throws IllegalAccessException, ClassNotFoundException, InstantiationException, ClassCastException {
      BeanManager beanManager = this.getBeanManager();
      return beanManager != null ? beanManager.getBean(clazz) : null;
   }

   public Object createInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException, ClassCastException {
      BeanManager beanManager = this.getBeanManager();
      return beanManager != null ? beanManager.getBean(className) : null;
   }

   public Object newBeanInstance(Class clazz) {
      BeanManager beanManager = this.getBeanManager();
      return beanManager != null ? beanManager.newBeanInstance(clazz, false) : null;
   }

   public Object newBeanInstance(String className) {
      return this.newBeanInstance(className, false);
   }

   public Object newBeanInstance(String className, boolean invokePostConstruct) {
      BeanManager beanManager = this.getBeanManager();
      return beanManager != null ? beanManager.newBeanInstance(className, invokePostConstruct) : null;
   }

   public void injectOnExternalInstance(Object instance) throws InjectionException {
      BeanManager beanManager = this.getBeanManager();
      if (beanManager != null) {
         beanManager.injectOnExternalInstance(instance);
      }

   }

   public void invokePostConstruct(Object bean) throws InjectionException {
      BeanManager beanManager = this.getBeanManager();
      if (beanManager != null) {
         beanManager.invokePostConstruct(bean);
      }

   }

   public void invokePreDestroy(Object bean) throws InjectionException {
      BeanManager beanManager = this.getBeanManager();
      if (beanManager != null) {
         beanManager.invokePreDestroy(bean);
      }

   }

   public void destroyBean(Object bean) {
      BeanManager beanManager = this.getBeanManager();
      if (beanManager != null) {
         beanManager.destroyBean(bean);
      }

   }

   private BeanManager getBeanManager() {
      InjectionDeployment deployment = this.getInjectionContainer().getDeployment();
      return deployment != null ? deployment.getBeanManager(this.m_moduleName) : null;
   }

   public Object getReference(String className) {
      BeanManager beanManager = this.getBeanManager();
      return beanManager != null ? beanManager.getReference(className) : null;
   }
}
