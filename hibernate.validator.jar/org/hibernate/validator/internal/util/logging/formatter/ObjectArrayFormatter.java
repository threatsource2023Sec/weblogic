package org.hibernate.validator.internal.util.logging.formatter;

import java.util.Arrays;

public class ObjectArrayFormatter {
   private final String stringRepresentation;

   public ObjectArrayFormatter(Object[] array) {
      this.stringRepresentation = Arrays.toString(array);
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
