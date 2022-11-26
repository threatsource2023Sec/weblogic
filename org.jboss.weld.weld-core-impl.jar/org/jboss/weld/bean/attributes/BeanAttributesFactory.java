package org.jboss.weld.bean.attributes;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.inject.New.Literal;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.literal.NamedLiteral;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MergedStereotypes;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;

public class BeanAttributesFactory {
   private static final Set DEFAULT_QUALIFIERS;

   private BeanAttributesFactory() {
   }

   public static BeanAttributes forBean(EnhancedAnnotated annotated, BeanManagerImpl manager) {
      return (new BeanAttributesBuilder(annotated, manager)).build();
   }

   public static BeanAttributes forNewBean(Set types, Class javaClass) {
      Set qualifiers = Collections.singleton(Literal.of(javaClass));
      return new ImmutableBeanAttributes(Collections.emptySet(), false, (String)null, qualifiers, types, Dependent.class);
   }

   public static BeanAttributes forNewManagedBean(EnhancedAnnotatedType weldClass, BeanManagerImpl manager) {
      return forNewBean(SharedObjectCache.instance(manager).getSharedSet(Beans.getTypes(weldClass)), weldClass.getJavaClass());
   }

   static {
      DEFAULT_QUALIFIERS = ImmutableSet.of(javax.enterprise.inject.Any.Literal.INSTANCE, javax.enterprise.inject.Default.Literal.INSTANCE);
   }

   public static class BeanAttributesBuilder {
      private MergedStereotypes mergedStereotypes;
      private boolean alternative;
      private String name;
      private Set qualifiers;
      private Set types;
      private Class scope;
      private BeanManagerImpl manager;
      protected final EnhancedAnnotated annotated;

      public BeanAttributesBuilder(EnhancedAnnotated annotated, Set types, BeanManagerImpl manager) {
         this.manager = manager;
         this.annotated = annotated;
         this.initStereotypes(annotated, manager);
         this.initAlternative(annotated);
         this.initName(annotated);
         this.initQualifiers(annotated);
         this.initScope(annotated);
         this.types = types;
      }

      public BeanAttributesBuilder(EnhancedAnnotated annotated, BeanManagerImpl manager) {
         this(annotated, SharedObjectCache.instance(manager).getSharedSet(Beans.getTypes(annotated)), manager);
      }

      protected void initStereotypes(EnhancedAnnotated annotated, BeanManagerImpl manager) {
         this.mergedStereotypes = MergedStereotypes.of(annotated, manager);
      }

      protected void initAlternative(EnhancedAnnotated annotated) {
         this.alternative = Beans.isAlternative(annotated, this.mergedStereotypes);
      }

      protected void initName(EnhancedAnnotated annotated) {
         boolean beanNameDefaulted = false;
         if (annotated.isAnnotationPresent(Named.class)) {
            String javaName = ((Named)annotated.getAnnotation(Named.class)).value();
            if (!"".equals(javaName)) {
               this.name = javaName;
               return;
            }

            beanNameDefaulted = true;
         }

         if (beanNameDefaulted || this.mergedStereotypes != null && this.mergedStereotypes.isBeanNameDefaulted()) {
            this.name = this.getDefaultName(annotated);
         }

      }

      protected String getDefaultName(EnhancedAnnotated annotated) {
         if (annotated instanceof EnhancedAnnotatedType) {
            StringBuilder defaultName = new StringBuilder(((EnhancedAnnotatedType)annotated).getSimpleName());
            defaultName.setCharAt(0, Character.toLowerCase(defaultName.charAt(0)));
            return defaultName.toString();
         } else if (annotated instanceof EnhancedAnnotatedField) {
            return ((EnhancedAnnotatedField)annotated).getPropertyName();
         } else {
            return annotated instanceof EnhancedAnnotatedMethod ? ((EnhancedAnnotatedMethod)annotated).getPropertyName() : null;
         }
      }

      protected void initQualifiers(Set qualifiers) {
         if (qualifiers.isEmpty()) {
            this.qualifiers = BeanAttributesFactory.DEFAULT_QUALIFIERS;
         } else {
            Set normalizedQualifiers = new HashSet(qualifiers.size() + 2);
            if (qualifiers.size() == 1) {
               Annotation qualifier = (Annotation)qualifiers.iterator().next();
               if (qualifier.annotationType().equals(Named.class) || qualifier.equals(javax.enterprise.inject.Any.Literal.INSTANCE)) {
                  normalizedQualifiers.add(javax.enterprise.inject.Default.Literal.INSTANCE);
               }
            } else if (qualifiers.size() == 2 && qualifiers.contains(javax.enterprise.inject.Any.Literal.INSTANCE)) {
               Iterator var3 = qualifiers.iterator();

               while(var3.hasNext()) {
                  Annotation qualifier = (Annotation)var3.next();
                  if (qualifier.annotationType().equals(Named.class)) {
                     normalizedQualifiers.add(javax.enterprise.inject.Default.Literal.INSTANCE);
                     break;
                  }
               }
            }

            normalizedQualifiers.addAll(qualifiers);
            normalizedQualifiers.add(javax.enterprise.inject.Any.Literal.INSTANCE);
            if (this.name != null && normalizedQualifiers.remove(NamedLiteral.DEFAULT)) {
               normalizedQualifiers.add(new NamedLiteral(this.name));
            }

            this.qualifiers = SharedObjectCache.instance(this.manager).getSharedSet(normalizedQualifiers);
         }

      }

      protected void initQualifiers(EnhancedAnnotated annotated) {
         this.initQualifiers(annotated.getMetaAnnotations(Qualifier.class));
      }

      protected void initScope(EnhancedAnnotated annotated) {
         if (annotated instanceof EnhancedAnnotatedType) {
            EnhancedAnnotatedType weldClass = (EnhancedAnnotatedType)annotated;

            for(EnhancedAnnotatedType clazz = weldClass; clazz != null; clazz = clazz.getEnhancedSuperclass()) {
               Set scopes = new HashSet();
               scopes.addAll(clazz.getDeclaredMetaAnnotations(Scope.class));
               scopes.addAll(clazz.getDeclaredMetaAnnotations(NormalScope.class));
               this.validateScopeSet(scopes, annotated);
               if (scopes.size() == 1) {
                  if (annotated.isAnnotationPresent(((Annotation)scopes.iterator().next()).annotationType())) {
                     this.scope = ((Annotation)scopes.iterator().next()).annotationType();
                  }
                  break;
               }
            }
         } else {
            Set scopes = new HashSet();
            scopes.addAll(annotated.getMetaAnnotations(Scope.class));
            scopes.addAll(annotated.getMetaAnnotations(NormalScope.class));
            if (scopes.size() == 1) {
               this.scope = ((Annotation)scopes.iterator().next()).annotationType();
            }

            this.validateScopeSet(scopes, annotated);
         }

         if (this.scope == null) {
            this.initScopeFromStereotype();
         }

         if (this.scope == null) {
            this.scope = Dependent.class;
         }

      }

      protected void validateScopeSet(Set scopes, EnhancedAnnotated annotated) {
         if (scopes.size() > 1) {
            throw BeanLogger.LOG.onlyOneScopeAllowed(annotated);
         }
      }

      protected boolean initScopeFromStereotype() {
         Set possibleScopes = this.mergedStereotypes.getPossibleScopes();
         if (possibleScopes.size() == 1) {
            this.scope = ((Annotation)possibleScopes.iterator().next()).annotationType();
            return true;
         } else if (possibleScopes.size() > 1) {
            String stack;
            Class declaringClass;
            if (this.annotated instanceof EnhancedAnnotatedMember) {
               EnhancedAnnotatedMember member = (EnhancedAnnotatedMember)this.annotated;
               declaringClass = member.getDeclaringType().getJavaClass();
               stack = "\n  at " + Formats.formatAsStackTraceElement(member.getJavaMember());
            } else {
               declaringClass = this.annotated.getJavaClass();
               stack = "";
            }

            throw BeanLogger.LOG.multipleScopesFoundFromStereotypes(Formats.formatType(declaringClass, false), Formats.formatTypes(this.mergedStereotypes.getStereotypes(), false), possibleScopes, stack);
         } else {
            return false;
         }
      }

      public BeanAttributes build() {
         return new ImmutableBeanAttributes(this.mergedStereotypes.getStereotypes(), this.alternative, this.name, this.qualifiers, this.types, this.scope);
      }
   }
}
