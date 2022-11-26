package org.jboss.weld.metadata.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Set;
import javax.enterprise.context.NormalScope;
import javax.inject.Scope;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.collections.Arrays2;

public class ScopeModel extends AnnotationModel {
   private static final Set META_ANNOTATIONS = Arrays2.asSet(Scope.class, NormalScope.class);
   private final boolean normal;
   private final boolean passivating;

   public ScopeModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      super(enhancedAnnotatedAnnotation);
      if (this.isValid()) {
         if (enhancedAnnotatedAnnotation.isAnnotationPresent(NormalScope.class)) {
            this.passivating = ((NormalScope)enhancedAnnotatedAnnotation.getAnnotation(NormalScope.class)).passivating();
            this.normal = true;
         } else {
            this.normal = false;
            this.passivating = false;
         }
      } else {
         this.normal = false;
         this.passivating = false;
      }

   }

   protected void check(EnhancedAnnotation annotatedAnnotation) {
      super.check(annotatedAnnotation);
      if (this.isValid()) {
         if (!annotatedAnnotation.isAnnotationPresent(Target.class)) {
            ReflectionLogger.LOG.missingTarget(annotatedAnnotation);
         } else if (!Arrays2.unorderedEquals(((Target)annotatedAnnotation.getAnnotation(Target.class)).value(), ElementType.METHOD, ElementType.FIELD, ElementType.TYPE)) {
            ReflectionLogger.LOG.missingTargetMethodFieldType(annotatedAnnotation);
         }
      }

   }

   public boolean isNormal() {
      return this.normal;
   }

   public boolean isPassivating() {
      return this.passivating;
   }

   protected Set getMetaAnnotationTypes() {
      return META_ANNOTATIONS;
   }

   public String toString() {
      String valid = this.isValid() ? "Valid " : "Invalid";
      String normal = this.isNormal() ? "normal " : "non-normal ";
      String passivating = this.isPassivating() ? "passivating " : "pon-passivating ";
      return valid + normal + passivating + " scope model for " + this.getRawType();
   }
}
