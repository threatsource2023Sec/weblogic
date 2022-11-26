package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Field;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class EnhancedAnnotatedFieldImpl extends AbstractEnhancedAnnotatedMember implements EnhancedAnnotatedField {
   private final AnnotatedField slim;

   public static EnhancedAnnotatedFieldImpl of(AnnotatedField annotatedField, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      EnhancedAnnotatedType downcastDeclaringType = (EnhancedAnnotatedType)Reflections.cast(declaringClass);
      return new EnhancedAnnotatedFieldImpl(annotatedField, buildAnnotationMap(annotatedField.getAnnotations()), buildAnnotationMap(annotatedField.getAnnotations()), downcastDeclaringType, classTransformer);
   }

   private EnhancedAnnotatedFieldImpl(AnnotatedField annotatedField, Map annotationMap, Map declaredAnnotationMap, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      super(annotatedField, annotationMap, declaredAnnotationMap, classTransformer, declaringClass);
      this.slim = annotatedField;
   }

   public Field getAnnotatedField() {
      return this.slim.getJavaMember();
   }

   public Field getDelegate() {
      return this.slim.getJavaMember();
   }

   public String getPropertyName() {
      return this.getName();
   }

   public String toString() {
      return Formats.formatAnnotatedField(this);
   }

   public boolean isGeneric() {
      return false;
   }

   public AnnotatedField slim() {
      return this.slim;
   }
}
