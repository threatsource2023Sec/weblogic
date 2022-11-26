package org.jboss.weld.injection;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class MethodInjectionPoint extends AbstractCallableInjectionPoint {
   protected MethodInjectionPointType type;

   protected MethodInjectionPoint(MethodInjectionPointType methodInjectionPointType, EnhancedAnnotatedCallable callable, Bean declaringBean, Class declaringComponentClass, InjectionPointFactory factory, BeanManagerImpl manager) {
      super(callable, declaringBean, declaringComponentClass, MethodInjectionPoint.MethodInjectionPointType.OBSERVER.equals(methodInjectionPointType) || MethodInjectionPoint.MethodInjectionPointType.DISPOSER.equals(methodInjectionPointType), factory, manager);
      this.type = methodInjectionPointType;
   }

   public abstract Object invoke(Object var1, Object var2, BeanManagerImpl var3, CreationalContext var4, Class var5);

   abstract Object invoke(Object var1, Object[] var2, Class var3);

   public abstract AnnotatedMethod getAnnotated();

   public static enum MethodInjectionPointType {
      INITIALIZER,
      PRODUCER,
      DISPOSER,
      OBSERVER;
   }
}
