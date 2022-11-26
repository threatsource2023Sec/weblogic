package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.SimpleTypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.TypeMismatchException;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.naming.Context;
import javax.naming.NamingException;

public class JndiObjectFactoryBean extends JndiObjectLocator implements FactoryBean, BeanFactoryAware, BeanClassLoaderAware {
   @Nullable
   private Class[] proxyInterfaces;
   private boolean lookupOnStartup = true;
   private boolean cache = true;
   private boolean exposeAccessContext = false;
   @Nullable
   private Object defaultObject;
   @Nullable
   private ConfigurableBeanFactory beanFactory;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Object jndiObject;

   public void setProxyInterface(Class proxyInterface) {
      this.proxyInterfaces = new Class[]{proxyInterface};
   }

   public void setProxyInterfaces(Class... proxyInterfaces) {
      this.proxyInterfaces = proxyInterfaces;
   }

   public void setLookupOnStartup(boolean lookupOnStartup) {
      this.lookupOnStartup = lookupOnStartup;
   }

   public void setCache(boolean cache) {
      this.cache = cache;
   }

   public void setExposeAccessContext(boolean exposeAccessContext) {
      this.exposeAccessContext = exposeAccessContext;
   }

   public void setDefaultObject(Object defaultObject) {
      this.defaultObject = defaultObject;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (beanFactory instanceof ConfigurableBeanFactory) {
         this.beanFactory = (ConfigurableBeanFactory)beanFactory;
      }

   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
      super.afterPropertiesSet();
      if (this.proxyInterfaces == null && this.lookupOnStartup && this.cache && !this.exposeAccessContext) {
         if (this.defaultObject != null && this.getExpectedType() != null && !this.getExpectedType().isInstance(this.defaultObject)) {
            TypeConverter converter = this.beanFactory != null ? this.beanFactory.getTypeConverter() : new SimpleTypeConverter();

            try {
               this.defaultObject = ((TypeConverter)converter).convertIfNecessary(this.defaultObject, this.getExpectedType());
            } catch (TypeMismatchException var3) {
               throw new IllegalArgumentException("Default object [" + this.defaultObject + "] of type [" + this.defaultObject.getClass().getName() + "] is not of expected type [" + this.getExpectedType().getName() + "] and cannot be converted either", var3);
            }
         }

         this.jndiObject = this.lookupWithFallback();
      } else {
         if (this.defaultObject != null) {
            throw new IllegalArgumentException("'defaultObject' is not supported in combination with 'proxyInterface'");
         }

         this.jndiObject = JndiObjectFactoryBean.JndiObjectProxyFactory.createJndiObjectProxy(this);
      }

   }

   protected Object lookupWithFallback() throws NamingException {
      ClassLoader originalClassLoader = ClassUtils.overrideThreadContextClassLoader(this.beanClassLoader);

      Object var3;
      try {
         Object var2 = this.lookup();
         return var2;
      } catch (TypeMismatchNamingException var8) {
         throw var8;
      } catch (NamingException var9) {
         if (this.defaultObject == null) {
            throw var9;
         }

         if (this.logger.isTraceEnabled()) {
            this.logger.trace("JNDI lookup failed - returning specified default object instead", var9);
         } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("JNDI lookup failed - returning specified default object instead: " + var9);
         }

         var3 = this.defaultObject;
      } finally {
         if (originalClassLoader != null) {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
         }

      }

      return var3;
   }

   @Nullable
   public Object getObject() {
      return this.jndiObject;
   }

   public Class getObjectType() {
      if (this.proxyInterfaces != null) {
         if (this.proxyInterfaces.length == 1) {
            return this.proxyInterfaces[0];
         }

         if (this.proxyInterfaces.length > 1) {
            return this.createCompositeInterface(this.proxyInterfaces);
         }
      }

      return this.jndiObject != null ? this.jndiObject.getClass() : this.getExpectedType();
   }

   public boolean isSingleton() {
      return true;
   }

   protected Class createCompositeInterface(Class[] interfaces) {
      return ClassUtils.createCompositeInterface(interfaces, this.beanClassLoader);
   }

   private static class JndiContextExposingInterceptor implements MethodInterceptor {
      private final JndiTemplate jndiTemplate;

      public JndiContextExposingInterceptor(JndiTemplate jndiTemplate) {
         this.jndiTemplate = jndiTemplate;
      }

      public Object invoke(MethodInvocation invocation) throws Throwable {
         Context ctx = this.isEligible(invocation.getMethod()) ? this.jndiTemplate.getContext() : null;

         Object var3;
         try {
            var3 = invocation.proceed();
         } finally {
            this.jndiTemplate.releaseContext(ctx);
         }

         return var3;
      }

      protected boolean isEligible(Method method) {
         return Object.class != method.getDeclaringClass();
      }
   }

   private static class JndiObjectProxyFactory {
      private static Object createJndiObjectProxy(JndiObjectFactoryBean jof) throws NamingException {
         JndiObjectTargetSource targetSource = new JndiObjectTargetSource();
         targetSource.setJndiTemplate(jof.getJndiTemplate());
         String jndiName = jof.getJndiName();
         Assert.state(jndiName != null, "No JNDI name specified");
         targetSource.setJndiName(jndiName);
         targetSource.setExpectedType(jof.getExpectedType());
         targetSource.setResourceRef(jof.isResourceRef());
         targetSource.setLookupOnStartup(jof.lookupOnStartup);
         targetSource.setCache(jof.cache);
         targetSource.afterPropertiesSet();
         ProxyFactory proxyFactory = new ProxyFactory();
         if (jof.proxyInterfaces != null) {
            proxyFactory.setInterfaces(jof.proxyInterfaces);
         } else {
            Class targetClass = targetSource.getTargetClass();
            if (targetClass == null) {
               throw new IllegalStateException("Cannot deactivate 'lookupOnStartup' without specifying a 'proxyInterface' or 'expectedType'");
            }

            Class[] ifcs = ClassUtils.getAllInterfacesForClass(targetClass, jof.beanClassLoader);
            Class[] var6 = ifcs;
            int var7 = ifcs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Class ifc = var6[var8];
               if (Modifier.isPublic(ifc.getModifiers())) {
                  proxyFactory.addInterface(ifc);
               }
            }
         }

         if (jof.exposeAccessContext) {
            proxyFactory.addAdvice(new JndiContextExposingInterceptor(jof.getJndiTemplate()));
         }

         proxyFactory.setTargetSource(targetSource);
         return proxyFactory.getProxy(jof.beanClassLoader);
      }
   }
}
