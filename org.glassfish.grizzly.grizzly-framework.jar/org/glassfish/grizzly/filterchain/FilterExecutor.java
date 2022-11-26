package org.glassfish.grizzly.filterchain;

import java.io.IOException;

public interface FilterExecutor {
   NextAction execute(Filter var1, FilterChainContext var2) throws IOException;

   int defaultStartIdx(FilterChainContext var1);

   int defaultEndIdx(FilterChainContext var1);

   int getNextFilter(FilterChainContext var1);

   int getPreviousFilter(FilterChainContext var1);

   void initIndexes(FilterChainContext var1);

   boolean hasNextFilter(FilterChainContext var1, int var2);

   boolean hasPreviousFilter(FilterChainContext var1, int var2);

   boolean isUpstream();

   boolean isDownstream();
}
