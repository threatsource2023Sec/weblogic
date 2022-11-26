package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.Target;

@Target({})
public @interface TargetFacet {
   int typeNum();

   String value();

   public static class Type {
      public static final int LENGTH = 0;
      public static final int MIN_LENGTH = 1;
      public static final int MAX_LENGTH = 2;
      public static final int PATTERN = 10;
      public static final int ENUMERATION = 11;
      public static final int WHITESPACE = 11;
      public static final int MAX_INCLUSIVE = 5;
      public static final int MAX_EXCLUSIVE = 6;
      public static final int MIN_INCLUSIVE = 4;
      public static final int MIN_EXCLUSIVE = 3;
      public static final int TOTAL_DIGITS = 7;
      public static final int FRACTION_DIGITS = 8;
   }
}
