package org.jboss.weld.bean;

import java.lang.reflect.Method;
import javax.enterprise.inject.spi.Decorator;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.reflection.Reflections;

public class CustomDecoratorWrapper extends ForwardingDecorator implements WeldDecorator {
   private final Decorator delegate;
   private final EnhancedAnnotatedType weldClass;
   private final DecoratedMethods decoratedMethods;

   public static CustomDecoratorWrapper of(Decorator delegate, BeanManagerImpl beanManager) {
      return new CustomDecoratorWrapper(delegate, beanManager);
   }

   private CustomDecoratorWrapper(Decorator delegate, BeanManagerImpl beanManager) {
      this.delegate = delegate;
      this.weldClass = ((ClassTransformer)beanManager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType((Class)Reflections.cast(delegate.getBeanClass()), beanManager.getId());
      this.decoratedMethods = new DecoratedMethods(beanManager, this);
   }

   public Decorator delegate() {
      return this.delegate;
   }

   public EnhancedAnnotatedType getEnhancedAnnotated() {
      return this.weldClass;
   }

   public InvokableAnnotatedMethod getDecoratorMethod(Method method) {
      return this.decoratedMethods.getDecoratedMethod(method);
   }
}
