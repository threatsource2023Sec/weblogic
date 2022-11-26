package org.jboss.weld.injection.attributes;

import java.lang.reflect.Member;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.reflection.Reflections;

public class InferringParameterInjectionPointAttributes extends AbstractInferringInjectionPointAttributes implements ParameterInjectionPointAttributes {
   private static final long serialVersionUID = 1237037554422642608L;
   private final AnnotatedParameter parameter;

   public static InferringParameterInjectionPointAttributes of(EnhancedAnnotatedParameter parameter, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      return new InferringParameterInjectionPointAttributes(parameter, bean, declaringComponentClass, manager);
   }

   protected InferringParameterInjectionPointAttributes(EnhancedAnnotatedParameter parameter, Bean bean, Class declaringComponentClass, BeanManagerImpl manager) {
      super(parameter, manager.getContextId(), bean, SharedObjectCache.instance(manager).getSharedSet(parameter.getQualifiers()), declaringComponentClass);
      this.parameter = parameter.slim();
   }

   public Member getMember() {
      return this.getAnnotated().getDeclaringCallable().getJavaMember();
   }

   public AnnotatedParameter getAnnotated() {
      return this.parameter;
   }

   public int hashCode() {
      return this.getAnnotated().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof InferringParameterInjectionPointAttributes) {
         AnnotatedParameter parameter = ((InferringParameterInjectionPointAttributes)Reflections.cast(obj)).getAnnotated();
         return AnnotatedTypes.compareAnnotatedParameters(this.getAnnotated(), parameter);
      } else {
         return false;
      }
   }
}
