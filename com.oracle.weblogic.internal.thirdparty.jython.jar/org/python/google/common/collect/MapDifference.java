package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface MapDifference {
   boolean areEqual();

   Map entriesOnlyOnLeft();

   Map entriesOnlyOnRight();

   Map entriesInCommon();

   Map entriesDiffering();

   boolean equals(@Nullable Object var1);

   int hashCode();

   public interface ValueDifference {
      Object leftValue();

      Object rightValue();

      boolean equals(@Nullable Object var1);

      int hashCode();
   }
}
