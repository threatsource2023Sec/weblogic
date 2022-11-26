package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.util.StringUtils;

public class QualifierEntry implements ParseState.Entry {
   private String typeName;

   public QualifierEntry(String typeName) {
      if (!StringUtils.hasText(typeName)) {
         throw new IllegalArgumentException("Invalid qualifier type '" + typeName + "'.");
      } else {
         this.typeName = typeName;
      }
   }

   public String toString() {
      return "Qualifier '" + this.typeName + "'";
   }
}
