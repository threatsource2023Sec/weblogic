package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;

interface ImportRegistry {
   @Nullable
   AnnotationMetadata getImportingClassFor(String var1);

   void removeImportingClass(String var1);
}
