package org.python.google.common.collect;

import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true
)
final class ComparatorOrdering extends Ordering implements Serializable {
   final Comparator comparator;
   private static final long serialVersionUID = 0L;

   ComparatorOrdering(Comparator comparator) {
      this.comparator = (Comparator)Preconditions.checkNotNull(comparator);
   }

   public int compare(Object a, Object b) {
      return this.comparator.compare(a, b);
   }

   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (object instanceof ComparatorOrdering) {
         ComparatorOrdering that = (ComparatorOrdering)object;
         return this.comparator.equals(that.comparator);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.comparator.hashCode();
   }

   public String toString() {
      return this.comparator.toString();
   }
}
