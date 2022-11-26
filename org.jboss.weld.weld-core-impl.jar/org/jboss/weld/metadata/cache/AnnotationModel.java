package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.logging.ReflectionLogger;

public abstract class AnnotationModel {
   private final AnnotatedType annotatedAnnotation;
   protected boolean valid;

   public AnnotationModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      this.annotatedAnnotation = enhancedAnnotatedAnnotation.slim();
      this.init(enhancedAnnotatedAnnotation);
   }

   protected void init(EnhancedAnnotation annotatedAnnotation) {
      this.initType(annotatedAnnotation);
      this.initValid(annotatedAnnotation);
      this.check(annotatedAnnotation);
   }

   protected void initType(EnhancedAnnotation annotatedAnnotation) {
      if (!Annotation.class.isAssignableFrom(this.getRawType())) {
         throw MetadataLogger.LOG.metaAnnotationOnWrongType(this.getMetaAnnotationTypes(), this.getRawType());
      }
   }

   protected void initValid(EnhancedAnnotation annotatedAnnotation) {
      this.valid = false;
      Iterator var2 = this.getMetaAnnotationTypes().iterator();

      while(var2.hasNext()) {
         Class annotationType = (Class)var2.next();
         if (annotatedAnnotation.isAnnotationPresent(annotationType)) {
            this.valid = true;
         }
      }

   }

   protected void check(EnhancedAnnotation annotatedAnnotation) {
      if (this.valid && (!annotatedAnnotation.isAnnotationPresent(Retention.class) || annotatedAnnotation.isAnnotationPresent(Retention.class) && !((Retention)annotatedAnnotation.getAnnotation(Retention.class)).value().equals(RetentionPolicy.RUNTIME))) {
         this.valid = false;
         ReflectionLogger.LOG.missingRetention(annotatedAnnotation);
      }

   }

   public Class getRawType() {
      return this.annotatedAnnotation.getJavaClass();
   }

   protected abstract Set getMetaAnnotationTypes();

   public boolean isValid() {
      return this.valid;
   }

   public AnnotatedType getAnnotatedAnnotation() {
      return this.annotatedAnnotation;
   }

   public String toString() {
      return (this.isValid() ? "Valid" : "Invalid") + " annotation model for " + this.getRawType();
   }
}
