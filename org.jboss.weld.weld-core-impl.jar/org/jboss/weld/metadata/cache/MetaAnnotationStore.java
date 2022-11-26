package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Named;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;

public class MetaAnnotationStore implements Service {
   private final ComputingCache stereotypes;
   private final ComputingCache scopes;
   private final ComputingCache qualifiers;
   private final ComputingCache interceptorBindings;
   private final ComputingCache qualifierInstanceCache;
   private final SharedObjectCache sharedObjectCache;

   public MetaAnnotationStore(ClassTransformer classTransformer) {
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.stereotypes = cacheBuilder.build(new StereotypeFunction(classTransformer));
      this.scopes = cacheBuilder.build(new ScopeFunction(classTransformer));
      this.qualifiers = cacheBuilder.build(new QualifierFunction(classTransformer));
      this.interceptorBindings = cacheBuilder.build(new InterceptorBindingFunction(classTransformer));
      this.qualifierInstanceCache = cacheBuilder.build(new QualifierInstanceFunction(this));
      this.sharedObjectCache = classTransformer.getSharedObjectCache();
   }

   public void clearAnnotationData(Class annotationClass) {
      this.stereotypes.invalidate(annotationClass);
      this.scopes.invalidate(annotationClass);
      this.qualifiers.invalidate(annotationClass);
      this.interceptorBindings.invalidate(annotationClass);
   }

   public StereotypeModel getStereotype(Class stereotype) {
      return (StereotypeModel)this.stereotypes.getCastValue(stereotype);
   }

   public ScopeModel getScopeModel(Class scope) {
      return (ScopeModel)this.scopes.getCastValue(scope);
   }

   public QualifierModel getBindingTypeModel(Class bindingType) {
      return (QualifierModel)this.qualifiers.getCastValue(bindingType);
   }

   public InterceptorBindingModel getInterceptorBindingModel(Class interceptorBinding) {
      return (InterceptorBindingModel)this.interceptorBindings.getCastValue(interceptorBinding);
   }

   public QualifierInstance getQualifierInstance(Annotation annotation) {
      return isCacheAllowed(annotation) ? (QualifierInstance)this.qualifierInstanceCache.getValue(annotation) : QualifierInstance.of(annotation, this);
   }

   public Set getQualifierInstances(Bean bean) {
      return bean instanceof RIBean ? ((RIBean)bean).getQualifierInstances() : this.getQualifierInstances(bean.getQualifiers());
   }

   public Set getQualifierInstances(Set annotations) {
      if (annotations != null && !annotations.isEmpty()) {
         ImmutableSet.Builder builder = ImmutableSet.builder();
         boolean useSharedCache = true;
         Iterator var4 = annotations.iterator();

         while(var4.hasNext()) {
            Annotation annotation = (Annotation)var4.next();
            if (isCacheAllowed(annotation)) {
               builder.add(this.qualifierInstanceCache.getValue(annotation));
            } else {
               builder.add(QualifierInstance.of(annotation, this));
               useSharedCache = false;
            }
         }

         return useSharedCache ? this.sharedObjectCache.getSharedSet(builder.build()) : builder.build();
      } else {
         return Collections.emptySet();
      }
   }

   public String toString() {
      String newLine = "\n";
      StringBuilder buffer = new StringBuilder();
      buffer.append("Metadata cache").append("\n");
      buffer.append("Registered binding type models: ").append(this.qualifiers.size()).append("\n");
      buffer.append("Registered scope type models: ").append(this.scopes.size()).append("\n");
      buffer.append("Registered stereotype models: ").append(this.stereotypes.size()).append("\n");
      buffer.append("Registered interceptor binding models: ").append(this.interceptorBindings.size()).append("\n");
      buffer.append("Cached qualifier instances: ").append(this.qualifierInstanceCache.size()).append("\n");
      return buffer.toString();
   }

   public void cleanup() {
      this.qualifiers.clear();
      this.scopes.clear();
      this.stereotypes.clear();
      this.interceptorBindings.clear();
      this.qualifierInstanceCache.clear();
   }

   private static boolean isCacheAllowed(Annotation annotation) {
      if (annotation.annotationType().equals(Named.class)) {
         Named named = (Named)annotation;
         return named.value().equals("");
      } else {
         return true;
      }
   }

   private static class QualifierInstanceFunction implements Function {
      private final MetaAnnotationStore metaAnnotationStore;

      private QualifierInstanceFunction(MetaAnnotationStore metaAnnotationStore) {
         this.metaAnnotationStore = metaAnnotationStore;
      }

      public QualifierInstance apply(Annotation key) {
         return QualifierInstance.of(key, this.metaAnnotationStore);
      }

      // $FF: synthetic method
      QualifierInstanceFunction(MetaAnnotationStore x0, Object x1) {
         this(x0);
      }
   }

   private static class InterceptorBindingFunction extends AbstractMetaAnnotationFunction {
      public InterceptorBindingFunction(ClassTransformer classTransformer) {
         super(classTransformer, null);
      }

      public InterceptorBindingModel apply(Class from) {
         return new InterceptorBindingModel(this.getClassTransformer().getEnhancedAnnotation(from));
      }
   }

   private static class QualifierFunction extends AbstractMetaAnnotationFunction {
      public QualifierFunction(ClassTransformer classTransformer) {
         super(classTransformer, null);
      }

      public QualifierModel apply(Class from) {
         return new QualifierModel(this.getClassTransformer().getEnhancedAnnotation(from));
      }
   }

   private static class ScopeFunction extends AbstractMetaAnnotationFunction {
      public ScopeFunction(ClassTransformer classTransformer) {
         super(classTransformer, null);
      }

      public ScopeModel apply(Class from) {
         return new ScopeModel(this.getClassTransformer().getEnhancedAnnotation(from));
      }
   }

   private static class StereotypeFunction extends AbstractMetaAnnotationFunction {
      public StereotypeFunction(ClassTransformer classTransformer) {
         super(classTransformer, null);
      }

      public StereotypeModel apply(Class from) {
         return new StereotypeModel(this.getClassTransformer().getEnhancedAnnotation(from));
      }
   }

   private abstract static class AbstractMetaAnnotationFunction implements Function {
      private final ClassTransformer classTransformer;

      private AbstractMetaAnnotationFunction(ClassTransformer classTransformer) {
         this.classTransformer = classTransformer;
      }

      public ClassTransformer getClassTransformer() {
         return this.classTransformer;
      }

      // $FF: synthetic method
      AbstractMetaAnnotationFunction(ClassTransformer x0, Object x1) {
         this(x0);
      }
   }
}
