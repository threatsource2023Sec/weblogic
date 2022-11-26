package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import java.util.List;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.ReadResult;

public interface FilterChain extends Processor, List {
   FilterChainContext obtainFilterChainContext(Connection var1);

   FilterChainContext obtainFilterChainContext(Connection var1, Closeable var2);

   FilterChainContext obtainFilterChainContext(Connection var1, int var2, int var3, int var4);

   FilterChainContext obtainFilterChainContext(Connection var1, Closeable var2, int var3, int var4, int var5);

   int indexOfType(Class var1);

   ProcessorResult execute(FilterChainContext var1);

   void flush(Connection var1, CompletionHandler var2);

   void fireEventUpstream(Connection var1, FilterChainEvent var2, CompletionHandler var3);

   void fireEventDownstream(Connection var1, FilterChainEvent var2, CompletionHandler var3);

   ReadResult read(FilterChainContext var1) throws IOException;

   void fail(FilterChainContext var1, Throwable var2);
}
