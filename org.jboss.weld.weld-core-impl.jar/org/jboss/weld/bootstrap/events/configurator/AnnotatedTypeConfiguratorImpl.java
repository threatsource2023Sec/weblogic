package org.jboss.weld.bootstrap.events.configurator;

import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedTypeConfiguratorImpl extends AnnotatedConfigurator implements AnnotatedTypeConfigurator, Configurator {
   private final Set methods;
   private final Set fields;
   private final Set constructors;

   public AnnotatedTypeConfiguratorImpl(AnnotatedType annotatedType) {
      super(annotatedType);
      this.constructors = (Set)annotatedType.getConstructors().stream().map((c) -> {
         return AnnotatedConstructorConfiguratorImpl.from(c);
      }).collect(ImmutableSet.collector());
      this.methods = (Set)annotatedType.getMethods().stream().map((m) -> {
         return AnnotatedMethodConfiguratorImpl.from(m);
      }).collect(ImmutableSet.collector());
      this.fields = (Set)annotatedType.getFields().stream().map((f) -> {
         return AnnotatedFieldConfiguratorImpl.from(f);
      }).collect(ImmutableSet.collector());
   }

   public Set methods() {
      return (Set)Reflections.cast(this.methods);
   }

   public Set fields() {
      return (Set)Reflections.cast(this.fields);
   }

   public Set constructors() {
      return (Set)Reflections.cast(this.constructors);
   }

   protected AnnotatedTypeConfiguratorImpl self() {
      return this;
   }

   public AnnotatedType complete() {
      return (new AnnotatedTypeBuilderImpl(this)).build();
   }

   Set getMethods() {
      return this.methods;
   }

   Set getFields() {
      return this.fields;
   }

   Set getConstructors() {
      return this.constructors;
   }
}
