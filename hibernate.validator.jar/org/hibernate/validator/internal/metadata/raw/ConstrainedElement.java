package org.hibernate.validator.internal.metadata.raw;

import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;

public interface ConstrainedElement extends Iterable {
   ConstrainedElementKind getKind();

   Set getConstraints();

   Set getTypeArgumentConstraints();

   CascadingMetaDataBuilder getCascadingMetaDataBuilder();

   boolean isConstrained();

   ConfigurationSource getSource();

   public static enum ConstrainedElementKind {
      TYPE,
      FIELD,
      CONSTRUCTOR,
      METHOD,
      PARAMETER;
   }
}
