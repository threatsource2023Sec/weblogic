package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Holder;

public class FilterChainContext implements AttributeStorage {
   private static final Logger logger = Grizzly.logger(FilterChainContext.class);
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(FilterChainContext.class, 8);
   public static final int NO_FILTER_INDEX = Integer.MIN_VALUE;
   private static final NextAction INVOKE_ACTION = new InvokeAction();
   private static final NextAction STOP_ACTION = new StopAction();
   private static final NextAction SUSPEND_ACTION = new SuspendAction();
   private static final NextAction RERUN_FILTER_ACTION = new RerunFilterAction();
   final InternalContextImpl internalContext = new InternalContextImpl(this);
   final TransportContext transportFilterContext = new TransportContext();
   private volatile State state;
   private Operation operation;
   protected CompletionHandler operationCompletionHandler;
   private final Runnable contextRunnable;
   private Object message;
   private Closeable closeable;
   protected FilterChainEvent event;
   private Holder addressHolder;
   NextAction predefinedNextAction;
   private int filterIdx;
   private int startIdx;
   private int endIdx;
   private final StopAction cachedStopAction;
   private final InvokeAction cachedInvokeAction;
   private final List completionListeners;
   private final List copyListeners;

   public static FilterChainContext create(Connection connection) {
      return create(connection, connection);
   }

   public static FilterChainContext create(Connection connection, Closeable closeable) {
      FilterChainContext context = (FilterChainContext)ThreadCache.takeFromCache(CACHE_IDX);
      if (context == null) {
         context = new FilterChainContext();
      }

      context.setConnection(connection);
      context.setCloseable(closeable);
      context.getTransportContext().isBlocking = connection.isBlocking();
      return context;
   }

   public FilterChainContext() {
      this.operation = FilterChainContext.Operation.NONE;
      this.cachedStopAction = new StopAction();
      this.cachedInvokeAction = new InvokeAction();
      this.completionListeners = new ArrayList(2);
      this.copyListeners = new ArrayList(2);
      this.filterIdx = Integer.MIN_VALUE;
      this.contextRunnable = new Runnable() {
         public void run() {
            try {
               if (FilterChainContext.this.state == FilterChainContext.State.SUSPEND) {
                  FilterChainContext.this.state = FilterChainContext.State.RUNNING;
               }

               ProcessorExecutor.execute(FilterChainContext.this.internalContext);
            } catch (Exception var2) {
               FilterChainContext.logger.log(Level.FINE, "Exception during running Processor", var2);
            }

         }
      };
   }

   public Runnable suspend() {
      this.internalContext.suspend();
      this.state = FilterChainContext.State.SUSPEND;
      return this.getRunnable();
   }

   public void resume() {
      this.internalContext.resume();
      this.getRunnable().run();
   }

   public void resumeNext() {
      this.resume(this.getInvokeAction());
   }

   public void resume(NextAction nextAction) {
      this.internalContext.resume();

      try {
         if (this.state == FilterChainContext.State.SUSPEND) {
            this.state = FilterChainContext.State.RUNNING;
         }

         this.predefinedNextAction = nextAction;
         ProcessorExecutor.execute(this.internalContext);
      } catch (Exception var3) {
         logger.log(Level.FINE, "Exception during running Processor", var3);
      }

   }

   public void fork() {
      this.fork((NextAction)null);
   }

   public void fork(NextAction nextAction) {
      try {
         this.predefinedNextAction = this.getForkAction(nextAction);
         ProcessorExecutor.execute(this.internalContext);
      } catch (Exception var3) {
         logger.log(Level.FINE, "Exception during running Processor", var3);
      }

   }

   public State state() {
      return this.state;
   }

   public int nextFilterIdx() {
      return ++this.filterIdx;
   }

   public int previousFilterIdx() {
      return --this.filterIdx;
   }

   public int getFilterIdx() {
      return this.filterIdx;
   }

   public void setFilterIdx(int index) {
      this.filterIdx = index;
   }

   public int getStartIdx() {
      return this.startIdx;
   }

   public void setStartIdx(int startIdx) {
      this.startIdx = startIdx;
   }

   public int getEndIdx() {
      return this.endIdx;
   }

   public void setEndIdx(int endIdx) {
      this.endIdx = endIdx;
   }

   public FilterChain getFilterChain() {
      return (FilterChain)this.internalContext.getProcessor();
   }

   public Connection getConnection() {
      return this.internalContext.getConnection();
   }

   void setConnection(Connection connection) {
      this.internalContext.setConnection(connection);
   }

   public Closeable getCloseable() {
      return this.closeable;
   }

   void setCloseable(Closeable closeable) {
      this.closeable = (Closeable)(closeable != null ? closeable : this.getConnection());
   }

   public Object getMessage() {
      return this.message;
   }

   public void setMessage(Object message) {
      this.message = message;
   }

   public Holder getAddressHolder() {
      return this.addressHolder;
   }

   public void setAddressHolder(Holder addressHolder) {
      this.addressHolder = addressHolder;
   }

   public Object getAddress() {
      return this.addressHolder != null ? this.addressHolder.get() : null;
   }

   public void setAddress(Object address) {
      this.addressHolder = Holder.staticHolder(address);
   }

   protected final Runnable getRunnable() {
      return this.contextRunnable;
   }

   public TransportContext getTransportContext() {
      return this.transportFilterContext;
   }

   public final Context getInternalContext() {
      return this.internalContext;
   }

   Operation getOperation() {
      return this.operation;
   }

   void setOperation(Operation operation) {
      this.operation = operation;
   }

   public NextAction getInvokeAction() {
      return INVOKE_ACTION;
   }

   public NextAction getInvokeAction(Object unparsedChunk) {
      if (unparsedChunk == null) {
         return INVOKE_ACTION;
      } else {
         this.cachedInvokeAction.setUnparsedChunk(unparsedChunk);
         return this.cachedInvokeAction;
      }
   }

   public NextAction getInvokeAction(Object incompleteChunk, Appender appender) {
      if (incompleteChunk == null) {
         return INVOKE_ACTION;
      } else {
         if (appender == null) {
            if (incompleteChunk instanceof Buffer) {
               appender = Buffers.getBufferAppender(true);
            } else if (!(incompleteChunk instanceof Appendable)) {
               throw new IllegalArgumentException("Remainder has to be either " + Buffer.class.getName() + " or " + Appendable.class.getName() + " but was " + incompleteChunk.getClass().getName());
            }
         }

         this.cachedInvokeAction.setIncompleteChunk(incompleteChunk, appender);
         return this.cachedInvokeAction;
      }
   }

   public NextAction getStopAction() {
      return STOP_ACTION;
   }

   public NextAction getStopAction(Object incompleteChunk) {
      return incompleteChunk instanceof Buffer ? this.getStopAction((Buffer)incompleteChunk, Buffers.getBufferAppender(true)) : this.getStopAction(incompleteChunk, (Appender)null);
   }

   public NextAction getStopAction(Object incompleteChunk, Appender appender) {
      if (incompleteChunk == null) {
         return STOP_ACTION;
      } else if (appender == null && !(incompleteChunk instanceof Appendable)) {
         throw new IllegalArgumentException("Remainder has to be either " + Buffer.class.getName() + " or " + Appendable.class.getName() + " but was " + incompleteChunk.getClass().getName());
      } else {
         this.cachedStopAction.setIncompleteChunk(incompleteChunk, appender);
         return this.cachedStopAction;
      }
   }

   public NextAction getForkAction() {
      return this.getForkAction((NextAction)null);
   }

   public NextAction getForkAction(NextAction nextAction) {
      FilterChainContext contextCopy = this.copy();
      contextCopy.addressHolder = this.addressHolder;
      contextCopy.predefinedNextAction = nextAction;
      return new ForkAction(contextCopy);
   }

   /** @deprecated */
   public NextAction getSuspendingStopAction() {
      return this.getForkAction();
   }

   public NextAction getSuspendAction() {
      return SUSPEND_ACTION;
   }

   public NextAction getRerunFilterAction() {
      return RERUN_FILTER_ACTION;
   }

   public ReadResult read() throws IOException {
      FilterChainContext newContext = this.getFilterChain().obtainFilterChainContext(this.getConnection());
      newContext.closeable = this.closeable;
      newContext.operation = FilterChainContext.Operation.READ;
      newContext.transportFilterContext.configureBlocking(true);
      newContext.startIdx = 0;
      newContext.filterIdx = 0;
      newContext.endIdx = this.filterIdx;
      this.getAttributes().copyTo(newContext.getAttributes());
      ReadResult rr = this.getFilterChain().read(newContext);
      newContext.completeAndRecycle();
      return rr;
   }

   public void write(Object message) {
      this.write((Object)null, message, (CompletionHandler)null, (PushBackHandler)null, (MessageCloner)null, this.transportFilterContext.isBlocking());
   }

   public void write(Object message, boolean blocking) {
      this.write((Object)null, message, (CompletionHandler)null, (PushBackHandler)null, (MessageCloner)null, blocking);
   }

   public void write(Object message, CompletionHandler completionHandler) {
      this.write((Object)null, message, completionHandler, (PushBackHandler)null, (MessageCloner)null, this.transportFilterContext.isBlocking());
   }

   public void write(Object message, CompletionHandler completionHandler, boolean blocking) {
      this.write((Object)null, message, completionHandler, (PushBackHandler)null, (MessageCloner)null, blocking);
   }

   public void write(Object address, Object message, CompletionHandler completionHandler) {
      this.write(address, message, completionHandler, (PushBackHandler)null, (MessageCloner)null, this.transportFilterContext.isBlocking());
   }

   public void write(Object address, Object message, CompletionHandler completionHandler, boolean blocking) {
      this.write(address, message, completionHandler, (PushBackHandler)null, (MessageCloner)null, blocking);
   }

   /** @deprecated */
   @Deprecated
   public void write(Object address, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
      this.write(address, message, completionHandler, pushBackHandler, this.transportFilterContext.isBlocking());
   }

   /** @deprecated */
   @Deprecated
   public void write(Object address, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler, boolean blocking) {
      this.write(address, message, completionHandler, pushBackHandler, (MessageCloner)null, blocking);
   }

   public void write(Object address, Object message, CompletionHandler completionHandler, MessageCloner cloner) {
      this.write(address, message, completionHandler, (PushBackHandler)null, cloner, this.transportFilterContext.isBlocking());
   }

   /** @deprecated */
   @Deprecated
   public void write(Object address, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler, MessageCloner cloner) {
      this.write(address, message, completionHandler, pushBackHandler, cloner, this.transportFilterContext.isBlocking());
   }

   public void write(Object address, Object message, CompletionHandler completionHandler, MessageCloner cloner, boolean blocking) {
      this.write(address, message, completionHandler, (PushBackHandler)null, cloner, blocking);
   }

   /** @deprecated */
   @Deprecated
   public void write(Object address, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler, MessageCloner cloner, boolean blocking) {
      FilterChainContext newContext = this.getFilterChain().obtainFilterChainContext(this.getConnection());
      newContext.operation = FilterChainContext.Operation.WRITE;
      newContext.transportFilterContext.configureBlocking(blocking);
      newContext.message = message;
      newContext.addressHolder = address == null ? this.addressHolder : Holder.staticHolder(address);
      newContext.closeable = this.closeable;
      newContext.transportFilterContext.completionHandler = completionHandler;
      newContext.transportFilterContext.pushBackHandler = pushBackHandler;
      newContext.transportFilterContext.cloner = cloner;
      newContext.startIdx = this.filterIdx - 1;
      newContext.filterIdx = this.filterIdx - 1;
      newContext.endIdx = -1;
      this.getAttributes().copyTo(newContext.getAttributes());
      ProcessorExecutor.execute(newContext.internalContext);
   }

   public void flush(CompletionHandler completionHandler) {
      FilterChainContext newContext = this.getFilterChain().obtainFilterChainContext(this.getConnection());
      newContext.operation = FilterChainContext.Operation.EVENT;
      newContext.closeable = this.closeable;
      newContext.event = TransportFilter.createFlushEvent(completionHandler);
      newContext.transportFilterContext.configureBlocking(this.transportFilterContext.isBlocking());
      newContext.addressHolder = this.addressHolder;
      newContext.startIdx = this.filterIdx - 1;
      newContext.filterIdx = this.filterIdx - 1;
      newContext.endIdx = -1;
      this.getAttributes().copyTo(newContext.getAttributes());
      ProcessorExecutor.execute(newContext.internalContext);
   }

   public void notifyUpstream(FilterChainEvent event) {
      this.notifyUpstream(event, (CompletionHandler)null);
   }

   public void notifyUpstream(FilterChainEvent event, CompletionHandler completionHandler) {
      FilterChainContext newContext = this.getFilterChain().obtainFilterChainContext(this.getConnection());
      newContext.setOperation(FilterChainContext.Operation.EVENT);
      newContext.event = event;
      newContext.closeable = this.closeable;
      newContext.addressHolder = this.addressHolder;
      newContext.startIdx = this.filterIdx + 1;
      newContext.filterIdx = this.filterIdx + 1;
      newContext.endIdx = this.endIdx;
      this.getAttributes().copyTo(newContext.getAttributes());
      newContext.operationCompletionHandler = completionHandler;
      ProcessorExecutor.execute(newContext.internalContext);
   }

   public void notifyDownstream(FilterChainEvent event) {
      this.notifyDownstream(event, (CompletionHandler)null);
   }

   public void notifyDownstream(FilterChainEvent event, CompletionHandler completionHandler) {
      FilterChainContext newContext = this.getFilterChain().obtainFilterChainContext(this.getConnection());
      newContext.setOperation(FilterChainContext.Operation.EVENT);
      newContext.event = event;
      newContext.closeable = this.closeable;
      newContext.addressHolder = this.addressHolder;
      newContext.startIdx = this.filterIdx - 1;
      newContext.filterIdx = this.filterIdx - 1;
      newContext.endIdx = -1;
      this.getAttributes().copyTo(newContext.getAttributes());
      newContext.operationCompletionHandler = completionHandler;
      ProcessorExecutor.execute(newContext.internalContext);
   }

   public void fail(Throwable error) {
      this.getFilterChain().fail(this, error);
   }

   public AttributeHolder getAttributes() {
      return this.internalContext.getAttributes();
   }

   public final void addCompletionListener(CompletionListener listener) {
      this.completionListeners.add(listener);
   }

   public final boolean removeCompletionListener(CompletionListener listener) {
      return this.completionListeners.remove(listener);
   }

   public final void addCopyListener(CopyListener listener) {
      this.copyListeners.add(listener);
   }

   public final boolean removeCopyListener(CopyListener listener) {
      return this.copyListeners.remove(listener);
   }

   public final MemoryManager getMemoryManager() {
      return this.getConnection().getMemoryManager();
   }

   public FilterChainContext copy() {
      FilterChain p = this.getFilterChain();
      FilterChainContext newContext = p.obtainFilterChainContext(this.getConnection());
      newContext.setOperation(this.getOperation());
      newContext.setCloseable(this.getCloseable());
      this.internalContext.softCopyTo(newContext.internalContext);
      newContext.setStartIdx(this.getStartIdx());
      newContext.setEndIdx(this.getEndIdx());
      newContext.setFilterIdx(this.getFilterIdx());
      this.getAttributes().copyTo(newContext.getAttributes());
      notifyCopy(this, newContext, this.copyListeners);
      return newContext;
   }

   public void reset() {
      this.cachedInvokeAction.reset();
      this.cachedStopAction.reset();
      this.message = null;
      this.closeable = null;
      this.event = null;
      this.addressHolder = null;
      this.filterIdx = Integer.MIN_VALUE;
      this.state = FilterChainContext.State.RUNNING;
      this.operationCompletionHandler = null;
      this.operation = FilterChainContext.Operation.NONE;
      this.internalContext.reset();
      this.transportFilterContext.reset();
      this.copyListeners.clear();
      this.predefinedNextAction = null;
   }

   public void completeAndRecycle() {
      notifyComplete(this, this.completionListeners);
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public void completeAndRelease() {
      notifyComplete(this, this.completionListeners);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(384);
      sb.append("FilterChainContext [");
      sb.append("connection=").append(this.getConnection());
      sb.append(", closeable=").append(this.getCloseable());
      sb.append(", operation=").append(this.getOperation());
      sb.append(", message=").append(String.valueOf(this.getMessage()));
      sb.append(", address=").append(this.getAddress());
      sb.append(']');
      return sb.toString();
   }

   static Operation ioEvent2Operation(IOEvent ioEvent) {
      switch (ioEvent) {
         case READ:
            return FilterChainContext.Operation.READ;
         case WRITE:
            return FilterChainContext.Operation.WRITE;
         case ACCEPTED:
            return FilterChainContext.Operation.ACCEPT;
         case CONNECTED:
            return FilterChainContext.Operation.CONNECT;
         case CLOSED:
            return FilterChainContext.Operation.CLOSE;
         default:
            return FilterChainContext.Operation.NONE;
      }
   }

   static void notifyComplete(FilterChainContext context, List completionListeners) {
      int size = completionListeners.size();

      for(int i = size - 1; i >= 0; --i) {
         ((CompletionListener)completionListeners.get(i)).onComplete(context);
      }

      completionListeners.clear();
   }

   static void notifyCopy(FilterChainContext srcContext, FilterChainContext copiedContext, List copyListeners) {
      int size = copyListeners.size();

      for(int i = 0; i < size; ++i) {
         ((CopyListener)copyListeners.get(i)).onCopy(srcContext, copiedContext);
      }

   }

   public interface CopyListener {
      void onCopy(FilterChainContext var1, FilterChainContext var2);
   }

   public interface CompletionListener {
      void onComplete(FilterChainContext var1);
   }

   public static final class TransportContext {
      private boolean isBlocking;
      CompletionHandler completionHandler;
      /** @deprecated */
      @Deprecated
      PushBackHandler pushBackHandler;
      MessageCloner cloner;

      public void configureBlocking(boolean isBlocking) {
         this.isBlocking = isBlocking;
      }

      public boolean isBlocking() {
         return this.isBlocking;
      }

      public CompletionHandler getCompletionHandler() {
         return this.completionHandler;
      }

      public void setCompletionHandler(CompletionHandler completionHandler) {
         this.completionHandler = completionHandler;
      }

      /** @deprecated */
      @Deprecated
      public PushBackHandler getPushBackHandler() {
         return this.pushBackHandler;
      }

      /** @deprecated */
      @Deprecated
      public void setPushBackHandler(PushBackHandler pushBackHandler) {
         this.pushBackHandler = pushBackHandler;
      }

      public MessageCloner getMessageCloner() {
         return this.cloner;
      }

      public void setMessageCloner(MessageCloner cloner) {
         this.cloner = cloner;
      }

      void reset() {
         this.isBlocking = false;
         this.completionHandler = null;
         this.pushBackHandler = null;
         this.cloner = null;
      }
   }

   public static enum Operation {
      NONE,
      ACCEPT,
      CONNECT,
      READ,
      WRITE,
      EVENT,
      CLOSE;
   }

   public static enum State {
      RUNNING,
      SUSPEND;
   }
}
