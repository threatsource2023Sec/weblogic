package org.python.icu.util;

public interface RangeValueIterator {
   boolean next(Element var1);

   void reset();

   public static class Element {
      public int start;
      public int limit;
      public int value;
   }
}
