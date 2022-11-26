package org.jboss.weld.bean;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.Decorators;
import org.jboss.weld.util.reflection.Formats;

public class DecoratorImpl extends ManagedBean implements WeldDecorator {
   private DecoratedMethods decoratedMethods;
   private WeldInjectionPointAttributes delegateInjectionPoint;
   private Set delegateBindings;
   private Type delegateType;
   private Set delegateTypes;
   private Set decoratedTypes;

   public static DecoratorImpl of(BeanAttributes attributes, EnhancedAnnotatedType clazz, BeanManagerImpl beanManager) {
      return new DecoratorImpl(attributes, clazz, beanManager);
   }

   protected DecoratorImpl(BeanAttributes attributes, EnhancedAnnotatedType type, BeanManagerImpl beanManager) {
      super(attributes, type, new StringBeanIdentifier(BeanIdentifiers.forDecorator(type)), beanManager);
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
      this.initDelegateInjectionPoint();
      this.initDecoratedTypes();
      this.initDelegateBindings();
      this.initDelegateType();
   }

   protected void initDecoratedTypes() {
      Set decoratedTypes = new HashSet(this.getEnhancedAnnotated().getInterfaceClosure());
      decoratedTypes.retainAll(this.getTypes());
      decoratedTypes.remove(Serializable.class);
      this.decoratedTypes = SharedObjectCache.instance(this.beanManager).getSharedSet(decoratedTypes);
      this.decoratedMethods = new DecoratedMethods(this.beanManager, this);
   }

   protected void initDelegateInjectionPoint() {
      this.delegateInjectionPoint = Decorators.findDelegateInjectionPoint(this.getEnhancedAnnotated(), this.getInjectionPoints());
   }

   protected void initDelegateBindings() {
      this.delegateBindings = new HashSet();
      this.delegateBindings.addAll(this.delegateInjectionPoint.getQualifiers());
   }

   protected void initDelegateType() {
      this.delegateType = this.delegateInjectionPoint.getType();
      this.delegateTypes = new HashSet();
      this.delegateTypes.add(this.delegateType);
   }

   public Set getDelegateQualifiers() {
      return this.delegateBindings;
   }

   public Type getDelegateType() {
      return this.delegateType;
   }

   public Set getDecoratedTypes() {
      return this.decoratedTypes;
   }

   public WeldInjectionPointAttributes getDelegateInjectionPoint() {
      return this.delegateInjectionPoint;
   }

   public InvokableAnnotatedMethod getDecoratorMethod(Method method) {
      return this.decoratedMethods.getDecoratedMethod(method);
   }

   public String toString() {
      return "Decorator [" + this.getBeanClass().toString() + "] decorates [" + Formats.formatTypes(this.getDecoratedTypes()) + "] with delegate type [" + Formats.formatType(this.getDelegateType()) + "] and delegate qualifiers [" + Formats.formatAnnotations((Iterable)this.getDelegateQualifiers()) + "]";
   }
}
