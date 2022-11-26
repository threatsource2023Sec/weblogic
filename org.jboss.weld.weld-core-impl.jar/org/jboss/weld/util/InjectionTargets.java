package org.jboss.weld.util;

import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.producer.Injector;
import org.jboss.weld.injection.producer.LifecycleCallbackInvoker;
import org.jboss.weld.injection.producer.NonProducibleInjectionTarget;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class InjectionTargets {
   private InjectionTargets() {
   }

   public static NonProducibleInjectionTarget createNonProducibleInjectionTarget(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      return createNonProducibleInjectionTarget(type, bean, (Injector)null, (LifecycleCallbackInvoker)null, beanManager);
   }

   public static NonProducibleInjectionTarget createNonProducibleInjectionTarget(EnhancedAnnotatedType type, Bean bean, Injector injector, LifecycleCallbackInvoker invoker, BeanManagerImpl beanManager) {
      try {
         if (type.isAbstract()) {
            if (type.getJavaClass().isInterface()) {
               throw BeanLogger.LOG.injectionTargetCannotBeCreatedForInterface(type);
            } else {
               BeanLogger.LOG.injectionTargetCreatedForAbstractClass(type.getJavaClass());
               return NonProducibleInjectionTarget.create(type, bean, injector, invoker, beanManager);
            }
         } else if (!Reflections.isTopLevelOrStaticNestedClass(type.getJavaClass())) {
            BeanLogger.LOG.injectionTargetCreatedForNonStaticInnerClass(type.getJavaClass());
            return NonProducibleInjectionTarget.create(type, bean, injector, invoker, beanManager);
         } else if (Beans.getBeanConstructor(type) == null) {
            if (bean != null) {
               throw BeanLogger.LOG.injectionTargetCreatedForClassWithoutAppropriateConstructorException(type.getJavaClass());
            } else {
               BeanLogger.LOG.injectionTargetCreatedForClassWithoutAppropriateConstructor(type.getJavaClass());
               return NonProducibleInjectionTarget.create(type, (Bean)null, injector, invoker, beanManager);
            }
         } else {
            return null;
         }
      } catch (Exception var6) {
         throw new WeldException(var6);
      }
   }
}
