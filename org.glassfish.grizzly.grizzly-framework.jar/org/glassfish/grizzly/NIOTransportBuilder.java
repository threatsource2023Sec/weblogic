package org.glassfish.grizzly;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.nio.NIOChannelDistributor;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.SelectionKeyHandler;
import org.glassfish.grizzly.nio.SelectorHandler;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public abstract class NIOTransportBuilder {
   protected final Class transportClass;
   protected ThreadPoolConfig workerConfig;
   protected ThreadPoolConfig kernelConfig;
   protected SelectorProvider selectorProvider;
   protected SelectorHandler selectorHandler;
   protected SelectionKeyHandler selectionKeyHandler;
   protected MemoryManager memoryManager;
   protected AttributeBuilder attributeBuilder;
   protected IOStrategy ioStrategy;
   protected int selectorRunnerCount;
   protected NIOChannelDistributor nioChannelDistributor;
   protected String name;
   protected Processor processor;
   protected ProcessorSelector processorSelector;
   protected int readBufferSize;
   protected int writeBufferSize;
   protected int clientSocketSoTimeout;
   protected int connectionTimeout;
   protected boolean reuseAddress;
   protected int maxPendingBytesPerConnection;
   protected boolean optimizedForMultiplexing;
   protected long readTimeout;
   protected long writeTimeout;

   protected NIOTransportBuilder(Class transportClass) {
      this.selectorHandler = SelectorHandler.DEFAULT_SELECTOR_HANDLER;
      this.selectionKeyHandler = SelectionKeyHandler.DEFAULT_SELECTION_KEY_HANDLER;
      this.memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
      this.attributeBuilder = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER;
      this.ioStrategy = WorkerThreadIOStrategy.getInstance();
      this.selectorRunnerCount = -1;
      this.readBufferSize = -1;
      this.writeBufferSize = -1;
      this.clientSocketSoTimeout = 0;
      this.connectionTimeout = 30000;
      this.reuseAddress = true;
      this.maxPendingBytesPerConnection = -2;
      this.optimizedForMultiplexing = false;
      this.readTimeout = TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS);
      this.writeTimeout = TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS);
      this.transportClass = transportClass;
   }

   public int getSelectorRunnersCount() {
      return this.selectorRunnerCount;
   }

   public NIOTransportBuilder setSelectorRunnersCount(int selectorRunnersCount) {
      this.selectorRunnerCount = selectorRunnersCount;
      return this.getThis();
   }

   public ThreadPoolConfig getWorkerThreadPoolConfig() {
      return this.workerConfig;
   }

   public NIOTransportBuilder setWorkerThreadPoolConfig(ThreadPoolConfig workerConfig) {
      this.workerConfig = workerConfig;
      return this.getThis();
   }

   public ThreadPoolConfig getSelectorThreadPoolConfig() {
      return this.kernelConfig;
   }

   public NIOTransportBuilder setSelectorThreadPoolConfig(ThreadPoolConfig kernelConfig) {
      this.kernelConfig = kernelConfig;
      return this.getThis();
   }

   public IOStrategy getIOStrategy() {
      return this.ioStrategy;
   }

   public NIOTransportBuilder setIOStrategy(IOStrategy ioStrategy) {
      this.ioStrategy = ioStrategy;
      return this.getThis();
   }

   public MemoryManager getMemoryManager() {
      return this.memoryManager;
   }

   public NIOTransportBuilder setMemoryManager(MemoryManager memoryManager) {
      this.memoryManager = memoryManager;
      return this.getThis();
   }

   public SelectorHandler getSelectorHandler() {
      return this.selectorHandler;
   }

   public NIOTransportBuilder setSelectorHandler(SelectorHandler selectorHandler) {
      this.selectorHandler = selectorHandler;
      return this.getThis();
   }

   public SelectionKeyHandler getSelectionKeyHandler() {
      return this.selectionKeyHandler;
   }

   public NIOTransportBuilder setSelectionKeyHandler(SelectionKeyHandler selectionKeyHandler) {
      this.selectionKeyHandler = selectionKeyHandler;
      return this.getThis();
   }

   public AttributeBuilder getAttributeBuilder() {
      return this.attributeBuilder;
   }

   public NIOTransportBuilder setAttributeBuilder(AttributeBuilder attributeBuilder) {
      this.attributeBuilder = attributeBuilder;
      return this.getThis();
   }

   public NIOChannelDistributor getNIOChannelDistributor() {
      return this.nioChannelDistributor;
   }

   public NIOTransportBuilder setNIOChannelDistributor(NIOChannelDistributor nioChannelDistributor) {
      this.nioChannelDistributor = nioChannelDistributor;
      return this.getThis();
   }

   public SelectorProvider getSelectorProvider() {
      return this.selectorProvider;
   }

   public NIOTransportBuilder setSelectorProvider(SelectorProvider selectorProvider) {
      this.selectorProvider = selectorProvider;
      return this.getThis();
   }

   public String getName() {
      return this.name;
   }

   public NIOTransportBuilder setName(String name) {
      this.name = name;
      return this.getThis();
   }

   public Processor getProcessor() {
      return this.processor;
   }

   public NIOTransportBuilder setProcessor(Processor processor) {
      this.processor = processor;
      return this.getThis();
   }

   public ProcessorSelector getProcessorSelector() {
      return this.processorSelector;
   }

   public NIOTransportBuilder setProcessorSelector(ProcessorSelector processorSelector) {
      this.processorSelector = processorSelector;
      return this.getThis();
   }

   public int getReadBufferSize() {
      return this.readBufferSize;
   }

   public NIOTransportBuilder setReadBufferSize(int readBufferSize) {
      this.readBufferSize = readBufferSize;
      return this.getThis();
   }

   public int getWriteBufferSize() {
      return this.writeBufferSize;
   }

   public NIOTransportBuilder setWriteBufferSize(int writeBufferSize) {
      this.writeBufferSize = writeBufferSize;
      return this.getThis();
   }

   public int getClientSocketSoTimeout() {
      return this.clientSocketSoTimeout;
   }

   public NIOTransportBuilder setClientSocketSoTimeout(int clientSocketSoTimeout) {
      this.clientSocketSoTimeout = clientSocketSoTimeout;
      return this.getThis();
   }

   public int getConnectionTimeout() {
      return this.connectionTimeout;
   }

   public NIOTransportBuilder setConnectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this.getThis();
   }

   public long getReadTimeout(TimeUnit timeUnit) {
      return this.readTimeout <= 0L ? -1L : timeUnit.convert(this.readTimeout, TimeUnit.MILLISECONDS);
   }

   public NIOTransportBuilder setReadTimeout(long timeout, TimeUnit timeUnit) {
      if (timeout <= 0L) {
         this.readTimeout = -1L;
      } else {
         this.readTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      }

      return this.getThis();
   }

   public long getWriteTimeout(TimeUnit timeUnit) {
      return this.writeTimeout <= 0L ? -1L : timeUnit.convert(this.writeTimeout, TimeUnit.MILLISECONDS);
   }

   public NIOTransportBuilder setWriteTimeout(long timeout, TimeUnit timeUnit) {
      if (timeout <= 0L) {
         this.writeTimeout = -1L;
      } else {
         this.writeTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      }

      return this.getThis();
   }

   public boolean isReuseAddress() {
      return this.reuseAddress;
   }

   public NIOTransportBuilder setReuseAddress(boolean reuseAddress) {
      this.reuseAddress = reuseAddress;
      return this.getThis();
   }

   public int getMaxAsyncWriteQueueSizeInBytes() {
      return this.maxPendingBytesPerConnection;
   }

   public NIOTransportBuilder setMaxAsyncWriteQueueSizeInBytes(int maxAsyncWriteQueueSizeInBytes) {
      this.maxPendingBytesPerConnection = maxAsyncWriteQueueSizeInBytes;
      return this.getThis();
   }

   public boolean isOptimizedForMultiplexing() {
      return this.optimizedForMultiplexing;
   }

   public NIOTransportBuilder setOptimizedForMultiplexing(boolean optimizedForMultiplexing) {
      this.optimizedForMultiplexing = optimizedForMultiplexing;
      return this.getThis();
   }

   public NIOTransport build() {
      NIOTransport transport = this.create(this.name);
      transport.setIOStrategy(this.ioStrategy);
      if (this.workerConfig != null) {
         transport.setWorkerThreadPoolConfig(this.workerConfig.copy());
      }

      if (this.kernelConfig != null) {
         transport.setKernelThreadPoolConfig(this.kernelConfig.copy());
      }

      transport.setSelectorProvider(this.selectorProvider);
      transport.setSelectorHandler(this.selectorHandler);
      transport.setSelectionKeyHandler(this.selectionKeyHandler);
      transport.setMemoryManager(this.memoryManager);
      transport.setAttributeBuilder(this.attributeBuilder);
      transport.setSelectorRunnersCount(this.selectorRunnerCount);
      transport.setNIOChannelDistributor(this.nioChannelDistributor);
      transport.setProcessor(this.processor);
      transport.setProcessorSelector(this.processorSelector);
      transport.setClientSocketSoTimeout(this.clientSocketSoTimeout);
      transport.setConnectionTimeout(this.connectionTimeout);
      transport.setReadTimeout(this.readTimeout, TimeUnit.MILLISECONDS);
      transport.setWriteTimeout(this.writeTimeout, TimeUnit.MILLISECONDS);
      transport.setReadBufferSize(this.readBufferSize);
      transport.setWriteBufferSize(this.writeBufferSize);
      transport.setReuseAddress(this.reuseAddress);
      transport.setOptimizedForMultiplexing(this.isOptimizedForMultiplexing());
      transport.getAsyncQueueIO().getWriter().setMaxPendingBytesPerConnection(this.maxPendingBytesPerConnection);
      return transport;
   }

   protected abstract NIOTransportBuilder getThis();

   protected abstract NIOTransport create(String var1);
}
