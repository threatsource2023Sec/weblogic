package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;
import org.glassfish.grizzly.utils.StateHolder;

public abstract class AbstractTransport implements Transport {
   protected String name;
   protected volatile boolean isBlocking;
   protected volatile boolean isStandalone;
   protected final StateHolder state;
   protected Processor processor;
   protected ProcessorSelector processorSelector;
   protected IOStrategy strategy;
   protected MemoryManager memoryManager;
   protected ExecutorService workerThreadPool;
   protected ExecutorService kernelPool;
   protected AttributeBuilder attributeBuilder;
   protected int readBufferSize;
   protected int writeBufferSize;
   protected ThreadPoolConfig workerPoolConfig;
   protected ThreadPoolConfig kernelPoolConfig;
   protected boolean managedWorkerPool = true;
   protected long writeTimeout;
   protected long readTimeout;
   protected final DefaultMonitoringConfig transportMonitoringConfig;
   protected final DefaultMonitoringConfig connectionMonitoringConfig;
   protected final DefaultMonitoringConfig threadPoolMonitoringConfig;

   public AbstractTransport(String name) {
      this.writeTimeout = TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS);
      this.readTimeout = TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS);
      this.transportMonitoringConfig = new DefaultMonitoringConfig(TransportProbe.class) {
         public Object createManagementObject() {
            return AbstractTransport.this.createJmxManagementObject();
         }
      };
      this.connectionMonitoringConfig = new DefaultMonitoringConfig(ConnectionProbe.class);
      this.threadPoolMonitoringConfig = new DefaultMonitoringConfig(ThreadPoolProbe.class);
      this.name = name;
      this.state = new StateHolder(Transport.State.STOPPED);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
      notifyProbesConfigChanged(this);
   }

   public boolean isBlocking() {
      return this.isBlocking;
   }

   public void configureBlocking(boolean isBlocking) {
      this.isBlocking = isBlocking;
      notifyProbesConfigChanged(this);
   }

   public boolean isStandalone() {
      return this.isStandalone;
   }

   public StateHolder getState() {
      return this.state;
   }

   public int getReadBufferSize() {
      return this.readBufferSize;
   }

   public void setReadBufferSize(int readBufferSize) {
      this.readBufferSize = readBufferSize;
      notifyProbesConfigChanged(this);
   }

   public int getWriteBufferSize() {
      return this.writeBufferSize;
   }

   public void setWriteBufferSize(int writeBufferSize) {
      this.writeBufferSize = writeBufferSize;
      notifyProbesConfigChanged(this);
   }

   public boolean isStopped() {
      Transport.State currentState = (Transport.State)this.state.getState();
      return currentState == Transport.State.STOPPED || currentState == Transport.State.STOPPING;
   }

   public boolean isPaused() {
      return this.state.getState() == Transport.State.PAUSED;
   }

   public Processor obtainProcessor(IOEvent ioEvent, Connection connection) {
      if (this.processor != null && this.processor.isInterested(ioEvent)) {
         return this.processor;
      } else {
         return this.processorSelector != null ? this.processorSelector.select(ioEvent, connection) : null;
      }
   }

   public Processor getProcessor() {
      return this.processor;
   }

   public void setProcessor(Processor processor) {
      this.processor = processor;
      notifyProbesConfigChanged(this);
   }

   public ProcessorSelector getProcessorSelector() {
      return this.processorSelector;
   }

   public void setProcessorSelector(ProcessorSelector selector) {
      this.processorSelector = selector;
      notifyProbesConfigChanged(this);
   }

   public IOStrategy getIOStrategy() {
      return this.strategy;
   }

   public void setIOStrategy(IOStrategy IOStrategy) {
      this.strategy = IOStrategy;
      ThreadPoolConfig strategyConfig = IOStrategy.createDefaultWorkerPoolConfig(this);
      if (strategyConfig == null) {
         this.workerPoolConfig = null;
      } else if (this.workerPoolConfig == null) {
         this.setWorkerThreadPoolConfig(strategyConfig);
      }

      notifyProbesConfigChanged(this);
   }

   public MemoryManager getMemoryManager() {
      return this.memoryManager;
   }

   public void setMemoryManager(MemoryManager memoryManager) {
      this.memoryManager = memoryManager;
      notifyProbesConfigChanged(this);
   }

   public ExecutorService getWorkerThreadPool() {
      return this.workerThreadPool;
   }

   public ExecutorService getKernelThreadPool() {
      return this.kernelPool;
   }

   public void setKernelThreadPool(ExecutorService kernelPool) {
      this.kernelPool = kernelPool;
   }

   public void setKernelThreadPoolConfig(ThreadPoolConfig kernelPoolConfig) {
      if (this.isStopped()) {
         this.kernelPoolConfig = kernelPoolConfig;
      }

   }

   public void setWorkerThreadPoolConfig(ThreadPoolConfig workerPoolConfig) {
      if (this.isStopped()) {
         this.workerPoolConfig = workerPoolConfig;
      }

   }

   public ThreadPoolConfig getKernelThreadPoolConfig() {
      return this.isStopped() ? this.kernelPoolConfig : this.kernelPoolConfig.copy();
   }

   public ThreadPoolConfig getWorkerThreadPoolConfig() {
      return this.isStopped() ? this.workerPoolConfig : this.workerPoolConfig.copy();
   }

   public void setWorkerThreadPool(ExecutorService threadPool) {
      this.managedWorkerPool = false;
      if (threadPool instanceof MonitoringAware && this.threadPoolMonitoringConfig.hasProbes()) {
         ((MonitoringAware)threadPool).getMonitoringConfig().addProbes(this.threadPoolMonitoringConfig.getProbes());
      }

      this.setWorkerThreadPool0(threadPool);
   }

   protected void setWorkerThreadPool0(ExecutorService threadPool) {
      this.workerThreadPool = threadPool;
      notifyProbesConfigChanged(this);
   }

   protected void setKernelPool0(ExecutorService kernelPool) {
      this.kernelPool = kernelPool;
   }

   public AttributeBuilder getAttributeBuilder() {
      return this.attributeBuilder;
   }

   public void setAttributeBuilder(AttributeBuilder attributeBuilder) {
      this.attributeBuilder = attributeBuilder;
      notifyProbesConfigChanged(this);
   }

   protected abstract void closeConnection(Connection var1) throws IOException;

   public MonitoringConfig getConnectionMonitoringConfig() {
      return this.connectionMonitoringConfig;
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.transportMonitoringConfig;
   }

   public MonitoringConfig getThreadPoolMonitoringConfig() {
      return this.threadPoolMonitoringConfig;
   }

   public long getReadTimeout(TimeUnit timeUnit) {
      return this.readTimeout <= 0L ? -1L : timeUnit.convert(this.readTimeout, TimeUnit.MILLISECONDS);
   }

   public void setReadTimeout(long timeout, TimeUnit timeUnit) {
      if (timeout <= 0L) {
         this.readTimeout = -1L;
      } else {
         this.readTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      }

   }

   public long getWriteTimeout(TimeUnit timeUnit) {
      return this.writeTimeout <= 0L ? -1L : timeUnit.convert(this.writeTimeout, TimeUnit.MILLISECONDS);
   }

   public void setWriteTimeout(long timeout, TimeUnit timeUnit) {
      if (timeout <= 0L) {
         this.writeTimeout = -1L;
      } else {
         this.writeTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      }

   }

   protected static void notifyProbesBeforeStart(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onBeforeStartEvent(transport);
         }
      }

   }

   protected static void notifyProbesBeforeStop(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onBeforeStopEvent(transport);
         }
      }

   }

   protected static void notifyProbesStop(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onStopEvent(transport);
         }
      }

   }

   protected static void notifyProbesBeforePause(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onBeforePauseEvent(transport);
         }
      }

   }

   protected static void notifyProbesPause(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onPauseEvent(transport);
         }
      }

   }

   protected static void notifyProbesBeforeResume(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onBeforeStartEvent(transport);
         }
      }

   }

   protected static void notifyProbesConfigChanged(AbstractTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onConfigChangeEvent(transport);
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public void stop() throws IOException {
      this.shutdownNow();
   }

   protected abstract Object createJmxManagementObject();
}
