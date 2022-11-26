package org.jboss.weld.injection.producer;

import java.util.List;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class SubclassDecoratorApplyingInstantiator extends AbstractDecoratorApplyingInstantiator {
   public SubclassDecoratorApplyingInstantiator(String contextId, Instantiator delegate, Bean bean, List decorators, Class implementationClass) {
      super(contextId, delegate, bean, decorators, implementationClass);
   }

   public SubclassDecoratorApplyingInstantiator(String contextId, Instantiator delegate, Bean bean, List decorators) {
      super(contextId, delegate, bean, decorators, (Class)Reflections.cast(bean.getBeanClass()));
   }

   protected Object applyDecorators(Object instance, CreationalContext creationalContext, InjectionPoint originalInjectionPoint, BeanManagerImpl manager) {
      Object outerDelegate = this.getOuterDelegate(instance, creationalContext, originalInjectionPoint, manager);
      this.registerOuterDecorator((ProxyObject)instance, outerDelegate);
      return instance;
   }
}
