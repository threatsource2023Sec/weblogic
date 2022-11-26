package org.apache.oro.util;

public final class CacheLRU extends GenericCache {
   private int __head;
   private int __tail;
   private int[] __next;
   private int[] __prev;

   public CacheLRU() {
      this(20);
   }

   public CacheLRU(int var1) {
      super(var1);
      this.__head = 0;
      this.__tail = 0;
      this.__next = new int[super._cache.length];
      this.__prev = new int[super._cache.length];

      for(int var2 = 0; var2 < this.__next.length; ++var2) {
         this.__next[var2] = this.__prev[var2] = -1;
      }

   }

   private void __moveToFront(int var1) {
      if (this.__head != var1) {
         int var2 = this.__next[var1];
         int var3 = this.__prev[var1];
         this.__next[var3] = var2;
         if (var2 >= 0) {
            this.__prev[var2] = var3;
         } else {
            this.__tail = var3;
         }

         this.__prev[var1] = -1;
         this.__next[var1] = this.__head;
         this.__prev[this.__head] = var1;
         this.__head = var1;
      }

   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var4 = super._table.get(var1);
      if (var4 != null) {
         GenericCacheEntry var5 = (GenericCacheEntry)var4;
         var5._value = var2;
         var5._key = var1;
         this.__moveToFront(var5._index);
      } else {
         if (!this.isFull()) {
            if (super._numEntries > 0) {
               this.__prev[super._numEntries] = this.__tail;
               this.__next[super._numEntries] = -1;
               this.__moveToFront(super._numEntries);
            }

            ++super._numEntries;
         } else {
            super._table.remove(super._cache[this.__tail]._key);
            this.__moveToFront(this.__tail);
         }

         super._cache[this.__head]._value = var2;
         super._cache[this.__head]._key = var1;
         super._table.put(var1, super._cache[this.__head]);
      }
   }

   public synchronized Object getElement(Object var1) {
      Object var2 = super._table.get(var1);
      if (var2 != null) {
         GenericCacheEntry var3 = (GenericCacheEntry)var2;
         this.__moveToFront(var3._index);
         return var3._value;
      } else {
         return null;
      }
   }
}
