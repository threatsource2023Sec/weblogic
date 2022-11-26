package org.glassfish.grizzly.http.server.util;

import java.io.IOException;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.CompositeBuffer.DisposeOrder;

public class HttpPipelineOptAddOn implements AddOn {
   private static final int DEFAULT_MAX_BUFFER_SIZE = 16384;
   private final int maxBufferSize;

   public HttpPipelineOptAddOn() {
      this(16384);
   }

   public HttpPipelineOptAddOn(int maxBufferSize) {
      this.maxBufferSize = maxBufferSize;
   }

   public void setup(NetworkListener networkListener, FilterChainBuilder builder) {
      int tfIdx = builder.indexOfType(TransportFilter.class);
      builder.add(tfIdx + 1, new PlugFilter(this.maxBufferSize, networkListener.getTransport().getAttributeBuilder()));
   }

   private static class PlugFilter extends BaseFilter {
      private final Attribute plugAttr;
      private final int maxBufferSize;

      public PlugFilter(int maxBufferSize, AttributeBuilder builder) {
         this.maxBufferSize = maxBufferSize;
         this.plugAttr = builder.createAttribute(PlugFilter.class + ".plug");
      }

      public NextAction handleRead(FilterChainContext ctx) throws IOException {
         if (!ctx.getTransportContext().isBlocking()) {
            Plug plug = HttpPipelineOptAddOn.PlugFilter.Plug.create(ctx, this);
            ctx.getInternalContext().addLifeCycleListener(plug);
            this.plugAttr.set(ctx, plug);
         }

         return ctx.getInvokeAction();
      }

      public NextAction handleWrite(FilterChainContext ctx) throws IOException {
         Plug plug = (Plug)this.plugAttr.get(ctx);
         if (plug != null && plug.isPlugged) {
            WritableMessage msg = (WritableMessage)ctx.getMessage();
            if (!msg.isExternal()) {
               Buffer buf = (Buffer)msg;
               MessageCloner cloner = ctx.getTransportContext().getMessageCloner();
               plug.append(cloner == null ? buf : (Buffer)cloner.clone(ctx.getConnection(), buf), ctx.getTransportContext().getCompletionHandler());
               if (plug.size() > this.maxBufferSize) {
                  plug.flush();
               }

               return ctx.getStopAction();
            }

            plug.flush();
         }

         return ctx.getInvokeAction();
      }

      public static final class AggrCompletionHandler implements CompletionHandler {
         private CompletionHandler[] handlers = new CompletionHandler[16];
         private int sz;

         public void add(CompletionHandler handler) {
            this.ensureSize();
            this.handlers[this.sz++] = handler;
         }

         public void cancelled() {
            for(int i = 0; i < this.sz; ++i) {
               this.handlers[i].cancelled();
            }

         }

         public void failed(Throwable throwable) {
            for(int i = 0; i < this.sz; ++i) {
               this.handlers[i].failed(throwable);
            }

         }

         public void completed(Object result) {
            for(int i = 0; i < this.sz; ++i) {
               this.handlers[i].completed(result);
            }

         }

         public void updated(Object result) {
            for(int i = 0; i < this.sz; ++i) {
               this.handlers[i].updated(result);
            }

         }

         public void clear() {
            for(int i = 0; i < this.sz; ++i) {
               this.handlers[i] = null;
            }

            this.sz = 0;
         }

         private void ensureSize() {
            if (this.handlers.length == this.sz) {
               this.handlers = (CompletionHandler[])Arrays.copyOf(this.handlers, this.sz * 3 / 2 + 1);
            }

         }
      }

      public static class Plug extends IOEventLifeCycleListener.Adapter {
         private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(Plug.class, 4);
         private final MessageCloner cloner = new MessageCloner() {
            public Buffer clone(Connection connection, Buffer originalMessage) {
               Plug.this.isWrittenInThisThread = false;
               return originalMessage;
            }
         };
         private FilterChainContext ctx;
         private PlugFilter plugFilter;
         private CompositeBuffer buffer;
         private boolean isPlugged;
         private AggrCompletionHandler aggrCompletionHandler;
         private boolean isWrittenInThisThread;

         public static Plug create(FilterChainContext ctx, PlugFilter plugFilter) {
            Plug plug = (Plug)ThreadCache.takeFromCache(CACHE_IDX);
            if (plug == null) {
               plug = new Plug();
            }

            return plug.init(ctx, plugFilter);
         }

         Plug init(FilterChainContext ctx, PlugFilter plugFilter) {
            this.ctx = ctx.copy();
            this.plugFilter = plugFilter;
            this.isPlugged = true;
            return this;
         }

         private boolean append(Buffer msg, CompletionHandler completionHandler) {
            if (this.isPlugged) {
               this.obtainCompositeBuffer().append(msg);
               if (completionHandler != null) {
                  this.obtainAggrCompletionHandler().add(completionHandler);
               }

               return true;
            } else {
               return false;
            }
         }

         private CompositeBuffer obtainCompositeBuffer() {
            if (this.buffer == null) {
               this.buffer = CompositeBuffer.newBuffer(this.ctx.getMemoryManager());
               this.buffer.allowBufferDispose(true);
               this.buffer.allowInternalBuffersDispose(true);
               this.buffer.disposeOrder(DisposeOrder.LAST_TO_FIRST);
            }

            return this.buffer;
         }

         private AggrCompletionHandler obtainAggrCompletionHandler() {
            if (this.aggrCompletionHandler == null) {
               this.aggrCompletionHandler = new AggrCompletionHandler();
            }

            return this.aggrCompletionHandler;
         }

         public void onContextSuspend(Context context) throws IOException {
            this.unplug(context);
         }

         public void onContextManualIOEventControl(Context context) throws IOException {
            this.unplug(context);
         }

         public void onComplete(Context context, Object data) throws IOException {
            this.unplug(context);
         }

         private void unplug(Context context) {
            if (this.isPlugged) {
               this.flush();
               this.ctx.completeAndRecycle();
               this.isPlugged = false;
               context.removeLifeCycleListener(this);
               this.plugFilter.plugAttr.remove(context);
               this.recycle();
            }

         }

         private void flush() {
            if (this.isPlugged && this.buffer != null) {
               this.isWrittenInThisThread = true;
               this.ctx.write((Object)null, this.buffer, this.aggrCompletionHandler, this.cloner);
               this.buffer = null;
               if (this.isWrittenInThisThread && this.aggrCompletionHandler != null) {
                  this.aggrCompletionHandler.clear();
               } else {
                  this.aggrCompletionHandler = null;
               }
            }

         }

         private void recycle() {
            if (this.aggrCompletionHandler != null) {
               this.aggrCompletionHandler.clear();
            }

            this.ctx = null;
            this.plugFilter = null;
            ThreadCache.putToCache(CACHE_IDX, this);
         }

         private int size() {
            return this.isPlugged && this.buffer != null ? this.buffer.remaining() : 0;
         }
      }
   }
}
