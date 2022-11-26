package org.hibernate.validator.internal.util.logging.formatter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayOfClassesObjectFormatter {
   private final String stringRepresentation;

   public ArrayOfClassesObjectFormatter(Class[] classes) {
      this.stringRepresentation = (String)Arrays.stream(classes).map((c) -> {
         return c.getName();
      }).collect(Collectors.joining(", "));
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
