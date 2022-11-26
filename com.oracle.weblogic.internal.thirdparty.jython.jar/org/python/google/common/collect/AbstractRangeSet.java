package org.python.google.common.collect;

import java.util.Iterator;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
abstract class AbstractRangeSet implements RangeSet {
   public boolean contains(Comparable value) {
      return this.rangeContaining(value) != null;
   }

   public abstract Range rangeContaining(Comparable var1);

   public boolean isEmpty() {
      return this.asRanges().isEmpty();
   }

   public void add(Range range) {
      throw new UnsupportedOperationException();
   }

   public void remove(Range range) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      this.remove(Range.all());
   }

   public boolean enclosesAll(RangeSet other) {
      return this.enclosesAll((Iterable)other.asRanges());
   }

   public boolean enclosesAll(Iterable ranges) {
      Iterator var2 = ranges.iterator();

      Range range;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         range = (Range)var2.next();
      } while(this.encloses(range));

      return false;
   }

   public void addAll(RangeSet other) {
      this.addAll((Iterable)other.asRanges());
   }

   public void addAll(Iterable ranges) {
      Iterator var2 = ranges.iterator();

      while(var2.hasNext()) {
         Range range = (Range)var2.next();
         this.add(range);
      }

   }

   public void removeAll(RangeSet other) {
      this.removeAll((Iterable)other.asRanges());
   }

   public void removeAll(Iterable ranges) {
      Iterator var2 = ranges.iterator();

      while(var2.hasNext()) {
         Range range = (Range)var2.next();
         this.remove(range);
      }

   }

   public boolean intersects(Range otherRange) {
      return !this.subRangeSet(otherRange).isEmpty();
   }

   public abstract boolean encloses(Range var1);

   public boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (obj instanceof RangeSet) {
         RangeSet other = (RangeSet)obj;
         return this.asRanges().equals(other.asRanges());
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.asRanges().hashCode();
   }

   public final String toString() {
      return this.asRanges().toString();
   }
}
