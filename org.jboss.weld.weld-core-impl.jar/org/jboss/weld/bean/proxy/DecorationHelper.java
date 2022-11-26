package org.jboss.weld.bean.proxy;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.contexts.SerializableContextualInstanceImpl;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.reflection.Reflections;

public class DecorationHelper implements PrivilegedAction {
   private static ThreadLocal helperStackHolder = new ThreadLocal() {
      protected Stack initialValue() {
         return new Stack();
      }
   };
   private final Class proxyClassForDecorator;
   private final TargetBeanInstance targetBeanInstance;
   private Object originalInstance;
   private Object previousDelegate;
   private int counter;
   private final BeanManagerImpl beanManager;
   private final ContextualStore contextualStore;
   private final Bean bean;
   private final ProxyInstantiator instantiator;
   List decorators;

   public DecorationHelper(TargetBeanInstance originalInstance, Bean bean, Class proxyClassForDecorator, BeanManagerImpl beanManager, ContextualStore contextualStore, List decorators) {
      this.originalInstance = Reflections.cast(originalInstance.getInstance());
      this.targetBeanInstance = originalInstance;
      this.beanManager = beanManager;
      this.contextualStore = contextualStore;
      this.decorators = new LinkedList(decorators);
      this.proxyClassForDecorator = proxyClassForDecorator;
      this.bean = bean;
      this.instantiator = (ProxyInstantiator)beanManager.getServices().get(ProxyInstantiator.class);
      this.counter = 0;
   }

   public static void push(DecorationHelper helper) {
      ((Stack)helperStackHolder.get()).push(helper);
   }

   public static DecorationHelper peek() {
      return (DecorationHelper)((Stack)helperStackHolder.get()).peek();
   }

   public static void pop() {
      Stack stack = (Stack)helperStackHolder.get();
      stack.pop();
      if (stack.isEmpty()) {
         helperStackHolder.remove();
      }

   }

   public Object getNextDelegate(InjectionPoint injectionPoint, CreationalContext creationalContext) {
      if (this.counter == this.decorators.size()) {
         this.previousDelegate = this.originalInstance;
         return this.originalInstance;
      } else {
         Object proxy = this.createProxy(injectionPoint, creationalContext);
         this.previousDelegate = proxy;
         return proxy;
      }
   }

   private Object createProxy(InjectionPoint injectionPoint, CreationalContext creationalContext) {
      Object proxy = System.getSecurityManager() == null ? this.run() : AccessController.doPrivileged(this);
      TargetBeanInstance newTargetBeanInstance = new TargetBeanInstance(this.targetBeanInstance);
      Decorator decorator = (Decorator)Reflections.cast(this.decorators.get(this.counter++));
      DecoratorProxyMethodHandler methodHandler = this.createMethodHandler(injectionPoint, creationalContext, decorator);
      newTargetBeanInstance.setInterceptorsHandler(methodHandler);
      ProxyFactory.setBeanInstance(this.beanManager.getContextId(), proxy, newTargetBeanInstance, this.bean);
      return proxy;
   }

   public DecoratorProxyMethodHandler createMethodHandler(InjectionPoint injectionPoint, CreationalContext creationalContext, Decorator decorator) {
      Object decoratorInstance = this.beanManager.getInjectableReference(injectionPoint, decorator, creationalContext);

      assert this.previousDelegate != null : "previousDelegate should have been set when calling beanManager.getReference(), but it wasn't!";

      SerializableContextualInstanceImpl serializableContextualInstance = new SerializableContextualInstanceImpl(decorator, decoratorInstance, (CreationalContext)null, this.contextualStore);
      return new DecoratorProxyMethodHandler(serializableContextualInstance, this.previousDelegate);
   }

   public Object run() {
      try {
         return this.instantiator.newInstance(this.proxyClassForDecorator);
      } catch (InstantiationException var2) {
         throw new DefinitionException(BeanLogger.LOG.proxyInstantiationFailed(this), var2.getCause());
      } catch (IllegalAccessException var3) {
         throw new DefinitionException(BeanLogger.LOG.proxyInstantiationBeanAccessFailed(this), var3.getCause());
      }
   }
}
