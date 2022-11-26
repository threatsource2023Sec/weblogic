package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

interface AnnotationAttributeExtractor {
   Class getAnnotationType();

   @Nullable
   Object getAnnotatedElement();

   Object getSource();

   @Nullable
   Object getAttributeValue(Method var1);
}
