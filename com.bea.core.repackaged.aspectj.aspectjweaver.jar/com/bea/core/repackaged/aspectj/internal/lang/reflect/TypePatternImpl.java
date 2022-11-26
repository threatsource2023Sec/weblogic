package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.TypePattern;

public class TypePatternImpl implements TypePattern {
   private String typePattern;

   public TypePatternImpl(String pattern) {
      this.typePattern = pattern;
   }

   public String asString() {
      return this.typePattern;
   }

   public String toString() {
      return this.asString();
   }
}
