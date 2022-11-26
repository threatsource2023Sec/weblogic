package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract class AbstractAliasAwareAnnotationAttributeExtractor implements AnnotationAttributeExtractor {
   private final Class annotationType;
   @Nullable
   private final Object annotatedElement;
   private final Object source;
   private final Map attributeAliasMap;

   AbstractAliasAwareAnnotationAttributeExtractor(Class annotationType, @Nullable Object annotatedElement, Object source) {
      Assert.notNull(annotationType, (String)"annotationType must not be null");
      Assert.notNull(source, "source must not be null");
      this.annotationType = annotationType;
      this.annotatedElement = annotatedElement;
      this.source = source;
      this.attributeAliasMap = AnnotationUtils.getAttributeAliasMap(annotationType);
   }

   public final Class getAnnotationType() {
      return this.annotationType;
   }

   @Nullable
   public final Object getAnnotatedElement() {
      return this.annotatedElement;
   }

   public final Object getSource() {
      return this.source;
   }

   @Nullable
   public final Object getAttributeValue(Method attributeMethod) {
      String attributeName = attributeMethod.getName();
      Object attributeValue = this.getRawAttributeValue(attributeMethod);
      List aliasNames = (List)this.attributeAliasMap.get(attributeName);
      if (aliasNames != null) {
         Object defaultValue = AnnotationUtils.getDefaultValue(this.annotationType, attributeName);
         Iterator var6 = aliasNames.iterator();

         while(var6.hasNext()) {
            String aliasName = (String)var6.next();
            Object aliasValue = this.getRawAttributeValue(aliasName);
            if (!ObjectUtils.nullSafeEquals(attributeValue, aliasValue) && !ObjectUtils.nullSafeEquals(attributeValue, defaultValue) && !ObjectUtils.nullSafeEquals(aliasValue, defaultValue)) {
               String elementName = this.annotatedElement != null ? this.annotatedElement.toString() : "unknown element";
               throw new AnnotationConfigurationException(String.format("In annotation [%s] declared on %s and synthesized from [%s], attribute '%s' and its alias '%s' are present with values of [%s] and [%s], but only one is permitted.", this.annotationType.getName(), elementName, this.source, attributeName, aliasName, ObjectUtils.nullSafeToString(attributeValue), ObjectUtils.nullSafeToString(aliasValue)));
            }

            if (ObjectUtils.nullSafeEquals(attributeValue, defaultValue)) {
               attributeValue = aliasValue;
            }
         }
      }

      return attributeValue;
   }

   @Nullable
   protected abstract Object getRawAttributeValue(Method var1);

   @Nullable
   protected abstract Object getRawAttributeValue(String var1);
}
