package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;

public interface ImportAware extends Aware {
   void setImportMetadata(AnnotationMetadata var1);
}
