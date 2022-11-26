package com.bea.core.repackaged.springframework.util.comparator;

import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.util.Comparator;

/** @deprecated */
@Deprecated
public class InvertibleComparator implements Comparator, Serializable {
   private final Comparator comparator;
   private boolean ascending = true;

   public InvertibleComparator(Comparator comparator) {
      Assert.notNull(comparator, (String)"Comparator must not be null");
      this.comparator = comparator;
   }

   public InvertibleComparator(Comparator comparator, boolean ascending) {
      Assert.notNull(comparator, (String)"Comparator must not be null");
      this.comparator = comparator;
      this.setAscending(ascending);
   }

   public void setAscending(boolean ascending) {
      this.ascending = ascending;
   }

   public boolean isAscending() {
      return this.ascending;
   }

   public void invertOrder() {
      this.ascending = !this.ascending;
   }

   public int compare(Object o1, Object o2) {
      int result = this.comparator.compare(o1, o2);
      if (result != 0) {
         if (!this.ascending) {
            if (Integer.MIN_VALUE == result) {
               result = Integer.MAX_VALUE;
            } else {
               result *= -1;
            }
         }

         return result;
      } else {
         return 0;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof InvertibleComparator)) {
         return false;
      } else {
         InvertibleComparator otherComp = (InvertibleComparator)other;
         return this.comparator.equals(otherComp.comparator) && this.ascending == otherComp.ascending;
      }
   }

   public int hashCode() {
      return this.comparator.hashCode();
   }

   public String toString() {
      return "InvertibleComparator: [" + this.comparator + "]; ascending=" + this.ascending;
   }
}
