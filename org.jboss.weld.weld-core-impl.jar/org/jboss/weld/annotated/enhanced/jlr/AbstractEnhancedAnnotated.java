package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.Default.Literal;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.inject.Qualifier;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.Multimap;
import org.jboss.weld.util.collections.Multimaps;
import org.jboss.weld.util.collections.SetMultimap;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractEnhancedAnnotated implements EnhancedAnnotated {
   private static final Set DEFAULT_QUALIFIERS;
   private final Map annotationMap;
   private final Multimap metaAnnotationMap;
   private final Class rawType;
   private final Type[] actualTypeArguments;
   private final Annotated delegate;
   private final Set annotations;

   protected static Map buildAnnotationMap(Iterable annotations) {
      Map annotationMap = new HashMap();
      Iterator var2 = annotations.iterator();

      while(var2.hasNext()) {
         Annotation annotation = (Annotation)var2.next();
         annotationMap.put(annotation.annotationType(), annotation);
      }

      return annotationMap;
   }

   protected static void addMetaAnnotations(SetMultimap metaAnnotationMap, Annotation annotation, Iterable metaAnnotations, boolean declared) {
      Iterator var4 = metaAnnotations.iterator();

      while(var4.hasNext()) {
         Annotation metaAnnotation = (Annotation)var4.next();
         addMetaAnnotation(metaAnnotationMap, annotation, metaAnnotation.annotationType(), declared);
      }

   }

   private static void addMetaAnnotation(SetMultimap metaAnnotationMap, Annotation annotation, Class metaAnnotationType, boolean declared) {
      if (declared) {
         if (!MAPPED_DECLARED_METAANNOTATIONS.contains(metaAnnotationType)) {
            return;
         }
      } else if (!MAPPED_METAANNOTATIONS.contains(metaAnnotationType)) {
         return;
      }

      metaAnnotationMap.put(metaAnnotationType, annotation);
   }

   public AbstractEnhancedAnnotated(Annotated annotated, Map annotationMap, Map declaredAnnotationMap, ClassTransformer classTransformer) {
      this.delegate = annotated;
      if (annotated instanceof AnnotatedType) {
         this.rawType = ((AnnotatedType)Reflections.cast(annotated)).getJavaClass();
      } else {
         this.rawType = Reflections.getRawType(annotated.getBaseType());
      }

      if (annotationMap == null) {
         throw ReflectionLogger.LOG.annotationMapNull();
      } else {
         this.annotationMap = WeldCollections.immutableMapView(annotationMap);
         SetMultimap metaAnnotationMap = SetMultimap.newSetMultimap();
         this.processMetaAnnotations(metaAnnotationMap, annotationMap.values(), classTransformer, false);
         this.metaAnnotationMap = Multimaps.unmodifiableMultimap(metaAnnotationMap);
         if (declaredAnnotationMap == null) {
            throw ReflectionLogger.LOG.declaredAnnotationMapNull();
         } else {
            if (this.delegate.getBaseType() instanceof ParameterizedType) {
               this.actualTypeArguments = ((ParameterizedType)this.delegate.getBaseType()).getActualTypeArguments();
            } else {
               this.actualTypeArguments = Arrays2.EMPTY_TYPE_ARRAY;
            }

            this.annotations = ImmutableSet.copyOf(this.annotationMap.values());
         }
      }
   }

   protected void processMetaAnnotations(SetMultimap metaAnnotationMap, Collection annotations, ClassTransformer classTransformer, boolean declared) {
      Iterator var5 = annotations.iterator();

      while(var5.hasNext()) {
         Annotation annotation = (Annotation)var5.next();
         this.processMetaAnnotations(metaAnnotationMap, annotation, classTransformer, declared);
      }

   }

   protected void processMetaAnnotations(SetMultimap metaAnnotationMap, Annotation[] annotations, ClassTransformer classTransformer, boolean declared) {
      Annotation[] var5 = annotations;
      int var6 = annotations.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Annotation annotation = var5[var7];
         this.processMetaAnnotations(metaAnnotationMap, annotation, classTransformer, declared);
      }

   }

   protected void processMetaAnnotations(SetMultimap metaAnnotationMap, Annotation annotation, ClassTransformer classTransformer, boolean declared) {
      SlimAnnotatedType syntheticAnnotationAnnotatedType = classTransformer.getSyntheticAnnotationAnnotatedType(annotation.annotationType());
      if (syntheticAnnotationAnnotatedType != null) {
         addMetaAnnotations(metaAnnotationMap, annotation, syntheticAnnotationAnnotatedType.getAnnotations(), declared);
      } else {
         addMetaAnnotations(metaAnnotationMap, annotation, classTransformer.getReflectionCache().getAnnotations(annotation.annotationType()), declared);
         ReflectionCache.AnnotationClass annotationClass = classTransformer.getReflectionCache().getAnnotationClass(annotation.annotationType());
         if (annotationClass.isRepeatableAnnotationContainer()) {
            this.processMetaAnnotations(metaAnnotationMap, annotationClass.getRepeatableAnnotations(annotation), classTransformer, declared);
         }
      }

      addMetaAnnotations(metaAnnotationMap, annotation, classTransformer.getTypeStore().get(annotation.annotationType()), declared);
   }

   public Class getJavaClass() {
      return this.rawType;
   }

   public Type[] getActualTypeArguments() {
      return (Type[])Arrays.copyOf(this.actualTypeArguments, this.actualTypeArguments.length);
   }

   public Set getInterfaceClosure() {
      Set interfaces = new HashSet();
      Iterator var2 = this.getTypeClosure().iterator();

      while(var2.hasNext()) {
         Type t = (Type)var2.next();
         if (Reflections.getRawType(t).isInterface()) {
            interfaces.add(t);
         }
      }

      return WeldCollections.immutableSetView(interfaces);
   }

   public abstract Object getDelegate();

   public boolean isParameterizedType() {
      return this.rawType.getTypeParameters().length > 0;
   }

   public boolean isPrimitive() {
      return this.getJavaClass().isPrimitive();
   }

   public Type getBaseType() {
      return this.delegate.getBaseType();
   }

   public Set getTypeClosure() {
      return this.delegate.getTypeClosure();
   }

   public Set getAnnotations() {
      return this.annotations;
   }

   public Set getMetaAnnotations(Class metaAnnotationType) {
      return ImmutableSet.copyOf(this.metaAnnotationMap.get(metaAnnotationType));
   }

   /** @deprecated */
   @Deprecated
   public Set getQualifiers() {
      Set qualifiers = this.getMetaAnnotations(Qualifier.class);
      return qualifiers.size() > 0 ? WeldCollections.immutableSetView(qualifiers) : DEFAULT_QUALIFIERS;
   }

   /** @deprecated */
   @Deprecated
   public Annotation[] getBindingsAsArray() {
      return (Annotation[])this.getQualifiers().toArray(Reflections.EMPTY_ANNOTATIONS);
   }

   public Annotation getAnnotation(Class annotationType) {
      return (Annotation)annotationType.cast(this.annotationMap.get(annotationType));
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.annotationMap.containsKey(annotationType);
   }

   Map getAnnotationMap() {
      return this.annotationMap;
   }

   static {
      DEFAULT_QUALIFIERS = Collections.singleton(Literal.INSTANCE);
   }
}
