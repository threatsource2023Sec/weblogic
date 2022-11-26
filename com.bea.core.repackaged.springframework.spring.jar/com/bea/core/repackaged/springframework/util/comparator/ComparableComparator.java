package com.bea.core.repackaged.springframework.util.comparator;

import java.util.Comparator;

public class ComparableComparator implements Comparator {
   public static final ComparableComparator INSTANCE = new ComparableComparator();

   public int compare(Comparable o1, Comparable o2) {
      return o1.compareTo(o2);
   }
}
