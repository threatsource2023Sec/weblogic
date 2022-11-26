package com.bea.core.repackaged.aspectj.weaver;

public interface AnnotatedElement {
   boolean hasAnnotation(UnresolvedType var1);

   ResolvedType[] getAnnotationTypes();

   AnnotationAJ getAnnotationOfType(UnresolvedType var1);
}
