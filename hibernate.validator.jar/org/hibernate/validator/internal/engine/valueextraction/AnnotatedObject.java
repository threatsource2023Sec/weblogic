package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class AnnotatedObject implements TypeVariable {
   public static final AnnotatedObject INSTANCE = new AnnotatedObject();

   private AnnotatedObject() {
   }

   public Annotation getAnnotation(Class annotationClass) {
      throw new UnsupportedOperationException();
   }

   public Annotation[] getAnnotations() {
      throw new UnsupportedOperationException();
   }

   public Annotation[] getDeclaredAnnotations() {
      throw new UnsupportedOperationException();
   }

   public Type[] getBounds() {
      throw new UnsupportedOperationException();
   }

   public Class getGenericDeclaration() {
      throw new UnsupportedOperationException();
   }

   public String getName() {
      throw new UnsupportedOperationException();
   }

   public AnnotatedType[] getAnnotatedBounds() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return "AnnotatedObject.INSTANCE";
   }
}
