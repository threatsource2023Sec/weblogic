package com.bea.core.repackaged.springframework.util.comparator;

import java.util.Comparator;

public abstract class Comparators {
   public static Comparator comparable() {
      return ComparableComparator.INSTANCE;
   }

   public static Comparator nullsLow() {
      return NullSafeComparator.NULLS_LOW;
   }

   public static Comparator nullsLow(Comparator comparator) {
      return new NullSafeComparator(comparator, true);
   }

   public static Comparator nullsHigh() {
      return NullSafeComparator.NULLS_HIGH;
   }

   public static Comparator nullsHigh(Comparator comparator) {
      return new NullSafeComparator(comparator, false);
   }
}
