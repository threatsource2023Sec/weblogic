package org.jboss.weld.resources;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.annotated.enhanced.jlr.EnhancedAnnotatedTypeImpl;
import org.jboss.weld.annotated.enhanced.jlr.EnhancedAnnotationImpl;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.backed.BackedAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedType;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.reflection.Reflections;

public class ClassTransformer implements BootstrapService {
   private final ConcurrentMap syntheticAnnotationsAnnotatedTypes = new ConcurrentHashMap();
   private final ConcurrentMap slimAnnotatedTypesById;
   private final ComputingCache backedAnnotatedTypes;
   private final ComputingCache enhancedAnnotatedTypes;
   private final ComputingCache annotations;
   private final TypeStore typeStore;
   private final SharedObjectCache cache;
   private final ReflectionCache reflectionCache;
   private final String contextId;

   public static ClassTransformer instance(BeanManagerImpl manager) {
      return (ClassTransformer)manager.getServices().get(ClassTransformer.class);
   }

   public ClassTransformer(TypeStore typeStore, SharedObjectCache cache, ReflectionCache reflectionCache, String contextId) {
      this.contextId = contextId;
      this.backedAnnotatedTypes = ComputingCacheBuilder.newBuilder().setWeakValues().build(new TransformClassToBackedAnnotatedType());
      this.enhancedAnnotatedTypes = ComputingCacheBuilder.newBuilder().build(new TransformSlimAnnotatedTypeToEnhancedAnnotatedType());
      this.annotations = ComputingCacheBuilder.newBuilder().build(new TransformClassToWeldAnnotation());
      this.typeStore = typeStore;
      this.cache = cache;
      this.reflectionCache = reflectionCache;
      this.slimAnnotatedTypesById = new ConcurrentHashMap();
   }

   public BackedAnnotatedType getBackedAnnotatedType(Class rawType, Type baseType, String bdaId, String suffix) {
      try {
         return (BackedAnnotatedType)this.backedAnnotatedTypes.getCastValue(new TypeHolder(rawType, baseType, bdaId, suffix));
      } catch (RuntimeException var6) {
         if (!(var6 instanceof TypeNotPresentException) && !(var6 instanceof ResourceLoadingException)) {
            throw var6;
         } else {
            BootstrapLogger.LOG.exceptionWhileLoadingClass(rawType.getName(), var6);
            throw new ResourceLoadingException("Exception while loading class " + rawType.getName(), var6);
         }
      } catch (Error var7) {
         if (!(var7 instanceof NoClassDefFoundError) && !(var7 instanceof LinkageError)) {
            BootstrapLogger.LOG.errorWhileLoadingClass(rawType.getName(), var7);
            throw var7;
         } else {
            throw new ResourceLoadingException("Error while loading class " + rawType.getName(), var7);
         }
      }
   }

   public BackedAnnotatedType getBackedAnnotatedType(Class rawType, String bdaId) {
      return this.getBackedAnnotatedType(rawType, rawType, bdaId, (String)null);
   }

   public BackedAnnotatedType getBackedAnnotatedType(Class rawType, String bdaId, String suffix) {
      return this.getBackedAnnotatedType(rawType, rawType, bdaId, suffix);
   }

   public SlimAnnotatedType getSlimAnnotatedTypeById(AnnotatedTypeIdentifier id) {
      return (SlimAnnotatedType)Reflections.cast(this.slimAnnotatedTypesById.get(id));
   }

   public UnbackedAnnotatedType getUnbackedAnnotatedType(AnnotatedType source, String bdaId, String suffix) {
      UnbackedAnnotatedType type = UnbackedAnnotatedType.additionalAnnotatedType(this.contextId, source, bdaId, suffix, this.cache);
      return (UnbackedAnnotatedType)this.updateLookupTable(type);
   }

   public UnbackedAnnotatedType getUnbackedAnnotatedType(SlimAnnotatedType originalType, AnnotatedType source) {
      UnbackedAnnotatedType type = UnbackedAnnotatedType.modifiedAnnotatedType(originalType, source, this.cache);
      return (UnbackedAnnotatedType)this.updateLookupTable(type);
   }

   public UnbackedAnnotatedType getSyntheticAnnotationAnnotatedType(Class annotationType) {
      return (UnbackedAnnotatedType)this.syntheticAnnotationsAnnotatedTypes.get(annotationType);
   }

   private SlimAnnotatedType updateLookupTable(SlimAnnotatedType annotatedType) {
      SlimAnnotatedType previousValue = (SlimAnnotatedType)this.slimAnnotatedTypesById.putIfAbsent(annotatedType.getIdentifier(), annotatedType);
      return previousValue == null ? annotatedType : (SlimAnnotatedType)Reflections.cast(previousValue);
   }

   public EnhancedAnnotatedType getEnhancedAnnotatedType(Class rawType, String bdaId) {
      return this.getEnhancedAnnotatedType(this.getBackedAnnotatedType(rawType, bdaId));
   }

   public EnhancedAnnotatedType getEnhancedAnnotatedType(Class rawType, Type baseType, String bdaId) {
      return this.getEnhancedAnnotatedType(this.getBackedAnnotatedType(rawType, baseType, bdaId, (String)null));
   }

   public EnhancedAnnotatedType getEnhancedAnnotatedType(AnnotatedType annotatedType, String bdaId) {
      if (annotatedType instanceof EnhancedAnnotatedType) {
         return (EnhancedAnnotatedType)Reflections.cast(annotatedType);
      } else {
         return annotatedType instanceof SlimAnnotatedType ? (EnhancedAnnotatedType)Reflections.cast(this.getEnhancedAnnotatedType((SlimAnnotatedType)annotatedType)) : this.getEnhancedAnnotatedType(this.getUnbackedAnnotatedType(annotatedType, bdaId, AnnotatedTypes.createTypeId(annotatedType)));
      }
   }

   public EnhancedAnnotatedType getEnhancedAnnotatedType(SlimAnnotatedType annotatedType) {
      return (EnhancedAnnotatedType)Reflections.cast(this.enhancedAnnotatedTypes.getValue(annotatedType));
   }

   public EnhancedAnnotation getEnhancedAnnotation(Class clazz) {
      return (EnhancedAnnotation)this.annotations.getCastValue(clazz);
   }

   public void clearAnnotationData(Class annotationClass) {
      this.annotations.invalidate(annotationClass);
   }

   public TypeStore getTypeStore() {
      return this.typeStore;
   }

   public SharedObjectCache getSharedObjectCache() {
      return this.cache;
   }

   public ReflectionCache getReflectionCache() {
      return this.reflectionCache;
   }

   public void addSyntheticAnnotation(AnnotatedType annotation, String bdaId) {
      this.syntheticAnnotationsAnnotatedTypes.put(annotation.getJavaClass(), this.getUnbackedAnnotatedType(annotation, bdaId, "syntheticAnnotation"));
      this.clearAnnotationData(annotation.getJavaClass());
   }

   public void disposeBackedAnnotatedType(Class rawType, String bdaId, String suffix) {
      TypeHolder typeHolder = new TypeHolder(rawType, rawType, bdaId, suffix);
      BackedAnnotatedType annotatedType = (BackedAnnotatedType)Reflections.cast(this.backedAnnotatedTypes.getValueIfPresent(typeHolder));
      if (annotatedType != null) {
         this.backedAnnotatedTypes.invalidate(typeHolder);
         this.slimAnnotatedTypesById.remove(annotatedType.getIdentifier());
         this.enhancedAnnotatedTypes.invalidate(annotatedType);
      }

   }

   public void cleanupAfterBoot() {
      this.enhancedAnnotatedTypes.clear();
      this.annotations.clear();
      this.backedAnnotatedTypes.forEachValue(BackedAnnotatedType::clear);
      this.backedAnnotatedTypes.clear();
   }

   public void cleanup() {
      this.cleanupAfterBoot();
      this.slimAnnotatedTypesById.clear();
      this.syntheticAnnotationsAnnotatedTypes.clear();
   }

   public void removeAll(Set removable) {
      Iterator var2 = removable.iterator();

      while(var2.hasNext()) {
         Bean bean = (Bean)var2.next();
         if (bean instanceof AbstractClassBean) {
            this.slimAnnotatedTypesById.remove(((AbstractClassBean)bean).getAnnotated().getIdentifier());
         }
      }

   }

   private static final class TypeHolder {
      private final String bdaId;
      private final Class rawType;
      private final Type baseType;
      private final String suffix;

      private TypeHolder(Class rawType, Type baseType, String bdaId, String suffix) {
         this.rawType = rawType;
         this.baseType = baseType;
         this.bdaId = bdaId;
         this.suffix = suffix;
      }

      Type getBaseType() {
         return this.baseType;
      }

      Class getRawType() {
         return this.rawType;
      }

      String getBdaId() {
         return this.bdaId;
      }

      String getSuffix() {
         return this.suffix;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof TypeHolder)) {
            return false;
         } else {
            TypeHolder that = (TypeHolder)obj;
            return Objects.equals(this.getBaseType(), that.getBaseType()) && Objects.equals(this.getBdaId(), that.getBdaId()) && Objects.equals(this.getSuffix(), that.getSuffix());
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.getBaseType(), this.getBdaId(), this.getSuffix()});
      }

      public String toString() {
         return "TypeHolder [bdaId=" + this.bdaId + ", rawType=" + this.rawType + ", baseType=" + this.baseType + ", suffix=" + this.suffix + "]";
      }

      // $FF: synthetic method
      TypeHolder(Class x0, Type x1, String x2, String x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private class TransformSlimAnnotatedTypeToEnhancedAnnotatedType implements Function {
      private TransformSlimAnnotatedTypeToEnhancedAnnotatedType() {
      }

      public EnhancedAnnotatedType apply(SlimAnnotatedType annotatedType) {
         return EnhancedAnnotatedTypeImpl.of(annotatedType, ClassTransformer.this);
      }

      // $FF: synthetic method
      TransformSlimAnnotatedTypeToEnhancedAnnotatedType(Object x1) {
         this();
      }
   }

   private class TransformClassToBackedAnnotatedType implements Function {
      private TransformClassToBackedAnnotatedType() {
      }

      public BackedAnnotatedType apply(TypeHolder typeHolder) {
         Reflections.checkDeclaringClassLoadable(typeHolder.getRawType());
         BackedAnnotatedType type = BackedAnnotatedType.of(typeHolder.getRawType(), typeHolder.getBaseType(), ClassTransformer.this.cache, ClassTransformer.this.reflectionCache, ClassTransformer.this.contextId, typeHolder.getBdaId(), typeHolder.getSuffix());
         return (BackedAnnotatedType)ClassTransformer.this.updateLookupTable(type);
      }

      // $FF: synthetic method
      TransformClassToBackedAnnotatedType(Object x1) {
         this();
      }
   }

   private class TransformClassToWeldAnnotation implements Function {
      private TransformClassToWeldAnnotation() {
      }

      public EnhancedAnnotation apply(Class from) {
         SlimAnnotatedType slimAnnotatedType = (SlimAnnotatedType)ClassTransformer.this.syntheticAnnotationsAnnotatedTypes.get(from);
         if (slimAnnotatedType == null) {
            slimAnnotatedType = ClassTransformer.this.getBackedAnnotatedType(from, AnnotatedTypeIdentifier.NULL_BDA_ID);
         }

         return EnhancedAnnotationImpl.create((SlimAnnotatedType)slimAnnotatedType, ClassTransformer.this);
      }

      // $FF: synthetic method
      TransformClassToWeldAnnotation(Object x1) {
         this();
      }
   }
}
