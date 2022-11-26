package org.python.netty.util.concurrent;

import org.python.netty.util.internal.InternalThreadLocalMap;

public class FastThreadLocalThread extends Thread {
   private InternalThreadLocalMap threadLocalMap;

   public FastThreadLocalThread() {
   }

   public FastThreadLocalThread(Runnable target) {
      super(target);
   }

   public FastThreadLocalThread(ThreadGroup group, Runnable target) {
      super(group, target);
   }

   public FastThreadLocalThread(String name) {
      super(name);
   }

   public FastThreadLocalThread(ThreadGroup group, String name) {
      super(group, name);
   }

   public FastThreadLocalThread(Runnable target, String name) {
      super(target, name);
   }

   public FastThreadLocalThread(ThreadGroup group, Runnable target, String name) {
      super(group, target, name);
   }

   public FastThreadLocalThread(ThreadGroup group, Runnable target, String name, long stackSize) {
      super(group, target, name, stackSize);
   }

   public final InternalThreadLocalMap threadLocalMap() {
      return this.threadLocalMap;
   }

   public final void setThreadLocalMap(InternalThreadLocalMap threadLocalMap) {
      this.threadLocalMap = threadLocalMap;
   }
}
