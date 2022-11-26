package weblogic.corba.utils;

import java.util.Arrays;
import java.util.Random;

public final class AlternateIndirectionHashtable {
   private static final boolean DEBUG = false;
   private int[] bucketToHandleMap;
   private Object[] handleToObjectMap;
   private int[] handleMap;
   private int count;
   private float loadFactor;
   private int threshold;
   private int capacity;

   public AlternateIndirectionHashtable(int initialCapacity, float loadFactor) {
      if (initialCapacity > 0 && !((double)loadFactor <= 0.0)) {
         for(this.capacity = 1; this.capacity < initialCapacity; this.capacity <<= 1) {
         }

         this.loadFactor = loadFactor;
         this.bucketToHandleMap = new int[this.capacity];
         this.handleToObjectMap = new Object[this.capacity];
         this.handleMap = new int[this.capacity * 2];
         this.threshold = (int)(loadFactor * (float)this.capacity);
         this.clear();
      } else {
         throw new IllegalArgumentException();
      }
   }

   public AlternateIndirectionHashtable() {
      this(16, 3.0F);
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   private static final int hash(int key) {
      key += ~(key << 9);
      key ^= key >>> 14;
      key += key << 4;
      key ^= key >>> 10;
      return key;
   }

   public Object get(int key) {
      int index = hash(key) & this.bucketToHandleMap.length - 1;

      for(int i = this.bucketToHandleMap[index]; i >= 0; i = this.handleMap[i]) {
         if (this.handleMap[i + this.capacity] == key) {
            return this.handleToObjectMap[i];
         }
      }

      return null;
   }

   public Object put(int key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         return this.putUnchecked(key, value);
      }
   }

   private Object putUnchecked(int key, Object value) {
      if (this.count >= this.capacity) {
         this.increaseCapacity();
      }

      if (this.count >= this.threshold) {
         this.rehash();
      }

      int index = hash(key) & this.bucketToHandleMap.length - 1;
      this.handleToObjectMap[this.count] = value;
      this.handleMap[this.count] = this.bucketToHandleMap[index];
      this.handleMap[this.count + this.capacity] = key;
      this.bucketToHandleMap[index] = this.count++;
      return value;
   }

   public int reserve(int key) {
      this.putUnchecked(key, (Object)null);
      return this.count - 1;
   }

   public void putReserved(int index, int key, Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         this.handleToObjectMap[index] = value;
      }
   }

   private void increaseCapacity() {
      int newCapacity = this.capacity * 2 + 1;
      Object[] htom = new Object[newCapacity];
      System.arraycopy(this.handleToObjectMap, 0, htom, 0, this.count);
      this.handleToObjectMap = htom;
      int[] hm = new int[newCapacity * 2];
      System.arraycopy(this.handleMap, 0, hm, 0, this.count);
      System.arraycopy(this.handleMap, this.capacity, hm, newCapacity, this.count);
      this.handleMap = hm;
      this.capacity = newCapacity;
   }

   private void rehash() {
      int newCapacity = this.bucketToHandleMap.length << 1;
      this.bucketToHandleMap = new int[newCapacity];
      Arrays.fill(this.bucketToHandleMap, -1);
      this.threshold = (int)((float)newCapacity * this.loadFactor);

      int index;
      for(int i = 0; i < this.count; this.bucketToHandleMap[index] = i++) {
         int key = this.handleMap[i + this.capacity];
         index = hash(key) & newCapacity - 1;
         this.handleMap[i] = this.bucketToHandleMap[index];
      }

   }

   public void clear() {
      Arrays.fill(this.bucketToHandleMap, -1);
      Arrays.fill(this.handleToObjectMap, 0, this.count, (Object)null);
      this.count = 0;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("{");

      for(int i = 0; i < this.count; ++i) {
         buf.append("" + this.handleMap[i + this.capacity] + "=" + this.handleToObjectMap[i]);
         buf.append(", ");
      }

      buf.append("}");
      return buf.toString();
   }

   public static void main(String[] a) {
      IndirectionHashtable t = new IndirectionHashtable();
      Random rand = new Random(System.currentTimeMillis());
      int seed = rand.nextInt();
      int[] ls = new int[63];

      int i;
      for(i = 0; i < ls.length; ++i) {
         ls[i] = seed + i * 3;
         Object val = String.valueOf(ls[i]);
         t.put(ls[i], val);
         System.out.println("put: " + ls[i] + ", '" + val + "'");
      }

      System.out.println("TABLE: \n" + t);

      for(i = 0; i < ls.length; ++i) {
         Object o = t.get(ls[i]);
         if (o == null) {
            System.err.println("not found: " + ls[i]);
         } else if (!o.equals(String.valueOf(ls[i]))) {
            System.err.println(o + "!=" + ls[i]);
         } else {
            System.out.println("OK: " + o);
         }
      }

   }
}
