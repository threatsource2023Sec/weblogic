package org.hibernate.validator.internal.util.logging.formatter;

import java.util.Collection;
import org.hibernate.validator.internal.util.StringHelper;

public class CollectionOfObjectsToStringFormatter {
   private final String stringRepresentation;

   public CollectionOfObjectsToStringFormatter(Collection objects) {
      this.stringRepresentation = StringHelper.join((Iterable)objects, ", ");
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
