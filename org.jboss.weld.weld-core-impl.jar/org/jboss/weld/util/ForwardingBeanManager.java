package org.jboss.weld.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.ProducerFactory;

public abstract class ForwardingBeanManager implements BeanManager, Serializable {
   private static final long serialVersionUID = -3116833950882475733L;

   public abstract BeanManager delegate();

   public Object getReference(Bean bean, Type beanType, CreationalContext ctx) {
      return this.delegate().getReference(bean, beanType, ctx);
   }

   public Object getInjectableReference(InjectionPoint ij, CreationalContext ctx) {
      return this.delegate().getInjectableReference(ij, ctx);
   }

   public CreationalContext createCreationalContext(Contextual contextual) {
      return this.delegate().createCreationalContext(contextual);
   }

   public Set getBeans(Type beanType, Annotation... qualifiers) {
      return this.delegate().getBeans(beanType, qualifiers);
   }

   public Set getBeans(String name) {
      return this.delegate().getBeans(name);
   }

   public Bean getPassivationCapableBean(String id) {
      return this.delegate().getPassivationCapableBean(id);
   }

   public Bean resolve(Set beans) {
      return this.delegate().resolve(beans);
   }

   public void validate(InjectionPoint injectionPoint) {
      this.delegate().validate(injectionPoint);
   }

   public void fireEvent(Object event, Annotation... qualifiers) {
      this.delegate().fireEvent(event, qualifiers);
   }

   public Set resolveObserverMethods(Object event, Annotation... qualifiers) {
      return this.delegate().resolveObserverMethods(event, qualifiers);
   }

   public List resolveDecorators(Set types, Annotation... qualifiers) {
      return this.delegate().resolveDecorators(types, qualifiers);
   }

   public List resolveInterceptors(InterceptionType type, Annotation... interceptorBindings) {
      return this.delegate().resolveInterceptors(type, interceptorBindings);
   }

   public boolean isScope(Class annotationType) {
      return this.delegate().isScope(annotationType);
   }

   public boolean isNormalScope(Class annotationType) {
      return this.delegate().isNormalScope(annotationType);
   }

   public boolean isPassivatingScope(Class annotationType) {
      return this.delegate().isPassivatingScope(annotationType);
   }

   public boolean isQualifier(Class annotationType) {
      return this.delegate().isQualifier(annotationType);
   }

   public boolean isInterceptorBinding(Class annotationType) {
      return this.delegate().isInterceptorBinding(annotationType);
   }

   public boolean isStereotype(Class annotationType) {
      return this.delegate().isStereotype(annotationType);
   }

   public Set getInterceptorBindingDefinition(Class bindingType) {
      return this.delegate().getInterceptorBindingDefinition(bindingType);
   }

   public Set getStereotypeDefinition(Class stereotype) {
      return this.delegate().getStereotypeDefinition(stereotype);
   }

   public boolean areQualifiersEquivalent(Annotation qualifier1, Annotation qualifier2) {
      return this.delegate().areQualifiersEquivalent(qualifier1, qualifier2);
   }

   public boolean areInterceptorBindingsEquivalent(Annotation interceptorBinding1, Annotation interceptorBinding2) {
      return this.delegate().areInterceptorBindingsEquivalent(interceptorBinding1, interceptorBinding2);
   }

   public int getQualifierHashCode(Annotation qualifier) {
      return this.delegate().getQualifierHashCode(qualifier);
   }

   public int getInterceptorBindingHashCode(Annotation interceptorBinding) {
      return this.delegate().getInterceptorBindingHashCode(interceptorBinding);
   }

   public Context getContext(Class scopeType) {
      return this.delegate().getContext(scopeType);
   }

   public ELResolver getELResolver() {
      return this.delegate().getELResolver();
   }

   public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory) {
      return this.delegate().wrapExpressionFactory(expressionFactory);
   }

   public AnnotatedType createAnnotatedType(Class type) {
      return this.delegate().createAnnotatedType(type);
   }

   public InjectionTarget createInjectionTarget(AnnotatedType type) {
      return this.delegate().createInjectionTarget(type);
   }

   public BeanAttributes createBeanAttributes(AnnotatedType type) {
      return this.delegate().createBeanAttributes(type);
   }

   public BeanAttributes createBeanAttributes(AnnotatedMember type) {
      return this.delegate().createBeanAttributes(type);
   }

   public Bean createBean(BeanAttributes attributes, Class beanClass, InjectionTargetFactory injectionTargetFactory) {
      return this.delegate().createBean(attributes, beanClass, injectionTargetFactory);
   }

   public Bean createBean(BeanAttributes attributes, Class beanClass, ProducerFactory producerFactory) {
      return this.delegate().createBean(attributes, beanClass, producerFactory);
   }

   public InjectionPoint createInjectionPoint(AnnotatedField field) {
      return this.delegate().createInjectionPoint(field);
   }

   public InjectionPoint createInjectionPoint(AnnotatedParameter parameter) {
      return this.delegate().createInjectionPoint(parameter);
   }

   public Extension getExtension(Class extensionClass) {
      return this.delegate().getExtension(extensionClass);
   }

   public InterceptionFactory createInterceptionFactory(CreationalContext ctx, Class clazz) {
      return this.delegate().createInterceptionFactory(ctx, clazz);
   }

   public Event getEvent() {
      return this.delegate().getEvent();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof ForwardingBeanManager) {
         ForwardingBeanManager that = (ForwardingBeanManager)obj;
         return this.delegate().equals(that.delegate());
      } else {
         return this.delegate().equals(obj);
      }
   }

   public String toString() {
      return this.delegate().toString();
   }

   public InjectionTargetFactory getInjectionTargetFactory(AnnotatedType annotatedType) {
      return this.delegate().getInjectionTargetFactory(annotatedType);
   }

   public ProducerFactory getProducerFactory(AnnotatedField field, Bean declaringBean) {
      return this.delegate().getProducerFactory(field, declaringBean);
   }

   public ProducerFactory getProducerFactory(AnnotatedMethod method, Bean declaringBean) {
      return this.delegate().getProducerFactory(method, declaringBean);
   }

   public Instance createInstance() {
      return this.delegate().createInstance();
   }
}
