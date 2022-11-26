package com.octetstring.vde.backend.standard;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class KeyPool {
   private static KeyPool instance = null;
   private byte[] pool = null;
   private int poolSize = 10000;
   private int poolLast = 0;
   private double poolGrowth = 1.25;
   private SortedSet keySet = null;

   private KeyPool() {
      this.pool = new byte[this.poolSize];
      this.keySet = Collections.synchronizedSortedSet(new TreeSet());
   }

   public static KeyPool getInstance() {
      if (instance == null) {
         instance = new KeyPool();
      }

      return instance;
   }

   public byte charAt(int index) {
      return this.pool[index];
   }

   private synchronized int create(byte[] key) {
      if (this.poolLast + key.length > this.poolSize) {
         this.growpool();
      }

      System.arraycopy(key, 0, this.pool, this.poolLast, key.length);
      int pos = this.poolLast;
      this.poolLast += key.length;
      return pos;
   }

   public KeyPtr get(byte[] key) {
      if (key.length > 0) {
         return new KeyPtr(key);
      } else {
         boolean tryit = true;

         while(tryit) {
            try {
               SortedSet aSet = this.keySet.tailSet(new KeyPtr(key));
               Iterator iter = aSet.iterator();
               if (iter.hasNext()) {
                  KeyPtr aKey = (KeyPtr)iter.next();
                  if (aKey.startsWith(key)) {
                     if (aKey.last - aKey.first == key.length) {
                        return aKey;
                     }

                     return new KeyPtr(aKey.first, aKey.first + key.length);
                  }
               }

               tryit = false;
            } catch (ConcurrentModificationException var6) {
            }
         }

         int pos = this.create(key);
         KeyPtr aKey = new KeyPtr(pos, pos + key.length);
         if (aKey == null) {
            return new KeyPtr(key);
         } else {
            this.keySet.add(aKey);
            return aKey;
         }
      }
   }

   public String getString(int first, int last) {
      return new String(this.getBytes(first, last));
   }

   public byte[] getBytes(int first, int last) {
      byte[] results = new byte[last - first];
      System.arraycopy(this.pool, first, results, 0, results.length);
      return results;
   }

   private void growpool() {
      this.poolSize = (int)((double)this.poolSize * this.poolGrowth);
      byte[] newPool = new byte[this.poolSize];
      System.arraycopy(this.pool, 0, newPool, 0, this.poolLast);
      this.pool = newPool;
   }
}
