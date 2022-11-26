package com.bea.core.repackaged.springframework.util.comparator;

import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/** @deprecated */
@Deprecated
public class CompoundComparator implements Comparator, Serializable {
   private final List comparators;

   public CompoundComparator() {
      this.comparators = new ArrayList();
   }

   public CompoundComparator(Comparator... comparators) {
      Assert.notNull(comparators, (String)"Comparators must not be null");
      this.comparators = new ArrayList(comparators.length);
      Comparator[] var2 = comparators;
      int var3 = comparators.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Comparator comparator = var2[var4];
         this.addComparator(comparator);
      }

   }

   public void addComparator(Comparator comparator) {
      if (comparator instanceof InvertibleComparator) {
         this.comparators.add((InvertibleComparator)comparator);
      } else {
         this.comparators.add(new InvertibleComparator(comparator));
      }

   }

   public void addComparator(Comparator comparator, boolean ascending) {
      this.comparators.add(new InvertibleComparator(comparator, ascending));
   }

   public void setComparator(int index, Comparator comparator) {
      if (comparator instanceof InvertibleComparator) {
         this.comparators.set(index, (InvertibleComparator)comparator);
      } else {
         this.comparators.set(index, new InvertibleComparator(comparator));
      }

   }

   public void setComparator(int index, Comparator comparator, boolean ascending) {
      this.comparators.set(index, new InvertibleComparator(comparator, ascending));
   }

   public void invertOrder() {
      Iterator var1 = this.comparators.iterator();

      while(var1.hasNext()) {
         InvertibleComparator comparator = (InvertibleComparator)var1.next();
         comparator.invertOrder();
      }

   }

   public void invertOrder(int index) {
      ((InvertibleComparator)this.comparators.get(index)).invertOrder();
   }

   public void setAscendingOrder(int index) {
      ((InvertibleComparator)this.comparators.get(index)).setAscending(true);
   }

   public void setDescendingOrder(int index) {
      ((InvertibleComparator)this.comparators.get(index)).setAscending(false);
   }

   public int getComparatorCount() {
      return this.comparators.size();
   }

   public int compare(Object o1, Object o2) {
      Assert.state(!this.comparators.isEmpty(), "No sort definitions have been added to this CompoundComparator to compare");
      Iterator var3 = this.comparators.iterator();

      int result;
      do {
         if (!var3.hasNext()) {
            return 0;
         }

         InvertibleComparator comparator = (InvertibleComparator)var3.next();
         result = comparator.compare(o1, o2);
      } while(result == 0);

      return result;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof CompoundComparator && this.comparators.equals(((CompoundComparator)other).comparators);
   }

   public int hashCode() {
      return this.comparators.hashCode();
   }

   public String toString() {
      return "CompoundComparator: " + this.comparators;
   }
}
