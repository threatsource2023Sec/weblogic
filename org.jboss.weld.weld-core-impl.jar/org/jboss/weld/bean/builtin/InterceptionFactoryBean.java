package org.jboss.weld.bean.builtin;

import java.lang.reflect.ParameterizedType;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InterceptionFactory;
import org.jboss.weld.injection.InterceptionFactoryImpl;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptionFactoryBean extends AbstractStaticallyDecorableBuiltInBean {
   private static final Set TYPES = ImmutableSet.of(InterceptionFactory.class, Object.class);

   public InterceptionFactoryBean(BeanManagerImpl beanManager) {
      super(beanManager, (Class)Reflections.cast(InterceptionFactory.class));
   }

   protected InterceptionFactory newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      AnnotatedParameter annotatedParameter = (AnnotatedParameter)ip.getAnnotated();
      ParameterizedType parameterizedType = (ParameterizedType)annotatedParameter.getBaseType();
      AnnotatedType annotatedType = this.beanManager.createAnnotatedType(Reflections.getRawType(parameterizedType.getActualTypeArguments()[0]));
      return InterceptionFactoryImpl.of(this.beanManager, creationalContext, annotatedType);
   }

   public Set getTypes() {
      return TYPES;
   }

   public String toString() {
      return "Implicit Bean [" + InterceptionFactory.class.getName() + "] with qualifiers [@Default]";
   }
}
