package com.bea.core.repackaged.springframework.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

public class BooleanComparator implements Comparator, Serializable {
   public static final BooleanComparator TRUE_LOW = new BooleanComparator(true);
   public static final BooleanComparator TRUE_HIGH = new BooleanComparator(false);
   private final boolean trueLow;

   public BooleanComparator(boolean trueLow) {
      this.trueLow = trueLow;
   }

   public int compare(Boolean v1, Boolean v2) {
      return v1 ^ v2 ? (v1 ^ this.trueLow ? 1 : -1) : 0;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof BooleanComparator && this.trueLow == ((BooleanComparator)other).trueLow;
   }

   public int hashCode() {
      return this.getClass().hashCode() * (this.trueLow ? -1 : 1);
   }

   public String toString() {
      return "BooleanComparator: " + (this.trueLow ? "true low" : "true high");
   }
}
