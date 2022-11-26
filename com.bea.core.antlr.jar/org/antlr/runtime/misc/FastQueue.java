package org.antlr.runtime.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FastQueue {
   protected List data = new ArrayList();
   protected int p = 0;
   protected int range = -1;

   public void reset() {
      this.clear();
   }

   public void clear() {
      this.p = 0;
      this.data.clear();
   }

   public Object remove() {
      Object o = this.elementAt(0);
      ++this.p;
      if (this.p == this.data.size()) {
         this.clear();
      }

      return o;
   }

   public void add(Object o) {
      this.data.add(o);
   }

   public int size() {
      return this.data.size() - this.p;
   }

   public int range() {
      return this.range;
   }

   public Object head() {
      return this.elementAt(0);
   }

   public Object elementAt(int i) {
      int absIndex = this.p + i;
      if (absIndex >= this.data.size()) {
         throw new NoSuchElementException("queue index " + absIndex + " > last index " + (this.data.size() - 1));
      } else if (absIndex < 0) {
         throw new NoSuchElementException("queue index " + absIndex + " < 0");
      } else {
         if (absIndex > this.range) {
            this.range = absIndex;
         }

         return this.data.get(absIndex);
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      int n = this.size();

      for(int i = 0; i < n; ++i) {
         buf.append(this.elementAt(i));
         if (i + 1 < n) {
            buf.append(" ");
         }
      }

      return buf.toString();
   }
}
