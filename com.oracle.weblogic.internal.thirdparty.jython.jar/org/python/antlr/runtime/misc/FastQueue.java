package org.python.antlr.runtime.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FastQueue {
   protected List data = new ArrayList();
   protected int p = 0;

   public void reset() {
      this.p = 0;
      this.data.clear();
   }

   public Object remove() {
      Object o = this.get(0);
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

   public Object head() {
      return this.get(0);
   }

   public Object get(int i) {
      if (this.p + i >= this.data.size()) {
         throw new NoSuchElementException("queue index " + (this.p + i) + " > size " + this.data.size());
      } else {
         return this.data.get(this.p + i);
      }
   }

   public void clear() {
      this.p = 0;
      this.data.clear();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      int n = this.size();

      for(int i = 0; i < n; ++i) {
         buf.append(this.get(i));
         if (i + 1 < n) {
            buf.append(" ");
         }
      }

      return buf.toString();
   }
}
