package org.glassfish.grizzly.filterchain;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;

public class FilterChainProcessorSelector implements ProcessorSelector {
   protected final FilterChainBuilder builder;

   public FilterChainProcessorSelector(FilterChainBuilder builder) {
      this.builder = builder;
   }

   public Processor select(IOEvent ioEvent, Connection connection) {
      FilterChain chain = this.builder.build();
      return chain.isInterested(ioEvent) ? chain : null;
   }
}
