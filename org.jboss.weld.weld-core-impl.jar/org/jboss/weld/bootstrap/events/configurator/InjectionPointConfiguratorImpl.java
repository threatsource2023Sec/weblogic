package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.configurator.InjectionPointConfigurator;
import org.jboss.weld.util.Preconditions;

public class InjectionPointConfiguratorImpl implements InjectionPointConfigurator, Configurator {
   private Type requiredType;
   private final Set qualifiers;
   private Bean bean;
   private boolean isDelegate;
   private boolean isTransient;
   private Member member;
   private Annotated annotated;

   public InjectionPointConfiguratorImpl() {
      this.qualifiers = new HashSet();
   }

   public InjectionPointConfiguratorImpl(InjectionPoint injectionPoint) {
      this();
      this.read(injectionPoint);
   }

   public InjectionPointConfigurator read(InjectionPoint injectionPoint) {
      this.bean = injectionPoint.getBean();
      this.type(injectionPoint.getType());
      this.qualifiers(injectionPoint.getQualifiers());
      this.delegate(injectionPoint.isDelegate());
      this.transientField(injectionPoint.isTransient());
      this.member(injectionPoint.getMember());
      this.annotated(injectionPoint.getAnnotated());
      return this;
   }

   public InjectionPointConfigurator type(Type type) {
      Preconditions.checkArgumentNotNull(type);
      this.requiredType = type;
      return this;
   }

   public InjectionPointConfigurator addQualifier(Annotation qualifier) {
      Preconditions.checkArgumentNotNull(qualifier);
      this.qualifiers.remove(Literal.INSTANCE);
      this.qualifiers.add(qualifier);
      return this;
   }

   public InjectionPointConfigurator addQualifiers(Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.qualifiers.remove(Literal.INSTANCE);
      Collections.addAll(this.qualifiers, qualifiers);
      return this;
   }

   public InjectionPointConfigurator addQualifiers(Set qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      this.qualifiers.remove(Literal.INSTANCE);
      this.qualifiers.addAll(qualifiers);
      return this;
   }

   public InjectionPointConfigurator qualifiers(Annotation... qualifiers) {
      this.qualifiers.clear();
      return this.addQualifiers(qualifiers);
   }

   public InjectionPointConfigurator qualifiers(Set qualifiers) {
      this.qualifiers.clear();
      return this.addQualifiers(qualifiers);
   }

   public InjectionPointConfigurator delegate(boolean delegate) {
      this.isDelegate = delegate;
      return this;
   }

   public InjectionPointConfigurator transientField(boolean trans) {
      this.isTransient = trans;
      return this;
   }

   public InjectionPointConfigurator member(Member member) {
      this.member = member;
      return this;
   }

   public InjectionPointConfigurator annotated(Annotated annotated) {
      this.annotated = annotated;
      return this;
   }

   public InjectionPoint complete() {
      return new ImmutableInjectionPoint(this);
   }

   static class ImmutableInjectionPoint implements InjectionPoint {
      private final Type requiredType;
      private final Set qualifiers;
      private final Bean bean;
      private final boolean isDelegate;
      private final boolean isTransient;
      private final Member member;
      private final Annotated annotated;

      private ImmutableInjectionPoint(InjectionPointConfiguratorImpl configurator) {
         this.requiredType = configurator.requiredType;
         this.qualifiers = configurator.qualifiers;
         this.bean = configurator.bean;
         this.isDelegate = configurator.isDelegate;
         this.isTransient = configurator.isTransient;
         this.member = configurator.member;
         this.annotated = configurator.annotated;
      }

      public Type getType() {
         return this.requiredType;
      }

      public Set getQualifiers() {
         return this.qualifiers;
      }

      public Bean getBean() {
         return this.bean;
      }

      public Member getMember() {
         return this.member;
      }

      public Annotated getAnnotated() {
         return this.annotated;
      }

      public boolean isDelegate() {
         return this.isDelegate;
      }

      public boolean isTransient() {
         return this.isTransient;
      }

      public String toString() {
         return "InjectionPoint with type=" + this.requiredType + ", qualifiers=" + this.qualifiers + ", delegate=" + this.isDelegate + ", transient=" + this.isTransient + ".";
      }

      // $FF: synthetic method
      ImmutableInjectionPoint(InjectionPointConfiguratorImpl x0, Object x1) {
         this(x0);
      }
   }
}
