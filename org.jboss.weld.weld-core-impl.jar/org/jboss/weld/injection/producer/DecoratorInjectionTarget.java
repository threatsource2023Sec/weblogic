package org.jboss.weld.injection.producer;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.proxy.DecoratorProxy;
import org.jboss.weld.bean.proxy.DecoratorProxyFactory;
import org.jboss.weld.bean.proxy.ProxyMethodHandler;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.bean.proxy.TargetBeanInstance;
import org.jboss.weld.injection.ConstructorInjectionPoint;
import org.jboss.weld.injection.FieldInjectionPoint;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.Decorators;

public class DecoratorInjectionTarget extends BeanInjectionTarget {
   private final WeldInjectionPointAttributes delegateInjectionPoint;
   private final Field accessibleField;

   public DecoratorInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      super(type, bean, beanManager);
      this.delegateInjectionPoint = Decorators.findDelegateInjectionPoint(type, this.getInjectionPoints());
      if (this.delegateInjectionPoint instanceof FieldInjectionPoint) {
         FieldInjectionPoint fip = (FieldInjectionPoint)this.delegateInjectionPoint;
         this.accessibleField = (Field)AccessController.doPrivileged(new GetAccessibleCopyOfMember(fip.getAnnotated().getJavaMember()));
      } else {
         this.accessibleField = null;
      }

      this.checkAbstractMethods(type);
   }

   protected Instantiator initInstantiator(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager, Set injectionPoints) {
      if (type.isAbstract()) {
         ConstructorInjectionPoint originalConstructor = InjectionPointFactory.instance().createConstructorInjectionPoint(bean, type, beanManager);
         injectionPoints.addAll(originalConstructor.getParameterInjectionPoints());
         final WeldInjectionPointAttributes delegateInjectionPoint = Decorators.findDelegateInjectionPoint(type, injectionPoints);
         return new SubclassedComponentInstantiator(type, bean, originalConstructor, beanManager) {
            protected Class createEnhancedSubclass(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl manager) {
               return (new DecoratorProxyFactory(manager.getContextId(), type.getJavaClass(), delegateInjectionPoint, bean)).getProxyClass();
            }
         };
      } else {
         DefaultInstantiator instantiator = new DefaultInstantiator(type, bean, beanManager);
         injectionPoints.addAll(instantiator.getConstructorInjectionPoint().getParameterInjectionPoints());
         return instantiator;
      }
   }

   protected void checkDelegateInjectionPoints() {
   }

   public void inject(Object instance, CreationalContext ctx) {
      super.inject(instance, ctx);
      if (this.accessibleField != null && instance instanceof DecoratorProxy) {
         Object delegate;
         try {
            delegate = this.accessibleField.get(instance);
         } catch (IllegalAccessException var5) {
            throw UtilLogger.LOG.accessErrorOnField(this.accessibleField.getName(), this.accessibleField.getDeclaringClass(), var5);
         }

         ProxyMethodHandler handler = new ProxyMethodHandler(this.beanManager.getContextId(), new TargetBeanInstance(delegate), this.getBean());
         ((ProxyObject)instance).weld_setHandler(handler);
      }

   }

   public void initializeAfterBeanDiscovery(EnhancedAnnotatedType annotatedType) {
   }

   private void checkAbstractMethods(EnhancedAnnotatedType type) {
      if (type.isAbstract()) {
         Set decoratedTypes = null;
         Bean bean = this.getBean();
         if (bean != null && bean instanceof DecoratorImpl) {
            decoratedTypes = ((DecoratorImpl)bean).getDecoratedTypes();
         }

         Decorators.checkAbstractMethods(decoratedTypes, type, this.beanManager);
      }
   }
}
