package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class AnnotationReadingVisitorUtils {
   public static AnnotationAttributes convertClassValues(Object annotatedElement, @Nullable ClassLoader classLoader, AnnotationAttributes original, boolean classValuesAsString) {
      AnnotationAttributes result = new AnnotationAttributes(original);
      AnnotationUtils.postProcessAnnotationAttributes(annotatedElement, result, classValuesAsString);
      Iterator var5 = result.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();

         try {
            Object value = entry.getValue();
            if (value instanceof AnnotationAttributes) {
               value = convertClassValues(annotatedElement, classLoader, (AnnotationAttributes)value, classValuesAsString);
            } else if (value instanceof AnnotationAttributes[]) {
               AnnotationAttributes[] values = (AnnotationAttributes[])((AnnotationAttributes[])value);

               for(int i = 0; i < values.length; ++i) {
                  values[i] = convertClassValues(annotatedElement, classLoader, values[i], classValuesAsString);
               }

               value = values;
            } else if (value instanceof Type) {
               value = classValuesAsString ? ((Type)value).getClassName() : ClassUtils.forName(((Type)value).getClassName(), classLoader);
            } else {
               int i;
               if (value instanceof Type[]) {
                  Type[] array = (Type[])((Type[])value);
                  Object[] convArray = classValuesAsString ? new String[array.length] : new Class[array.length];

                  for(i = 0; i < array.length; ++i) {
                     ((Object[])convArray)[i] = classValuesAsString ? array[i].getClassName() : ClassUtils.forName(array[i].getClassName(), classLoader);
                  }

                  value = convArray;
               } else if (classValuesAsString) {
                  if (value instanceof Class) {
                     value = ((Class)value).getName();
                  } else if (value instanceof Class[]) {
                     Class[] clazzArray = (Class[])((Class[])value);
                     String[] newValue = new String[clazzArray.length];

                     for(i = 0; i < clazzArray.length; ++i) {
                        newValue[i] = clazzArray[i].getName();
                     }

                     value = newValue;
                  }
               }
            }

            entry.setValue(value);
         } catch (Throwable var11) {
            result.put(entry.getKey(), var11);
         }
      }

      return result;
   }

   @Nullable
   public static AnnotationAttributes getMergedAnnotationAttributes(LinkedMultiValueMap attributesMap, Map metaAnnotationMap, String annotationName) {
      List attributesList = attributesMap.get(annotationName);
      if (CollectionUtils.isEmpty((Collection)attributesList)) {
         return null;
      } else {
         AnnotationAttributes result = new AnnotationAttributes((AnnotationAttributes)attributesList.get(0));
         Set overridableAttributeNames = new HashSet(result.keySet());
         overridableAttributeNames.remove("value");
         List annotationTypes = new ArrayList(attributesMap.keySet());
         Collections.reverse(annotationTypes);
         annotationTypes.remove(annotationName);
         Iterator var7 = annotationTypes.iterator();

         while(true) {
            List currentAttributesList;
            Set metaAnns;
            do {
               do {
                  String currentAnnotationType;
                  do {
                     if (!var7.hasNext()) {
                        return result;
                     }

                     currentAnnotationType = (String)var7.next();
                     currentAttributesList = attributesMap.get(currentAnnotationType);
                  } while(ObjectUtils.isEmpty((Object)currentAttributesList));

                  metaAnns = (Set)metaAnnotationMap.get(currentAnnotationType);
               } while(metaAnns == null);
            } while(!metaAnns.contains(annotationName));

            AnnotationAttributes currentAttributes = (AnnotationAttributes)currentAttributesList.get(0);
            Iterator var12 = overridableAttributeNames.iterator();

            while(var12.hasNext()) {
               String overridableAttributeName = (String)var12.next();
               Object value = currentAttributes.get(overridableAttributeName);
               if (value != null) {
                  result.put(overridableAttributeName, value);
               }
            }
         }
      }
   }
}
