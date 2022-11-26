package org.python.netty.buffer;

import java.util.List;

public interface PoolArenaMetric {
   int numThreadCaches();

   int numTinySubpages();

   int numSmallSubpages();

   int numChunkLists();

   List tinySubpages();

   List smallSubpages();

   List chunkLists();

   long numAllocations();

   long numTinyAllocations();

   long numSmallAllocations();

   long numNormalAllocations();

   long numHugeAllocations();

   long numDeallocations();

   long numTinyDeallocations();

   long numSmallDeallocations();

   long numNormalDeallocations();

   long numHugeDeallocations();

   long numActiveAllocations();

   long numActiveTinyAllocations();

   long numActiveSmallAllocations();

   long numActiveNormalAllocations();

   long numActiveHugeAllocations();

   long numActiveBytes();
}
