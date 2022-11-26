package weblogic.diagnostics.debugpatch;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class DebugPatchHelper {
   private static Map integersMap = new ConcurrentHashMap();
   private static Map longsMap = new ConcurrentHashMap();
   private static Map buffersMap = new ConcurrentHashMap();
   private static Map mapsMap = new ConcurrentHashMap();
   private static Map listsMap = new ConcurrentHashMap();

   public static AtomicInteger findOrCreateInteger(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      AtomicInteger val = (AtomicInteger)integersMap.get(key);
      if (val == null) {
         synchronized(integersMap) {
            val = (AtomicInteger)integersMap.get(key);
            if (val == null) {
               val = new AtomicInteger();
               integersMap.put(key, val);
            }
         }
      }

      return val;
   }

   public static AtomicLong findOrCreateLong(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      AtomicLong val = (AtomicLong)longsMap.get(key);
      if (val == null) {
         synchronized(longsMap) {
            val = (AtomicLong)longsMap.get(key);
            if (val == null) {
               val = new AtomicLong();
               longsMap.put(key, val);
            }
         }
      }

      return val;
   }

   public static StringBuffer findOrCreateBuffer(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      StringBuffer val = (StringBuffer)buffersMap.get(key);
      if (val == null) {
         synchronized(buffersMap) {
            val = (StringBuffer)buffersMap.get(key);
            if (val == null) {
               val = new StringBuffer();
               buffersMap.put(key, val);
            }
         }
      }

      return val;
   }

   public static ConcurrentHashMap findOrCreateMap(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      ConcurrentHashMap val = (ConcurrentHashMap)mapsMap.get(key);
      if (val == null) {
         synchronized(mapsMap) {
            val = (ConcurrentHashMap)mapsMap.get(key);
            if (val == null) {
               val = new ConcurrentHashMap();
               mapsMap.put(key, val);
            }
         }
      }

      return val;
   }

   public static List findOrCreateList(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      List val = (List)listsMap.get(key);
      if (val == null) {
         synchronized(listsMap) {
            val = (List)listsMap.get(key);
            if (val == null) {
               val = Collections.synchronizedList(new ArrayList());
               listsMap.put(key, val);
            }
         }
      }

      return val;
   }

   public static String getPrintableMapData(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      ConcurrentHashMap map = (ConcurrentHashMap)mapsMap.get(key);
      StringBuffer buf = new StringBuffer();
      buf.append("Map for key: {").append(key.getName()).append(", ").append(key.getOwner()).append("} ");
      if (map == null) {
         buf.append("not found");
      } else {
         Iterator var5 = map.entrySet().iterator();

         while(var5.hasNext()) {
            Object e = var5.next();
            Map.Entry entry = (Map.Entry)e;
            buf.append("\n  ").append(entry.getKey()).append("=").append(entry.getValue());
         }
      }

      return buf.toString();
   }

   public static boolean removeInteger(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      return integersMap.remove(key) != null;
   }

   public static boolean removeLong(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      return longsMap.remove(key) != null;
   }

   public static boolean removeBuffer(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      return buffersMap.remove(key) != null;
   }

   public static boolean removeMap(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      return mapsMap.remove(key) != null;
   }

   public static boolean removeList(String name, Object owner) {
      OwnerKey key = new OwnerKey(name, owner);
      return listsMap.remove(key) != null;
   }

   private static int clearMap(Map map) {
      synchronized(map) {
         int size = map.size();
         map.clear();
         return size;
      }
   }

   public static int removeIntegers() {
      return clearMap(integersMap);
   }

   public static int removeLongs() {
      return clearMap(longsMap);
   }

   public static int removeBuffers() {
      return clearMap(buffersMap);
   }

   public static int removeMaps() {
      return clearMap(mapsMap);
   }

   public static int removeLists() {
      return clearMap(listsMap);
   }

   public static int removeAll() {
      int count = removeIntegers();
      count += removeLongs();
      count += removeBuffers();
      count += removeMaps();
      count += removeLists();
      return count;
   }

   public static int getIntegersCount() {
      return integersMap.size();
   }

   public static int getLongsCount() {
      return longsMap.size();
   }

   public static int getBuffersCount() {
      return buffersMap.size();
   }

   public static int getMapsCount() {
      return mapsMap.size();
   }

   public static int getListsCount() {
      return listsMap.size();
   }

   private static class OwnerKey {
      private String name;
      private WeakReference ref;
      private int hash;

      OwnerKey(String name, Object owner) {
         this.name = name;
         this.ref = new WeakReference(owner);
         this.hash = name.hashCode() + 31 * owner.hashCode();
      }

      String getName() {
         return this.name;
      }

      Object getOwner() {
         return this.ref.get();
      }

      public int hashCode() {
         return this.hash;
      }

      public boolean equals(Object o) {
         if (!(o instanceof OwnerKey)) {
            return false;
         } else {
            OwnerKey other = (OwnerKey)o;
            if (!this.name.equals(other.name)) {
               return false;
            } else {
               Object owner = this.ref.get();
               if (owner == null) {
                  return false;
               } else {
                  Object otherOwner = other.ref.get();
                  return otherOwner == null ? false : owner.equals(otherOwner);
               }
            }
         }
      }
   }
}
