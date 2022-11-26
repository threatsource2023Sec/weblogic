package org.glassfish.grizzly.utils;

import java.io.IOException;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class DelayFilter extends BaseFilter {
   private final long readTimeoutMillis;
   private final long writeTimeoutMillis;

   public DelayFilter(long readTimeoutMillis, long writeTimeoutMillis) {
      this.readTimeoutMillis = readTimeoutMillis;
      this.writeTimeoutMillis = writeTimeoutMillis;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      try {
         Thread.sleep(this.readTimeoutMillis);
      } catch (Exception var3) {
      }

      return ctx.getInvokeAction();
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      try {
         Thread.sleep(this.writeTimeoutMillis);
      } catch (Exception var3) {
      }

      return ctx.getInvokeAction();
   }
}
