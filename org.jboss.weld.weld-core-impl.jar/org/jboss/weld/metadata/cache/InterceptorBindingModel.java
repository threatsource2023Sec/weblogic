package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.interceptor.InterceptorBinding;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.security.SetAccessibleAction;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptorBindingModel extends AbstractBindingModel {
   private static final Set META_ANNOTATIONS = Collections.singleton(InterceptorBinding.class);
   private Set inheritedInterceptionBindingTypes;
   private Set metaAnnotations;

   public InterceptorBindingModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      super(enhancedAnnotatedAnnotation);
   }

   protected void init(EnhancedAnnotation annotatedAnnotation) {
      super.init(annotatedAnnotation);
      if (this.isValid()) {
         this.initInterceptionBindingTypes(annotatedAnnotation);
         this.checkArrayAndAnnotationValuedMembers(annotatedAnnotation);
         this.checkMetaAnnotations(annotatedAnnotation);
         this.metaAnnotations = annotatedAnnotation.getAnnotations();
      }

   }

   protected Set getMetaAnnotationTypes() {
      return META_ANNOTATIONS;
   }

   public Set getMetaAnnotations() {
      return this.metaAnnotations;
   }

   protected void initInterceptionBindingTypes(EnhancedAnnotation annotatedAnnotation) {
      this.inheritedInterceptionBindingTypes = annotatedAnnotation.getMetaAnnotations(InterceptorBinding.class);
   }

   protected void check(EnhancedAnnotation annotatedAnnotation) {
      super.check(annotatedAnnotation);
      if (this.isValid()) {
         if (!annotatedAnnotation.isAnnotationPresent(Target.class)) {
            ReflectionLogger.LOG.missingTarget(annotatedAnnotation);
         }

         if (!isValidTargetType(annotatedAnnotation)) {
            ReflectionLogger.LOG.missingTargetTypeMethodOrTargetType(annotatedAnnotation);
         }
      }

   }

   private static boolean isValidTargetType(EnhancedAnnotation annotation) {
      Target target = (Target)annotation.getAnnotation(Target.class);
      return target != null && (Arrays2.unorderedEquals(target.value(), ElementType.TYPE, ElementType.METHOD) || Arrays2.unorderedEquals(target.value(), ElementType.TYPE));
   }

   private void checkMetaAnnotations(EnhancedAnnotation annotatedAnnotation) {
      ElementType[] elementTypes = this.getTargetElementTypes((Target)annotatedAnnotation.getAnnotation(Target.class));
      Iterator var3 = this.getInheritedInterceptionBindingTypes().iterator();

      while(var3.hasNext()) {
         Annotation inheritedBinding = (Annotation)var3.next();
         ElementType[] metaAnnotationElementTypes = this.getTargetElementTypes((Target)inheritedBinding.annotationType().getAnnotation(Target.class));
         if (!Arrays2.containsAll(metaAnnotationElementTypes, elementTypes)) {
            ReflectionLogger.LOG.invalidInterceptorBindingTargetDeclaration(inheritedBinding.annotationType().getName(), Arrays.toString(metaAnnotationElementTypes), annotatedAnnotation.getJavaClass().getName(), Arrays.toString(elementTypes));
         }
      }

   }

   private ElementType[] getTargetElementTypes(Target target) {
      return target == null ? ElementType.values() : target.value();
   }

   private void checkArrayAndAnnotationValuedMembers(EnhancedAnnotation annotatedAnnotation) {
      Iterator var2 = annotatedAnnotation.getMembers().iterator();

      EnhancedAnnotatedMethod annotatedMethod;
      do {
         do {
            if (!var2.hasNext()) {
               return;
            }

            annotatedMethod = (EnhancedAnnotatedMethod)var2.next();
         } while(!Reflections.isArrayType(annotatedMethod.getJavaClass()) && !Annotation.class.isAssignableFrom(annotatedMethod.getJavaClass()));
      } while(this.getNonBindingMembers().contains(annotatedMethod.slim()));

      throw MetadataLogger.LOG.nonBindingMemberTypeException(annotatedMethod);
   }

   public Set getInheritedInterceptionBindingTypes() {
      return this.inheritedInterceptionBindingTypes;
   }

   public boolean isEqual(Annotation instance, Annotation other) {
      return this.isEqual(instance, other, false);
   }

   public boolean isEqual(Annotation instance, Annotation other, boolean includeNonBindingTypes) {
      if (instance.annotationType().equals(this.getRawType()) && other.annotationType().equals(this.getRawType())) {
         Iterator var4 = this.getAnnotatedAnnotation().getMethods().iterator();

         while(true) {
            AnnotatedMethod annotatedMethod;
            do {
               if (!var4.hasNext()) {
                  return true;
               }

               annotatedMethod = (AnnotatedMethod)var4.next();
            } while(!includeNonBindingTypes && this.getNonBindingMembers().contains(annotatedMethod));

            try {
               AccessController.doPrivileged(SetAccessibleAction.of(annotatedMethod.getJavaMember()));
               Object thisValue = annotatedMethod.getJavaMember().invoke(instance);
               Object thatValue = annotatedMethod.getJavaMember().invoke(other);
               if (!thisValue.equals(thatValue)) {
                  return false;
               }
            } catch (IllegalArgumentException var8) {
               throw new WeldException(var8);
            } catch (IllegalAccessException var9) {
               throw new WeldException(var9);
            } catch (InvocationTargetException var10) {
               throw new WeldException(var10);
            }
         }
      } else {
         return false;
      }
   }
}
