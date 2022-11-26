package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class MapAnnotationAttributeExtractor extends AbstractAliasAwareAnnotationAttributeExtractor {
   MapAnnotationAttributeExtractor(Map attributes, Class annotationType, @Nullable AnnotatedElement annotatedElement) {
      super(annotationType, annotatedElement, enrichAndValidateAttributes(attributes, annotationType));
   }

   @Nullable
   protected Object getRawAttributeValue(Method attributeMethod) {
      return this.getRawAttributeValue(attributeMethod.getName());
   }

   @Nullable
   protected Object getRawAttributeValue(String attributeName) {
      return ((Map)this.getSource()).get(attributeName);
   }

   private static Map enrichAndValidateAttributes(Map originalAttributes, Class annotationType) {
      Map attributes = new LinkedHashMap(originalAttributes);
      Map attributeAliasMap = AnnotationUtils.getAttributeAliasMap(annotationType);
      Iterator var4 = AnnotationUtils.getAttributeMethods(annotationType).iterator();

      while(true) {
         String attributeName;
         Object attributeValue;
         Object array;
         Class requiredReturnType;
         Class actualReturnType;
         do {
            if (!var4.hasNext()) {
               return attributes;
            }

            Method attributeMethod = (Method)var4.next();
            attributeName = attributeMethod.getName();
            attributeValue = attributes.get(attributeName);
            if (attributeValue == null) {
               List aliasNames = (List)attributeAliasMap.get(attributeName);
               if (aliasNames != null) {
                  Iterator var9 = aliasNames.iterator();

                  while(var9.hasNext()) {
                     String aliasName = (String)var9.next();
                     array = attributes.get(aliasName);
                     if (array != null) {
                        attributeValue = array;
                        attributes.put(attributeName, array);
                        break;
                     }
                  }
               }
            }

            if (attributeValue == null) {
               Object defaultValue = AnnotationUtils.getDefaultValue(annotationType, attributeName);
               if (defaultValue != null) {
                  attributeValue = defaultValue;
                  attributes.put(attributeName, defaultValue);
               }
            }

            Assert.notNull(attributeValue, () -> {
               return String.format("Attributes map %s returned null for required attribute '%s' defined by annotation type [%s].", attributes, attributeName, annotationType.getName());
            });
            requiredReturnType = attributeMethod.getReturnType();
            actualReturnType = attributeValue.getClass();
         } while(ClassUtils.isAssignable(requiredReturnType, actualReturnType));

         boolean converted = false;
         if (requiredReturnType.isArray() && requiredReturnType.getComponentType() == actualReturnType) {
            array = Array.newInstance(requiredReturnType.getComponentType(), 1);
            Array.set(array, 0, attributeValue);
            attributes.put(attributeName, array);
            converted = true;
         } else if (Annotation.class.isAssignableFrom(requiredReturnType) && Map.class.isAssignableFrom(actualReturnType)) {
            Map map = (Map)attributeValue;
            attributes.put(attributeName, AnnotationUtils.synthesizeAnnotation(map, requiredReturnType, (AnnotatedElement)null));
            converted = true;
         } else if (requiredReturnType.isArray() && actualReturnType.isArray() && Annotation.class.isAssignableFrom(requiredReturnType.getComponentType()) && Map.class.isAssignableFrom(actualReturnType.getComponentType())) {
            Class nestedAnnotationType = requiredReturnType.getComponentType();
            Map[] maps = (Map[])((Map[])attributeValue);
            attributes.put(attributeName, AnnotationUtils.synthesizeAnnotationArray(maps, nestedAnnotationType));
            converted = true;
         }

         Assert.isTrue(converted, () -> {
            return String.format("Attributes map %s returned a value of type [%s] for attribute '%s', but a value of type [%s] is required as defined by annotation type [%s].", attributes, actualReturnType.getName(), attributeName, requiredReturnType.getName(), annotationType.getName());
         });
      }
   }
}
