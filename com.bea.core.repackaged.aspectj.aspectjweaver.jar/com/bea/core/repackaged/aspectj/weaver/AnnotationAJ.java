package com.bea.core.repackaged.aspectj.weaver;

import java.util.Set;

public interface AnnotationAJ {
   AnnotationAJ[] EMPTY_ARRAY = new AnnotationAJ[0];

   String getTypeSignature();

   String getTypeName();

   ResolvedType getType();

   boolean allowedOnAnnotationType();

   boolean allowedOnField();

   boolean allowedOnRegularType();

   Set getTargets();

   boolean hasNamedValue(String var1);

   boolean hasNameValuePair(String var1, String var2);

   String getValidTargets();

   String stringify();

   boolean specifiesTarget();

   boolean isRuntimeVisible();

   String getStringFormOfValue(String var1);
}
