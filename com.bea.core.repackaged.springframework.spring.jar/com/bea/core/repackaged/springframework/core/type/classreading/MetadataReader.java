package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.ClassMetadata;

public interface MetadataReader {
   Resource getResource();

   ClassMetadata getClassMetadata();

   AnnotationMetadata getAnnotationMetadata();
}
