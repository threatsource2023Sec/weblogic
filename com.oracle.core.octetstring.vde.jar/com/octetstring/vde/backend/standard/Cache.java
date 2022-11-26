package com.octetstring.vde.backend.standard;

import com.octetstring.vde.Entry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cache {
   private int[] entryCache = null;
   private Map cachestore = null;
   private int cacheCount = 0;
   private int maxEntryCache = 10;

   public Cache(int size) {
      this.maxEntryCache = size;
      this.cachestore = Collections.synchronizedMap(new HashMap());
      this.entryCache = new int[this.maxEntryCache + 1];
   }

   public void add(Integer eid, Entry current) {
      this.cachestore.put(eid, current);
      int cacheNo = this.nextCacheCount();
      if (this.entryCache[cacheNo] != 0) {
         Integer removeid = new Integer(this.entryCache[cacheNo]);
         Entry oldEntry = (Entry)this.cachestore.get(removeid);
         oldEntry = null;
         if (removeid != 0) {
            this.cachestore.remove(removeid);
         }
      }

      this.entryCache[cacheNo] = eid;
   }

   public Entry get(Integer eid) {
      return (Entry)this.cachestore.get(eid);
   }

   private synchronized int nextCacheCount() {
      if (this.cacheCount >= this.maxEntryCache) {
         this.cacheCount = 1;
      } else {
         ++this.cacheCount;
      }

      return this.cacheCount - 1;
   }

   public void remove(Integer eid) {
      this.cachestore.remove(eid);
      int eidint = eid;

      for(int i = 0; i < this.entryCache.length; ++i) {
         if (this.entryCache[i] == eidint) {
            this.entryCache[i] = 0;
            break;
         }
      }

   }
}
