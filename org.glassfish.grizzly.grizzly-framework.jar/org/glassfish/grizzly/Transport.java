package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.StateHolder;

public interface Transport extends MonitoringAware {
   int DEFAULT_READ_BUFFER_SIZE = -1;
   int DEFAULT_WRITE_BUFFER_SIZE = -1;
   int DEFAULT_READ_TIMEOUT = 30;
   int DEFAULT_WRITE_TIMEOUT = 30;

   String getName();

   void setName(String var1);

   StateHolder getState();

   boolean isBlocking();

   void configureBlocking(boolean var1);

   void configureStandalone(boolean var1);

   boolean isStandalone();

   Processor obtainProcessor(IOEvent var1, Connection var2);

   Processor getProcessor();

   void setProcessor(Processor var1);

   ProcessorSelector getProcessorSelector();

   void setProcessorSelector(ProcessorSelector var1);

   MemoryManager getMemoryManager();

   void setMemoryManager(MemoryManager var1);

   IOStrategy getIOStrategy();

   void setIOStrategy(IOStrategy var1);

   int getReadBufferSize();

   void setReadBufferSize(int var1);

   int getWriteBufferSize();

   void setWriteBufferSize(int var1);

   ExecutorService getWorkerThreadPool();

   ExecutorService getKernelThreadPool();

   void setWorkerThreadPool(ExecutorService var1);

   void setKernelThreadPool(ExecutorService var1);

   void setKernelThreadPoolConfig(ThreadPoolConfig var1);

   void setWorkerThreadPoolConfig(ThreadPoolConfig var1);

   ThreadPoolConfig getKernelThreadPoolConfig();

   ThreadPoolConfig getWorkerThreadPoolConfig();

   AttributeBuilder getAttributeBuilder();

   void setAttributeBuilder(AttributeBuilder var1);

   void start() throws IOException;

   /** @deprecated */
   @Deprecated
   void stop() throws IOException;

   GrizzlyFuture shutdown();

   GrizzlyFuture shutdown(long var1, TimeUnit var3);

   void shutdownNow() throws IOException;

   boolean addShutdownListener(GracefulShutdownListener var1);

   void pause();

   void resume();

   void fireIOEvent(IOEvent var1, Connection var2, IOEventLifeCycleListener var3);

   boolean isStopped();

   boolean isPaused();

   Reader getReader(Connection var1);

   Reader getReader(boolean var1);

   Writer getWriter(Connection var1);

   Writer getWriter(boolean var1);

   MonitoringConfig getConnectionMonitoringConfig();

   MonitoringConfig getThreadPoolMonitoringConfig();

   MonitoringConfig getMonitoringConfig();

   void notifyTransportError(Throwable var1);

   long getReadTimeout(TimeUnit var1);

   void setReadTimeout(long var1, TimeUnit var3);

   long getWriteTimeout(TimeUnit var1);

   void setWriteTimeout(long var1, TimeUnit var3);

   public static enum State {
      STARTING,
      STARTED,
      PAUSING,
      PAUSED,
      STOPPING,
      STOPPED;
   }
}
