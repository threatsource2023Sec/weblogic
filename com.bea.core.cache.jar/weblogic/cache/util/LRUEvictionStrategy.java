package weblogic.cache.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.cache.CacheEntry;
import weblogic.cache.EvictionStrategy;

public class LRUEvictionStrategy extends BaseEvictionStrategy implements EvictionStrategy {
   protected transient Entry header = new Entry();

   public LRUEvictionStrategy(long ttl, long idleTime) {
      super(ttl, idleTime);
      this.header.next = this.header.previous = this.header;
   }

   public CacheEntry createEntry(Object key, Object value) {
      return new Entry(key, value, this.idleTime, this.ttl, this.header);
   }

   public CacheEntry restoreEntry(CacheEntry entry) {
      Entry e;
      if (entry instanceof Entry) {
         e = (Entry)entry;
         e.restore(this.header);
      } else {
         e = new Entry(entry, this.idleTime, this.ttl, this.header);
      }

      return e;
   }

   public Map evict() {
      Entry last = this.header.previous;
      last.discard();
      Map m = new HashMap();
      m.put(last.getKey(), last.getValue());
      return m;
   }

   public void clear() {
      synchronized(this.header) {
         this.header.next = this.header;
         this.header.previous = this.header;
      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      List l = new ArrayList();

      for(Entry e = this.header.previous; e != this.header; e = e.previous) {
         l.add(e);
      }

      out.writeObject(l);
   }

   private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
      this.header = new Entry();
      this.header.next = this.header.previous = this.header;
      List l = (List)in.readObject();
      Iterator var3 = l.iterator();

      while(var3.hasNext()) {
         Entry e = (Entry)var3.next();
         e.restore(this.header);
      }

   }

   protected static class Entry extends BaseCacheEntry {
      protected transient Entry next;
      protected transient Entry previous;
      protected transient Entry strategyHeader;

      private Entry() {
         super((Object)null, (Object)null, Long.MAX_VALUE, Long.MAX_VALUE);
      }

      public Entry(Object key, Object value, long idleTime, long ttl, Entry header) {
         super(key, value, idleTime, ttl);
         this.strategyHeader = header;
         this.addFirst();
      }

      public Entry(CacheEntry entry, long idleTime, long ttl, Entry header) {
         super(entry, idleTime, ttl);
         this.strategyHeader = header;
         this.addFirst();
      }

      private void addFirst() {
         synchronized(this.strategyHeader) {
            this.next = this.strategyHeader.next;
            this.previous = this.strategyHeader;
            this.strategyHeader.next = this;
            this.next.previous = this;
         }
      }

      public void touch() {
         super.touch();
         if (this.strategyHeader != null) {
            this.reorder();
         }

      }

      protected void reorder() {
         this.removeSelf();
         this.addFirst();
      }

      public void discard() {
         super.discard();
         if (this.strategyHeader != null) {
            this.removeSelf();
         }

      }

      private void removeSelf() {
         synchronized(this.strategyHeader) {
            if (this.next != null && this.previous != null) {
               this.previous.next = this.next;
               this.next.previous = this.previous;
               this.next = this.previous = null;
            }

         }
      }

      public void restore(Entry header) {
         super.restore();
         this.strategyHeader = header;
         this.addFirst();
      }

      // $FF: synthetic method
      Entry(Object x0) {
         this();
      }
   }
}
