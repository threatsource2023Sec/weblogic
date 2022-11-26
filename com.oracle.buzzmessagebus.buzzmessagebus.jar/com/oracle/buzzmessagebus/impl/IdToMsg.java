package com.oracle.buzzmessagebus.impl;

import com.oracle.common.collections.LongArray;
import com.oracle.common.collections.SparseArray;

class IdToMsg {
   private static int PARTITIONS = Integer.getInteger("com.oracle.buzzmessagebus.impl.IdToMsg.IdToMsg.PARTITIONS", 0);
   private final String name;
   private final LongArray[] idToMsgs;

   IdToMsg(String name) {
      this.name = name;
      this.idToMsgs = new SparseArray[PARTITIONS];

      for(int i = 0; i < PARTITIONS; ++i) {
         this.idToMsgs[i] = new SparseArray();
      }

   }

   private LongArray pick(long id) {
      return this.idToMsgs[(int)((Long.MAX_VALUE & id) % (long)PARTITIONS)];
   }

   BuzzMessageTokenImpl get(long id) {
      LongArray idToMsg = this.pick(id);
      synchronized(idToMsg) {
         return (BuzzMessageTokenImpl)idToMsg.get(id);
      }
   }

   void put(long id, BuzzMessageTokenImpl m) {
      LongArray idToMsg = this.pick(id);
      synchronized(idToMsg) {
         idToMsg.set(id, m);
      }
   }

   BuzzMessageTokenImpl remove(long id) {
      LongArray idToMsg = this.pick(id);
      synchronized(idToMsg) {
         return (BuzzMessageTokenImpl)idToMsg.remove(id);
      }
   }

   BuzzMessageTokenImpl putIfAbsent(long id, BuzzMessageTokenImpl m) {
      LongArray idToMsg = this.pick(id);
      synchronized(idToMsg) {
         BuzzMessageTokenImpl v = (BuzzMessageTokenImpl)idToMsg.set(id, m);
         if (v != null) {
            idToMsg.set(id, v);
            return v;
         } else {
            return v;
         }
      }
   }

   int size() {
      int v = 0;
      LongArray[] var2 = this.idToMsgs;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LongArray a = var2[var4];
         v += a.getSize();
      }

      return v;
   }

   public String toString() {
      return "{IdToMsg " + this.name + ' ' + this.size() + '}';
   }

   static {
      if (PARTITIONS == 0) {
         int n = Runtime.getRuntime().availableProcessors() * 3;
         int[] primes = new int[]{2, 2, 2, 3, 3, 5, 5, 7, 7, 7, 7, 11, 11, 13, 13, 13, 13, 17, 17, 19, 19, 19, 19, 23, 23, 23, 23, 23, 23, 29, 29, 31, 31, 31, 31, 31, 31, 37, 37, 37, 37, 41, 41, 43, 43, 43, 43, 47, 47, 47, 47, 47, 47, 53, 53, 53, 53, 53, 53, 59, 59, 61, 61, 61, 61, 61, 61, 67, 67, 67, 67, 71, 71, 73, 73, 73, 73, 73, 73, 79, 79, 79, 79, 83, 83, 83, 83, 83, 83, 89, 89, 89, 89, 89, 89, 89, 89, 97, 97, 97, 97};
         PARTITIONS = n < primes.length - 1 ? primes[n] : 101;
      }

   }
}
