package weblogic.corba.utils;

import java.util.Arrays;
import java.util.Random;

public final class IndirectionValueHashtable {
   private static final boolean DEBUG = false;
   private int[] bucketToHandleMap;
   private Object[] handleToObjectMap;
   private int[] handleMap;
   private int count;
   private int threshold;
   private int capacity;
   private float loadFactor;

   public IndirectionValueHashtable(int initialCapacity, float loadFactor) {
      if (initialCapacity > 0 && !((double)loadFactor <= 0.0)) {
         this.loadFactor = loadFactor;
         this.bucketToHandleMap = new int[initialCapacity];
         this.handleToObjectMap = new Object[initialCapacity];
         this.handleMap = new int[initialCapacity * 3];
         this.threshold = (int)((float)initialCapacity * loadFactor);
         this.capacity = initialCapacity;
         this.clear();
      } else {
         throw new IllegalArgumentException();
      }
   }

   public IndirectionValueHashtable(int initialCapacity) {
      this(initialCapacity, 3.0F);
   }

   public IndirectionValueHashtable() {
      this(10, 3.0F);
   }

   public int size() {
      return this.count;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   private static final int hash(Object key, int qualifier) {
      return key instanceof String ? key.hashCode() + qualifier & Integer.MAX_VALUE : System.identityHashCode(key) + qualifier & Integer.MAX_VALUE;
   }

   public int get(Object key, int qualifier) {
      int hash = hash(key, qualifier);

      for(int i = this.bucketToHandleMap[hash % this.bucketToHandleMap.length]; i >= 0; i = this.handleMap[i]) {
         if (key instanceof String && key.equals(this.handleToObjectMap[i])) {
            return this.handleMap[i + this.capacity];
         }

         if (this.handleToObjectMap[i] == key) {
            return this.handleMap[i + this.capacity];
         }
      }

      return hash | Integer.MIN_VALUE;
   }

   private void increaseCapacity() {
      int newCapacity = this.capacity * 2 + 1;
      Object[] htom = new Object[newCapacity];
      System.arraycopy(this.handleToObjectMap, 0, htom, 0, this.count);
      this.handleToObjectMap = htom;
      int[] hm = new int[newCapacity * 3];
      System.arraycopy(this.handleMap, 0, hm, 0, this.count);
      System.arraycopy(this.handleMap, this.capacity, hm, newCapacity, this.count);
      System.arraycopy(this.handleMap, this.capacity * 2, hm, newCapacity * 2, this.count);
      this.handleMap = hm;
      this.capacity = newCapacity;
   }

   private void rehash() {
      int newCapacity = this.bucketToHandleMap.length * 2 + 1;
      this.bucketToHandleMap = new int[newCapacity];
      Arrays.fill(this.bucketToHandleMap, -1);
      this.threshold = (int)((float)newCapacity * this.loadFactor);

      int index;
      for(int i = 0; i < this.count; this.bucketToHandleMap[index] = i++) {
         Object var10000 = this.handleToObjectMap[i];
         index = this.handleMap[i + this.capacity * 2] % this.bucketToHandleMap.length;
         this.handleMap[i] = this.bucketToHandleMap[index];
      }

   }

   public void put(Object key, int qualifier, int value, int hash) {
      if (key == null) {
         throw new NullPointerException();
      } else {
         if (this.count >= this.capacity) {
            this.increaseCapacity();
         }

         if (this.count >= this.threshold) {
            this.rehash();
         }

         hash = hash < 0 ? hash & Integer.MAX_VALUE : hash(key, qualifier);
         int index = hash % this.bucketToHandleMap.length;
         this.handleToObjectMap[this.count] = key;
         this.handleMap[this.count] = this.bucketToHandleMap[index];
         this.handleMap[this.count + this.capacity] = value;
         this.handleMap[this.count + this.capacity * 2] = hash;
         this.bucketToHandleMap[index] = this.count++;
      }
   }

   public void put(Object key, int qualifier, int value) {
      this.put(key, qualifier, value, 0);
   }

   public void clear() {
      Arrays.fill(this.bucketToHandleMap, -1);
      Arrays.fill(this.handleToObjectMap, 0, this.count, (Object)null);
      this.count = 0;
   }

   public static void main(String[] a) {
      IndirectionValueHashtable t = new IndirectionValueHashtable();
      Random rand = new Random(System.currentTimeMillis());
      int seed = rand.nextInt();
      Object[] ls = new Object[127];
      int[] ns = new int[127];

      int i;
      for(i = 0; i < ls.length; ++i) {
         ns[i] = rand.nextInt();
         ls[i] = String.valueOf(ns[i]);
         t.put(ls[i], i % 2, ns[i]);
      }

      System.out.println("TABLE: \n" + t);

      for(i = 0; i < ls.length; ++i) {
         int n = t.get(ls[i], i % 2);
         if (n == 0) {
            System.err.println("not found: " + ls[i]);
         } else if (n != ns[i]) {
            System.err.println(n + "!=" + ns[i]);
         } else {
            System.out.println("OK: " + ls[i]);
         }
      }

   }
}
