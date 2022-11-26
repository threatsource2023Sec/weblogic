package org.python.sizeof;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class IdentityHashSet implements Iterable {
   public static final float DEFAULT_LOAD_FACTOR = 0.75F;
   public static final int MIN_CAPACITY = 4;
   public Object[] keys;
   public int assigned;
   public final float loadFactor;
   private int resizeThreshold;

   public IdentityHashSet() {
      this(16, 0.75F);
   }

   public IdentityHashSet(int initialCapacity) {
      this(initialCapacity, 0.75F);
   }

   public IdentityHashSet(int initialCapacity, float loadFactor) {
      initialCapacity = Math.max(4, initialCapacity);

      assert initialCapacity > 0 : "Initial capacity must be between (0, 2147483647].";

      assert loadFactor > 0.0F && loadFactor < 1.0F : "Load factor must be between (0, 1).";

      this.loadFactor = loadFactor;
      this.allocateBuffers(this.roundCapacity(initialCapacity));
   }

   public boolean add(Object e) {
      assert e != null : "Null keys not allowed.";

      if (this.assigned >= this.resizeThreshold) {
         this.expandAndRehash();
      }

      int mask = this.keys.length - 1;

      int slot;
      Object existing;
      for(slot = rehash(e) & mask; (existing = this.keys[slot]) != null; slot = slot + 1 & mask) {
         if (e == existing) {
            return false;
         }
      }

      ++this.assigned;
      this.keys[slot] = e;
      return true;
   }

   public boolean contains(Object e) {
      int mask = this.keys.length - 1;

      Object existing;
      for(int slot = rehash(e) & mask; (existing = this.keys[slot]) != null; slot = slot + 1 & mask) {
         if (e == existing) {
            return true;
         }
      }

      return false;
   }

   private static int rehash(Object o) {
      return MurmurHash3.hash(System.identityHashCode(o));
   }

   private void expandAndRehash() {
      Object[] oldKeys = this.keys;

      assert this.assigned >= this.resizeThreshold;

      this.allocateBuffers(this.nextCapacity(this.keys.length));
      int mask = this.keys.length - 1;

      for(int i = 0; i < oldKeys.length; ++i) {
         Object key = oldKeys[i];
         if (key != null) {
            int slot;
            for(slot = rehash(key) & mask; this.keys[slot] != null; slot = slot + 1 & mask) {
            }

            this.keys[slot] = key;
         }
      }

      Arrays.fill(oldKeys, (Object)null);
   }

   private void allocateBuffers(int capacity) {
      this.keys = new Object[capacity];
      this.resizeThreshold = (int)((float)capacity * 0.75F);
   }

   protected int nextCapacity(int current) {
      assert current > 0 && Long.bitCount((long)current) == 1 : "Capacity must be a power of two.";

      assert current << 1 > 0 : "Maximum capacity exceeded (1073741824).";

      if (current < 2) {
         current = 2;
      }

      return current << 1;
   }

   protected int roundCapacity(int requestedCapacity) {
      if (requestedCapacity > 1073741824) {
         return 1073741824;
      } else {
         int capacity;
         for(capacity = 4; capacity < requestedCapacity; capacity <<= 1) {
         }

         return capacity;
      }
   }

   public void clear() {
      this.assigned = 0;
      Arrays.fill(this.keys, (Object)null);
   }

   public int size() {
      return this.assigned;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public Iterator iterator() {
      return new Iterator() {
         int pos = -1;
         Object nextElement = this.fetchNext();

         public boolean hasNext() {
            return this.nextElement != null;
         }

         public Object next() {
            Object r = this.nextElement;
            if (r == null) {
               throw new NoSuchElementException();
            } else {
               this.nextElement = this.fetchNext();
               return r;
            }
         }

         private Object fetchNext() {
            ++this.pos;

            while(this.pos < IdentityHashSet.this.keys.length && IdentityHashSet.this.keys[this.pos] == null) {
               ++this.pos;
            }

            return this.pos >= IdentityHashSet.this.keys.length ? null : IdentityHashSet.this.keys[this.pos];
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
