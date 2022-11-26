package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.ICloseType;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.Futures;
import org.glassfish.grizzly.utils.NullaryFunction;

public abstract class NIOConnection implements Connection {
   protected static final Object NOTIFICATION_INITIALIZED;
   protected static final Object NOTIFICATION_CLOSED_COMPLETE;
   private static final boolean WIN32;
   private static final Logger LOGGER;
   private static final short MAX_ZERO_READ_COUNT = 100;
   private boolean isInitialReadRequired = true;
   protected final NIOTransport transport;
   protected volatile int maxAsyncWriteQueueSize;
   protected volatile long readTimeoutMillis = 30000L;
   protected volatile long writeTimeoutMillis = 30000L;
   protected volatile SelectableChannel channel;
   protected volatile SelectionKey selectionKey;
   protected volatile SelectorRunner selectorRunner;
   protected volatile Processor processor;
   protected volatile ProcessorSelector processorSelector;
   protected final AttributeHolder attributes;
   private volatile TaskQueue asyncReadQueue;
   private final TaskQueue asyncWriteQueue;
   protected static final AtomicReferenceFieldUpdater connectCloseSemaphoreUpdater;
   private volatile Object connectCloseSemaphore;
   private final AtomicBoolean isCloseScheduled = new AtomicBoolean();
   private static final AtomicReferenceFieldUpdater closeReasonUpdater;
   private volatile CloseReason closeReason;
   private volatile GrizzlyFuture closeFuture;
   protected volatile boolean isBlocking;
   protected volatile boolean isStandalone;
   protected short zeroByteReadCount;
   private final List closeListeners = Collections.synchronizedList(new LinkedList());
   private final ProcessorStatesMap processorStateStorage = new ProcessorStatesMap();
   protected final DefaultMonitoringConfig monitoringConfig = new DefaultMonitoringConfig(ConnectionProbe.class);
   private final SelectorHandler.Task writeSimulatorRunnable = new SelectorHandler.Task() {
      public boolean run() throws IOException {
         return NIOConnection.this.transport.getIOStrategy().executeIoEvent(NIOConnection.this, IOEvent.WRITE, false);
      }
   };
   private final SelectorHandler.Task readSimulatorRunnable = new SelectorHandler.Task() {
      public boolean run() throws IOException {
         return NIOConnection.this.transport.getIOStrategy().executeIoEvent(NIOConnection.this, IOEvent.READ, false);
      }
   };

   public NIOConnection(NIOTransport transport) {
      this.transport = transport;
      this.asyncWriteQueue = TaskQueue.createTaskQueue(new TaskQueue.MutableMaxQueueSize() {
         public int getMaxQueueSize() {
            return NIOConnection.this.maxAsyncWriteQueueSize;
         }
      });
      this.attributes = transport.getAttributeBuilder().createSafeAttributeHolder();
   }

   public void configureBlocking(boolean isBlocking) {
      this.isBlocking = isBlocking;
   }

   public boolean isBlocking() {
      return this.isBlocking;
   }

   public MemoryManager getMemoryManager() {
      return this.transport.getMemoryManager();
   }

   public synchronized void configureStandalone(boolean isStandalone) {
      if (this.isStandalone != isStandalone) {
         this.isStandalone = isStandalone;
         if (isStandalone) {
            this.processor = StandaloneProcessor.INSTANCE;
            this.processorSelector = StandaloneProcessorSelector.INSTANCE;
         } else {
            this.processor = this.transport.getProcessor();
            this.processorSelector = this.transport.getProcessorSelector();
         }
      }

   }

   public boolean isStandalone() {
      return this.isStandalone;
   }

   public Transport getTransport() {
      return this.transport;
   }

   public int getMaxAsyncWriteQueueSize() {
      return this.maxAsyncWriteQueueSize;
   }

   public void setMaxAsyncWriteQueueSize(int maxAsyncWriteQueueSize) {
      this.maxAsyncWriteQueueSize = maxAsyncWriteQueueSize;
   }

   public long getReadTimeout(TimeUnit timeUnit) {
      return timeUnit.convert(this.readTimeoutMillis, TimeUnit.MILLISECONDS);
   }

   public void setReadTimeout(long timeout, TimeUnit timeUnit) {
      this.readTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
   }

   public long getWriteTimeout(TimeUnit timeUnit) {
      return timeUnit.convert(this.writeTimeoutMillis, TimeUnit.MILLISECONDS);
   }

   public void setWriteTimeout(long timeout, TimeUnit timeUnit) {
      this.writeTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
   }

   public SelectorRunner getSelectorRunner() {
      return this.selectorRunner;
   }

   protected void setSelectorRunner(SelectorRunner selectorRunner) {
      this.selectorRunner = selectorRunner;
   }

   public void attachToSelectorRunner(SelectorRunner selectorRunner) throws IOException {
      this.detachSelectorRunner();
      SelectorHandler selectorHandler = this.transport.getSelectorHandler();
      FutureImpl future = Futures.createSafeFuture();
      selectorHandler.registerChannelAsync(selectorRunner, this.channel, 0, this, Futures.toCompletionHandler(future));

      try {
         RegisterChannelResult result = (RegisterChannelResult)future.get(this.readTimeoutMillis, TimeUnit.MILLISECONDS);
         this.selectorRunner = selectorRunner;
         this.selectionKey = result.getSelectionKey();
      } catch (TimeoutException | InterruptedException var5) {
         throw new IOException("", var5);
      } catch (ExecutionException var6) {
         throw new IOException("", var6.getCause());
      }
   }

   public void detachSelectorRunner() throws IOException {
      SelectorRunner selectorRunnerLocal = this.selectorRunner;
      this.selectionKey = null;
      this.selectorRunner = null;
      if (selectorRunnerLocal != null) {
         this.transport.getSelectorHandler().deregisterChannel(selectorRunnerLocal, this.channel);
      }

   }

   public SelectableChannel getChannel() {
      return this.channel;
   }

   protected void setChannel(SelectableChannel channel) {
      this.channel = channel;
   }

   public SelectionKey getSelectionKey() {
      return this.selectionKey;
   }

   protected void setSelectionKey(SelectionKey selectionKey) {
      this.selectionKey = selectionKey;
      this.setChannel(selectionKey.channel());
   }

   public Processor obtainProcessor(IOEvent ioEvent) {
      if (this.processor == null && this.processorSelector == null) {
         return this.transport.obtainProcessor(ioEvent, this);
      } else if (this.processor != null && this.processor.isInterested(ioEvent)) {
         return this.processor;
      } else {
         if (this.processorSelector != null) {
            Processor selectedProcessor = this.processorSelector.select(ioEvent, this);
            if (selectedProcessor != null) {
               return selectedProcessor;
            }
         }

         return null;
      }
   }

   public Processor getProcessor() {
      return this.processor;
   }

   public void setProcessor(Processor preferableProcessor) {
      this.processor = preferableProcessor;
   }

   public ProcessorSelector getProcessorSelector() {
      return this.processorSelector;
   }

   public void setProcessorSelector(ProcessorSelector preferableProcessorSelector) {
      this.processorSelector = preferableProcessorSelector;
   }

   public Object obtainProcessorState(Processor processor, NullaryFunction factory) {
      return this.processorStateStorage.getState(processor, factory);
   }

   public void executeInEventThread(IOEvent event, final Runnable runnable) {
      Executor threadPool = this.transport.getIOStrategy().getThreadPoolFor(this, event);
      if (threadPool == null) {
         this.transport.getSelectorHandler().enque(this.selectorRunner, new SelectorHandler.Task() {
            public boolean run() throws Exception {
               runnable.run();
               return true;
            }
         }, (CompletionHandler)null);
      } else {
         threadPool.execute(runnable);
      }

   }

   public TaskQueue getAsyncReadQueue() {
      if (this.asyncReadQueue == null) {
         synchronized(this) {
            if (this.asyncReadQueue == null) {
               this.asyncReadQueue = TaskQueue.createTaskQueue((TaskQueue.MutableMaxQueueSize)null);
            }
         }
      }

      return this.asyncReadQueue;
   }

   public TaskQueue getAsyncWriteQueue() {
      return this.asyncWriteQueue;
   }

   public AttributeHolder getAttributes() {
      return this.attributes;
   }

   public GrizzlyFuture read() {
      FutureImpl future = Futures.createSafeFuture();
      this.read(Futures.toCompletionHandler(future));
      return future;
   }

   public void read(CompletionHandler completionHandler) {
      Processor obtainedProcessor = this.obtainProcessor(IOEvent.READ);
      obtainedProcessor.read(this, completionHandler);
   }

   public GrizzlyFuture write(Object message) {
      FutureImpl future = Futures.createSafeFuture();
      this.write((SocketAddress)null, message, Futures.toCompletionHandler(future), (PushBackHandler)null);
      return future;
   }

   public void write(Object message, CompletionHandler completionHandler) {
      this.write((SocketAddress)null, message, completionHandler, (PushBackHandler)null);
   }

   /** @deprecated */
   @Deprecated
   public void write(Object message, CompletionHandler completionHandler, PushBackHandler pushbackHandler) {
      this.write((SocketAddress)null, message, completionHandler, pushbackHandler);
   }

   public void write(SocketAddress dstAddress, Object message, CompletionHandler completionHandler) {
      this.write((SocketAddress)dstAddress, message, completionHandler, (PushBackHandler)null);
   }

   /** @deprecated */
   @Deprecated
   public void write(SocketAddress dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushbackHandler) {
      Processor obtainedProcessor = this.obtainProcessor(IOEvent.WRITE);
      obtainedProcessor.write(this, dstAddress, message, completionHandler, (PushBackHandler)pushbackHandler);
   }

   public boolean isOpen() {
      return this.channel != null && this.channel.isOpen() && this.closeReason == null;
   }

   public void assertOpen() throws IOException {
      CloseReason reason = this.getCloseReason();
      if (reason != null) {
         throw new IOException("Connection is closed", reason.getCause());
      }
   }

   public boolean isClosed() {
      return !this.isOpen();
   }

   public CloseReason getCloseReason() {
      CloseReason cr = this.closeReason;
      if (cr != null) {
         return cr;
      } else {
         return this.channel != null && this.channel.isOpen() ? null : CloseReason.LOCALLY_CLOSED_REASON;
      }
   }

   public void terminateSilently() {
      this.terminate0((CompletionHandler)null, CloseReason.LOCALLY_CLOSED_REASON);
   }

   public GrizzlyFuture terminate() {
      FutureImpl future = Futures.createSafeFuture();
      this.terminate0(Futures.toCompletionHandler(future), CloseReason.LOCALLY_CLOSED_REASON);
      return future;
   }

   public void terminateWithReason(IOException reason) {
      this.terminate0((CompletionHandler)null, new CloseReason(org.glassfish.grizzly.CloseType.LOCALLY, reason));
   }

   public GrizzlyFuture close() {
      FutureImpl future = Futures.createSafeFuture();
      this.closeGracefully0(Futures.toCompletionHandler(future), CloseReason.LOCALLY_CLOSED_REASON);
      return future;
   }

   /** @deprecated */
   public void close(CompletionHandler completionHandler) {
      this.closeGracefully0(completionHandler, CloseReason.LOCALLY_CLOSED_REASON);
   }

   public final void closeSilently() {
      this.closeGracefully0((CompletionHandler)null, CloseReason.LOCALLY_CLOSED_REASON);
   }

   public void closeWithReason(IOException reason) {
      this.closeGracefully0((CompletionHandler)null, new CloseReason(org.glassfish.grizzly.CloseType.LOCALLY, reason));
   }

   public GrizzlyFuture closeFuture() {
      if (this.closeFuture == null) {
         synchronized(this) {
            if (this.closeFuture == null) {
               CloseReason cr = this.closeReason;
               if (cr == null) {
                  final FutureImpl f = Futures.createSafeFuture();
                  this.addCloseListener(new org.glassfish.grizzly.CloseListener() {
                     public void onClosed(Closeable closeable, ICloseType type) throws IOException {
                        CloseReason cr = NIOConnection.this.closeReason;

                        assert cr != null;

                        f.result(cr);
                     }
                  });
                  this.closeFuture = f;
               } else {
                  this.closeFuture = Futures.createReadyFuture((Object)cr);
               }
            }
         }
      }

      return this.closeFuture;
   }

   protected void closeGracefully0(final CompletionHandler completionHandler, final CloseReason closeReason) {
      if (this.isCloseScheduled.compareAndSet(false, true)) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            closeReason = new CloseReason(closeReason.getType(), new IOException("Connection is closed at", closeReason.getCause()));
         }

         this.transport.getWriter(this).write(this, (WritableMessage)Buffers.EMPTY_BUFFER, (CompletionHandler)(new EmptyCompletionHandler() {
            public void completed(WriteResult result) {
               NIOConnection.this.terminate0(completionHandler, closeReason);
            }

            public void failed(Throwable throwable) {
               NIOConnection.this.terminate0(completionHandler, closeReason);
            }
         }));
      } else if (completionHandler != null) {
         this.addCloseListener(new org.glassfish.grizzly.CloseListener() {
            public void onClosed(Closeable closeable, ICloseType type) throws IOException {
               completionHandler.completed(NIOConnection.this);
            }
         });
      }

   }

   protected void terminate0(CompletionHandler completionHandler, CloseReason reason) {
      this.isCloseScheduled.set(true);
      if (closeReasonUpdater.compareAndSet(this, (Object)null, reason)) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            this.closeReason = new CloseReason(reason.getType(), new IOException("Connection is closed at", reason.getCause()));
         }

         this.preClose();
         this.notifyCloseListeners(reason);
         notifyProbesClose(this);
         this.transport.getSelectorHandler().execute(this.selectorRunner, new SelectorHandler.Task() {
            public boolean run() {
               try {
                  NIOConnection.this.doClose();
               } catch (IOException var2) {
                  NIOConnection.LOGGER.log(Level.FINE, "Error during connection close", var2);
               }

               return true;
            }
         }, new CompletionHandlerAdapter((FutureImpl)null, completionHandler) {
            protected Connection adapt(SelectorHandler.Task result) {
               return NIOConnection.this;
            }

            public void failed(Throwable throwable) {
               try {
                  NIOConnection.this.doClose();
               } catch (Exception var3) {
               }

               this.completed((Object)null);
            }
         });
      } else {
         Futures.notifyResult((FutureImpl)null, completionHandler, this);
      }

   }

   protected void doClose() throws IOException {
      this.transport.closeConnection(this);
   }

   public void addCloseListener(org.glassfish.grizzly.CloseListener closeListener) {
      CloseReason reason = this.closeReason;
      if (reason == null) {
         this.closeListeners.add(closeListener);
         reason = this.closeReason;
         if (reason != null && this.closeListeners.remove(closeListener)) {
            this.invokeCloseListener(closeListener, reason.getType());
         }
      } else {
         this.invokeCloseListener(closeListener, reason.getType());
      }

   }

   public boolean removeCloseListener(org.glassfish.grizzly.CloseListener closeListener) {
      return this.closeListeners.remove(closeListener);
   }

   public void addCloseListener(Connection.CloseListener closeListener) {
      this.addCloseListener((org.glassfish.grizzly.CloseListener)closeListener);
   }

   public boolean removeCloseListener(Connection.CloseListener closeListener) {
      return this.removeCloseListener((org.glassfish.grizzly.CloseListener)closeListener);
   }

   public void notifyConnectionError(Throwable error) {
      notifyProbesError(this, error);
   }

   public final MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected static void notifyProbesBind(NIOConnection connection) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ConnectionProbe probe = var2[var4];
            probe.onBindEvent(connection);
         }
      }

   }

   protected static void notifyProbesAccept(NIOConnection serverConnection, NIOConnection clientConnection) {
      ConnectionProbe[] probes = (ConnectionProbe[])serverConnection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionProbe probe = var3[var5];
            probe.onAcceptEvent(serverConnection, clientConnection);
         }
      }

   }

   protected static void notifyProbesConnect(NIOConnection connection) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ConnectionProbe probe = var2[var4];
            probe.onConnectEvent(connection);
         }
      }

   }

   protected static void notifyProbesRead(NIOConnection connection, Buffer data, int size) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var4 = probes;
         int var5 = probes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ConnectionProbe probe = var4[var6];
            probe.onReadEvent(connection, data, size);
         }
      }

   }

   protected static void notifyProbesWrite(NIOConnection connection, Buffer data, long size) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var5 = probes;
         int var6 = probes.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ConnectionProbe probe = var5[var7];
            probe.onWriteEvent(connection, data, size);
         }
      }

   }

   protected static void notifyIOEventReady(NIOConnection connection, IOEvent ioEvent) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionProbe probe = var3[var5];
            probe.onIOEventReadyEvent(connection, ioEvent);
         }
      }

   }

   protected static void notifyIOEventEnabled(NIOConnection connection, IOEvent ioEvent) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionProbe probe = var3[var5];
            probe.onIOEventEnableEvent(connection, ioEvent);
         }
      }

   }

   protected static void notifyIOEventDisabled(NIOConnection connection, IOEvent ioEvent) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionProbe probe = var3[var5];
            probe.onIOEventDisableEvent(connection, ioEvent);
         }
      }

   }

   protected static void notifyProbesClose(NIOConnection connection) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ConnectionProbe probe = var2[var4];
            probe.onCloseEvent(connection);
         }
      }

   }

   protected static void notifyProbesError(NIOConnection connection, Throwable error) {
      ConnectionProbe[] probes = (ConnectionProbe[])connection.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ConnectionProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionProbe probe = var3[var5];
            probe.onErrorEvent(connection, error);
         }
      }

   }

   private void notifyCloseListeners(CloseReason closeReason) {
      if (!this.closeListeners.isEmpty()) {
         org.glassfish.grizzly.CloseType closeType = closeReason.getType();
         ArrayList copiedCloseListeners;
         synchronized(this.closeListeners) {
            copiedCloseListeners = new ArrayList(this.closeListeners);
            this.closeListeners.clear();
         }

         Iterator var4 = copiedCloseListeners.iterator();

         while(var4.hasNext()) {
            org.glassfish.grizzly.CloseListener closeListener = (org.glassfish.grizzly.CloseListener)var4.next();
            this.invokeCloseListener(closeListener, closeType);
         }
      }

   }

   protected void preClose() {
      if (connectCloseSemaphoreUpdater.getAndSet(this, NOTIFICATION_CLOSED_COMPLETE) == NOTIFICATION_INITIALIZED) {
         this.transport.fireIOEvent(IOEvent.CLOSED, this, (IOEventLifeCycleListener)null);
      }

   }

   protected void enableInitialOpRead() throws IOException {
      if (this.isInitialReadRequired) {
         this.enableIOEvent(IOEvent.READ);
      }

   }

   public void simulateIOEvent(IOEvent ioEvent) throws IOException {
      if (this.isOpen()) {
         SelectorHandler selectorHandler = this.transport.getSelectorHandler();
         switch (ioEvent) {
            case WRITE:
               selectorHandler.enque(this.selectorRunner, this.writeSimulatorRunnable, (CompletionHandler)null);
               break;
            case READ:
               selectorHandler.enque(this.selectorRunner, this.readSimulatorRunnable, (CompletionHandler)null);
               break;
            default:
               throw new IllegalArgumentException("We support only READ and WRITE events. Got " + ioEvent);
         }

      }
   }

   public final void enableIOEvent(IOEvent ioEvent) throws IOException {
      boolean isOpRead = ioEvent == IOEvent.READ;
      int interest = ioEvent.getSelectionKeyInterest();
      if (interest != 0 && (!isOpRead || !this.isCloseScheduled.get()) && this.closeReason == null) {
         notifyIOEventEnabled(this, ioEvent);
         this.isInitialReadRequired = this.isInitialReadRequired && !isOpRead;
         SelectorHandler selectorHandler = this.transport.getSelectorHandler();
         selectorHandler.registerKeyInterest(this.selectorRunner, this.selectionKey, interest);
      }
   }

   public final void disableIOEvent(IOEvent ioEvent) throws IOException {
      int interest = ioEvent.getSelectionKeyInterest();
      if (interest != 0) {
         notifyIOEventDisabled(this, ioEvent);
         SelectorHandler selectorHandler = this.transport.getSelectorHandler();
         selectorHandler.deregisterKeyInterest(this.selectorRunner, this.selectionKey, interest);
      }
   }

   protected final void checkEmptyRead(int size) {
      if (WIN32) {
         if (size == 0) {
            short count = ++this.zeroByteReadCount;
            if (count >= 100) {
               this.closeSilently();
            }
         } else {
            this.zeroByteReadCount = 0;
         }
      }

   }

   final void onSelectionKeyUpdated(SelectionKey newSelectionKey) {
      this.selectionKey = newSelectionKey;
   }

   private void invokeCloseListener(org.glassfish.grizzly.CloseListener closeListener, org.glassfish.grizzly.CloseType closeType) {
      try {
         if (closeListener instanceof Connection.CloseListener) {
            Connection.CloseType closeLocal;
            if (closeType == org.glassfish.grizzly.CloseType.LOCALLY) {
               closeLocal = Connection.CloseType.LOCALLY;
            } else {
               closeLocal = Connection.CloseType.REMOTELY;
            }

            ((Connection.CloseListener)closeListener).onClosed((Connection)this, (Connection.CloseType)closeLocal);
         } else {
            closeListener.onClosed(this, closeType);
         }
      } catch (Exception var4) {
      }

   }

   void setMonitoringProbes(ConnectionProbe[] monitoringProbes) {
      this.monitoringConfig.addProbes(monitoringProbes);
   }

   static {
      NOTIFICATION_INITIALIZED = Boolean.TRUE;
      NOTIFICATION_CLOSED_COMPLETE = Boolean.FALSE;
      WIN32 = "\\".equals(System.getProperty("file.separator"));
      LOGGER = Grizzly.logger(NIOConnection.class);
      connectCloseSemaphoreUpdater = AtomicReferenceFieldUpdater.newUpdater(NIOConnection.class, Object.class, "connectCloseSemaphore");
      closeReasonUpdater = AtomicReferenceFieldUpdater.newUpdater(NIOConnection.class, CloseReason.class, "closeReason");
   }

   private static final class ProcessorStatesMap {
      private volatile int volatileFlag;
      private ProcessorState singleProcessorState;
      private ConcurrentMap processorStatesMap;

      private ProcessorStatesMap() {
      }

      public Object getState(Processor processor, NullaryFunction stateFactory) {
         int c = this.volatileFlag;
         if (c == 0) {
            return this.getStateSync(processor, stateFactory);
         } else {
            ProcessorState localProcessorState = this.singleProcessorState;
            if (localProcessorState != null) {
               return localProcessorState.processor.equals(processor) ? localProcessorState.state : NIOConnection.ProcessorStatesMap.StaticMapAccessor.getFromMap(this, processor, stateFactory);
            } else {
               return this.getStateSync(processor, stateFactory);
            }
         }
      }

      private synchronized Object getStateSync(Processor processor, NullaryFunction stateFactory) {
         if (this.volatileFlag == 0) {
            Object state = stateFactory.evaluate();
            this.singleProcessorState = new ProcessorState(processor, state);
            ++this.volatileFlag;
            return state;
         } else {
            return this.volatileFlag == 1 && this.singleProcessorState.processor.equals(processor) ? this.singleProcessorState.state : NIOConnection.ProcessorStatesMap.StaticMapAccessor.getFromMapSync(this, processor, stateFactory);
         }
      }

      // $FF: synthetic method
      ProcessorStatesMap(Object x0) {
         this();
      }

      private static final class StaticMapAccessor {
         private static Object getFromMap(ProcessorStatesMap storage, Processor processor, NullaryFunction stateFactory) {
            Map localStateMap = storage.processorStatesMap;
            if (localStateMap != null) {
               Object state = storage.processorStatesMap.get(processor);
               if (state != null) {
                  return state;
               }
            }

            return storage.getStateSync(processor, stateFactory);
         }

         private static Object getFromMapSync(ProcessorStatesMap storage, Processor processor, NullaryFunction stateFactory) {
            ConcurrentMap localStatesMap = storage.processorStatesMap;
            Object state;
            if (localStatesMap != null) {
               if (localStatesMap.containsKey(processor)) {
                  return localStatesMap.get(processor);
               } else {
                  state = stateFactory.evaluate();
                  localStatesMap.put(processor, state);
                  return state;
               }
            } else {
               ConcurrentMap localStatesMap = new ConcurrentHashMap(4);
               state = stateFactory.evaluate();
               localStatesMap.put(processor, state);
               storage.processorStatesMap = localStatesMap;
               storage.volatileFlag++;
               return state;
            }
         }

         static {
            Grizzly.logger(StaticMapAccessor.class).fine("Map is going to be used as Connection<->ProcessorState storage");
         }
      }

      private static final class ProcessorState {
         private final Processor processor;
         private final Object state;

         public ProcessorState(Processor processor, Object state) {
            this.processor = processor;
            this.state = state;
         }
      }
   }
}
