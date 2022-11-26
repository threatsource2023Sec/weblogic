package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.SignaturePattern;

public class SignaturePatternImpl implements SignaturePattern {
   private String sigPattern;

   public SignaturePatternImpl(String pattern) {
      this.sigPattern = pattern;
   }

   public String asString() {
      return this.sigPattern;
   }

   public String toString() {
      return this.asString();
   }
}
