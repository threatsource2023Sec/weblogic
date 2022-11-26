package jnr.ffi.util;

import java.lang.annotation.Annotation;
import java.util.Comparator;

final class AnnotationNameComparator implements Comparator {
   static final Comparator INSTANCE = new AnnotationNameComparator();

   public static Comparator getInstance() {
      return INSTANCE;
   }

   public int compare(Annotation o1, Annotation o2) {
      return o1.annotationType().getName().compareTo(o2.annotationType().getName());
   }

   public boolean equals(Object other) {
      return other != null && this.getClass().equals(other.getClass());
   }
}
