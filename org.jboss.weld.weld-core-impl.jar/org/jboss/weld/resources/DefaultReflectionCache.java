package org.jboss.weld.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.context.NormalScope;
import javax.inject.Scope;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.util.Annotations;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;

public class DefaultReflectionCache extends AbstractBootstrapService implements ReflectionCache {
   private final TypeStore store;
   private final Function ANNOTATIONS_FUNCTION = (input) -> {
      return ImmutableSet.of(this.internalGetAnnotations(input));
   };
   private final Function DECLARED_ANNOTATIONS_FUNCTION = (input) -> {
      return ImmutableSet.of(this.internalGetDeclaredAnnotations(input));
   };
   private final ComputingCache annotations;
   private final ComputingCache declaredAnnotations;
   private final ComputingCache backedAnnotatedTypeAnnotations;
   private final ComputingCache annotationClasses;

   protected Annotation[] internalGetAnnotations(AnnotatedElement element) {
      return element.getAnnotations();
   }

   protected Annotation[] internalGetDeclaredAnnotations(AnnotatedElement element) {
      return element.getDeclaredAnnotations();
   }

   public DefaultReflectionCache(TypeStore store) {
      this.store = store;
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.annotations = cacheBuilder.build(this.ANNOTATIONS_FUNCTION);
      this.declaredAnnotations = cacheBuilder.build(this.DECLARED_ANNOTATIONS_FUNCTION);
      this.backedAnnotatedTypeAnnotations = cacheBuilder.build(new BackedAnnotatedTypeAnnotationsFunction());
      this.annotationClasses = cacheBuilder.build(new AnnotationClassFunction());
   }

   public void cleanupAfterBoot() {
      this.annotations.clear();
      this.declaredAnnotations.clear();
      this.backedAnnotatedTypeAnnotations.clear();
      this.annotationClasses.clear();
   }

   public Set getAnnotations(AnnotatedElement element) {
      return (Set)this.annotations.getValue(element);
   }

   public Set getDeclaredAnnotations(AnnotatedElement element) {
      return (Set)this.declaredAnnotations.getValue(element);
   }

   public Set getBackedAnnotatedTypeAnnotationSet(Class javaClass) {
      return (Set)this.backedAnnotatedTypeAnnotations.getValue(javaClass);
   }

   public ReflectionCache.AnnotationClass getAnnotationClass(Class clazz) {
      return (ReflectionCache.AnnotationClass)this.annotationClasses.getCastValue(clazz);
   }

   private static class AnnotationClassImpl implements ReflectionCache.AnnotationClass {
      private final boolean scope;
      private final Method repeatableAnnotationAccessor;
      private final Set metaAnnotations;

      public AnnotationClassImpl(boolean scope, Method repeatableAnnotationAccessor, Set metaAnnotations) {
         this.scope = scope;
         this.repeatableAnnotationAccessor = repeatableAnnotationAccessor;
         this.metaAnnotations = metaAnnotations;
      }

      public Set getMetaAnnotations() {
         return this.metaAnnotations;
      }

      public boolean isScope() {
         return this.scope;
      }

      public boolean isRepeatableAnnotationContainer() {
         return this.repeatableAnnotationAccessor != null;
      }

      public Annotation[] getRepeatableAnnotations(Annotation annotation) {
         if (!this.isRepeatableAnnotationContainer()) {
            throw new IllegalStateException("Not a repeatable annotation container " + annotation);
         } else {
            try {
               return (Annotation[])((Annotation[])this.repeatableAnnotationAccessor.invoke(annotation));
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var3) {
               throw new RuntimeException("Error reading repeatable annotations on " + annotation.annotationType(), var3);
            }
         }
      }
   }

   private class AnnotationClassFunction implements Function {
      private AnnotationClassFunction() {
      }

      public ReflectionCache.AnnotationClass apply(Class input) {
         boolean scope = input.isAnnotationPresent(NormalScope.class) || input.isAnnotationPresent(Scope.class) || DefaultReflectionCache.this.store.isExtraScope(input);
         Method repeatableAnnotationAccessor = Annotations.getRepeatableAnnotationAccessor(input);
         Set metaAnnotations = ImmutableSet.of(DefaultReflectionCache.this.internalGetAnnotations(input));
         return new AnnotationClassImpl(scope, repeatableAnnotationAccessor, metaAnnotations);
      }

      // $FF: synthetic method
      AnnotationClassFunction(Object x1) {
         this();
      }
   }

   private class BackedAnnotatedTypeAnnotationsFunction implements Function {
      private BackedAnnotatedTypeAnnotationsFunction() {
      }

      public Set apply(Class javaClass) {
         Set annotations = DefaultReflectionCache.this.getAnnotations(javaClass);
         boolean scopeFound = false;
         Iterator var4 = annotations.iterator();

         while(var4.hasNext()) {
            Annotation annotation = (Annotation)var4.next();
            boolean isScope = DefaultReflectionCache.this.getAnnotationClass(annotation.annotationType()).isScope();
            if (isScope && scopeFound) {
               return this.applyScopeInheritanceRules(annotations, javaClass);
            }

            if (isScope) {
               scopeFound = true;
            }
         }

         return annotations;
      }

      public Set applyScopeInheritanceRules(Set annotations, Class javaClass) {
         ImmutableSet.Builder result = ImmutableSet.builder();
         Iterator var4 = annotations.iterator();

         while(var4.hasNext()) {
            Annotation annotation = (Annotation)var4.next();
            if (!DefaultReflectionCache.this.getAnnotationClass(annotation.annotationType()).isScope()) {
               result.add(annotation);
            }
         }

         result.addAll((Iterable)this.findTopLevelScopeDefinitions(javaClass));
         return result.build();
      }

      public Set findTopLevelScopeDefinitions(Class javaClass) {
         for(Class clazz = javaClass; clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            Set scopes = new HashSet();
            Iterator var4 = DefaultReflectionCache.this.getDeclaredAnnotations(clazz).iterator();

            while(var4.hasNext()) {
               Annotation annotation = (Annotation)var4.next();
               if (DefaultReflectionCache.this.getAnnotationClass(annotation.annotationType()).isScope()) {
                  scopes.add(annotation);
               }
            }

            if (scopes.size() > 0) {
               return scopes;
            }
         }

         throw new IllegalStateException();
      }

      // $FF: synthetic method
      BackedAnnotatedTypeAnnotationsFunction(Object x1) {
         this();
      }
   }
}
