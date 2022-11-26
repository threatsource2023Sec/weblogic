package com.sun.faces.cdi;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.PassivationCapable;

abstract class CdiProducer implements Bean, PassivationCapable, Serializable {
   private static final long serialVersionUID = 1L;
   private String id = this.getClass().getName();
   private String name;
   private Class beanClass = Object.class;
   private Set types = Collections.singleton(Object.class);
   private Set qualifiers = Collections.unmodifiableSet(asSet(new DefaultAnnotationLiteral(), new AnyAnnotationLiteral()));
   private Class scope = Dependent.class;
   private Function create;

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public Set getTypes() {
      return this.types;
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }

   public Class getScope() {
      return this.scope;
   }

   public Object create(CreationalContext creationalContext) {
      return this.create.apply(creationalContext);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public Set getStereotypes() {
      return Collections.emptySet();
   }

   public boolean isAlternative() {
      return false;
   }

   public boolean isNullable() {
      return false;
   }

   protected CdiProducer name(String name) {
      this.name = name;
      return this;
   }

   protected CdiProducer create(Function create) {
      this.create = create;
      return this;
   }

   protected CdiProducer beanClass(Class beanClass) {
      this.beanClass = beanClass;
      return this;
   }

   protected CdiProducer types(Type... types) {
      this.types = asSet(types);
      return this;
   }

   protected CdiProducer beanClassAndType(Class beanClass) {
      this.beanClass(beanClass);
      this.types(beanClass);
      return this;
   }

   protected CdiProducer qualifiers(Annotation... qualifiers) {
      this.qualifiers = asSet(qualifiers);
      return this;
   }

   protected CdiProducer scope(Class scope) {
      this.scope = scope;
      return this;
   }

   protected CdiProducer addToId(Object object) {
      this.id = this.id + " " + object.toString();
      return this;
   }

   @SafeVarargs
   protected static Set asSet(Object... a) {
      return new HashSet(Arrays.asList(a));
   }
}
