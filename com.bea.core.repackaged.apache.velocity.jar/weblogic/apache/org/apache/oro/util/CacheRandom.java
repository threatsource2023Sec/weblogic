package weblogic.apache.org.apache.oro.util;

import java.util.Random;

public final class CacheRandom extends GenericCache {
   private Random __random;

   public CacheRandom() {
      this(20);
   }

   public CacheRandom(int var1) {
      super(var1);
      this.__random = new Random(System.currentTimeMillis());
   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var4 = super._table.get(var1);
      if (var4 != null) {
         GenericCacheEntry var5 = (GenericCacheEntry)var4;
         var5._value = var2;
         var5._key = var1;
      } else {
         int var3;
         if (!this.isFull()) {
            var3 = super._numEntries++;
         } else {
            var3 = (int)((float)super._cache.length * this.__random.nextFloat());
            super._table.remove(super._cache[var3]._key);
         }

         super._cache[var3]._value = var2;
         super._cache[var3]._key = var1;
         super._table.put(var1, super._cache[var3]);
      }
   }
}
