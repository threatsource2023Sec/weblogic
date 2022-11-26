package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Appender;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorResult;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.utils.Futures;
import org.glassfish.grizzly.utils.NullaryFunction;

public final class DefaultFilterChain extends ListFacadeFilterChain {
   private final FiltersStateFactory filtersStateFactory;
   private static final Logger LOGGER = Grizzly.logger(DefaultFilterChain.class);

   public DefaultFilterChain() {
      this(new ArrayList());
   }

   public DefaultFilterChain(Collection initialFilters) {
      super(new ArrayList(initialFilters));
      this.filtersStateFactory = new FiltersStateFactory();
   }

   public ProcessorResult process(Context context) {
      if (this.isEmpty()) {
         return ProcessorResult.createComplete();
      } else {
         InternalContextImpl internalContext = (InternalContextImpl)context;
         FilterChainContext filterChainContext = internalContext.filterChainContext;
         if (filterChainContext.getOperation() == FilterChainContext.Operation.NONE) {
            IOEvent ioEvent = internalContext.getIoEvent();
            if (ioEvent == IOEvent.WRITE) {
               Connection connection = context.getConnection();
               AsyncQueueEnabledTransport transport = (AsyncQueueEnabledTransport)connection.getTransport();
               AsyncQueueWriter writer = transport.getAsyncQueueIO().getWriter();
               return writer.processAsync(context).toProcessorResult();
            }

            filterChainContext.setOperation(FilterChainContext.ioEvent2Operation(ioEvent));
         }

         return this.execute(filterChainContext);
      }
   }

   public ProcessorResult execute(FilterChainContext var1) {
      // $FF: Couldn't be decompiled
   }

   protected final FilterExecution executeChainPart(FilterChainContext ctx, FilterExecutor executor, int start, int end, FiltersState filtersState) throws IOException {
      int i = start;
      Filter currentFilter = null;
      int lastNextActionType = 0;
      NextAction lastNextAction = null;

      Object chunk;
      while(i != end) {
         currentFilter = this.get(i);
         if (ctx.predefinedNextAction == null) {
            this.checkStoredMessage(ctx, filtersState, i);
            lastNextAction = this.executeFilter(executor, currentFilter, ctx);
         } else {
            lastNextAction = ctx.predefinedNextAction;
            ctx.predefinedNextAction = null;
         }

         lastNextActionType = lastNextAction.type();
         if (lastNextActionType != 0) {
            break;
         }

         InvokeAction invokeAction = (InvokeAction)lastNextAction;
         chunk = invokeAction.getChunk();
         if (chunk != null) {
            this.storeMessage(ctx, filtersState, invokeAction.isIncomplete(), i, chunk, invokeAction.getAppender());
         }

         i = executor.getNextFilter(ctx);
         ctx.setFilterIdx(i);
      }

      switch (lastNextActionType) {
         case 0:
            this.notifyComplete(ctx);
            break;
         case 1:
            assert currentFilter != null;

            StopAction stopAction = (StopAction)lastNextAction;
            chunk = stopAction.getIncompleteChunk();
            if (chunk != null) {
               this.storeMessage(ctx, filtersState, true, i, chunk, stopAction.getAppender());
            }
            break;
         case 2:
            return DefaultFilterChain.FilterExecution.createTerminate();
         case 3:
         case 4:
         default:
            break;
         case 5:
            ForkAction forkAction = (ForkAction)lastNextAction;
            return DefaultFilterChain.FilterExecution.createReExecute(forkAction.getContext());
      }

      return DefaultFilterChain.FilterExecution.createContinue();
   }

   protected NextAction executeFilter(FilterExecutor executor, Filter currentFilter, FilterChainContext ctx) throws IOException {
      NextAction nextNextAction;
      do {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINE, "Execute filter. filter={0} context={1}", new Object[]{currentFilter, ctx});
         }

         nextNextAction = executor.execute(currentFilter, ctx);
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINE, "after execute filter. filter={0} context={1} nextAction={2}", new Object[]{currentFilter, ctx, nextNextAction});
         }
      } while(nextNextAction.type() == 4);

      return nextNextAction;
   }

   private static boolean prepareRemainder(FilterChainContext ctx, FiltersState filtersState) {
      int idx = filtersState.peekUnparsedIdx(ctx.getOperation(), ctx.getStartIdx(), ctx.getEndIdx());
      if (idx != -1) {
         ctx.setFilterIdx(idx);
         ctx.setMessage((Object)null);
         return true;
      } else {
         return false;
      }
   }

   public void read(Connection connection, CompletionHandler completionHandler) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.setOperation(FilterChainContext.Operation.READ);
      context.getTransportContext().configureBlocking(true);
      ExecutorResolver.resolve(context).initIndexes(context);

      try {
         ReadResult readResult = this.read(context);
         Futures.notifyResult((FutureImpl)null, completionHandler, readResult);
      } catch (IOException var5) {
         Futures.notifyFailure((FutureImpl)null, completionHandler, var5);
      }

   }

   public ReadResult read(FilterChainContext context) throws IOException {
      Connection connection = context.getConnection();
      if (!context.getTransportContext().isBlocking()) {
         throw new IllegalStateException("FilterChain doesn't support standalone non blocking read. Please use Filter instead.");
      } else {
         FutureImpl future = Futures.createUnsafeFuture();
         context.operationCompletionHandler = Futures.toCompletionHandler(future);
         FilterExecutor executor = ExecutorResolver.resolve(context);
         FiltersState filtersState = this.obtainFiltersState(connection);

         do {
            if (!prepareRemainder(context, filtersState)) {
               context.setFilterIdx(0);
               context.setMessage((Object)null);
            }

            this.executeChainPart(context, executor, context.getFilterIdx(), context.getEndIdx(), filtersState);
         } while(!future.isDone());

         try {
            FilterChainContext retContext = (FilterChainContext)future.get();
            ReadResult rr = ReadResult.create(connection);
            rr.setMessage(retContext.getMessage());
            rr.setSrcAddressHolder(retContext.getAddressHolder());
            future.recycle(false);
            return rr;
         } catch (ExecutionException var8) {
            Throwable t = var8.getCause();
            if (t instanceof IOException) {
               throw (IOException)t;
            } else {
               throw new IOException(t);
            }
         } catch (InterruptedException var9) {
            throw new IOException(var9);
         }
      }
   }

   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler) {
      this.write(connection, dstAddress, message, completionHandler, (MessageCloner)null);
   }

   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, MessageCloner messageCloner) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.transportFilterContext.completionHandler = completionHandler;
      context.transportFilterContext.cloner = messageCloner;
      context.setAddress(dstAddress);
      context.setMessage(message);
      context.setOperation(FilterChainContext.Operation.WRITE);
      ProcessorExecutor.execute(context.internalContext);
   }

   /** @deprecated */
   @Deprecated
   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.transportFilterContext.completionHandler = completionHandler;
      context.transportFilterContext.pushBackHandler = pushBackHandler;
      context.setAddress(dstAddress);
      context.setMessage(message);
      context.setOperation(FilterChainContext.Operation.WRITE);
      ProcessorExecutor.execute(context.internalContext);
   }

   public void flush(Connection connection, CompletionHandler completionHandler) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.setOperation(FilterChainContext.Operation.EVENT);
      context.event = TransportFilter.createFlushEvent(completionHandler);
      ExecutorResolver.DOWNSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
      ProcessorExecutor.execute(context.internalContext);
   }

   public void fireEventDownstream(Connection connection, FilterChainEvent event, CompletionHandler completionHandler) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.operationCompletionHandler = completionHandler;
      context.setOperation(FilterChainContext.Operation.EVENT);
      context.event = event;
      ExecutorResolver.DOWNSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
      ProcessorExecutor.execute(context.internalContext);
   }

   public void fireEventUpstream(Connection connection, FilterChainEvent event, CompletionHandler completionHandler) {
      FilterChainContext context = this.obtainFilterChainContext(connection);
      context.operationCompletionHandler = completionHandler;
      context.setOperation(FilterChainContext.Operation.EVENT);
      context.event = event;
      ExecutorResolver.UPSTREAM_EXECUTOR_SAMPLE.initIndexes(context);
      ProcessorExecutor.execute(context.internalContext);
   }

   public void fail(FilterChainContext context, Throwable failure) {
      this.throwChain(context, ExecutorResolver.resolve(context), failure);
   }

   private void throwChain(FilterChainContext ctx, FilterExecutor executor, Throwable exception) {
      this.notifyFailure(ctx, exception);
      int endIdx = ctx.getStartIdx();
      if (ctx.getFilterIdx() != endIdx) {
         int i;
         do {
            i = executor.getPreviousFilter(ctx);
            ctx.setFilterIdx(i);
            this.get(i).exceptionOccurred(ctx, exception);
         } while(i != endIdx);

      }
   }

   public DefaultFilterChain subList(int fromIndex, int toIndex) {
      return new DefaultFilterChain(this.filters.subList(fromIndex, toIndex));
   }

   private FiltersState obtainFiltersState(Connection connection) {
      return (FiltersState)connection.obtainProcessorState(this, this.filtersStateFactory);
   }

   private void checkStoredMessage(FilterChainContext ctx, FiltersState filtersState, int filterIdx) {
      if (filtersState != null) {
         ctx.setMessage(filtersState.append(ctx.getOperation(), filterIdx, ctx.getMessage()));
      }

   }

   private void storeMessage(FilterChainContext ctx, FiltersState filtersState, boolean isIncomplete, int filterIdx, Object messageToStore, Appender appender) {
      assert messageToStore != null;

      filtersState.set(ctx.getOperation(), filterIdx, isIncomplete, messageToStore, appender);
   }

   private void notifyComplete(FilterChainContext context) {
      CompletionHandler completionHandler = context.operationCompletionHandler;
      if (completionHandler != null) {
         completionHandler.completed(context);
      }

      CompletionHandler transportCompletionHandler = context.transportFilterContext.completionHandler;
      if (transportCompletionHandler != null) {
         transportCompletionHandler.completed((Object)null);
      }

   }

   private void notifyFailure(FilterChainContext context, Throwable e) {
      CompletionHandler completionHandler = context.operationCompletionHandler;
      if (completionHandler != null) {
         completionHandler.failed(e);
      }

      CompletionHandler transportCompletionHandler = context.transportFilterContext.completionHandler;
      if (transportCompletionHandler != null) {
         transportCompletionHandler.failed(e);
      }

   }

   private static final class FilterExecution {
      private static final int CONTINUE_TYPE = 0;
      private static final int TERMINATE_TYPE = 1;
      private static final int REEXECUTE_TYPE = 2;
      private static final FilterExecution CONTINUE = new FilterExecution(0, (FilterChainContext)null);
      private static final FilterExecution TERMINATE = new FilterExecution(1, (FilterChainContext)null);
      private final int type;
      private final FilterChainContext context;

      public static FilterExecution createContinue() {
         return CONTINUE;
      }

      public static FilterExecution createTerminate() {
         return TERMINATE;
      }

      public static FilterExecution createReExecute(FilterChainContext context) {
         return new FilterExecution(2, context);
      }

      public FilterExecution(int type, FilterChainContext context) {
         this.type = type;
         this.context = context;
      }

      public int getType() {
         return this.type;
      }

      public FilterChainContext getContext() {
         return this.context;
      }

      // $FF: synthetic method
      static int access$100(FilterExecution x0) {
         return x0.type;
      }
   }

   private final class FiltersStateFactory implements NullaryFunction {
      private FiltersStateFactory() {
      }

      public FiltersState evaluate() {
         return new FiltersState(DefaultFilterChain.this.size());
      }

      // $FF: synthetic method
      FiltersStateFactory(Object x1) {
         this();
      }
   }

   private static final class FilterStateElement {
      private boolean isIncomplete;
      private Object state;
      private Appender appender;
      private boolean isValid = true;

      static FilterStateElement create(boolean isIncomplete, Object remainder) {
         return remainder instanceof Buffer ? create(isIncomplete, (Buffer)remainder, Buffers.getBufferAppender(true)) : create(isIncomplete, (Appendable)remainder);
      }

      static FilterStateElement create(boolean isIncomplete, Appendable state) {
         return new FilterStateElement(isIncomplete, state);
      }

      static FilterStateElement create(boolean isIncomplete, Object state, Appender appender) {
         return new FilterStateElement(isIncomplete, state, appender);
      }

      private FilterStateElement(boolean isIncomplete, Appendable state) {
         assert state != null;

         this.isIncomplete = isIncomplete;
         this.state = state;
         this.appender = null;
      }

      private FilterStateElement(boolean isIncomplete, Object state, Appender appender) {
         assert state != null;

         this.isIncomplete = isIncomplete;
         this.state = state;
         this.appender = appender;
      }

      private void set(boolean isIncomplete, Object state, Appender appender) {
         assert state != null;

         this.isIncomplete = isIncomplete;
         this.state = state;
         this.appender = appender;
         this.isValid = true;
      }

      private Object append(Object currentMessage) {
         Object resultMessage = currentMessage != null ? (this.appender != null ? this.appender.append(this.state, currentMessage) : ((Appendable)this.state).append(currentMessage)) : this.state;
         this.state = null;
         this.appender = null;
         this.isValid = false;
         return resultMessage;
      }
   }

   private static final class FiltersState {
      private static final int OPERATIONS_NUM = FilterChainContext.Operation.values().length;
      private final FilterStateElement[][] state;

      public FiltersState(int filtersNum) {
         this.state = new FilterStateElement[OPERATIONS_NUM][filtersNum];
      }

      public FilterStateElement get(FilterChainContext.Operation operation, int filterIndex) {
         int opIdx = operation.ordinal();
         FilterStateElement elem = this.state[opIdx][filterIndex];
         return elem != null && elem.isValid ? elem : null;
      }

      public void set(FilterChainContext.Operation operation, int filterIndex, boolean isIncomplete, Object messageToStore, Appender appender) {
         int opIdx = operation.ordinal();
         FilterStateElement elem = this.state[opIdx][filterIndex];
         if (elem != null) {
            elem.set(isIncomplete, messageToStore, appender);
         } else {
            this.state[opIdx][filterIndex] = DefaultFilterChain.FilterStateElement.create(isIncomplete, messageToStore, appender);
         }

      }

      public int peekUnparsedIdx(FilterChainContext.Operation operation, int start, int end) {
         if (start == end) {
            return -1;
         } else {
            int opIdx = operation.ordinal();
            int diff = end > start ? -1 : 1;
            int i = end;

            do {
               i += diff;
               FilterStateElement elem = this.state[opIdx][i];
               if (elem != null && elem.isValid && !elem.isIncomplete) {
                  return i;
               }
            } while(i != start);

            return -1;
         }
      }

      private Object append(FilterChainContext.Operation operation, int filterIdx, Object currentMessage) {
         FilterStateElement filterState = this.get(operation, filterIdx);
         return filterState != null ? filterState.append(currentMessage) : currentMessage;
      }
   }
}
