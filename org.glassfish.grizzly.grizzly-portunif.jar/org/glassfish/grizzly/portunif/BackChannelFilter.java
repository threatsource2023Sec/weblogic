package org.glassfish.grizzly.portunif;

import java.io.IOException;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;

public class BackChannelFilter extends BaseFilter {
   private final PUFilter puFilter;

   BackChannelFilter(PUFilter puFilter) {
      this.puFilter = puFilter;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      if (!this.isFilterChainRead(ctx)) {
         return ctx.getInvokeAction();
      } else {
         FilterChainContext suspendedParentContext = (FilterChainContext)this.puFilter.suspendedContextAttribute.get(ctx);

         assert suspendedParentContext != null;

         ReadResult readResult = suspendedParentContext.read();
         ctx.setMessage(readResult.getMessage());
         ctx.setAddressHolder(readResult.getSrcAddressHolder());
         readResult.recycle();
         return ctx.getInvokeAction();
      }
   }

   private boolean isFilterChainRead(FilterChainContext ctx) {
      return ctx.getMessage() == null;
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      FilterChainContext suspendedParentContext = (FilterChainContext)this.puFilter.suspendedContextAttribute.get(ctx);

      assert suspendedParentContext != null;

      FilterChainContext.TransportContext transportContext = ctx.getTransportContext();
      suspendedParentContext.write(ctx.getAddress(), ctx.getMessage(), transportContext.getCompletionHandler(), transportContext.getPushBackHandler(), transportContext.getMessageCloner(), transportContext.isBlocking());
      return ctx.getStopAction();
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (isDownstream(ctx)) {
         FilterChainContext suspendedParentContext = (FilterChainContext)this.puFilter.suspendedContextAttribute.get(ctx);

         assert suspendedParentContext != null;

         suspendedParentContext.notifyDownstream(event);
      }

      return ctx.getInvokeAction();
   }

   public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
      if (isDownstream(ctx)) {
         FilterChainContext suspendedParentContext = (FilterChainContext)this.puFilter.suspendedContextAttribute.get(ctx);

         assert suspendedParentContext != null;

         suspendedParentContext.fail(error);
      }

   }

   private static boolean isDownstream(FilterChainContext context) {
      return context.getStartIdx() > context.getEndIdx();
   }
}
