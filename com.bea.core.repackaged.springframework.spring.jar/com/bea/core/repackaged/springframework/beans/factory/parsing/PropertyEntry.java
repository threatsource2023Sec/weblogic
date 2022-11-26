package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.util.StringUtils;

public class PropertyEntry implements ParseState.Entry {
   private final String name;

   public PropertyEntry(String name) {
      if (!StringUtils.hasText(name)) {
         throw new IllegalArgumentException("Invalid property name '" + name + "'.");
      } else {
         this.name = name;
      }
   }

   public String toString() {
      return "Property '" + this.name + "'";
   }
}
