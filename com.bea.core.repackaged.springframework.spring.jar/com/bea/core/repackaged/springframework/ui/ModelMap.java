package com.bea.core.repackaged.springframework.ui;

import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap extends LinkedHashMap {
   public ModelMap() {
   }

   public ModelMap(String attributeName, @Nullable Object attributeValue) {
      this.addAttribute(attributeName, attributeValue);
   }

   public ModelMap(Object attributeValue) {
      this.addAttribute(attributeValue);
   }

   public ModelMap addAttribute(String attributeName, @Nullable Object attributeValue) {
      Assert.notNull(attributeName, (String)"Model attribute name must not be null");
      this.put(attributeName, attributeValue);
      return this;
   }

   public ModelMap addAttribute(Object attributeValue) {
      Assert.notNull(attributeValue, "Model object must not be null");
      return attributeValue instanceof Collection && ((Collection)attributeValue).isEmpty() ? this : this.addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
   }

   public ModelMap addAllAttributes(@Nullable Collection attributeValues) {
      if (attributeValues != null) {
         Iterator var2 = attributeValues.iterator();

         while(var2.hasNext()) {
            Object attributeValue = var2.next();
            this.addAttribute(attributeValue);
         }
      }

      return this;
   }

   public ModelMap addAllAttributes(@Nullable Map attributes) {
      if (attributes != null) {
         this.putAll(attributes);
      }

      return this;
   }

   public ModelMap mergeAttributes(@Nullable Map attributes) {
      if (attributes != null) {
         attributes.forEach((key, value) -> {
            if (!this.containsKey(key)) {
               this.put(key, value);
            }

         });
      }

      return this;
   }

   public boolean containsAttribute(String attributeName) {
      return this.containsKey(attributeName);
   }
}
