package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class DefaultAnnotationAttributeExtractor extends AbstractAliasAwareAnnotationAttributeExtractor {
   DefaultAnnotationAttributeExtractor(Annotation annotation, @Nullable Object annotatedElement) {
      super(annotation.annotationType(), annotatedElement, annotation);
   }

   @Nullable
   protected Object getRawAttributeValue(Method attributeMethod) {
      ReflectionUtils.makeAccessible(attributeMethod);
      return ReflectionUtils.invokeMethod(attributeMethod, this.getSource());
   }

   @Nullable
   protected Object getRawAttributeValue(String attributeName) {
      Method attributeMethod = ReflectionUtils.findMethod(this.getAnnotationType(), attributeName);
      return attributeMethod != null ? this.getRawAttributeValue(attributeMethod) : null;
   }
}
