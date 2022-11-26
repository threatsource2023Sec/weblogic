package org.jboss.weld.injection.producer;

import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.injection.InjectionContextImpl;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.InjectionPoints;

public class DefaultInjector implements Injector {
   private final List injectableFields;
   private final List initializerMethods;

   public static DefaultInjector of(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      return new DefaultInjector(type, bean, beanManager);
   }

   public DefaultInjector(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      this.injectableFields = InjectionPointFactory.instance().getFieldInjectionPoints(bean, type, beanManager);
      this.initializerMethods = BeanMethods.getInitializerMethods(bean, type, beanManager);
   }

   public void registerInjectionPoints(Set injectionPoints) {
      injectionPoints.addAll(InjectionPoints.flattenInjectionPoints(this.injectableFields));
      injectionPoints.addAll(InjectionPoints.flattenParameterInjectionPoints(this.initializerMethods));
   }

   public void inject(final Object instance, final CreationalContext ctx, final BeanManagerImpl manager, SlimAnnotatedType type, InjectionTarget injectionTarget) {
      (new InjectionContextImpl(manager, injectionTarget, type, instance) {
         public void proceed() {
            Beans.injectFieldsAndInitializers(instance, ctx, manager, DefaultInjector.this.injectableFields, DefaultInjector.this.initializerMethods);
         }
      }).run();
   }

   public List getInjectableFields() {
      return this.injectableFields;
   }

   public List getInitializerMethods() {
      return this.initializerMethods;
   }
}
