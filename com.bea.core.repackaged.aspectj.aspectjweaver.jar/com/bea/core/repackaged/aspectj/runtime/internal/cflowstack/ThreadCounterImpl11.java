package com.bea.core.repackaged.aspectj.runtime.internal.cflowstack;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class ThreadCounterImpl11 implements ThreadCounter {
   private Hashtable counters = new Hashtable();
   private Thread cached_thread;
   private Counter cached_counter;
   private int change_count = 0;
   private static final int COLLECT_AT = 20000;
   private static final int MIN_COLLECT_AT = 100;

   private synchronized Counter getThreadCounter() {
      if (Thread.currentThread() != this.cached_thread) {
         this.cached_thread = Thread.currentThread();
         this.cached_counter = (Counter)this.counters.get(this.cached_thread);
         if (this.cached_counter == null) {
            this.cached_counter = new Counter();
            this.counters.put(this.cached_thread, this.cached_counter);
         }

         ++this.change_count;
         int size = Math.max(1, this.counters.size());
         if (this.change_count > Math.max(100, 20000 / size)) {
            List dead_stacks = new ArrayList();
            Enumeration e = this.counters.keys();

            Thread t;
            while(e.hasMoreElements()) {
               t = (Thread)e.nextElement();
               if (!t.isAlive()) {
                  dead_stacks.add(t);
               }
            }

            Iterator e = dead_stacks.iterator();

            while(e.hasNext()) {
               t = (Thread)e.next();
               this.counters.remove(t);
            }

            this.change_count = 0;
         }
      }

      return this.cached_counter;
   }

   public void inc() {
      ++this.getThreadCounter().value;
   }

   public void dec() {
      --this.getThreadCounter().value;
   }

   public boolean isNotZero() {
      return this.getThreadCounter().value != 0;
   }

   public void removeThreadCounter() {
   }

   static class Counter {
      protected int value = 0;
   }
}
