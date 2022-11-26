package org.glassfish.grizzly.portunif;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.utils.ArraySet;

public class PUFilter extends BaseFilter {
   private static final Logger LOGGER = Grizzly.logger(PUFilter.class);
   private final SuspendedContextCopyListener suspendedContextCopyListener;
   private final BackChannelFilter backChannelFilter;
   private final ArraySet protocols;
   final Attribute puContextAttribute;
   final Attribute suspendedContextAttribute;
   private final boolean isCloseUnrecognizedConnection;

   public PUFilter() {
      this(true);
   }

   public PUFilter(boolean isCloseUnrecognizedConnection) {
      this.suspendedContextCopyListener = new SuspendedContextCopyListener();
      this.backChannelFilter = new BackChannelFilter(this);
      this.protocols = new ArraySet(PUProtocol.class);
      this.isCloseUnrecognizedConnection = isCloseUnrecognizedConnection;
      this.puContextAttribute = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(PUFilter.class.getName() + '-' + this.hashCode() + ".puContext");
      this.suspendedContextAttribute = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(PUFilter.class.getName() + '-' + this.hashCode() + ".suspendedContext");
   }

   public PUProtocol register(ProtocolFinder protocolFinder, FilterChain filterChain) {
      PUProtocol puProtocol = new PUProtocol(protocolFinder, filterChain);
      this.register(puProtocol);
      return puProtocol;
   }

   public void register(PUProtocol puProtocol) {
      Filter filter = (Filter)puProtocol.getFilterChain().get(0);
      if (filter != this.backChannelFilter) {
         throw new IllegalStateException("The first Filter in the protocol should be the BackChannelFilter");
      } else {
         this.protocols.add(puProtocol);
      }
   }

   public void deregister(PUProtocol puProtocol) {
      this.protocols.remove(puProtocol);
   }

   public Set getProtocols() {
      return this.protocols;
   }

   public Filter getBackChannelFilter() {
      return this.backChannelFilter;
   }

   public FilterChainBuilder getPUFilterChainBuilder() {
      FilterChainBuilder builder = FilterChainBuilder.stateless();
      builder.add(this.backChannelFilter);
      return builder;
   }

   public boolean isCloseUnrecognizedConnection() {
      return this.isCloseUnrecognizedConnection;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      PUContext puContext = (PUContext)this.puContextAttribute.get(connection);
      if (puContext == null) {
         puContext = new PUContext(this);
         this.puContextAttribute.set(connection, puContext);
      } else if (puContext.noProtocolsFound()) {
         return ctx.getInvokeAction();
      }

      PUProtocol protocol = puContext.protocol;
      if (protocol == null) {
         this.findProtocol(puContext, ctx);
         protocol = puContext.protocol;
      }

      if (protocol != null) {
         if (!puContext.isSticky) {
            puContext.reset();
         }

         FilterChainContext filterChainContext = this.obtainChildFilterChainContext(protocol, connection, ctx);
         filterChainContext.addCopyListener(this.suspendedContextCopyListener);
         this.suspendedContextAttribute.set(filterChainContext, ctx);
         NextAction suspendAction = ctx.getSuspendAction();
         ctx.suspend();
         ProcessorExecutor.execute(filterChainContext.getInternalContext());
         return suspendAction;
      } else if (puContext.noProtocolsFound()) {
         if (this.isCloseUnrecognizedConnection) {
            connection.closeSilently();
            return ctx.getStopAction();
         } else {
            return ctx.getInvokeAction();
         }
      } else {
         return ctx.getStopAction(ctx.getMessage());
      }
   }

   private FilterChainContext obtainChildFilterChainContext(PUProtocol protocol, Connection connection, FilterChainContext ctx) {
      FilterChain filterChain = protocol.getFilterChain();
      FilterChainContext filterChainContext = filterChain.obtainFilterChainContext(connection);
      Context context = filterChainContext.getInternalContext();
      context.setIoEvent(IOEvent.READ);
      context.addLifeCycleListener(new InternalProcessingHandler(ctx));
      filterChainContext.setAddressHolder(ctx.getAddressHolder());
      filterChainContext.setMessage(ctx.getMessage());
      return filterChainContext;
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (isUpstream(ctx)) {
         Connection connection = ctx.getConnection();
         PUContext puContext = (PUContext)this.puContextAttribute.get(connection);
         PUProtocol protocol;
         if (puContext != null && (protocol = puContext.protocol) != null) {
            FilterChain filterChain = protocol.getFilterChain();
            FilterChainContext context = filterChain.obtainFilterChainContext(connection);
            context.setStartIdx(-1);
            context.setFilterIdx(-1);
            context.setEndIdx(filterChain.size());
            this.suspendedContextAttribute.set(context, ctx);
            ctx.suspend();
            NextAction suspendAction = ctx.getSuspendAction();
            context.notifyUpstream(event, new InternalCompletionHandler(ctx));
            return suspendAction;
         }
      }

      return ctx.getInvokeAction();
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      return super.handleClose(ctx);
   }

   protected void findProtocol(PUContext puContext, FilterChainContext ctx) {
      PUProtocol[] protocolArray = (PUProtocol[])this.protocols.getArray();

      for(int i = 0; i < protocolArray.length; ++i) {
         PUProtocol protocol = protocolArray[i];
         if ((puContext.skippedProtocolFinders & 1 << i) == 0) {
            try {
               ProtocolFinder.Result result = protocol.getProtocolFinder().find(puContext, ctx);
               switch (result) {
                  case FOUND:
                     puContext.protocol = protocol;
                     return;
                  case NOT_FOUND:
                     puContext.skippedProtocolFinders = (short)(puContext.skippedProtocolFinders ^ 1 << i);
               }
            } catch (Exception var7) {
               LOGGER.log(Level.WARNING, "ProtocolFinder " + protocol.getProtocolFinder() + " reported error", var7);
            }
         }
      }

   }

   private static boolean isUpstream(FilterChainContext context) {
      return context.getStartIdx() < context.getEndIdx();
   }

   private class SuspendedContextCopyListener implements FilterChainContext.CopyListener {
      private SuspendedContextCopyListener() {
      }

      public void onCopy(FilterChainContext srcContext, FilterChainContext copiedContext) {
         FilterChainContext suspendedContextCopy = ((FilterChainContext)PUFilter.this.suspendedContextAttribute.get(srcContext)).copy();
         PUFilter.this.suspendedContextAttribute.set(copiedContext, suspendedContextCopy);
         copiedContext.addCopyListener(this);
      }

      // $FF: synthetic method
      SuspendedContextCopyListener(Object x1) {
         this();
      }
   }

   private static class InternalCompletionHandler implements CompletionHandler {
      private final FilterChainContext suspendedContext;

      public InternalCompletionHandler(FilterChainContext suspendedContext) {
         this.suspendedContext = suspendedContext;
      }

      public void cancelled() {
         this.failed(new CancellationException());
      }

      public void failed(Throwable throwable) {
         this.suspendedContext.fail(throwable);
      }

      public void completed(FilterChainContext context) {
         this.suspendedContext.resume(this.suspendedContext.getStopAction());
      }

      public void updated(FilterChainContext result) {
      }
   }

   private class InternalProcessingHandler extends IOEventLifeCycleListener.Adapter {
      private final FilterChainContext parentContext;

      private InternalProcessingHandler(FilterChainContext parentContext) {
         this.parentContext = parentContext;
      }

      public void onReregister(Context context) throws IOException {
         FilterChainContext suspendedContext = (FilterChainContext)PUFilter.this.suspendedContextAttribute.get(context);

         assert suspendedContext != null;

         suspendedContext.resume(suspendedContext.getForkAction());
      }

      public void onComplete(Context context, Object data) throws IOException {
         FilterChainContext suspendedContext = (FilterChainContext)PUFilter.this.suspendedContextAttribute.remove(context);

         assert suspendedContext != null;

         suspendedContext.resume(suspendedContext.getStopAction());
      }

      // $FF: synthetic method
      InternalProcessingHandler(FilterChainContext x1, Object x2) {
         this(x1);
      }
   }
}
