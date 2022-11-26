package com.bea.core.repackaged.springframework.beans.annotation;

import com.bea.core.repackaged.springframework.beans.BeanWrapper;
import com.bea.core.repackaged.springframework.beans.PropertyAccessorFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AnnotationBeanUtils {
   public static void copyPropertiesToBean(Annotation ann, Object bean, String... excludedProperties) {
      copyPropertiesToBean(ann, bean, (StringValueResolver)null, excludedProperties);
   }

   public static void copyPropertiesToBean(Annotation ann, Object bean, @Nullable StringValueResolver valueResolver, String... excludedProperties) {
      Set excluded = excludedProperties.length == 0 ? Collections.emptySet() : new HashSet(Arrays.asList(excludedProperties));
      Method[] annotationProperties = ann.annotationType().getDeclaredMethods();
      BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(bean);
      Method[] var7 = annotationProperties;
      int var8 = annotationProperties.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Method annotationProperty = var7[var9];
         String propertyName = annotationProperty.getName();
         if (!((Set)excluded).contains(propertyName) && bw.isWritableProperty(propertyName)) {
            Object value = ReflectionUtils.invokeMethod(annotationProperty, ann);
            if (valueResolver != null && value instanceof String) {
               value = valueResolver.resolveStringValue((String)value);
            }

            bw.setPropertyValue(propertyName, value);
         }
      }

   }
}
