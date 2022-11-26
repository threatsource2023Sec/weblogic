package org.jboss.weld.injection.attributes;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.injection.ParameterInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;

public class SpecialParameterInjectionPoint extends ForwardingInjectionPointAttributes implements ParameterInjectionPoint {
   private final ParameterInjectionPointAttributes attributes;

   public static ParameterInjectionPoint of(EnhancedAnnotatedParameter parameter, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      return new SpecialParameterInjectionPoint(parameter, bean, declaringComponentClass, manager);
   }

   protected SpecialParameterInjectionPoint(EnhancedAnnotatedParameter parameter, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      this.attributes = InferringParameterInjectionPointAttributes.of(parameter, bean, declaringComponentClass, manager);
   }

   public AnnotatedParameter getAnnotated() {
      return this.attributes.getAnnotated();
   }

   public Object getValueToInject(BeanManagerImpl manager, CreationalContext creationalContext) {
      throw new UnsupportedOperationException();
   }

   protected ParameterInjectionPointAttributes delegate() {
      return this.attributes;
   }
}
