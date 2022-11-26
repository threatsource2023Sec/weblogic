package org.jboss.weld.injection.producer;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.injection.InjectionContextImpl;
import org.jboss.weld.injection.ResourceInjectionFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.collections.ImmutableList;

public class ResourceInjector extends DefaultInjector {
   private List resourceInjectionsHierarchy;

   public static ResourceInjector of(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      return new ResourceInjector(type, bean, beanManager);
   }

   protected ResourceInjector(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl beanManager) {
      super(type, bean, beanManager);
      ResourceInjectionFactory factory = (ResourceInjectionFactory)beanManager.getServices().get(ResourceInjectionFactory.class);
      this.resourceInjectionsHierarchy = ImmutableList.copyOf((Collection)factory.getResourceInjections(bean, type, beanManager));
   }

   public void inject(final Object instance, final CreationalContext ctx, final BeanManagerImpl manager, SlimAnnotatedType type, InjectionTarget injectionTarget) {
      (new InjectionContextImpl(manager, injectionTarget, type, instance) {
         public void proceed() {
            Beans.injectEEFields(ResourceInjector.this.resourceInjectionsHierarchy, instance, ctx);
            Beans.injectFieldsAndInitializers(instance, ctx, manager, ResourceInjector.this.getInjectableFields(), ResourceInjector.this.getInitializerMethods());
         }
      }).run();
   }
}
