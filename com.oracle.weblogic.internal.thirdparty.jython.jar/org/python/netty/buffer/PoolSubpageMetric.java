package org.python.netty.buffer;

public interface PoolSubpageMetric {
   int maxNumElements();

   int numAvailable();

   int elementSize();

   int pageSize();
}
