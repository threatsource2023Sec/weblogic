package org.glassfish.grizzly.http.server.util;

import java.io.IOException;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.NetworkListener;

public class AggregatorAddOn implements AddOn {
   public void setup(NetworkListener networkListener, FilterChainBuilder builder) {
      int httpServerFilterIdx = builder.indexOfType(HttpServerFilter.class);
      if (httpServerFilterIdx >= 0) {
         builder.add(httpServerFilterIdx, new AggregatorFilter());
      }

   }

   private static class AggregatorFilter extends BaseFilter {
      private AggregatorFilter() {
      }

      public NextAction handleRead(FilterChainContext ctx) throws IOException {
         Object message = ctx.getMessage();
         return message instanceof HttpContent && !((HttpContent)message).isLast() ? ctx.getStopAction(message) : ctx.getInvokeAction();
      }

      // $FF: synthetic method
      AggregatorFilter(Object x0) {
         this();
      }
   }
}
