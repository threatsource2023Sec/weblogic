package org.jboss.weld.injection.producer;

import java.util.List;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.DecorationHelper;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.bean.proxy.TargetBeanInstance;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.ContextualStore;

public abstract class AbstractDecoratorApplyingInstantiator extends ForwardingInstantiator {
   private final Bean bean;
   private final Class proxyClass;
   private final List decorators;

   public AbstractDecoratorApplyingInstantiator(String contextId, Instantiator delegate, Bean bean, List decorators, Class implementationClass) {
      super(delegate);
      this.bean = bean;
      this.decorators = decorators;
      ProxyFactory factory = new ProxyFactory(contextId, implementationClass, bean.getTypes(), bean, true);
      this.proxyClass = factory.getProxyClass();
   }

   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      InjectionPoint originalInjectionPoint = (InjectionPoint)((CurrentInjectionPoint)manager.getServices().get(CurrentInjectionPoint.class)).peek();
      return this.applyDecorators(this.delegate().newInstance(ctx, manager), ctx, originalInjectionPoint, manager);
   }

   protected abstract Object applyDecorators(Object var1, CreationalContext var2, InjectionPoint var3, BeanManagerImpl var4);

   protected Object getOuterDelegate(Object instance, CreationalContext creationalContext, InjectionPoint originalInjectionPoint, BeanManagerImpl manager) {
      TargetBeanInstance beanInstance = new TargetBeanInstance(this.bean, instance);
      DecorationHelper decorationHelper = new DecorationHelper(beanInstance, this.bean, this.proxyClass, manager, (ContextualStore)manager.getServices().get(ContextualStore.class), this.decorators);
      DecorationHelper.push(decorationHelper);

      Object var8;
      try {
         Object outerDelegate = decorationHelper.getNextDelegate(originalInjectionPoint, creationalContext);
         if (outerDelegate == null) {
            throw new WeldException(BeanLogger.LOG.proxyInstantiationFailed(this));
         }

         var8 = outerDelegate;
      } finally {
         DecorationHelper.pop();
      }

      return var8;
   }

   protected void registerOuterDecorator(ProxyObject instance, Object outerDelegate) {
      CombinedInterceptorAndDecoratorStackMethodHandler wrapperMethodHandler = (CombinedInterceptorAndDecoratorStackMethodHandler)instance.weld_getHandler();
      wrapperMethodHandler.setOuterDecorator(outerDelegate);
   }

   public Bean getBean() {
      return this.bean;
   }

   public Class getProxyClass() {
      return this.proxyClass;
   }

   public List getDecorators() {
      return this.decorators;
   }

   public boolean hasDecoratorSupport() {
      return true;
   }
}
