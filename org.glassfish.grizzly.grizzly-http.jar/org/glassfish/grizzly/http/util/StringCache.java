package org.glassfish.grizzly.http.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public final class StringCache {
   private static final Logger logger = Grizzly.logger(StringCache.class);
   static boolean byteEnabled = "true".equals(System.getProperty("tomcat.util.buf.StringCache.byte.enabled", "false"));
   static boolean charEnabled = "true".equals(System.getProperty("tomcat.util.buf.StringCache.char.enabled", "false"));
   static int trainThreshold = Integer.parseInt(System.getProperty("tomcat.util.buf.StringCache.trainThreshold", "20000"));
   static int cacheSize = Integer.parseInt(System.getProperty("tomcat.util.buf.StringCache.cacheSize", "200"));
   static final HashMap bcStats;
   static int bcCount;
   static ByteEntry[] bcCache;
   static final HashMap ccStats;
   static int ccCount;
   static CharEntry[] ccCache;
   static int accessCount;
   static int hitCount;

   public static int getCacheSize() {
      return cacheSize;
   }

   public static void setCacheSize(int cacheSize) {
      StringCache.cacheSize = cacheSize;
   }

   public static boolean getByteEnabled() {
      return byteEnabled;
   }

   public static void setByteEnabled(boolean byteEnabled) {
      StringCache.byteEnabled = byteEnabled;
   }

   public static boolean getCharEnabled() {
      return charEnabled;
   }

   public static void setCharEnabled(boolean charEnabled) {
      StringCache.charEnabled = charEnabled;
   }

   public static int getTrainThreshold() {
      return trainThreshold;
   }

   public static void setTrainThreshold(int trainThreshold) {
      StringCache.trainThreshold = trainThreshold;
   }

   public static int getAccessCount() {
      return accessCount;
   }

   public static int getHitCount() {
      return hitCount;
   }

   public static void reset() {
      hitCount = 0;
      accessCount = 0;
      synchronized(bcStats) {
         bcCache = null;
         bcCount = 0;
      }

      synchronized(ccStats) {
         ccCache = null;
         ccCount = 0;
      }
   }

   public static String toString(ByteChunk bc) {
      String value;
      if (bcCache != null) {
         ++accessCount;
         value = find(bc);
         if (value == null) {
            return bc.toStringInternal();
         } else {
            ++hitCount;
            return value;
         }
      } else {
         value = bc.toStringInternal();
         if (byteEnabled) {
            synchronized(bcStats) {
               if (bcCache != null) {
                  return value;
               }

               if (bcCount <= trainThreshold) {
                  ++bcCount;
                  ByteEntry entry = new ByteEntry();
                  entry.value = value;
                  int[] count = (int[])bcStats.get(entry);
                  if (count == null) {
                     int end = bc.getEnd();
                     int start = bc.getStart();
                     entry.name = new byte[bc.getLength()];
                     System.arraycopy(bc.getBuffer(), start, entry.name, 0, end - start);
                     entry.charset = bc.getCharset();
                     count = new int[]{1};
                     bcStats.put(entry, count);
                  } else {
                     int var10002 = count[0]++;
                  }
               } else {
                  long t1 = System.currentTimeMillis();
                  TreeMap tempMap = new TreeMap();

                  ByteEntry entry;
                  ArrayList list;
                  for(Iterator entries = bcStats.keySet().iterator(); entries.hasNext(); list.add(entry)) {
                     entry = (ByteEntry)entries.next();
                     int[] countA = (int[])bcStats.get(entry);
                     Integer count = countA[0];
                     list = (ArrayList)tempMap.get(count);
                     if (list == null) {
                        list = new ArrayList();
                        tempMap.put(count, list);
                     }
                  }

                  int size = bcStats.size();
                  if (size > cacheSize) {
                     size = cacheSize;
                  }

                  ByteEntry[] tempbcCache = new ByteEntry[size];
                  ByteChunk tempChunk = new ByteChunk();
                  int n = 0;

                  while(true) {
                     if (n >= size) {
                        bcCount = 0;
                        bcStats.clear();
                        bcCache = tempbcCache;
                        if (logger.isLoggable(Level.FINEST)) {
                           long t2 = System.currentTimeMillis();
                           logger.log(Level.FINEST, "ByteCache generation time: " + (t2 - t1) + "ms");
                        }
                        break;
                     }

                     Object key = tempMap.lastKey();
                     ArrayList list = (ArrayList)tempMap.get(key);

                     for(int i = 0; i < list.size() && n < size; ++i) {
                        ByteEntry entry = (ByteEntry)list.get(i);
                        tempChunk.setBytes(entry.name, 0, entry.name.length);
                        int insertPos = findClosest(tempChunk, tempbcCache, n);
                        if (insertPos == n) {
                           tempbcCache[n + 1] = entry;
                        } else {
                           System.arraycopy(tempbcCache, insertPos + 1, tempbcCache, insertPos + 2, n - insertPos - 1);
                           tempbcCache[insertPos + 1] = entry;
                        }

                        ++n;
                     }

                     tempMap.remove(key);
                  }
               }
            }
         }

         return value;
      }
   }

   public static String toString(CharChunk cc) {
      String value;
      if (ccCache != null) {
         ++accessCount;
         value = find(cc);
         if (value == null) {
            return cc.toStringInternal();
         } else {
            ++hitCount;
            return value;
         }
      } else {
         value = cc.toStringInternal();
         if (charEnabled) {
            synchronized(ccStats) {
               if (ccCache != null) {
                  return value;
               }

               if (ccCount <= trainThreshold) {
                  ++ccCount;
                  CharEntry entry = new CharEntry();
                  entry.value = value;
                  int[] count = (int[])ccStats.get(entry);
                  if (count == null) {
                     int end = cc.getEnd();
                     int start = cc.getStart();
                     entry.name = new char[cc.getLength()];
                     System.arraycopy(cc.getBuffer(), start, entry.name, 0, end - start);
                     count = new int[]{1};
                     ccStats.put(entry, count);
                  } else {
                     int var10002 = count[0]++;
                  }
               } else {
                  long t1 = System.currentTimeMillis();
                  TreeMap tempMap = new TreeMap();

                  CharEntry entry;
                  ArrayList list;
                  for(Iterator entries = ccStats.keySet().iterator(); entries.hasNext(); list.add(entry)) {
                     entry = (CharEntry)entries.next();
                     int[] countA = (int[])ccStats.get(entry);
                     Integer count = countA[0];
                     list = (ArrayList)tempMap.get(count);
                     if (list == null) {
                        list = new ArrayList();
                        tempMap.put(count, list);
                     }
                  }

                  int size = ccStats.size();
                  if (size > cacheSize) {
                     size = cacheSize;
                  }

                  CharEntry[] tempccCache = new CharEntry[size];
                  CharChunk tempChunk = new CharChunk();
                  int n = 0;

                  while(true) {
                     if (n >= size) {
                        ccCount = 0;
                        ccStats.clear();
                        ccCache = tempccCache;
                        if (logger.isLoggable(Level.FINEST)) {
                           long t2 = System.currentTimeMillis();
                           logger.log(Level.FINEST, "CharCache generation time: " + (t2 - t1) + "ms");
                        }
                        break;
                     }

                     Object key = tempMap.lastKey();
                     ArrayList list = (ArrayList)tempMap.get(key);

                     for(int i = 0; i < list.size() && n < size; ++i) {
                        CharEntry entry = (CharEntry)list.get(i);
                        tempChunk.setChars(entry.name, 0, entry.name.length);
                        int insertPos = findClosest(tempChunk, tempccCache, n);
                        if (insertPos == n) {
                           tempccCache[n + 1] = entry;
                        } else {
                           System.arraycopy(tempccCache, insertPos + 1, tempccCache, insertPos + 2, n - insertPos - 1);
                           tempccCache[insertPos + 1] = entry;
                        }

                        ++n;
                     }

                     tempMap.remove(key);
                  }
               }
            }
         }

         return value;
      }
   }

   protected static int compare(ByteChunk name, byte[] compareTo) {
      int result = 0;
      byte[] b = name.getBuffer();
      int start = name.getStart();
      int end = name.getEnd();
      int len = compareTo.length;
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (b[i + start] > compareTo[i]) {
            result = 1;
         } else if (b[i + start] < compareTo[i]) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length > end - start) {
            result = -1;
         } else if (compareTo.length < end - start) {
            result = 1;
         }
      }

      return result;
   }

   protected static String find(ByteChunk name) {
      int pos = findClosest(name, bcCache, bcCache.length);
      return pos >= 0 && compare(name, bcCache[pos].name) == 0 && name.getCharset().equals(bcCache[pos].charset) ? bcCache[pos].value : null;
   }

   protected static int findClosest(ByteChunk name, ByteEntry[] array, int len) {
      int a = 0;
      int b = len - 1;
      if (b == -1) {
         return -1;
      } else if (compare(name, array[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = compare(name, array[i].name);
            if (result == 1) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = compare(name, array[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   protected static int compare(CharChunk name, char[] compareTo) {
      int result = 0;
      char[] c = name.getBuffer();
      int start = name.getStart();
      int end = name.getEnd();
      int len = compareTo.length;
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (c[i + start] > compareTo[i]) {
            result = 1;
         } else if (c[i + start] < compareTo[i]) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length > end - start) {
            result = -1;
         } else if (compareTo.length < end - start) {
            result = 1;
         }
      }

      return result;
   }

   protected static String find(CharChunk name) {
      int pos = findClosest(name, ccCache, ccCache.length);
      return pos >= 0 && compare(name, ccCache[pos].name) == 0 ? ccCache[pos].value : null;
   }

   protected static int findClosest(CharChunk name, CharEntry[] array, int len) {
      int a = 0;
      int b = len - 1;
      if (b == -1) {
         return -1;
      } else if (compare(name, array[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = compare(name, array[i].name);
            if (result == 1) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = compare(name, array[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   static {
      bcStats = new HashMap(cacheSize);
      bcCount = 0;
      bcCache = null;
      ccStats = new HashMap(cacheSize);
      ccCount = 0;
      ccCache = null;
      accessCount = 0;
      hitCount = 0;
   }

   protected static class CharEntry {
      public char[] name = null;
      public String value = null;

      public String toString() {
         return this.value;
      }

      public int hashCode() {
         return this.value.hashCode();
      }

      public boolean equals(Object obj) {
         return obj instanceof CharEntry && this.value.equals(((CharEntry)obj).value);
      }
   }

   protected static class ByteEntry {
      public byte[] name = null;
      public Charset charset = null;
      public String value = null;

      public String toString() {
         return this.value;
      }

      public int hashCode() {
         return this.value.hashCode();
      }

      public boolean equals(Object obj) {
         return obj instanceof ByteEntry && this.value.equals(((ByteEntry)obj).value);
      }
   }
}
