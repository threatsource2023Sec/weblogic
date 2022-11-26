package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.SimpleTypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBeanNotInitializedException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class AbstractFactoryBean implements FactoryBean, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private boolean singleton = true;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private BeanFactory beanFactory;
   private boolean initialized = false;
   @Nullable
   private Object singletonInstance;
   @Nullable
   private Object earlySingletonInstance;

   public void setSingleton(boolean singleton) {
      this.singleton = singleton;
   }

   public boolean isSingleton() {
      return this.singleton;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void setBeanFactory(@Nullable BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Nullable
   protected BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   protected TypeConverter getBeanTypeConverter() {
      BeanFactory beanFactory = this.getBeanFactory();
      return (TypeConverter)(beanFactory instanceof ConfigurableBeanFactory ? ((ConfigurableBeanFactory)beanFactory).getTypeConverter() : new SimpleTypeConverter());
   }

   public void afterPropertiesSet() throws Exception {
      if (this.isSingleton()) {
         this.initialized = true;
         this.singletonInstance = this.createInstance();
         this.earlySingletonInstance = null;
      }

   }

   public final Object getObject() throws Exception {
      if (this.isSingleton()) {
         return this.initialized ? this.singletonInstance : this.getEarlySingletonInstance();
      } else {
         return this.createInstance();
      }
   }

   private Object getEarlySingletonInstance() throws Exception {
      Class[] ifcs = this.getEarlySingletonInterfaces();
      if (ifcs == null) {
         throw new FactoryBeanNotInitializedException(this.getClass().getName() + " does not support circular references");
      } else {
         if (this.earlySingletonInstance == null) {
            this.earlySingletonInstance = Proxy.newProxyInstance(this.beanClassLoader, ifcs, new EarlySingletonInvocationHandler());
         }

         return this.earlySingletonInstance;
      }
   }

   @Nullable
   private Object getSingletonInstance() throws IllegalStateException {
      Assert.state(this.initialized, "Singleton instance not initialized yet");
      return this.singletonInstance;
   }

   public void destroy() throws Exception {
      if (this.isSingleton()) {
         this.destroyInstance(this.singletonInstance);
      }

   }

   @Nullable
   public abstract Class getObjectType();

   protected abstract Object createInstance() throws Exception;

   @Nullable
   protected Class[] getEarlySingletonInterfaces() {
      Class type = this.getObjectType();
      return type != null && type.isInterface() ? new Class[]{type} : null;
   }

   protected void destroyInstance(@Nullable Object instance) throws Exception {
   }

   private class EarlySingletonInvocationHandler implements InvocationHandler {
      private EarlySingletonInvocationHandler() {
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (ReflectionUtils.isEqualsMethod(method)) {
            return proxy == args[0];
         } else if (ReflectionUtils.isHashCodeMethod(method)) {
            return System.identityHashCode(proxy);
         } else if (!AbstractFactoryBean.this.initialized && ReflectionUtils.isToStringMethod(method)) {
            return "Early singleton proxy for interfaces " + ObjectUtils.nullSafeToString((Object[])AbstractFactoryBean.this.getEarlySingletonInterfaces());
         } else {
            try {
               return method.invoke(AbstractFactoryBean.this.getSingletonInstance(), args);
            } catch (InvocationTargetException var5) {
               throw var5.getTargetException();
            }
         }
      }

      // $FF: synthetic method
      EarlySingletonInvocationHandler(Object x1) {
         this();
      }
   }
}
