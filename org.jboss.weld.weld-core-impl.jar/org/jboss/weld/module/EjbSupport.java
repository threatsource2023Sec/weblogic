package org.jboss.weld.module;

import java.util.Collection;
import java.util.Collections;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.injection.producer.BasicInjectionTarget;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.SetMultimap;

public interface EjbSupport extends Service {
   EjbSupport NOOP_IMPLEMENTATION = new EjbSupport() {
      public void cleanup() {
      }

      private Object fail() {
         throw new IllegalStateException("Cannot process session bean. weld-ejb module not registered.");
      }

      public BasicInjectionTarget createSessionBeanInjectionTarget(EnhancedAnnotatedType type, SessionBean bean, BeanManagerImpl manager) {
         return (BasicInjectionTarget)this.fail();
      }

      public BeanAttributes createSessionBeanAttributes(EnhancedAnnotatedType type, BeanManagerImpl manager) {
         return (BeanAttributes)this.fail();
      }

      public BasicInjectionTarget createMessageDrivenInjectionTarget(EnhancedAnnotatedType type, EjbDescriptor descriptor, BeanManagerImpl manager) {
         return (BasicInjectionTarget)this.fail();
      }

      public void createSessionBeans(BeanDeployerEnvironment environment, SetMultimap classes, BeanManagerImpl manager) {
      }

      public void createNewSessionBeans(BeanDeployerEnvironment environment, BeanManagerImpl manager) {
      }

      public Class getTimeoutAnnotation() {
         return null;
      }

      public void registerCdiInterceptorsForMessageDrivenBeans(BeanDeployerEnvironment environment, BeanManagerImpl manager) {
      }

      public Collection getEjbDescriptors() {
         return Collections.emptyList();
      }

      public boolean isEjb(Class beanClass) {
         return false;
      }

      public EjbDescriptor getEjbDescriptor(String beanName) {
         return null;
      }

      public boolean isSessionBeanProxy(Object instance) {
         return false;
      }
   };

   BeanAttributes createSessionBeanAttributes(EnhancedAnnotatedType var1, BeanManagerImpl var2);

   BasicInjectionTarget createSessionBeanInjectionTarget(EnhancedAnnotatedType var1, SessionBean var2, BeanManagerImpl var3);

   BasicInjectionTarget createMessageDrivenInjectionTarget(EnhancedAnnotatedType var1, EjbDescriptor var2, BeanManagerImpl var3);

   void createSessionBeans(BeanDeployerEnvironment var1, SetMultimap var2, BeanManagerImpl var3);

   void createNewSessionBeans(BeanDeployerEnvironment var1, BeanManagerImpl var2);

   Class getTimeoutAnnotation();

   void registerCdiInterceptorsForMessageDrivenBeans(BeanDeployerEnvironment var1, BeanManagerImpl var2);

   boolean isEjb(Class var1);

   EjbDescriptor getEjbDescriptor(String var1);

   Collection getEjbDescriptors();

   boolean isSessionBeanProxy(Object var1);
}
