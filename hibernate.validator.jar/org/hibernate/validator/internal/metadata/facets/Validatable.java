package org.hibernate.validator.internal.metadata.facets;

public interface Validatable {
   Iterable getCascadables();

   boolean hasCascadables();
}
