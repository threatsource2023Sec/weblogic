package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.util.annotated.ForwardingAnnotatedConstructor;
import org.jboss.weld.util.annotated.ForwardingAnnotatedField;
import org.jboss.weld.util.annotated.ForwardingAnnotatedMethod;
import org.jboss.weld.util.annotated.ForwardingAnnotatedParameter;
import org.jboss.weld.util.annotated.ForwardingAnnotatedType;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

class AnnotatedTypeBuilderImpl {
   private final AnnotatedTypeConfiguratorImpl configurator;

   public AnnotatedTypeBuilderImpl(AnnotatedTypeConfiguratorImpl configurator) {
      this.configurator = configurator;
   }

   public AnnotatedTypeConfigurator configure() {
      return this.configurator;
   }

   public AnnotatedType build() {
      return new AnnotatedTypeImpl(this.configurator);
   }

   private static class Annotations {
      private final Set annotations;
      private final Map annotationsMap;

      private Annotations(AnnotatedConfigurator configurator) {
         this.annotations = ImmutableSet.copyOf(configurator.getAnnotations());
         this.annotationsMap = (Map)this.annotations.stream().collect(ImmutableMap.collector((a) -> {
            return a.annotationType();
         }, Function.identity()));
      }

      Set get() {
         return this.annotations;
      }

      boolean isAnnotationPresent(Class annotationType) {
         return this.annotationsMap.containsKey(annotationType);
      }

      Annotation getAnnotation(Class annotationType) {
         return (Annotation)this.annotationsMap.get(annotationType);
      }

      // $FF: synthetic method
      Annotations(AnnotatedConfigurator x0, Object x1) {
         this(x0);
      }
   }

   static class AnnotatedParameterImpl extends ForwardingAnnotatedParameter {
      private final Annotations annotations;
      private final AnnotatedParameter delegate;

      public AnnotatedParameterImpl(AnnotatedParameterConfiguratorImpl configurator) {
         this.delegate = (AnnotatedParameter)configurator.getAnnotated();
         this.annotations = new Annotations(configurator);
      }

      protected AnnotatedParameter delegate() {
         return this.delegate;
      }

      public Annotation getAnnotation(Class annotationType) {
         return this.annotations.getAnnotation(annotationType);
      }

      public Set getAnnotations() {
         return this.annotations.get();
      }

      public boolean isAnnotationPresent(Class annotationType) {
         return this.annotations.isAnnotationPresent(annotationType);
      }

      public String toString() {
         return Formats.formatAnnotatedParameter(this.delegate);
      }
   }

   static class AnnotatedConstructorImpl extends ForwardingAnnotatedConstructor {
      private final Annotations annotations;
      private final AnnotatedConstructor delegate;
      private final List parameters;

      AnnotatedConstructorImpl(AnnotatedConstructorConfiguratorImpl configurator) {
         this.delegate = (AnnotatedConstructor)configurator.getAnnotated();
         this.annotations = new Annotations(configurator);
         this.parameters = (List)configurator.getParams().stream().map((c) -> {
            return new AnnotatedParameterImpl(c);
         }).collect(ImmutableList.collector());
      }

      public AnnotatedConstructor delegate() {
         return this.delegate;
      }

      public List getParameters() {
         return this.parameters;
      }

      public Annotation getAnnotation(Class annotationType) {
         return this.annotations.getAnnotation(annotationType);
      }

      public Set getAnnotations() {
         return this.annotations.get();
      }

      public boolean isAnnotationPresent(Class annotationType) {
         return this.annotations.isAnnotationPresent(annotationType);
      }

      public String toString() {
         return Formats.formatAnnotatedConstructor(this.delegate);
      }
   }

   static class AnnotatedFieldImpl extends ForwardingAnnotatedField {
      private final Annotations annotations;
      private final AnnotatedField delegate;

      AnnotatedFieldImpl(AnnotatedFieldConfiguratorImpl configurator) {
         this.delegate = (AnnotatedField)configurator.getAnnotated();
         this.annotations = new Annotations(configurator);
      }

      public AnnotatedField delegate() {
         return this.delegate;
      }

      public Annotation getAnnotation(Class annotationType) {
         return this.annotations.getAnnotation(annotationType);
      }

      public Set getAnnotations() {
         return this.annotations.get();
      }

      public boolean isAnnotationPresent(Class annotationType) {
         return this.annotations.isAnnotationPresent(annotationType);
      }

      public String toString() {
         return Formats.formatAnnotatedField(this.delegate);
      }
   }

   static class AnnotatedMethodImpl extends ForwardingAnnotatedMethod {
      private final Annotations annotations;
      private final AnnotatedMethod delegate;
      private final List parameters;

      AnnotatedMethodImpl(AnnotatedMethodConfiguratorImpl configurator) {
         this.delegate = (AnnotatedMethod)configurator.getAnnotated();
         this.annotations = new Annotations(configurator);
         this.parameters = (List)configurator.getParams().stream().map((c) -> {
            return new AnnotatedParameterImpl(c);
         }).collect(ImmutableList.collector());
      }

      public AnnotatedMethod delegate() {
         return this.delegate;
      }

      public List getParameters() {
         return this.parameters;
      }

      public Annotation getAnnotation(Class annotationType) {
         return this.annotations.getAnnotation(annotationType);
      }

      public Set getAnnotations() {
         return this.annotations.get();
      }

      public boolean isAnnotationPresent(Class annotationType) {
         return this.annotations.isAnnotationPresent(annotationType);
      }

      public String toString() {
         return Formats.formatAnnotatedMethod(this.delegate);
      }
   }

   static class AnnotatedTypeImpl extends ForwardingAnnotatedType {
      private final Annotations annotations;
      private final AnnotatedType delegate;
      private final Set methods;
      private final Set fields;
      private final Set constructors;

      public AnnotatedTypeImpl(AnnotatedTypeConfiguratorImpl configurator) {
         this.delegate = (AnnotatedType)configurator.getAnnotated();
         this.annotations = new Annotations(configurator);
         this.methods = (Set)configurator.getMethods().stream().map((m) -> {
            return new AnnotatedMethodImpl(m);
         }).collect(ImmutableSet.collector());
         this.fields = (Set)configurator.getFields().stream().map((f) -> {
            return new AnnotatedFieldImpl(f);
         }).collect(ImmutableSet.collector());
         this.constructors = (Set)configurator.getConstructors().stream().map((c) -> {
            return new AnnotatedConstructorImpl(c);
         }).collect(ImmutableSet.collector());
      }

      public AnnotatedType delegate() {
         return this.delegate;
      }

      public Annotation getAnnotation(Class annotationType) {
         return this.annotations.getAnnotation(annotationType);
      }

      public Set getAnnotations() {
         return this.annotations.get();
      }

      public boolean isAnnotationPresent(Class annotationType) {
         return this.annotations.isAnnotationPresent(annotationType);
      }

      public Set getMethods() {
         return this.methods;
      }

      public Set getFields() {
         return this.fields;
      }

      public Set getConstructors() {
         return this.constructors;
      }

      public String toString() {
         return Formats.formatAnnotatedType(this.delegate);
      }
   }
}
