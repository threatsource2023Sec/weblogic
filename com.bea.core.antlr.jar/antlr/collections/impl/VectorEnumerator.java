package antlr.collections.impl;

import java.util.Enumeration;
import java.util.NoSuchElementException;

class VectorEnumerator implements Enumeration {
   Vector vector;
   int i;

   VectorEnumerator(Vector var1) {
      this.vector = var1;
      this.i = 0;
   }

   public boolean hasMoreElements() {
      synchronized(this.vector) {
         return this.i <= this.vector.lastElement;
      }
   }

   public Object nextElement() {
      synchronized(this.vector) {
         if (this.i <= this.vector.lastElement) {
            return this.vector.data[this.i++];
         } else {
            throw new NoSuchElementException("VectorEnumerator");
         }
      }
   }
}
