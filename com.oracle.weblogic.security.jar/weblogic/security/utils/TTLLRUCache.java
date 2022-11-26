package weblogic.security.utils;

import java.util.Iterator;
import java.util.Map;

public class TTLLRUCache extends LRUCache {
   private long timeToLive = 0L;

   public TTLLRUCache() {
   }

   public TTLLRUCache(int maxSize) {
      super(maxSize);
   }

   public TTLLRUCache(int maxSize, int secToLive) {
      super(maxSize);
      this.setTimeToLive(secToLive);
   }

   public void setTimeToLive(int secToLive) {
      if (secToLive < 0) {
         throw new IllegalArgumentException("Seconds to live cannot be negative");
      } else {
         this.timeToLive = (long)(secToLive * 1000);
      }
   }

   public Object put(Object key, Object value) {
      TTLEntry entry = (TTLEntry)super.put(key, new TTLEntry(value));
      return entry == null ? null : entry.value;
   }

   public Object get(Object key) {
      return this.extractValue(key, (TTLEntry)super.get(key));
   }

   public Object lookup(Object key) {
      return this.extractValue(key, (TTLEntry)super.lookup(key));
   }

   private Object extractValue(Object key, TTLEntry entry) {
      Object value = null;
      if (entry != null) {
         if (entry.isValid()) {
            value = entry.value;
         } else {
            super.remove(key);
         }
      }

      return value;
   }

   public boolean containsKey(Object key) {
      return this.lookup(key) != null;
   }

   public boolean containsValue(Object value) {
      return super.containsValue(new TTLEntry(value));
   }

   public Map.Entry remove() {
      Map.Entry entry = super.remove();
      if (entry != null) {
         entry.setValue(((TTLEntry)entry.getValue()).value);
      }

      return entry;
   }

   public Object remove(Object key) {
      TTLEntry entry = (TTLEntry)super.remove(key);
      return entry == null ? null : entry.value;
   }

   public Iterator iterator() {
      return new TTLIterator();
   }

   public void removeExpiredEntries() {
      Iterator it = super.iterator();
      boolean isValid = false;

      while(it.hasNext() && !isValid) {
         Map.Entry entry = (Map.Entry)it.next();
         TTLEntry ttlEntry = (TTLEntry)entry.getValue();
         isValid = ttlEntry.isValid();
         if (!isValid) {
            it.remove();
         }
      }

   }

   private class Entry implements Map.Entry {
      Object key;
      Object value;

      Entry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public Object getKey() {
         return this.key;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            boolean var10000;
            label43: {
               label29: {
                  Map.Entry e = (Map.Entry)o;
                  if (this.key == null) {
                     if (e.getKey() != null) {
                        break label29;
                     }
                  } else if (!this.key.equals(e.getKey())) {
                     break label29;
                  }

                  if (this.value == null) {
                     if (e.getValue() == null) {
                        break label43;
                     }
                  } else if (this.value.equals(e.getValue())) {
                     break label43;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public String toString() {
         return this.key + "=" + this.value;
      }
   }

   private class TTLIterator implements Iterator {
      Iterator iterator = TTLLRUCache.this.iterator();

      public TTLIterator() {
      }

      public Object next() {
         Map.Entry entry = (Map.Entry)this.iterator.next();
         if (entry != null) {
            entry = TTLLRUCache.this.new Entry(((Map.Entry)entry).getKey(), ((TTLEntry)((Map.Entry)entry).getValue()).value);
         }

         return entry;
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public void remove() {
         this.iterator.remove();
      }
   }

   private class TTLEntry {
      public Object value;
      public long timeStamp;

      public TTLEntry(Object value) {
         this.value = value;
         this.timeStamp = System.currentTimeMillis();
      }

      public boolean isValid() {
         return TTLLRUCache.this.timeToLive == 0L || System.currentTimeMillis() - this.timeStamp < TTLLRUCache.this.timeToLive;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof TTLEntry)) {
            return false;
         } else {
            TTLEntry e = (TTLEntry)obj;
            return this.value == null ? e.value == null : this.value.equals(e);
         }
      }

      public int hashCode() {
         return this.value != null ? this.value.hashCode() : 0;
      }
   }
}
