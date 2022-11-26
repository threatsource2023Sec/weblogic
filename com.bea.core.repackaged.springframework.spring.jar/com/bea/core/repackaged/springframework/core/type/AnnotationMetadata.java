package com.bea.core.repackaged.springframework.core.type;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {
   Set getAnnotationTypes();

   Set getMetaAnnotationTypes(String var1);

   boolean hasAnnotation(String var1);

   boolean hasMetaAnnotation(String var1);

   boolean hasAnnotatedMethods(String var1);

   Set getAnnotatedMethods(String var1);
}
