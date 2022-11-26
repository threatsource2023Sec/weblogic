package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import com.bea.core.repackaged.springframework.aop.target.SingletonTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBeanNotInitializedException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public abstract class AbstractSingletonProxyFactoryBean extends ProxyConfig implements FactoryBean, BeanClassLoaderAware, InitializingBean {
   @Nullable
   private Object target;
   @Nullable
   private Class[] proxyInterfaces;
   @Nullable
   private Object[] preInterceptors;
   @Nullable
   private Object[] postInterceptors;
   private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
   @Nullable
   private transient ClassLoader proxyClassLoader;
   @Nullable
   private Object proxy;

   public void setTarget(Object target) {
      this.target = target;
   }

   public void setProxyInterfaces(Class[] proxyInterfaces) {
      this.proxyInterfaces = proxyInterfaces;
   }

   public void setPreInterceptors(Object[] preInterceptors) {
      this.preInterceptors = preInterceptors;
   }

   public void setPostInterceptors(Object[] postInterceptors) {
      this.postInterceptors = postInterceptors;
   }

   public void setAdvisorAdapterRegistry(AdvisorAdapterRegistry advisorAdapterRegistry) {
      this.advisorAdapterRegistry = advisorAdapterRegistry;
   }

   public void setProxyClassLoader(ClassLoader classLoader) {
      this.proxyClassLoader = classLoader;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      if (this.proxyClassLoader == null) {
         this.proxyClassLoader = classLoader;
      }

   }

   public void afterPropertiesSet() {
      if (this.target == null) {
         throw new IllegalArgumentException("Property 'target' is required");
      } else if (this.target instanceof String) {
         throw new IllegalArgumentException("'target' needs to be a bean reference, not a bean name as value");
      } else {
         if (this.proxyClassLoader == null) {
            this.proxyClassLoader = ClassUtils.getDefaultClassLoader();
         }

         ProxyFactory proxyFactory = new ProxyFactory();
         Object[] var2;
         int var3;
         int var4;
         Object interceptor;
         if (this.preInterceptors != null) {
            var2 = this.preInterceptors;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               interceptor = var2[var4];
               proxyFactory.addAdvisor(this.advisorAdapterRegistry.wrap(interceptor));
            }
         }

         proxyFactory.addAdvisor(this.advisorAdapterRegistry.wrap(this.createMainInterceptor()));
         if (this.postInterceptors != null) {
            var2 = this.postInterceptors;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
               interceptor = var2[var4];
               proxyFactory.addAdvisor(this.advisorAdapterRegistry.wrap(interceptor));
            }
         }

         proxyFactory.copyFrom(this);
         TargetSource targetSource = this.createTargetSource(this.target);
         proxyFactory.setTargetSource(targetSource);
         if (this.proxyInterfaces != null) {
            proxyFactory.setInterfaces(this.proxyInterfaces);
         } else if (!this.isProxyTargetClass()) {
            Class targetClass = targetSource.getTargetClass();
            if (targetClass != null) {
               proxyFactory.setInterfaces(ClassUtils.getAllInterfacesForClass(targetClass, this.proxyClassLoader));
            }
         }

         this.postProcessProxyFactory(proxyFactory);
         this.proxy = proxyFactory.getProxy(this.proxyClassLoader);
      }
   }

   protected TargetSource createTargetSource(Object target) {
      return (TargetSource)(target instanceof TargetSource ? (TargetSource)target : new SingletonTargetSource(target));
   }

   protected void postProcessProxyFactory(ProxyFactory proxyFactory) {
   }

   public Object getObject() {
      if (this.proxy == null) {
         throw new FactoryBeanNotInitializedException();
      } else {
         return this.proxy;
      }
   }

   @Nullable
   public Class getObjectType() {
      if (this.proxy != null) {
         return this.proxy.getClass();
      } else if (this.proxyInterfaces != null && this.proxyInterfaces.length == 1) {
         return this.proxyInterfaces[0];
      } else if (this.target instanceof TargetSource) {
         return ((TargetSource)this.target).getTargetClass();
      } else {
         return this.target != null ? this.target.getClass() : null;
      }
   }

   public final boolean isSingleton() {
      return true;
   }

   protected abstract Object createMainInterceptor();
}
