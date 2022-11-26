package org.apache.oro.util;

public final class CacheFIFO2 extends GenericCache {
   private int __current;
   private boolean[] __tryAgain;

   public CacheFIFO2() {
      this(20);
   }

   public CacheFIFO2(int var1) {
      super(var1);
      this.__current = 0;
      this.__tryAgain = new boolean[super._cache.length];
   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var4 = super._table.get(var1);
      if (var4 != null) {
         GenericCacheEntry var5 = (GenericCacheEntry)var4;
         var5._value = var2;
         var5._key = var1;
         this.__tryAgain[var5._index] = true;
      } else {
         int var3;
         if (!this.isFull()) {
            var3 = super._numEntries++;
         } else {
            var3 = this.__current;

            while(this.__tryAgain[var3]) {
               this.__tryAgain[var3] = false;
               ++var3;
               if (var3 >= this.__tryAgain.length) {
                  var3 = 0;
               }
            }

            this.__current = var3 + 1;
            if (this.__current >= super._cache.length) {
               this.__current = 0;
            }

            super._table.remove(super._cache[var3]._key);
         }

         super._cache[var3]._value = var2;
         super._cache[var3]._key = var1;
         super._table.put(var1, super._cache[var3]);
      }
   }

   public synchronized Object getElement(Object var1) {
      Object var2 = super._table.get(var1);
      if (var2 != null) {
         GenericCacheEntry var3 = (GenericCacheEntry)var2;
         this.__tryAgain[var3._index] = true;
         return var3._value;
      } else {
         return null;
      }
   }
}
