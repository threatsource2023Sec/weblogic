package org.python.google.common.collect;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
final class ExplicitOrdering extends Ordering implements Serializable {
   final ImmutableMap rankMap;
   private static final long serialVersionUID = 0L;

   ExplicitOrdering(List valuesInOrder) {
      this(Maps.indexMap(valuesInOrder));
   }

   ExplicitOrdering(ImmutableMap rankMap) {
      this.rankMap = rankMap;
   }

   public int compare(Object left, Object right) {
      return this.rank(left) - this.rank(right);
   }

   private int rank(Object value) {
      Integer rank = (Integer)this.rankMap.get(value);
      if (rank == null) {
         throw new Ordering.IncomparableValueException(value);
      } else {
         return rank;
      }
   }

   public boolean equals(@Nullable Object object) {
      if (object instanceof ExplicitOrdering) {
         ExplicitOrdering that = (ExplicitOrdering)object;
         return this.rankMap.equals(that.rankMap);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.rankMap.hashCode();
   }

   public String toString() {
      return "Ordering.explicit(" + this.rankMap.keySet() + ")";
   }
}
