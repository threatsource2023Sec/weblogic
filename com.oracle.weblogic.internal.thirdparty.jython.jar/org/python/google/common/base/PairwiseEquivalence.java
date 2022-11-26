package org.python.google.common.base;

import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
final class PairwiseEquivalence extends Equivalence implements Serializable {
   final Equivalence elementEquivalence;
   private static final long serialVersionUID = 1L;

   PairwiseEquivalence(Equivalence elementEquivalence) {
      this.elementEquivalence = (Equivalence)Preconditions.checkNotNull(elementEquivalence);
   }

   protected boolean doEquivalent(Iterable iterableA, Iterable iterableB) {
      Iterator iteratorA = iterableA.iterator();
      Iterator iteratorB = iterableB.iterator();

      while(iteratorA.hasNext() && iteratorB.hasNext()) {
         if (!this.elementEquivalence.equivalent(iteratorA.next(), iteratorB.next())) {
            return false;
         }
      }

      return !iteratorA.hasNext() && !iteratorB.hasNext();
   }

   protected int doHash(Iterable iterable) {
      int hash = 78721;

      Object element;
      for(Iterator var3 = iterable.iterator(); var3.hasNext(); hash = hash * 24943 + this.elementEquivalence.hash(element)) {
         element = var3.next();
      }

      return hash;
   }

   public boolean equals(@Nullable Object object) {
      if (object instanceof PairwiseEquivalence) {
         PairwiseEquivalence that = (PairwiseEquivalence)object;
         return this.elementEquivalence.equals(that.elementEquivalence);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.elementEquivalence.hashCode() ^ 1185147655;
   }

   public String toString() {
      return this.elementEquivalence + ".pairwise()";
   }
}
