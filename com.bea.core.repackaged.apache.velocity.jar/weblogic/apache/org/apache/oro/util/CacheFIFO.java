package weblogic.apache.org.apache.oro.util;

public final class CacheFIFO extends GenericCache {
   private int __curent;

   public CacheFIFO() {
      this(20);
   }

   public CacheFIFO(int var1) {
      super(var1);
      this.__curent = 0;
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
            var3 = this.__curent;
            if (++this.__curent >= super._cache.length) {
               this.__curent = 0;
            }

            super._table.remove(super._cache[var3]._key);
         }

         super._cache[var3]._value = var2;
         super._cache[var3]._key = var1;
         super._table.put(var1, super._cache[var3]);
      }
   }
}
