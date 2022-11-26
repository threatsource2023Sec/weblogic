package weblogic.xml.xpath.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class LayeredMap {
   private static final int EMPTY_OFFSET = -1;
   private static final int DEFAULT_INITIAL_CAPACITY = 10;
   private static final int DEFAULT_INCREMENT = 10;
   private int mCapacityIncrement;
   private Map[] mArray;
   private int mOffset;

   public LayeredMap() {
      this(10, 10);
   }

   public LayeredMap(int initialCapacity, int capacityIncrement) {
      this.mOffset = -1;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException();
      } else if (capacityIncrement < 1) {
         throw new IllegalArgumentException();
      } else {
         this.mCapacityIncrement = capacityIncrement;
         this.mArray = new Map[initialCapacity];
      }
   }

   public Collection values() {
      Collection out = new ArrayList();
      Iterator i = this.keySet().iterator();

      while(i.hasNext()) {
         out.add(this.get(i.next()));
      }

      return out;
   }

   public Set keySet() {
      Set out = new HashSet();

      for(int i = this.mOffset; i >= 0; --i) {
         out.addAll(this.mArray[i].keySet());
      }

      return out;
   }

   public void put(Object key, Object value) {
      this.mArray[this.mOffset].put(key, value);
   }

   public Object get(Object key) {
      for(int i = this.mOffset; i >= 0; --i) {
         Object out = this.mArray[i].get(key);
         if (out != null) {
            return out;
         }
      }

      return null;
   }

   public boolean isEmpty() {
      return this.mOffset == -1;
   }

   public void pop() {
      --this.mOffset;
   }

   public void push() {
      ++this.mOffset;
      if (this.mOffset == this.mArray.length) {
         Map[] old = this.mArray;
         this.mArray = new Map[old.length + this.mCapacityIncrement];
         System.arraycopy(old, 0, this.mArray, 0, old.length);
      }

      if (this.mArray[this.mOffset] == null) {
         this.mArray[this.mOffset] = new HashMap();
      } else {
         this.mArray[this.mOffset].clear();
      }

   }
}
