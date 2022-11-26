package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.annotation.Annotation;

public interface DeclareAnnotation {
   AjType getDeclaringType();

   Kind getKind();

   SignaturePattern getSignaturePattern();

   TypePattern getTypePattern();

   Annotation getAnnotation();

   String getAnnotationAsText();

   public static enum Kind {
      Field,
      Method,
      Constructor,
      Type;
   }
}
