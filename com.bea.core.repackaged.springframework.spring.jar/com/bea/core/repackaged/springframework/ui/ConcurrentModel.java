package com.bea.core.repackaged.springframework.ui;

import com.bea.core.repackaged.springframework.core.Conventions;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentModel extends ConcurrentHashMap implements Model {
   public ConcurrentModel() {
   }

   public ConcurrentModel(String attributeName, Object attributeValue) {
      this.addAttribute(attributeName, attributeValue);
   }

   public ConcurrentModel(Object attributeValue) {
      this.addAttribute(attributeValue);
   }

   public Object put(String key, Object value) {
      return value != null ? super.put(key, value) : this.remove(key);
   }

   public void putAll(Map map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put((String)entry.getKey(), entry.getValue());
      }

   }

   public ConcurrentModel addAttribute(String attributeName, @Nullable Object attributeValue) {
      Assert.notNull(attributeName, (String)"Model attribute name must not be null");
      this.put(attributeName, attributeValue);
      return this;
   }

   public ConcurrentModel addAttribute(Object attributeValue) {
      Assert.notNull(attributeValue, "Model attribute value must not be null");
      return attributeValue instanceof Collection && ((Collection)attributeValue).isEmpty() ? this : this.addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
   }

   public ConcurrentModel addAllAttributes(@Nullable Collection attributeValues) {
      if (attributeValues != null) {
         Iterator var2 = attributeValues.iterator();

         while(var2.hasNext()) {
            Object attributeValue = var2.next();
            this.addAttribute(attributeValue);
         }
      }

      return this;
   }

   public ConcurrentModel addAllAttributes(@Nullable Map attributes) {
      if (attributes != null) {
         this.putAll(attributes);
      }

      return this;
   }

   public ConcurrentModel mergeAttributes(@Nullable Map attributes) {
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

   public Map asMap() {
      return this;
   }
}
