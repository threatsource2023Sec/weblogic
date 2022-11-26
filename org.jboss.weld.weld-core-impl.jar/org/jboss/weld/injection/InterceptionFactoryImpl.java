package org.jboss.weld.injection;

import java.security.AccessController;
import java.util.Optional;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.bean.proxy.InterceptedProxyMethodHandler;
import org.jboss.weld.bean.proxy.InterceptionFactoryDataCache;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.interceptor.proxy.InterceptionContext;
import org.jboss.weld.interceptor.proxy.InterceptorMethodHandler;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Proxies;

public class InterceptionFactoryImpl implements InterceptionFactory {
   private final BeanManagerImpl beanManager;
   private final CreationalContext creationalContext;
   private final AnnotatedType annotatedType;
   private AnnotatedTypeConfiguratorImpl configurator;
   private boolean ignoreFinalMethods;
   private boolean used;

   public static InterceptionFactoryImpl of(BeanManagerImpl beanManager, CreationalContext creationalContext, AnnotatedType annotatedType) {
      return new InterceptionFactoryImpl(beanManager, creationalContext, annotatedType);
   }

   private InterceptionFactoryImpl(BeanManagerImpl beanManager, CreationalContext creationalContext, AnnotatedType annotatedType) {
      this.beanManager = beanManager;
      this.creationalContext = creationalContext;
      this.annotatedType = annotatedType;
      this.ignoreFinalMethods = false;
      this.used = false;
   }

   public InterceptionFactory ignoreFinalMethods() {
      InterceptorLogger.LOG.interceptionFactoryIgnoreFinalMethodsInvoked(this.annotatedType.getJavaClass().getSimpleName());
      this.ignoreFinalMethods = true;
      return this;
   }

   public AnnotatedTypeConfigurator configure() {
      InterceptorLogger.LOG.interceptionFactoryConfigureInvoked(this.annotatedType.getJavaClass().getSimpleName());
      if (this.configurator == null) {
         this.configurator = new AnnotatedTypeConfiguratorImpl(this.annotatedType);
      }

      return this.configurator;
   }

   public Object createInterceptedInstance(Object instance) {
      if (this.used) {
         throw InterceptorLogger.LOG.interceptionFactoryNotReusable();
      } else if (instance instanceof ProxyObject) {
         InterceptorLogger.LOG.interceptionFactoryInternalContainerConstruct(instance.getClass());
         return instance;
      } else {
         UnproxyableResolutionException exception = Proxies.getUnproxyableTypeException(this.annotatedType.getBaseType(), (Bean)null, this.beanManager.getServices(), this.ignoreFinalMethods);
         if (exception != null) {
            throw exception;
         } else {
            this.used = true;
            Optional cached = ((InterceptionFactoryDataCache)this.beanManager.getServices().get(InterceptionFactoryDataCache.class)).getInterceptionFactoryData(this.configurator != null ? this.configurator.complete() : this.annotatedType);
            if (!cached.isPresent()) {
               InterceptorLogger.LOG.interceptionFactoryNotRequired(this.annotatedType.getJavaClass().getSimpleName());
               return instance;
            } else {
               InterceptionFactoryDataCache.InterceptionFactoryData data = (InterceptionFactoryDataCache.InterceptionFactoryData)cached.get();
               InterceptedProxyMethodHandler methodHandler = new InterceptedProxyMethodHandler(instance);
               methodHandler.setInterceptorMethodHandler(new InterceptorMethodHandler(InterceptionContext.forNonConstructorInterception(data.getInterceptionModel(), this.creationalContext, this.beanManager, data.getSlimAnnotatedType())));
               Object proxy = System.getSecurityManager() == null ? data.getInterceptedProxyFactory().run() : AccessController.doPrivileged(data.getInterceptedProxyFactory());
               ((ProxyObject)proxy).weld_setHandler(methodHandler);
               return proxy;
            }
         }
      }
   }
}
