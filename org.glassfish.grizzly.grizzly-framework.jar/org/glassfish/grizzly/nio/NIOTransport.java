package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransport;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.SocketBinder;
import org.glassfish.grizzly.SocketConnectorHandler;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.TransportProbe;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorPool;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.AbstractThreadPool;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.Futures;

public abstract class NIOTransport extends AbstractTransport implements SocketBinder, SocketConnectorHandler, TemporarySelectorsEnabledTransport, AsyncQueueEnabledTransport {
   public static final int DEFAULT_SERVER_SOCKET_SO_TIMEOUT = 0;
   public static final boolean DEFAULT_REUSE_ADDRESS = true;
   public static final int DEFAULT_CLIENT_SOCKET_SO_TIMEOUT = 0;
   public static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
   public static final int DEFAULT_SELECTOR_RUNNER_COUNT = -1;
   public static final boolean DEFAULT_OPTIMIZED_FOR_MULTIPLEXING = false;
   private static final Logger LOGGER = Grizzly.logger(NIOTransport.class);
   protected SelectorHandler selectorHandler;
   protected SelectionKeyHandler selectionKeyHandler;
   int serverSocketSoTimeout = 0;
   boolean reuseAddress = true;
   int clientSocketSoTimeout = 0;
   int connectionTimeout = 30000;
   protected ChannelConfigurator channelConfigurator;
   private int selectorRunnersCount = -1;
   private boolean optimizedForMultiplexing = false;
   protected SelectorRunner[] selectorRunners;
   protected NIOChannelDistributor nioChannelDistributor;
   protected SelectorProvider selectorProvider = SelectorProvider.provider();
   protected final TemporarySelectorIO temporarySelectorIO = this.createTemporarySelectorIO();
   protected Set shutdownListeners;
   protected FutureImpl shutdownFuture;
   protected ExecutorService shutdownService;

   public NIOTransport(String name) {
      super(name);
   }

   public abstract void unbindAll();

   public boolean addShutdownListener(GracefulShutdownListener shutdownListener) {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      boolean var4;
      try {
         Transport.State stateNow = (Transport.State)this.state.getState();
         if (stateNow == Transport.State.STOPPING && stateNow == Transport.State.STOPPED) {
            var4 = false;
            return var4;
         }

         if (this.shutdownListeners == null) {
            this.shutdownListeners = new HashSet();
         }

         var4 = this.shutdownListeners.add(shutdownListener);
      } finally {
         lock.unlock();
      }

      return var4;
   }

   public TemporarySelectorIO getTemporarySelectorIO() {
      return this.temporarySelectorIO;
   }

   public SelectionKeyHandler getSelectionKeyHandler() {
      return this.selectionKeyHandler;
   }

   public void setSelectionKeyHandler(SelectionKeyHandler selectionKeyHandler) {
      this.selectionKeyHandler = selectionKeyHandler;
      notifyProbesConfigChanged(this);
   }

   public SelectorHandler getSelectorHandler() {
      return this.selectorHandler;
   }

   public void setSelectorHandler(SelectorHandler selectorHandler) {
      this.selectorHandler = selectorHandler;
      notifyProbesConfigChanged(this);
   }

   public ChannelConfigurator getChannelConfigurator() {
      return this.channelConfigurator;
   }

   public void setChannelConfigurator(ChannelConfigurator channelConfigurator) {
      this.channelConfigurator = channelConfigurator;
      notifyProbesConfigChanged(this);
   }

   public int getSelectorRunnersCount() {
      if (this.selectorRunnersCount <= 0) {
         this.selectorRunnersCount = this.getDefaultSelectorRunnersCount();
      }

      return this.selectorRunnersCount;
   }

   public void setSelectorRunnersCount(int selectorRunnersCount) {
      if (selectorRunnersCount > 0) {
         this.selectorRunnersCount = selectorRunnersCount;
         if (this.kernelPoolConfig != null && this.kernelPoolConfig.getMaxPoolSize() < selectorRunnersCount) {
            this.kernelPoolConfig.setCorePoolSize(selectorRunnersCount).setMaxPoolSize(selectorRunnersCount);
         }

         notifyProbesConfigChanged(this);
      }

   }

   public SelectorProvider getSelectorProvider() {
      return this.selectorProvider;
   }

   public void setSelectorProvider(SelectorProvider selectorProvider) {
      this.selectorProvider = selectorProvider != null ? selectorProvider : SelectorProvider.provider();
   }

   public boolean isOptimizedForMultiplexing() {
      return this.optimizedForMultiplexing;
   }

   public void setOptimizedForMultiplexing(boolean optimizedForMultiplexing) {
      this.optimizedForMultiplexing = optimizedForMultiplexing;
      this.getAsyncQueueIO().getWriter().setAllowDirectWrite(!optimizedForMultiplexing);
   }

   protected synchronized void startSelectorRunners() throws IOException {
      this.selectorRunners = new SelectorRunner[this.selectorRunnersCount];

      for(int i = 0; i < this.selectorRunnersCount; ++i) {
         SelectorRunner runner = SelectorRunner.create(this);
         runner.start();
         this.selectorRunners[i] = runner;
      }

   }

   protected synchronized void stopSelectorRunners() {
      if (this.selectorRunners != null) {
         for(int i = 0; i < this.selectorRunners.length; ++i) {
            SelectorRunner runner = this.selectorRunners[i];
            if (runner != null) {
               runner.stop();
               this.selectorRunners[i] = null;
            }
         }

         this.selectorRunners = null;
      }
   }

   public NIOChannelDistributor getNIOChannelDistributor() {
      return this.nioChannelDistributor;
   }

   public void setNIOChannelDistributor(NIOChannelDistributor nioChannelDistributor) {
      this.nioChannelDistributor = nioChannelDistributor;
      notifyProbesConfigChanged(this);
   }

   public void notifyTransportError(Throwable error) {
      notifyProbesError(this, error);
   }

   protected SelectorRunner[] getSelectorRunners() {
      return this.selectorRunners;
   }

   protected static void notifyProbesError(NIOTransport transport, Throwable error) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TransportProbe probe = var3[var5];
            probe.onErrorEvent(transport, error);
         }
      }

   }

   protected static void notifyProbesStart(NIOTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onStartEvent(transport);
         }
      }

   }

   protected static void notifyProbesStop(NIOTransport transport) {
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

   protected static void notifyProbesPause(NIOTransport transport) {
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

   protected static void notifyProbesResume(NIOTransport transport) {
      TransportProbe[] probes = (TransportProbe[])transport.transportMonitoringConfig.getProbesUnsafe();
      if (probes != null) {
         TransportProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TransportProbe probe = var2[var4];
            probe.onResumeEvent(transport);
         }
      }

   }

   public void start() throws IOException {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         Transport.State currentState = (Transport.State)this.state.getState();
         if (currentState == Transport.State.STOPPED) {
            this.state.setState(Transport.State.STARTING);
            notifyProbesBeforeStart(this);
            if (this.selectorProvider == null) {
               this.selectorProvider = SelectorProvider.provider();
            }

            if (this.selectorHandler == null) {
               this.selectorHandler = new DefaultSelectorHandler();
            }

            if (this.selectionKeyHandler == null) {
               this.selectionKeyHandler = new DefaultSelectionKeyHandler();
            }

            if (this.processor == null && this.processorSelector == null) {
               this.processor = new StandaloneProcessor();
            }

            int selectorRunnersCnt = this.getSelectorRunnersCount();
            if (this.nioChannelDistributor == null) {
               this.nioChannelDistributor = new RoundRobinConnectionDistributor(this);
            }

            if (this.kernelPool == null) {
               if (this.kernelPoolConfig == null) {
                  this.kernelPoolConfig = ThreadPoolConfig.defaultConfig().setCorePoolSize(selectorRunnersCnt).setMaxPoolSize(selectorRunnersCnt).setPoolName("grizzly-nio-kernel");
               } else if (this.kernelPoolConfig.getMaxPoolSize() < selectorRunnersCnt) {
                  LOGGER.log(Level.INFO, "Adjusting kernel thread pool to max size {0} to handle configured number of SelectorRunners", selectorRunnersCnt);
                  this.kernelPoolConfig.setCorePoolSize(selectorRunnersCnt).setMaxPoolSize(selectorRunnersCnt);
               }

               this.kernelPoolConfig.setMemoryManager(this.memoryManager);
               this.setKernelPool0(GrizzlyExecutorService.createInstance(this.kernelPoolConfig));
            }

            if (this.workerThreadPool == null && this.workerPoolConfig != null) {
               if (this.getThreadPoolMonitoringConfig().hasProbes()) {
                  this.workerPoolConfig.getInitialMonitoringConfig().addProbes(this.getThreadPoolMonitoringConfig().getProbes());
               }

               this.workerPoolConfig.setMemoryManager(this.memoryManager);
               this.setWorkerThreadPool0(GrizzlyExecutorService.createInstance(this.workerPoolConfig));
            }

            int selectorPoolSize = 32;
            if (this.workerThreadPool instanceof AbstractThreadPool) {
               if (this.strategy instanceof SameThreadIOStrategy) {
                  selectorPoolSize = selectorRunnersCnt;
               } else {
                  selectorPoolSize = Math.min(((AbstractThreadPool)this.workerThreadPool).getConfig().getMaxPoolSize(), selectorPoolSize);
               }
            }

            if (this.strategy == null) {
               this.strategy = WorkerThreadIOStrategy.getInstance();
            }

            this.temporarySelectorIO.setSelectorPool(new TemporarySelectorPool(this.selectorProvider, selectorPoolSize));
            this.startSelectorRunners();
            this.listen();
            this.state.setState(Transport.State.STARTED);
            notifyProbesStart(this);
            return;
         }

         LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_STOP_STATE_EXCEPTION());
      } finally {
         lock.unlock();
      }

   }

   public GrizzlyFuture shutdown() {
      return this.shutdown(-1L, TimeUnit.MILLISECONDS);
   }

   public GrizzlyFuture shutdown(long gracePeriod, TimeUnit timeUnit) {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      FutureImpl var6;
      try {
         Transport.State stateNow = (Transport.State)this.state.getState();
         if (stateNow != Transport.State.STOPPING) {
            if (stateNow == Transport.State.STOPPED) {
               GrizzlyFuture var12 = Futures.createReadyFuture((Object)this);
               return var12;
            }

            if (stateNow == Transport.State.PAUSED) {
               this.resume();
            }

            this.state.setState(Transport.State.STOPPING);
            this.unbindAll();
            Object resultFuture;
            if (this.shutdownListeners != null && !this.shutdownListeners.isEmpty()) {
               this.shutdownFuture = Futures.createSafeFuture();
               this.shutdownService = this.createShutdownExecutorService();
               this.shutdownService.execute(new GracefulShutdownRunner(this, this.shutdownListeners, this.shutdownService, gracePeriod, timeUnit));
               this.shutdownListeners = null;
               resultFuture = this.shutdownFuture;
            } else {
               this.finalizeShutdown();
               resultFuture = Futures.createReadyFuture((Object)this);
            }

            Object var7 = resultFuture;
            return (GrizzlyFuture)var7;
         }

         var6 = this.shutdownFuture;
      } finally {
         lock.unlock();
      }

      return var6;
   }

   public void shutdownNow() throws IOException {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         Transport.State stateNow = (Transport.State)this.state.getState();
         if (stateNow != Transport.State.STOPPED) {
            if (stateNow == Transport.State.PAUSED) {
               this.resume();
            }

            this.state.setState(Transport.State.STOPPING);
            this.unbindAll();
            this.finalizeShutdown();
            return;
         }
      } finally {
         lock.unlock();
      }

   }

   protected abstract void closeConnection(Connection var1) throws IOException;

   protected abstract TemporarySelectorIO createTemporarySelectorIO();

   protected abstract void listen();

   protected int getDefaultSelectorRunnersCount() {
      return Runtime.getRuntime().availableProcessors();
   }

   protected void finalizeShutdown() {
      if (this.shutdownService != null && !this.shutdownService.isShutdown()) {
         boolean isInterrupted = Thread.currentThread().isInterrupted();
         this.shutdownService.shutdownNow();
         this.shutdownService = null;
         if (!isInterrupted) {
            Thread.interrupted();
         }
      }

      notifyProbesBeforeStop(this);
      this.stopSelectorRunners();
      if (this.workerThreadPool != null && this.managedWorkerPool) {
         this.workerThreadPool.shutdown();
         this.workerThreadPool = null;
      }

      if (this.kernelPool != null) {
         this.kernelPool.shutdownNow();
         this.kernelPool = null;
      }

      this.state.setState(Transport.State.STOPPED);
      notifyProbesStop(this);
      if (this.shutdownFuture != null) {
         this.shutdownFuture.result(this);
         this.shutdownFuture = null;
      }

   }

   public void pause() {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         if (this.state.getState() != Transport.State.STARTED) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_START_STATE_EXCEPTION());
            return;
         }

         this.state.setState(Transport.State.PAUSING);
         notifyProbesBeforePause(this);
         this.state.setState(Transport.State.PAUSED);
         notifyProbesPause(this);
      } finally {
         lock.unlock();
      }

   }

   public void resume() {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         if (this.state.getState() == Transport.State.PAUSED) {
            this.state.setState(Transport.State.STARTING);
            notifyProbesBeforeResume(this);
            this.state.setState(Transport.State.STARTED);
            notifyProbesResume(this);
            return;
         }

         LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_PAUSE_STATE_EXCEPTION());
      } finally {
         lock.unlock();
      }

   }

   protected void configureNIOConnection(NIOConnection connection) {
      connection.configureBlocking(this.isBlocking);
      connection.configureStandalone(this.isStandalone);
      connection.setProcessor(this.processor);
      connection.setProcessorSelector(this.processorSelector);
      connection.setReadTimeout(this.readTimeout, TimeUnit.MILLISECONDS);
      connection.setWriteTimeout(this.writeTimeout, TimeUnit.MILLISECONDS);
      if (this.connectionMonitoringConfig.hasProbes()) {
         connection.setMonitoringProbes((ConnectionProbe[])this.connectionMonitoringConfig.getProbes());
      }

   }

   public boolean isReuseAddress() {
      return this.reuseAddress;
   }

   public void setReuseAddress(boolean reuseAddress) {
      this.reuseAddress = reuseAddress;
      notifyProbesConfigChanged(this);
   }

   public int getClientSocketSoTimeout() {
      return this.clientSocketSoTimeout;
   }

   public void setClientSocketSoTimeout(int socketTimeout) {
      if (socketTimeout < 0) {
         throw new IllegalArgumentException("socketTimeout can't be negative value");
      } else {
         this.clientSocketSoTimeout = socketTimeout;
         notifyProbesConfigChanged(this);
      }
   }

   public int getConnectionTimeout() {
      return this.connectionTimeout;
   }

   public void setConnectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      notifyProbesConfigChanged(this);
   }

   public int getServerSocketSoTimeout() {
      return this.serverSocketSoTimeout;
   }

   public void setServerSocketSoTimeout(int serverSocketSoTimeout) {
      if (serverSocketSoTimeout < 0) {
         throw new IllegalArgumentException("socketTimeout can't be negative value");
      } else {
         this.serverSocketSoTimeout = serverSocketSoTimeout;
         notifyProbesConfigChanged(this);
      }
   }

   protected ExecutorService createShutdownExecutorService() {
      final String baseThreadIdentifier = this.getName() + '[' + Integer.toHexString(this.hashCode()) + "]-Shutdown-Thread";
      ThreadFactory factory = new ThreadFactory() {
         private int counter;

         public Thread newThread(Runnable r) {
            Thread t = new Thread(r, baseThreadIdentifier + "(" + this.counter++ + ')');
            t.setDaemon(true);
            return t;
         }
      };
      return Executors.newFixedThreadPool(2, factory);
   }
}
