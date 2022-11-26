package org.glassfish.soteria.cdi;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;

public class CdiDecorator extends CdiProducer implements Decorator {
   private Class decorator;
   private Type delegateType;
   private Set decoratedTypes;
   private BeanManager beanManager;
   private InjectionPoint decoratorInjectionPoint;
   private Set injectionPoints;
   private BiFunction create;

   public Object create(CreationalContext creationalContext) {
      return this.create.apply(creationalContext, this.beanManager.getInjectableReference(this.decoratorInjectionPoint, creationalContext));
   }

   public Set getInjectionPoints() {
      return this.injectionPoints;
   }

   public Type getDelegateType() {
      return this.delegateType;
   }

   public Set getDecoratedTypes() {
      return this.decoratedTypes;
   }

   public Set getDelegateQualifiers() {
      return Collections.emptySet();
   }

   public CdiDecorator decorator(Class decorator) {
      this.decorator = decorator;
      this.beanClassAndType(decorator);
      return this;
   }

   public CdiDecorator delegateAndDecoratedType(Type type) {
      this.delegateType = type;
      this.decoratedTypes = asSet(new Type[]{type});
      return this;
   }

   public CdiProducer create(BeanManager beanManager, BiFunction create) {
      this.decoratorInjectionPoint = new DecoratorInjectionPoint(this.getDelegateType(), (AnnotatedField)beanManager.createAnnotatedType(this.decorator).getFields().iterator().next(), this);
      this.injectionPoints = Collections.singleton(this.decoratorInjectionPoint);
      this.beanManager = beanManager;
      this.create = create;
      return this;
   }

   private static class DecoratorInjectionPoint implements InjectionPoint {
      private final Set qualifiers = Collections.singleton(new DefaultAnnotationLiteral());
      private final Type type;
      private final AnnotatedField annotatedField;
      private final Bean bean;

      public DecoratorInjectionPoint(Type type, AnnotatedField annotatedField, Bean bean) {
         this.type = type;
         this.annotatedField = annotatedField;
         this.bean = bean;
      }

      public Type getType() {
         return this.type;
      }

      public Set getQualifiers() {
         return this.qualifiers;
      }

      public Bean getBean() {
         return this.bean;
      }

      public Member getMember() {
         return this.annotatedField.getJavaMember();
      }

      public Annotated getAnnotated() {
         return this.annotatedField;
      }

      public boolean isDelegate() {
         return true;
      }

      public boolean isTransient() {
         return false;
      }
   }
}
