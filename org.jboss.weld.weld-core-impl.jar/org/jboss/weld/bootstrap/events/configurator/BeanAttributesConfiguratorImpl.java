package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.configurator.BeanAttributesConfigurator;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Named;
import org.jboss.weld.bean.attributes.ImmutableBeanAttributes;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.metadata.cache.StereotypeModel;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.HierarchyDiscovery;

public class BeanAttributesConfiguratorImpl implements BeanAttributesConfigurator, Configurator {
   private final BeanManagerImpl beanManager;
   private String name;
   final Set qualifiers;
   private Class scope;
   private final Set stereotypes;
   final Set types;
   private boolean isAlternative;

   public BeanAttributesConfiguratorImpl(BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
      this.qualifiers = new HashSet();
      this.types = new HashSet();
      this.types.add(Object.class);
      this.stereotypes = new HashSet();
   }

   public BeanAttributesConfiguratorImpl(BeanAttributes beanAttributes, BeanManagerImpl beanManager) {
      this(beanManager);
      this.read(beanAttributes);
   }

   public BeanAttributesConfigurator read(BeanAttributes beanAttributes) {
      Preconditions.checkArgumentNotNull(beanAttributes);
      this.name(beanAttributes.getName());
      this.qualifiers(beanAttributes.getQualifiers());
      this.scope(beanAttributes.getScope());
      this.stereotypes(beanAttributes.getStereotypes());
      this.types(beanAttributes.getTypes());
      this.alternative(beanAttributes.isAlternative());
      return this;
   }

   public BeanAttributesConfigurator addType(Type type) {
      Preconditions.checkArgumentNotNull(type);
      this.types.add(type);
      return this;
   }

   public BeanAttributesConfigurator addType(TypeLiteral typeLiteral) {
      Preconditions.checkArgumentNotNull(typeLiteral);
      this.types.add(typeLiteral.getType());
      return null;
   }

   public BeanAttributesConfigurator addTypes(Type... types) {
      Preconditions.checkArgumentNotNull(types);
      Collections.addAll(this.types, types);
      return this;
   }

   public BeanAttributesConfigurator addTypes(Set types) {
      Preconditions.checkArgumentNotNull(types);
      this.types.addAll(types);
      return this;
   }

   public BeanAttributesConfigurator addTransitiveTypeClosure(Type type) {
      Preconditions.checkArgumentNotNull(type);
      this.types.addAll(Beans.getLegalBeanTypes((new HierarchyDiscovery(type)).getTypeClosure(), type));
      return this;
   }

   public BeanAttributesConfigurator types(Type... types) {
      this.types.clear();
      return this.addTypes(types);
   }

   public BeanAttributesConfigurator types(Set types) {
      this.types.clear();
      return this.addTypes(types);
   }

   public BeanAttributesConfigurator scope(Class scope) {
      Preconditions.checkArgumentNotNull(scope);
      this.scope = scope;
      return this;
   }

   public BeanAttributesConfigurator addQualifier(Annotation qualifier) {
      Preconditions.checkArgumentNotNull(qualifier);
      this.removeDefaultQualifierIfNeeded(qualifier);
      this.qualifiers.add(qualifier);
      return this;
   }

   public BeanAttributesConfigurator addQualifiers(Annotation... qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      Annotation[] var2 = qualifiers;
      int var3 = qualifiers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation annotation = var2[var4];
         this.removeDefaultQualifierIfNeeded(annotation);
      }

      Collections.addAll(this.qualifiers, qualifiers);
      return this;
   }

   public BeanAttributesConfigurator addQualifiers(Set qualifiers) {
      Preconditions.checkArgumentNotNull(qualifiers);
      Iterator var2 = qualifiers.iterator();

      while(var2.hasNext()) {
         Annotation annotation = (Annotation)var2.next();
         this.removeDefaultQualifierIfNeeded(annotation);
      }

      this.qualifiers.addAll(qualifiers);
      return this;
   }

   public BeanAttributesConfigurator qualifiers(Annotation... qualifiers) {
      this.qualifiers.clear();
      return this.addQualifiers(qualifiers);
   }

   public BeanAttributesConfigurator qualifiers(Set qualifiers) {
      this.qualifiers.clear();
      return this.addQualifiers(qualifiers);
   }

   public BeanAttributesConfigurator addStereotype(Class stereotype) {
      Preconditions.checkArgumentNotNull(stereotype);
      this.stereotypes.add(stereotype);
      return this;
   }

   public BeanAttributesConfigurator addStereotypes(Set stereotypes) {
      Preconditions.checkArgumentNotNull(stereotypes);
      this.stereotypes.addAll(stereotypes);
      return this;
   }

   public BeanAttributesConfigurator stereotypes(Set stereotypes) {
      this.stereotypes.clear();
      return this.addStereotypes(stereotypes);
   }

   public BeanAttributesConfigurator name(String name) {
      this.name = name;
      return this;
   }

   public BeanAttributesConfigurator alternative(boolean alternative) {
      this.isAlternative = alternative;
      return this;
   }

   public BeanAttributes complete() {
      return new ImmutableBeanAttributes(ImmutableSet.copyOf(this.stereotypes), this.isAlternative, this.name, this.initQualifiers(this.qualifiers), ImmutableSet.copyOf(this.types), this.initScope());
   }

   private void removeDefaultQualifierIfNeeded(Annotation qualifier) {
      if (!qualifier.annotationType().equals(Named.class)) {
         this.qualifiers.remove(Literal.INSTANCE);
      }

   }

   private Class initScope() {
      if (this.scope != null) {
         return this.scope;
      } else if (!this.stereotypes.isEmpty()) {
         MetaAnnotationStore metaAnnotationStore = (MetaAnnotationStore)this.beanManager.getServices().get(MetaAnnotationStore.class);
         Set possibleScopeTypes = new HashSet();
         Iterator var3 = this.stereotypes.iterator();

         while(var3.hasNext()) {
            Class stereotype = (Class)var3.next();
            StereotypeModel model = metaAnnotationStore.getStereotype(stereotype);
            if (!model.isValid()) {
               throw BeanManagerLogger.LOG.notStereotype(stereotype);
            }

            possibleScopeTypes.add(model.getDefaultScopeType());
         }

         if (possibleScopeTypes.size() == 1) {
            return ((Annotation)possibleScopeTypes.iterator().next()).annotationType();
         } else {
            throw BeanLogger.LOG.multipleScopesFoundFromStereotypes(BeanAttributesConfigurator.class.getSimpleName(), Formats.formatTypes(this.stereotypes, false), possibleScopeTypes, "");
         }
      } else {
         return Dependent.class;
      }
   }

   private Set initQualifiers(Set qualifiers) {
      if (qualifiers.isEmpty()) {
         return Bindings.DEFAULT_QUALIFIERS;
      } else {
         Set normalized = new HashSet(qualifiers);
         normalized.remove(javax.enterprise.inject.Any.Literal.INSTANCE);
         normalized.remove(Literal.INSTANCE);
         Set normalized;
         if (normalized.isEmpty()) {
            normalized = Bindings.DEFAULT_QUALIFIERS;
         } else {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            if (normalized.size() == 1 && ((Annotation)qualifiers.iterator().next()).annotationType().equals(Named.class)) {
               builder.add(Literal.INSTANCE);
            }

            builder.add(javax.enterprise.inject.Any.Literal.INSTANCE);
            builder.addAll((Iterable)qualifiers);
            normalized = builder.build();
         }

         return normalized;
      }
   }
}
