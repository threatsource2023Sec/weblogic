package com.bea.core.repackaged.springframework.ui;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Map;

public class ExtendedModelMap extends ModelMap implements Model {
   public ExtendedModelMap addAttribute(String attributeName, @Nullable Object attributeValue) {
      super.addAttribute(attributeName, attributeValue);
      return this;
   }

   public ExtendedModelMap addAttribute(Object attributeValue) {
      super.addAttribute(attributeValue);
      return this;
   }

   public ExtendedModelMap addAllAttributes(@Nullable Collection attributeValues) {
      super.addAllAttributes(attributeValues);
      return this;
   }

   public ExtendedModelMap addAllAttributes(@Nullable Map attributes) {
      super.addAllAttributes(attributes);
      return this;
   }

   public ExtendedModelMap mergeAttributes(@Nullable Map attributes) {
      super.mergeAttributes(attributes);
      return this;
   }

   public Map asMap() {
      return this;
   }
}
