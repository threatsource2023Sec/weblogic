package weblogic.application.utils;

import java.util.Comparator;
import java.util.TreeMap;

public class CacheMap {
   private static final long MAX_MAX_LIFE = 600000L;
   private final Object dummyKey = new Object();
   private final int maxSize;
   private final long maxLife;
   private TreeMap map = new TreeMap(new KeyEntryComparator());

   public CacheMap(int maxSize, long maxLife) {
      this.maxSize = maxSize <= 0 ? Integer.MAX_VALUE : maxSize;
      this.maxLife = maxLife >= 0L && maxLife <= 600000L ? maxLife : 600000L;
   }

   private synchronized void cleanupExpiredEntries() {
      this.map = new TreeMap(this.map.tailMap(new KeyEntry()));
   }

   public synchronized Object get(Object key) {
      this.cleanupExpiredEntries();
      return this.map.get(new KeyEntry(key));
   }

   public synchronized boolean containsKey(Object key) {
      this.cleanupExpiredEntries();
      return this.map.containsKey(key);
   }

   public synchronized Object put(Object key, Object value) {
      this.cleanupExpiredEntries();

      while(this.map.size() >= this.maxSize) {
         this.map.remove(this.map.firstKey());
      }

      return this.map.put(new KeyEntry(key), value);
   }

   public synchronized Object remove(Object key) {
      return this.map.remove(new KeyEntry(key));
   }

   public synchronized int size() {
      return this.map.size();
   }

   private class KeyEntryComparator implements Comparator {
      private KeyEntryComparator() {
      }

      public int compare(KeyEntry o1, KeyEntry o2) {
         if (o1.equals(o2)) {
            return 0;
         } else if (o1.expirationTime < o2.expirationTime) {
            return -1;
         } else if (o1.expirationTime > o2.expirationTime) {
            return 1;
         } else if (o1.key == CacheMap.this.dummyKey) {
            return 1;
         } else if (o2.key == CacheMap.this.dummyKey) {
            return -1;
         } else {
            return o1.key instanceof Comparable ? ((Comparable)o1.key).compareTo(o2.key) : -1;
         }
      }

      // $FF: synthetic method
      KeyEntryComparator(Object x1) {
         this();
      }
   }

   private class KeyEntry {
      private final Object key;
      private final long expirationTime;

      private KeyEntry(Object key) {
         this.key = key;
         this.expirationTime = System.currentTimeMillis() + CacheMap.this.maxLife;
      }

      private KeyEntry() {
         this.key = CacheMap.this.dummyKey;
         this.expirationTime = System.currentTimeMillis();
      }

      public boolean equals(Object other) {
         return !(other instanceof KeyEntry) ? false : this.key.equals(((KeyEntry)other).key);
      }

      // $FF: synthetic method
      KeyEntry(Object x1) {
         this();
      }

      // $FF: synthetic method
      KeyEntry(Object x1, Object x2) {
         this((Object)x1);
      }
   }
}
