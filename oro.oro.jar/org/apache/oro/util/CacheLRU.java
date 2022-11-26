package org.apache.oro.util;

public final class CacheLRU extends GenericCache {
   private int __head;
   private int __tail;
   private int[] __next;
   private int[] __prev;

   public CacheLRU(int var1) {
      super(var1);
      this.__head = 0;
      this.__tail = 0;
      this.__next = new int[this._cache.length];
      this.__prev = new int[this._cache.length];

      for(int var2 = 0; var2 < this.__next.length; ++var2) {
         this.__next[var2] = this.__prev[var2] = -1;
      }

   }

   public CacheLRU() {
      this(20);
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

   public synchronized Object getElement(Object var1) {
      Object var2 = this._table.get(var1);
      if (var2 != null) {
         GenericCacheEntry var3 = (GenericCacheEntry)var2;
         this.__moveToFront(var3._index);
         return var3._value;
      } else {
         return null;
      }
   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var3 = this._table.get(var1);
      if (var3 != null) {
         GenericCacheEntry var4 = (GenericCacheEntry)var3;
         var4._value = var2;
         var4._key = var1;
         this.__moveToFront(var4._index);
      } else {
         if (!this.isFull()) {
            if (this._numEntries > 0) {
               this.__prev[this._numEntries] = this.__tail;
               this.__next[this._numEntries] = -1;
               this.__moveToFront(this._numEntries);
            }

            ++this._numEntries;
         } else {
            this._table.remove(this._cache[this.__tail]._key);
            this.__moveToFront(this.__tail);
         }

         this._cache[this.__head]._value = var2;
         this._cache[this.__head]._key = var1;
         this._table.put(var1, this._cache[this.__head]);
      }
   }
}
