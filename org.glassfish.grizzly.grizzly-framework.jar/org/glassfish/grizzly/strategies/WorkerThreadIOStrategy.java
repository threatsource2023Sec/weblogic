package org.glassfish.grizzly.strategies;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;

public final class WorkerThreadIOStrategy extends AbstractIOStrategy {
   private static final WorkerThreadIOStrategy INSTANCE = new WorkerThreadIOStrategy();
   private static final Logger logger = Grizzly.logger(WorkerThreadIOStrategy.class);

   private WorkerThreadIOStrategy() {
   }

   public static WorkerThreadIOStrategy getInstance() {
      return INSTANCE;
   }

   public boolean executeIoEvent(Connection connection, IOEvent ioEvent, boolean isIoEventEnabled) throws IOException {
      boolean isReadOrWriteEvent = isReadWrite(ioEvent);
      IOEventLifeCycleListener listener;
      if (isReadOrWriteEvent) {
         if (isIoEventEnabled) {
            connection.disableIOEvent(ioEvent);
         }

         listener = ENABLE_INTEREST_LIFECYCLE_LISTENER;
      } else {
         listener = null;
      }

      Executor threadPool = this.getThreadPoolFor(connection, ioEvent);
      if (threadPool != null) {
         threadPool.execute(new WorkerThreadRunnable(connection, ioEvent, listener));
      } else {
         run0(connection, ioEvent, listener);
      }

      return true;
   }

   private static void run0(Connection connection, IOEvent ioEvent, IOEventLifeCycleListener lifeCycleListener) {
      fireIOEvent(connection, ioEvent, lifeCycleListener, logger);
   }

   private static final class WorkerThreadRunnable implements Runnable {
      final Connection connection;
      final IOEvent ioEvent;
      final IOEventLifeCycleListener lifeCycleListener;

      private WorkerThreadRunnable(Connection connection, IOEvent ioEvent, IOEventLifeCycleListener lifeCycleListener) {
         this.connection = connection;
         this.ioEvent = ioEvent;
         this.lifeCycleListener = lifeCycleListener;
      }

      public void run() {
         WorkerThreadIOStrategy.run0(this.connection, this.ioEvent, this.lifeCycleListener);
      }

      // $FF: synthetic method
      WorkerThreadRunnable(Connection x0, IOEvent x1, IOEventLifeCycleListener x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
