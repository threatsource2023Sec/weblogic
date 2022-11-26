package org.glassfish.grizzly.strategies;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.SelectorRunner;

public final class LeaderFollowerNIOStrategy extends AbstractIOStrategy {
   private static final LeaderFollowerNIOStrategy INSTANCE = new LeaderFollowerNIOStrategy();
   private static final Logger logger = Grizzly.logger(LeaderFollowerNIOStrategy.class);

   private LeaderFollowerNIOStrategy() {
   }

   public static LeaderFollowerNIOStrategy getInstance() {
      return INSTANCE;
   }

   public boolean executeIoEvent(Connection connection, IOEvent ioEvent, boolean isIoEventEnabled) throws IOException {
      NIOConnection nioConnection = (NIOConnection)connection;
      IOEventLifeCycleListener listener = null;
      if (isReadWrite(ioEvent)) {
         if (isIoEventEnabled) {
            connection.disableIOEvent(ioEvent);
         }

         listener = ENABLE_INTEREST_LIFECYCLE_LISTENER;
      }

      Executor threadPool = this.getThreadPoolFor(connection, ioEvent);
      if (threadPool != null) {
         SelectorRunner runner = nioConnection.getSelectorRunner();
         runner.postpone();
         threadPool.execute(runner);
         fireIOEvent(connection, ioEvent, listener, logger);
         return false;
      } else {
         fireIOEvent(connection, ioEvent, listener, logger);
         return true;
      }
   }
}
