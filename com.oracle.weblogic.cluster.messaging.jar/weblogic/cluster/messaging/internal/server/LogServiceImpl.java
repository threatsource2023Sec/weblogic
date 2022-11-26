package weblogic.cluster.messaging.internal.server;

import weblogic.cluster.messaging.internal.LogService;

public class LogServiceImpl implements LogService {
   public static LogServiceImpl getInstance() {
      return LogServiceImpl.Factory.THE_ONE;
   }

   public void debug(String s) {
      DebugLogger.debug(s);
   }

   private static final class Factory {
      static final LogServiceImpl THE_ONE = new LogServiceImpl();
   }
}
