package org.apache.oro.util;

public final class CacheFIFO2 extends GenericCache {
   private int __current;
   private boolean[] __tryAgain;

   public CacheFIFO2(int var1) {
      super(var1);
      this.__current = 0;
      this.__tryAgain = new boolean[this._cache.length];
   }

   public CacheFIFO2() {
      this(20);
   }

   public synchronized Object getElement(Object var1) {
      Object var2 = this._table.get(var1);
      if (var2 != null) {
         GenericCacheEntry var3 = (GenericCacheEntry)var2;
         this.__tryAgain[var3._index] = true;
         return var3._value;
      } else {
         return null;
      }
   }

   public final synchronized void addElement(Object var1, Object var2) {
      Object var4 = this._table.get(var1);
      if (var4 != null) {
         GenericCacheEntry var5 = (GenericCacheEntry)var4;
         var5._value = var2;
         var5._key = var1;
         this.__tryAgain[var5._index] = true;
      } else {
         int var3;
         if (!this.isFull()) {
            var3 = this._numEntries++;
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
            if (this.__current >= this._cache.length) {
               this.__current = 0;
            }

            this._table.remove(this._cache[var3]._key);
         }

         this._cache[var3]._value = var2;
         this._cache[var3]._key = var1;
         this._table.put(var1, this._cache[var3]);
      }
   }
}
