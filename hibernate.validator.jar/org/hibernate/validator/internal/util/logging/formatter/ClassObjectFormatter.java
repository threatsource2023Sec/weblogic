package org.hibernate.validator.internal.util.logging.formatter;

public class ClassObjectFormatter {
   private final String stringRepresentation;

   public ClassObjectFormatter(Class clazz) {
      this.stringRepresentation = clazz.getName();
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
