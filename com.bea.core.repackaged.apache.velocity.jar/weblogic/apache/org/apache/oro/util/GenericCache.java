package weblogic.apache.org.apache.oro.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class GenericCache implements Cache, Serializable {
   public static final int DEFAULT_CAPACITY = 20;
   int _numEntries = 0;
   GenericCacheEntry[] _cache;
   Hashtable _table;

   GenericCache(int var1) {
      this._table = new Hashtable(var1);
      this._cache = new GenericCacheEntry[var1];

      while(true) {
         --var1;
         if (var1 < 0) {
            return;
         }

         this._cache[var1] = new GenericCacheEntry(var1);
      }
   }

   public abstract void addElement(Object var1, Object var2);

   public final int capacity() {
      return this._cache.length;
   }

   public synchronized Object getElement(Object var1) {
      Object var2 = this._table.get(var1);
      return var2 != null ? ((GenericCacheEntry)var2)._value : null;
   }

   public final boolean isFull() {
      return this._numEntries >= this._cache.length;
   }

   public final Enumeration keys() {
      return this._table.keys();
   }

   public final int size() {
      return this._numEntries;
   }
}
