package org.apache.oro.util;

import java.util.Random;

public final class CacheRandom extends GenericCache {
   private Random __random;

   public CacheRandom(int var1) {
      super(var1);
      this.__random = new Random(System.currentTimeMillis());
   }

   public CacheRandom() {
      this(20);
   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var4 = this._table.get(var1);
      if (var4 != null) {
         GenericCacheEntry var5 = (GenericCacheEntry)var4;
         var5._value = var2;
         var5._key = var1;
      } else {
         int var3;
         if (!this.isFull()) {
            var3 = this._numEntries++;
         } else {
            var3 = (int)((float)this._cache.length * this.__random.nextFloat());
            this._table.remove(this._cache[var3]._key);
         }

         this._cache[var3]._value = var2;
         this._cache[var3]._key = var1;
         this._table.put(var1, this._cache[var3]);
      }
   }
}
