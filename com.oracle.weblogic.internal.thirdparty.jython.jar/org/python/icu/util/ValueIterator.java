package org.python.icu.util;

public interface ValueIterator {
   boolean next(Element var1);

   void reset();

   void setRange(int var1, int var2);

   public static final class Element {
      public int integer;
      public Object value;
   }
}
