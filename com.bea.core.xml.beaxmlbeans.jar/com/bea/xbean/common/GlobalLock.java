package com.bea.xbean.common;

public class GlobalLock {
   private static final Mutex GLOBAL_MUTEX = new Mutex();

   public static void acquire() throws InterruptedException {
      GLOBAL_MUTEX.acquire();
   }

   public static void tryToAcquire() {
      GLOBAL_MUTEX.tryToAcquire();
   }

   public static void release() {
      GLOBAL_MUTEX.release();
   }
}
