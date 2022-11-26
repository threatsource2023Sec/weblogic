package org.hibernate.validator.internal.util.logging.formatter;

import java.lang.reflect.Type;

public class TypeFormatter {
   private final String stringRepresentation;

   public TypeFormatter(Type type) {
      this.stringRepresentation = type.getTypeName();
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
