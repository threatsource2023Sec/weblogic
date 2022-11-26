package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.NormalScope;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.interceptor.InterceptorBinding;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.collections.Arrays2;

public class StereotypeModel extends AnnotationModel {
   private static final Set META_ANNOTATIONS = Collections.singleton(Stereotype.class);
   private boolean alternative;
   private Annotation defaultScopeType;
   private boolean beanNameDefaulted;
   private Set interceptorBindings;
   private Set inheritedStereotypes;
   private Set metaAnnotations;

   public StereotypeModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      super(enhancedAnnotatedAnnotation);
   }

   protected void init(EnhancedAnnotation annotatedAnnotation) {
      super.init(annotatedAnnotation);
      if (this.valid) {
         this.initAlternative(annotatedAnnotation);
         this.initDefaultScopeType(annotatedAnnotation);
         this.initBeanNameDefaulted(annotatedAnnotation);
         this.initInterceptorBindings(annotatedAnnotation);
         this.initInheritedStereotypes(annotatedAnnotation);
         this.checkBindings(annotatedAnnotation);
         this.metaAnnotations = annotatedAnnotation.getAnnotations();
      }

   }

   private void checkBindings(EnhancedAnnotation annotatedAnnotation) {
      Set bindings = annotatedAnnotation.getMetaAnnotations(Qualifier.class);
      if (bindings.size() > 0) {
         Iterator var3 = bindings.iterator();

         while(var3.hasNext()) {
            Annotation annotation = (Annotation)var3.next();
            if (!annotation.annotationType().equals(Named.class)) {
               throw MetadataLogger.LOG.qualifierOnStereotype(annotatedAnnotation);
            }
         }
      }

   }

   private void initInterceptorBindings(EnhancedAnnotation annotatedAnnotation) {
      this.interceptorBindings = annotatedAnnotation.getMetaAnnotations(InterceptorBinding.class);
   }

   private void initInheritedStereotypes(EnhancedAnnotation annotatedAnnotation) {
      this.inheritedStereotypes = annotatedAnnotation.getMetaAnnotations(Stereotype.class);
   }

   private void initBeanNameDefaulted(EnhancedAnnotation annotatedAnnotation) {
      if (annotatedAnnotation.isAnnotationPresent(Named.class)) {
         if (!"".equals(((Named)annotatedAnnotation.getAnnotation(Named.class)).value())) {
            throw MetadataLogger.LOG.valueOnNamedStereotype(annotatedAnnotation);
         }

         this.beanNameDefaulted = true;
      }

   }

   private void initDefaultScopeType(EnhancedAnnotation annotatedAnnotation) {
      Set scopeTypes = new HashSet();
      scopeTypes.addAll(annotatedAnnotation.getMetaAnnotations(Scope.class));
      scopeTypes.addAll(annotatedAnnotation.getMetaAnnotations(NormalScope.class));
      if (scopeTypes.size() > 1) {
         throw MetadataLogger.LOG.multipleScopes(annotatedAnnotation);
      } else {
         if (scopeTypes.size() == 1) {
            this.defaultScopeType = (Annotation)scopeTypes.iterator().next();
         }

      }
   }

   private void initAlternative(EnhancedAnnotation annotatedAnnotation) {
      if (annotatedAnnotation.isAnnotationPresent(Alternative.class)) {
         this.alternative = true;
      }

   }

   protected void check(EnhancedAnnotation annotatedAnnotation) {
      super.check(annotatedAnnotation);
      if (this.isValid()) {
         if (!annotatedAnnotation.isAnnotationPresent(Target.class)) {
            ReflectionLogger.LOG.missingTarget(annotatedAnnotation);
         } else {
            ElementType[] elementTypes = ((Target)annotatedAnnotation.getAnnotation(Target.class)).value();
            if (!Arrays2.unorderedEquals(elementTypes, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE) && !Arrays2.unorderedEquals(elementTypes, ElementType.TYPE) && !Arrays2.unorderedEquals(elementTypes, ElementType.METHOD) && !Arrays2.unorderedEquals(elementTypes, ElementType.FIELD) && !Arrays2.unorderedEquals(elementTypes, ElementType.METHOD, ElementType.TYPE)) {
               ReflectionLogger.LOG.missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField(annotatedAnnotation);
            }
         }
      }

   }

   public Annotation getDefaultScopeType() {
      return this.defaultScopeType;
   }

   public Set getInterceptorBindings() {
      return this.interceptorBindings;
   }

   public boolean isBeanNameDefaulted() {
      return this.beanNameDefaulted;
   }

   protected Set getMetaAnnotationTypes() {
      return META_ANNOTATIONS;
   }

   public boolean isAlternative() {
      return this.alternative;
   }

   public Set getInheritedStereotypes() {
      return this.inheritedStereotypes;
   }

   public Set getMetaAnnotations() {
      return this.metaAnnotations;
   }
}
