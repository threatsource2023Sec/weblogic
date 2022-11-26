package org.python.google.common.collect;

import java.io.Serializable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true
)
final class NaturalOrdering extends Ordering implements Serializable {
   static final NaturalOrdering INSTANCE = new NaturalOrdering();
   private transient Ordering nullsFirst;
   private transient Ordering nullsLast;
   private static final long serialVersionUID = 0L;

   public int compare(Comparable left, Comparable right) {
      Preconditions.checkNotNull(left);
      Preconditions.checkNotNull(right);
      return left.compareTo(right);
   }

   public Ordering nullsFirst() {
      Ordering result = this.nullsFirst;
      if (result == null) {
         result = this.nullsFirst = super.nullsFirst();
      }

      return result;
   }

   public Ordering nullsLast() {
      Ordering result = this.nullsLast;
      if (result == null) {
         result = this.nullsLast = super.nullsLast();
      }

      return result;
   }

   public Ordering reverse() {
      return ReverseNaturalOrdering.INSTANCE;
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public String toString() {
      return "Ordering.natural()";
   }

   private NaturalOrdering() {
   }
}
