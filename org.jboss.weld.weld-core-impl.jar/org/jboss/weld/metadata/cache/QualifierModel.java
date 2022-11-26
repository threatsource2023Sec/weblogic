package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.inject.Qualifier;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.security.SetAccessibleAction;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

public class QualifierModel extends AbstractBindingModel {
   private static final Set META_ANNOTATIONS = Collections.singleton(Qualifier.class);

   public QualifierModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      super(enhancedAnnotatedAnnotation);
   }

   protected void initValid(EnhancedAnnotation annotatedAnnotation) {
      super.initValid(annotatedAnnotation);
      Iterator var2 = annotatedAnnotation.getMembers().iterator();

      while(true) {
         EnhancedAnnotatedMethod annotatedMethod;
         do {
            if (!var2.hasNext()) {
               return;
            }

            annotatedMethod = (EnhancedAnnotatedMethod)var2.next();
         } while(!Reflections.isArrayType(annotatedMethod.getJavaClass()) && !Annotation.class.isAssignableFrom(annotatedMethod.getJavaClass()));

         if (!this.getNonBindingMembers().contains(annotatedMethod.slim())) {
            MetadataLogger.LOG.nonBindingMemberType(annotatedMethod);
            super.valid = false;
         }
      }
   }

   protected void check(EnhancedAnnotation annotatedAnnotation) {
      super.check(annotatedAnnotation);
      if (this.isValid()) {
         if (!annotatedAnnotation.isAnnotationPresent(Target.class)) {
            ReflectionLogger.LOG.missingTarget(annotatedAnnotation);
         } else if (!Arrays2.unorderedEquals(((Target)annotatedAnnotation.getAnnotation(Target.class)).value(), ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE)) {
            ReflectionLogger.LOG.missingTargetMethodFieldParameterType(annotatedAnnotation);
         }
      }

   }

   protected Set getMetaAnnotationTypes() {
      return META_ANNOTATIONS;
   }

   public boolean hasNonBindingMembers() {
      return this.getNonBindingMembers().size() > 0;
   }

   public boolean isEqual(Annotation instance, Annotation other) {
      if (instance.annotationType().equals(this.getRawType()) && other.annotationType().equals(this.getRawType())) {
         Iterator var3 = this.getAnnotatedAnnotation().getMethods().iterator();

         while(var3.hasNext()) {
            AnnotatedMethod annotatedMethod = (AnnotatedMethod)var3.next();
            if (!this.getNonBindingMembers().contains(annotatedMethod)) {
               try {
                  AccessController.doPrivileged(SetAccessibleAction.of(annotatedMethod.getJavaMember()));
                  Object thisValue = annotatedMethod.getJavaMember().invoke(instance);
                  Object thatValue = annotatedMethod.getJavaMember().invoke(other);
                  if (!thisValue.equals(thatValue)) {
                     return false;
                  }
               } catch (IllegalArgumentException var7) {
                  throw new WeldException(var7);
               } catch (IllegalAccessException var8) {
                  throw new WeldException(var8);
               } catch (InvocationTargetException var9) {
                  throw new WeldException(var9);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return (this.isValid() ? "Valid" : "Invalid") + " qualifier model for " + this.getRawType() + " with non-binding members " + this.getNonBindingMembers();
   }
}
