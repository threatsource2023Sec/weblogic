package com.bea.core.repackaged.aspectj.runtime.internal.cflowstack;

public interface ThreadCounter {
   void inc();

   void dec();

   boolean isNotZero();

   void removeThreadCounter();
}
