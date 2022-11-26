package org.hibernate.validator.internal.metadata.aggregated;

import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ValidatableParametersMetaData implements Validatable {
   private final List parameterMetaData;
   private final List cascadables;

   public ValidatableParametersMetaData(List parameterMetaData) {
      this.parameterMetaData = CollectionHelper.toImmutableList(parameterMetaData);
      this.cascadables = CollectionHelper.toImmutableList((List)parameterMetaData.stream().filter((p) -> {
         return p.isCascading();
      }).collect(Collectors.toList()));
   }

   public Iterable getCascadables() {
      return this.cascadables;
   }

   public boolean hasCascadables() {
      return !this.cascadables.isEmpty();
   }
}
