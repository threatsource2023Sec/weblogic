package com.bea.core.repackaged.springframework.util.comparator;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Comparator;

public class NullSafeComparator implements Comparator {
   public static final NullSafeComparator NULLS_LOW = new NullSafeComparator(true);
   public static final NullSafeComparator NULLS_HIGH = new NullSafeComparator(false);
   private final Comparator nonNullComparator;
   private final boolean nullsLow;

   private NullSafeComparator(boolean nullsLow) {
      this.nonNullComparator = ComparableComparator.INSTANCE;
      this.nullsLow = nullsLow;
   }

   public NullSafeComparator(Comparator comparator, boolean nullsLow) {
      Assert.notNull(comparator, (String)"Non-null Comparator is required");
      this.nonNullComparator = comparator;
      this.nullsLow = nullsLow;
   }

   public int compare(@Nullable Object o1, @Nullable Object o2) {
      if (o1 == o2) {
         return 0;
      } else if (o1 == null) {
         return this.nullsLow ? -1 : 1;
      } else if (o2 == null) {
         return this.nullsLow ? 1 : -1;
      } else {
         return this.nonNullComparator.compare(o1, o2);
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NullSafeComparator)) {
         return false;
      } else {
         NullSafeComparator otherComp = (NullSafeComparator)other;
         return this.nonNullComparator.equals(otherComp.nonNullComparator) && this.nullsLow == otherComp.nullsLow;
      }
   }

   public int hashCode() {
      return this.nonNullComparator.hashCode() * (this.nullsLow ? -1 : 1);
   }

   public String toString() {
      return "NullSafeComparator: non-null comparator [" + this.nonNullComparator + "]; " + (this.nullsLow ? "nulls low" : "nulls high");
   }
}
