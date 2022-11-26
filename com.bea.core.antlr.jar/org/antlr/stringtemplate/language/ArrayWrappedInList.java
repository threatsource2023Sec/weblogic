package org.antlr.stringtemplate.language;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class ArrayWrappedInList extends ArrayList {
   protected Object array = null;
   protected int n;

   public ArrayWrappedInList(Object array) {
      this.array = array;
      this.n = Array.getLength(array);
   }

   public Object get(int i) {
      return Array.get(this.array, i);
   }

   public int size() {
      return this.n;
   }

   public boolean isEmpty() {
      return this.n == 0;
   }

   public Object[] toArray() {
      return (Object[])((Object[])this.array);
   }

   public Iterator iterator() {
      return new ArrayIterator(this.array);
   }
}
