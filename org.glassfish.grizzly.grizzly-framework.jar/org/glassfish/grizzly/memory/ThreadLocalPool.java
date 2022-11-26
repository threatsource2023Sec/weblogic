package org.glassfish.grizzly.memory;

public interface ThreadLocalPool {
   void reset(Object var1);

   Object allocate(int var1);

   Object reallocate(Object var1, int var2);

   boolean release(Object var1);

   boolean isLastAllocated(Object var1);

   Object reduceLastAllocated(Object var1);

   boolean wantReset(int var1);

   int remaining();

   boolean hasRemaining();
}
