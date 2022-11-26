package org.glassfish.grizzly.strategies;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.IOStrategy;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.asyncqueue.AsyncQueue;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public abstract class AbstractIOStrategy implements IOStrategy {
   private static final EnumSet READ_WRITE_EVENT_SET;
   private static final EnumSet WORKER_THREAD_EVENT_SET;
   protected static final IOEventLifeCycleListener ENABLE_INTEREST_LIFECYCLE_LISTENER;

   public ThreadPoolConfig createDefaultWorkerPoolConfig(Transport transport) {
      ThreadPoolConfig config = ThreadPoolConfig.defaultConfig().copy();
      int coresCount = Runtime.getRuntime().availableProcessors();
      config.setPoolName("Grizzly-worker");
      config.setCorePoolSize(coresCount * 2);
      config.setMaxPoolSize(coresCount * 2);
      config.setMemoryManager(transport.getMemoryManager());
      return config;
   }

   public final boolean executeIoEvent(Connection connection, IOEvent ioEvent) throws IOException {
      return this.executeIoEvent(connection, ioEvent, true);
   }

   public Executor getThreadPoolFor(Connection connection, IOEvent ioEvent) {
      return WORKER_THREAD_EVENT_SET.contains(ioEvent) ? connection.getTransport().getWorkerThreadPool() : null;
   }

   protected static boolean isReadWrite(IOEvent ioEvent) {
      return READ_WRITE_EVENT_SET.contains(ioEvent);
   }

   protected static void fireIOEvent(Connection connection, IOEvent ioEvent, IOEventLifeCycleListener listener, Logger logger) {
      try {
         connection.getTransport().fireIOEvent(ioEvent, connection, listener);
      } catch (Exception var5) {
         logger.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_IOSTRATEGY_UNCAUGHT_EXCEPTION(), var5);
         connection.closeSilently();
      }

   }

   static {
      READ_WRITE_EVENT_SET = EnumSet.of(IOEvent.READ, IOEvent.WRITE);
      WORKER_THREAD_EVENT_SET = EnumSet.of(IOEvent.READ, IOEvent.CLOSED);
      ENABLE_INTEREST_LIFECYCLE_LISTENER = new EnableInterestLifeCycleListener();
   }

   private static final class EnableInterestLifeCycleListener extends IOEventLifeCycleListener.Adapter {
      private EnableInterestLifeCycleListener() {
      }

      public void onReregister(Context context) throws IOException {
         this.onComplete(context, (Object)null);
      }

      public void onComplete(Context context, Object data) throws IOException {
         IOEvent ioEvent = context.getIoEvent();
         Connection connection = context.getConnection();
         if (AsyncQueue.EXPECTING_MORE_OPTION.equals(data)) {
            connection.simulateIOEvent(ioEvent);
         } else {
            connection.enableIOEvent(ioEvent);
         }

      }

      // $FF: synthetic method
      EnableInterestLifeCycleListener(Object x0) {
         this();
      }
   }
}
