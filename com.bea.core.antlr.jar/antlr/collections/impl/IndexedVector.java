package antlr.collections.impl;

import java.util.Enumeration;
import java.util.Hashtable;

public class IndexedVector {
   protected Vector elements;
   protected Hashtable index;

   public IndexedVector() {
      this.elements = new Vector(10);
      this.index = new Hashtable(10);
   }

   public IndexedVector(int var1) {
      this.elements = new Vector(var1);
      this.index = new Hashtable(var1);
   }

   public synchronized void appendElement(Object var1, Object var2) {
      this.elements.appendElement(var2);
      this.index.put(var1, var2);
   }

   public Object elementAt(int var1) {
      return this.elements.elementAt(var1);
   }

   public Enumeration elements() {
      return this.elements.elements();
   }

   public Object getElement(Object var1) {
      Object var2 = this.index.get(var1);
      return var2;
   }

   public synchronized boolean removeElement(Object var1) {
      Object var2 = this.index.get(var1);
      if (var2 == null) {
         return false;
      } else {
         this.index.remove(var1);
         this.elements.removeElement(var2);
         return false;
      }
   }

   public int size() {
      return this.elements.size();
   }
}
