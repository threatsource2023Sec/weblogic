package org.hibernate.validator.internal.util.logging.formatter;

import java.util.Collection;
import java.util.stream.Collectors;

public class CollectionOfClassesObjectFormatter {
   private final String stringRepresentation;

   public CollectionOfClassesObjectFormatter(Collection classes) {
      this.stringRepresentation = (String)classes.stream().map((c) -> {
         return c.getName();
      }).collect(Collectors.joining(", "));
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
