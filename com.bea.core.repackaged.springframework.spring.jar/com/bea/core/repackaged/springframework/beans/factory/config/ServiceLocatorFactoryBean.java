package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.FatalBeanException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class ServiceLocatorFactoryBean implements FactoryBean, BeanFactoryAware, InitializingBean {
   @Nullable
   private Class serviceLocatorInterface;
   @Nullable
   private Constructor serviceLocatorExceptionConstructor;
   @Nullable
   private Properties serviceMappings;
   @Nullable
   private ListableBeanFactory beanFactory;
   @Nullable
   private Object proxy;

   public void setServiceLocatorInterface(Class interfaceType) {
      this.serviceLocatorInterface = interfaceType;
   }

   public void setServiceLocatorExceptionClass(Class serviceLocatorExceptionClass) {
      this.serviceLocatorExceptionConstructor = this.determineServiceLocatorExceptionConstructor(serviceLocatorExceptionClass);
   }

   public void setServiceMappings(Properties serviceMappings) {
      this.serviceMappings = serviceMappings;
   }

   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      if (!(beanFactory instanceof ListableBeanFactory)) {
         throw new FatalBeanException("ServiceLocatorFactoryBean needs to run in a BeanFactory that is a ListableBeanFactory");
      } else {
         this.beanFactory = (ListableBeanFactory)beanFactory;
      }
   }

   public void afterPropertiesSet() {
      if (this.serviceLocatorInterface == null) {
         throw new IllegalArgumentException("Property 'serviceLocatorInterface' is required");
      } else {
         this.proxy = Proxy.newProxyInstance(this.serviceLocatorInterface.getClassLoader(), new Class[]{this.serviceLocatorInterface}, new ServiceLocatorInvocationHandler());
      }
   }

   protected Constructor determineServiceLocatorExceptionConstructor(Class exceptionClass) {
      try {
         return exceptionClass.getConstructor(String.class, Throwable.class);
      } catch (NoSuchMethodException var7) {
         try {
            return exceptionClass.getConstructor(Throwable.class);
         } catch (NoSuchMethodException var6) {
            try {
               return exceptionClass.getConstructor(String.class);
            } catch (NoSuchMethodException var5) {
               throw new IllegalArgumentException("Service locator exception [" + exceptionClass.getName() + "] neither has a (String, Throwable) constructor nor a (String) constructor");
            }
         }
      }
   }

   protected Exception createServiceLocatorException(Constructor exceptionConstructor, BeansException cause) {
      Class[] paramTypes = exceptionConstructor.getParameterTypes();
      Object[] args = new Object[paramTypes.length];

      for(int i = 0; i < paramTypes.length; ++i) {
         if (String.class == paramTypes[i]) {
            args[i] = cause.getMessage();
         } else if (paramTypes[i].isInstance(cause)) {
            args[i] = cause;
         }
      }

      return (Exception)BeanUtils.instantiateClass(exceptionConstructor, args);
   }

   @Nullable
   public Object getObject() {
      return this.proxy;
   }

   public Class getObjectType() {
      return this.serviceLocatorInterface;
   }

   public boolean isSingleton() {
      return true;
   }

   private class ServiceLocatorInvocationHandler implements InvocationHandler {
      private ServiceLocatorInvocationHandler() {
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (ReflectionUtils.isEqualsMethod(method)) {
            return proxy == args[0];
         } else if (ReflectionUtils.isHashCodeMethod(method)) {
            return System.identityHashCode(proxy);
         } else {
            return ReflectionUtils.isToStringMethod(method) ? "Service locator: " + ServiceLocatorFactoryBean.this.serviceLocatorInterface : this.invokeServiceLocatorMethod(method, args);
         }
      }

      private Object invokeServiceLocatorMethod(Method method, Object[] args) throws Exception {
         Class serviceLocatorMethodReturnType = this.getServiceLocatorMethodReturnType(method);

         try {
            String beanName = this.tryGetBeanName(args);
            Assert.state(ServiceLocatorFactoryBean.this.beanFactory != null, "No BeanFactory available");
            return StringUtils.hasLength(beanName) ? ServiceLocatorFactoryBean.this.beanFactory.getBean(beanName, serviceLocatorMethodReturnType) : ServiceLocatorFactoryBean.this.beanFactory.getBean(serviceLocatorMethodReturnType);
         } catch (BeansException var5) {
            if (ServiceLocatorFactoryBean.this.serviceLocatorExceptionConstructor != null) {
               throw ServiceLocatorFactoryBean.this.createServiceLocatorException(ServiceLocatorFactoryBean.this.serviceLocatorExceptionConstructor, var5);
            } else {
               throw var5;
            }
         }
      }

      private String tryGetBeanName(@Nullable Object[] args) {
         String beanName = "";
         if (args != null && args.length == 1 && args[0] != null) {
            beanName = args[0].toString();
         }

         if (ServiceLocatorFactoryBean.this.serviceMappings != null) {
            String mappedName = ServiceLocatorFactoryBean.this.serviceMappings.getProperty(beanName);
            if (mappedName != null) {
               beanName = mappedName;
            }
         }

         return beanName;
      }

      private Class getServiceLocatorMethodReturnType(Method method) throws NoSuchMethodException {
         Assert.state(ServiceLocatorFactoryBean.this.serviceLocatorInterface != null, "No service locator interface specified");
         Class[] paramTypes = method.getParameterTypes();
         Method interfaceMethod = ServiceLocatorFactoryBean.this.serviceLocatorInterface.getMethod(method.getName(), paramTypes);
         Class serviceLocatorReturnType = interfaceMethod.getReturnType();
         if (paramTypes.length <= 1 && Void.TYPE != serviceLocatorReturnType) {
            return serviceLocatorReturnType;
         } else {
            throw new UnsupportedOperationException("May only call methods with signature '<type> xxx()' or '<type> xxx(<idtype> id)' on factory interface, but tried to call: " + interfaceMethod);
         }
      }

      // $FF: synthetic method
      ServiceLocatorInvocationHandler(Object x1) {
         this();
      }
   }
}
