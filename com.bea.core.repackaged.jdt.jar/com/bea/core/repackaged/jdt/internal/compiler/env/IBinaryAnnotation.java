package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface IBinaryAnnotation {
   char[] getTypeName();

   IBinaryElementValuePair[] getElementValuePairs();

   default boolean isExternalAnnotation() {
      return false;
   }

   default boolean isDeprecatedAnnotation() {
      return false;
   }
}
