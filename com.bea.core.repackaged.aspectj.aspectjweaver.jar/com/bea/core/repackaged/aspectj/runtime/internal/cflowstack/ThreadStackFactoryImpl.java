package com.bea.core.repackaged.aspectj.runtime.internal.cflowstack;

import java.util.Stack;

public class ThreadStackFactoryImpl implements ThreadStackFactory {
   public ThreadStack getNewThreadStack() {
      return new ThreadStackImpl();
   }

   public ThreadCounter getNewThreadCounter() {
      return new ThreadCounterImpl();
   }

   private static class ThreadCounterImpl extends ThreadLocal implements ThreadCounter {
      private ThreadCounterImpl() {
      }

      public Object initialValue() {
         return new Counter();
      }

      public Counter getThreadCounter() {
         return (Counter)this.get();
      }

      public void removeThreadCounter() {
         this.remove();
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

      // $FF: synthetic method
      ThreadCounterImpl(Object x0) {
         this();
      }

      static class Counter {
         protected int value = 0;
      }
   }

   private static class ThreadStackImpl extends ThreadLocal implements ThreadStack {
      private ThreadStackImpl() {
      }

      public Object initialValue() {
         return new Stack();
      }

      public Stack getThreadStack() {
         return (Stack)this.get();
      }

      public void removeThreadStack() {
         this.remove();
      }

      // $FF: synthetic method
      ThreadStackImpl(Object x0) {
         this();
      }
   }
}
