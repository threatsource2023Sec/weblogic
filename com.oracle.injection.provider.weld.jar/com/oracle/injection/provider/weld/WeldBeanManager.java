package com.oracle.injection.provider.weld;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionException;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.bean.ManagedBean;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldManager;

class WeldBeanManager implements BeanManager {
   static final Logger m_logger = Logger.getLogger(WeldBeanManager.class.getName());
   private final ClassLoader m_classLoader;
   private final WeldManager m_weldManager;
   private final Map managedInstanceMap = Collections.synchronizedMap(new IdentityHashMap());
   private final ContainerIntegrationService m_containerIntegrationService;
   private CreationalContext ejbContext;

   public WeldBeanManager(ContainerIntegrationService containerIntegrationService, WeldManager weldManager, ClassLoader classLoader) {
      if (weldManager == null) {
         throw new IllegalArgumentException("WeldManager cannot be null");
      } else if (classLoader == null) {
         throw new IllegalArgumentException("ClassLoader cannot be null");
      } else {
         this.m_containerIntegrationService = containerIntegrationService;
         this.m_weldManager = weldManager;
         this.m_classLoader = classLoader;
      }
   }

   public Object getBean(String beanName) {
      return this.newBeanInstance(beanName, true);
   }

   public Object getBean(Class beanClass) {
      return this.newBeanInstance(beanClass, true);
   }

   public void destroyBean(Object bean) {
      if (m_logger.isLoggable(Level.FINE)) {
         Exception debugException = new Exception();
         m_logger.log(Level.FINE, "WeldBeanManager#destroyBean called for object " + bean + ".  Call stack: ", debugException);
      }

      ManagedInstance managedInstance = (ManagedInstance)this.managedInstanceMap.remove(bean);
      if (managedInstance != null) {
         managedInstance.destroyInstance();
      }

   }

   public Object newBeanInstance(String beanName, boolean invokePostConstruct) {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();

      try {
         Class clazz = Class.forName(beanName, true, this.m_classLoader);
         AnnotatedType annotatedType = beanManager.createAnnotatedType(clazz);
         InjectionTarget injectionTarget = this.m_weldManager.fireProcessInjectionTarget(annotatedType);
         CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
         Object instance = injectionTarget.produce(creationalContext);
         injectionTarget.inject(instance, creationalContext);
         if (invokePostConstruct) {
            injectionTarget.postConstruct(instance);
         }

         this.managedInstanceMap.put(instance, new ManagedInstance(instance, injectionTarget, creationalContext));
         if (m_logger.isLoggable(Level.FINE)) {
            Exception debugException = new Exception();
            m_logger.log(Level.FINE, "WeldBeanManager#newBeanInstance called and created " + instance + ".  Call stack: ", debugException);
         }

         return instance;
      } catch (ClassNotFoundException var10) {
         if (m_logger.isLoggable(Level.FINE)) {
            m_logger.log(Level.FINE, "Exception creating " + beanName + ".  For an MDB this is expected.  Exception: ", var10);
         }

         return null;
      }
   }

   public Object newBeanInstance(Class beanClass, boolean invokePostConstruct) {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();
      AnnotatedType annotatedType = beanManager.createAnnotatedType(beanClass);
      InjectionTarget injectionTarget = this.m_weldManager.fireProcessInjectionTarget(annotatedType);
      CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
      Object instance = injectionTarget.produce(creationalContext);
      injectionTarget.inject(instance, creationalContext);
      if (invokePostConstruct) {
         injectionTarget.postConstruct(instance);
      }

      this.managedInstanceMap.put(instance, new ManagedInstance(instance, injectionTarget, creationalContext));
      if (m_logger.isLoggable(Level.FINE)) {
         Exception debugException = new Exception();
         m_logger.log(Level.FINE, "WeldBeanManager#newBeanInstance called and created " + instance + ".  Call stack: ", debugException);
      }

      return instance;
   }

   public Object newInterceptorInstance(Class interceptorClass) {
      Object interceptorInstance = null;
      InjectionTarget injectionTarget = null;
      if (this.m_weldManager instanceof BeanManagerImpl) {
         List interceptors = ((BeanManagerImpl)this.m_weldManager).getInterceptors();
         Iterator var5 = interceptors.iterator();

         while(var5.hasNext()) {
            Interceptor interceptor = (Interceptor)var5.next();
            if (interceptor.getBeanClass().equals(interceptorClass)) {
               interceptorInstance = this.m_weldManager.getReference(interceptor, interceptorClass, this.ejbContext);
            }
         }
      }

      if (interceptorInstance == null) {
         Set availableBeans = this.m_weldManager.getBeans(interceptorClass, new Annotation[0]);
         if (availableBeans != null && !availableBeans.isEmpty()) {
            Bean interceptorBean = this.m_weldManager.resolve(availableBeans);
            interceptorInstance = this.m_weldManager.getReference(interceptorBean, interceptorClass, this.ejbContext);
         }
      }

      if (interceptorInstance == null) {
         CreationalContext creationalContext = this.m_weldManager.createCreationalContext((Contextual)null);
         AnnotatedType annotatedType = this.m_weldManager.createAnnotatedType(interceptorClass);
         injectionTarget = this.m_weldManager.getInjectionTargetFactory(annotatedType).createInterceptorInjectionTarget();
         interceptorInstance = injectionTarget.produce(creationalContext);
         injectionTarget.inject(interceptorInstance, creationalContext);
         this.managedInstanceMap.put(interceptorInstance, new ManagedInstance(interceptorInstance, injectionTarget, creationalContext));
      }

      if (m_logger.isLoggable(Level.FINE)) {
         Exception debugException = new Exception();
         m_logger.log(Level.FINE, "WeldBeanManager#newInterceptorInstance called and created " + interceptorInstance + ".  Call stack: ", debugException);
      }

      return interceptorInstance;
   }

   public void injectOnExternalInstance(Object instance) throws InjectionException {
      ManagedInstance managedInstance = (ManagedInstance)this.managedInstanceMap.get(instance);
      if (managedInstance == null) {
         managedInstance = this.createAndSaveInstanceManager(instance);
      }

      InjectionTarget injectionTarget = managedInstance.getInjectionTarget();
      CreationalContext creationalContext = managedInstance.getCreationalContext();
      injectionTarget.inject(instance, creationalContext);
   }

   public ExpressionFactory getWrappedExpressionFactory(ExpressionFactory originalFactory) {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();
      return beanManager.wrapExpressionFactory(originalFactory);
   }

   public ELResolver getELResolver() {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();
      return beanManager.getELResolver();
   }

   public Object getInternalBeanManager() {
      return this.getCDIBeanManager();
   }

   private ManagedInstance createAndSaveInstanceManager(Object managedBean) {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();
      AnnotatedType annotatedType = beanManager.createAnnotatedType(managedBean.getClass());
      InjectionTarget injectionTarget = this.m_weldManager.fireProcessInjectionTarget(annotatedType);
      CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
      ManagedInstance managedInstance = new ManagedInstance(managedBean, injectionTarget, creationalContext);
      this.managedInstanceMap.put(managedBean, managedInstance);
      if (m_logger.isLoggable(Level.FINE)) {
         Exception debugException = new Exception();
         m_logger.log(Level.FINE, "WeldBeanManager#createAndSaveInstanceManager called and created " + managedInstance + ".  Call stack: ", debugException);
      }

      return managedInstance;
   }

   public void invokePostConstruct(Object managedBean) throws InjectionException {
      ManagedInstance managedInstance = (ManagedInstance)this.managedInstanceMap.get(managedBean);
      if (managedInstance == null) {
         managedInstance = this.createAndSaveInstanceManager(managedBean);
      }

      InjectionTarget injectionTarget = managedInstance.getInjectionTarget();
      if (injectionTarget != null) {
         injectionTarget.postConstruct(managedBean);
      }

   }

   public void invokePreDestroy(Object managedBean) throws InjectionException {
      ManagedInstance managedInstance = (ManagedInstance)this.managedInstanceMap.get(managedBean);
      if (managedInstance != null) {
         InjectionTarget injectionTarget = managedInstance.getInjectionTarget();
         if (injectionTarget != null) {
            injectionTarget.preDestroy(managedBean);
         }

      } else {
         throw new InjectionException("Managed bean was not created nor injected by this BeanManager implementation");
      }
   }

   protected void addInstance(Object instance, InjectionTarget injectionTarget, CreationalContext creationalContext) {
      this.managedInstanceMap.put(instance, new ManagedInstance(instance, injectionTarget, creationalContext));
   }

   int sizeOfInstanceToInstanceManagers() {
      return this.managedInstanceMap.size();
   }

   protected javax.enterprise.inject.spi.BeanManager getCDIBeanManager() {
      return this.m_weldManager;
   }

   private EjbDescriptor getEjbDescriptor(String ejbName) {
      EjbDescriptor weldEjbDescriptor = this.m_weldManager.getEjbDescriptor(ejbName);
      if (weldEjbDescriptor != null) {
         return weldEjbDescriptor;
      } else {
         if (this.m_weldManager instanceof BeanManagerImpl) {
            HashSet beanManagers = ((BeanManagerImpl)this.m_weldManager).getAccessibleManagers();
            if (beanManagers != null) {
               Iterator var4 = beanManagers.iterator();

               while(var4.hasNext()) {
                  BeanManagerImpl oneBeanManager = (BeanManagerImpl)var4.next();
                  weldEjbDescriptor = oneBeanManager.getEjbDescriptor(ejbName);
                  if (weldEjbDescriptor != null) {
                     return weldEjbDescriptor;
                  }
               }
            }
         }

         return null;
      }
   }

   public Object createEjb(String ejbName) {
      EjbDescriptor weldEjbDescriptor = this.getEjbDescriptor(ejbName);
      if (weldEjbDescriptor == null) {
         if (m_logger.isLoggable(Level.INFO)) {
            m_logger.log(Level.INFO, "Could not find the weld descriptor for ejb: " + ejbName);
         }

         return null;
      } else {
         Bean bean = this.m_weldManager.getBean(weldEjbDescriptor);
         InjectionTarget it = this.m_weldManager.createInjectionTarget(weldEjbDescriptor);
         this.ejbContext = this.m_weldManager.createCreationalContext(bean);
         if (bean instanceof SessionBean) {
            this.m_containerIntegrationService.registerEjbDescriptorAroundConstructInterceptors(this.getCDIBeanManager(), this.ejbContext, weldEjbDescriptor.getBeanClass(), ((SessionBean)bean).getAnnotated());
         } else if (weldEjbDescriptor.isMessageDriven()) {
            this.m_containerIntegrationService.registerEjbDescriptorAroundConstructInterceptors(this.getCDIBeanManager(), this.ejbContext, weldEjbDescriptor.getBeanClass(), (AnnotatedType)null);
         }

         Object instance = it.produce(this.ejbContext);
         this.managedInstanceMap.put(instance, new ManagedInstance(instance, it, this.ejbContext));
         if (m_logger.isLoggable(Level.FINE)) {
            Exception debugException = new Exception();
            m_logger.log(Level.FINE, "WeldBeanManager#createEjb called and created " + instance + ".  Call stack: ", debugException);
         }

         return instance;
      }
   }

   public void injectEjbInstance(Object bean) {
      ManagedInstance managedInstance = (ManagedInstance)this.managedInstanceMap.get(bean);
      managedInstance.getInjectionTarget().inject(bean, managedInstance.getCreationalContext());
   }

   private static boolean isManagedBean(Bean bean) {
      return bean instanceof ManagedBean;
   }

   private static Object getBeanInstance(Bean bean, javax.enterprise.inject.spi.BeanManager beanManager) {
      CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
      return beanManager.getReference(bean, bean.getBeanClass(), creationalContext);
   }

   public Object getReference(String className) {
      javax.enterprise.inject.spi.BeanManager beanManager = this.getCDIBeanManager();
      Set availableBeans = beanManager.getBeans(className);
      if (!availableBeans.isEmpty()) {
         Bean bean = beanManager.resolve(availableBeans);
         return getBeanInstance(bean, beanManager);
      } else {
         availableBeans = beanManager.getBeans(Object.class, new Annotation[0]);
         if (!availableBeans.isEmpty()) {
            Iterator var4 = availableBeans.iterator();

            while(var4.hasNext()) {
               Bean bean = (Bean)var4.next();
               if (bean.getBeanClass().getName().equals(className) && isManagedBean(bean)) {
                  return getBeanInstance(bean, beanManager);
               }
            }
         }

         return null;
      }
   }
}
