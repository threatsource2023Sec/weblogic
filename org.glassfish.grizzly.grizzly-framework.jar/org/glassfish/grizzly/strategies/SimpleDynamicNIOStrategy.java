package org.glassfish.grizzly.strategies;

import java.io.IOException;
import java.util.concurrent.Executor;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOStrategy;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public final class SimpleDynamicNIOStrategy implements IOStrategy {
   private static final SimpleDynamicNIOStrategy INSTANCE = new SimpleDynamicNIOStrategy();
   private final SameThreadIOStrategy sameThreadStrategy = SameThreadIOStrategy.getInstance();
   private final WorkerThreadIOStrategy workerThreadStrategy = WorkerThreadIOStrategy.getInstance();
   private static final int WORKER_THREAD_THRESHOLD = 1;

   private SimpleDynamicNIOStrategy() {
   }

   public static SimpleDynamicNIOStrategy getInstance() {
      return INSTANCE;
   }

   public boolean executeIoEvent(Connection connection, IOEvent ioEvent) throws IOException {
      return this.executeIoEvent(connection, ioEvent, true);
   }

   public Executor getThreadPoolFor(Connection connection, IOEvent ioEvent) {
      int lastSelectedKeysCount = getLastSelectedKeysCount(connection);
      return lastSelectedKeysCount <= 1 ? this.sameThreadStrategy.getThreadPoolFor(connection, ioEvent) : this.workerThreadStrategy.getThreadPoolFor(connection, ioEvent);
   }

   public boolean executeIoEvent(Connection connection, IOEvent ioEvent, boolean isIoEventEnabled) throws IOException {
      int lastSelectedKeysCount = getLastSelectedKeysCount(connection);
      return lastSelectedKeysCount <= 1 ? this.sameThreadStrategy.executeIoEvent(connection, ioEvent, isIoEventEnabled) : this.workerThreadStrategy.executeIoEvent(connection, ioEvent, isIoEventEnabled);
   }

   public ThreadPoolConfig createDefaultWorkerPoolConfig(Transport transport) {
      ThreadPoolConfig config = ThreadPoolConfig.defaultConfig().copy();
      int selectorRunnerCount = ((NIOTransport)transport).getSelectorRunnersCount();
      config.setCorePoolSize(selectorRunnerCount * 2);
      config.setMaxPoolSize(selectorRunnerCount * 2);
      config.setMemoryManager(transport.getMemoryManager());
      return config;
   }

   private static int getLastSelectedKeysCount(Connection c) {
      return ((NIOConnection)c).getSelectorRunner().getLastSelectedKeysCount();
   }
}
